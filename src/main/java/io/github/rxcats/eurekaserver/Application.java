package io.github.rxcats.eurekaserver;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.Bean;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableEurekaServer
@SpringBootApplication
public class Application {

    @Autowired
    DiscoveryClient discoveryClient;

    @Bean
    CommandLineRunner commandLineRunner() {
        return (args) -> {
            log.info("description:{}", discoveryClient.description());
            discoveryClient.getServices().forEach(service -> log.info("service:{}", service));

            List<ServiceInstance> instances = discoveryClient.getInstances("{service-id}");
            instances.forEach(si -> {
                log.info("serviceId:{}, host:{}, port:{}, ", si.getServiceId(), si.getHost(), si.getPort());
            });
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
