FROM liferay/dxp:7.4.13.nightly

COPY --chown=liferay:liferay configs /home/liferay/configs
COPY --chown=liferay:liferay license /home/liferay/deploy
COPY --chown=liferay:liferay modules /home/liferay/modules
COPY --chown=liferay:liferay resources /home/liferay/resources
COPY --chown=liferay:liferay 100_liferay_image_setup.sh /usr/local/liferay/scripts/pre-configure/100_liferay_image_setup.sh

RUN cd /opt/liferay/tomcat/webapps/ROOT/WEB-INF/shielded-container-lib && \
    curl -O https://repo1.maven.org/maven2/org/apache/logging/log4j/log4j-layout-template-json/2.17.2/log4j-layout-template-json-2.17.2.jar && \
    curl -O https://repo1.maven.org/maven2/com/fasterxml/jackson/core/jackson-core/2.16.2/jackson-core-2.16.2.jar && \
    curl -O https://repo1.maven.org/maven2/com/fasterxml/jackson/core/jackson-annotations/2.16.2/jackson-annotations-2.16.2.jar
RUN cd /opt/liferay/tomcat/lib && \
    curl -O https://repo1.maven.org/maven2/co/elastic/logging/jul-ecs-formatter/1.6.0/jul-ecs-formatter-1.6.0.jar && \
    curl -O https://repo1.maven.org/maven2/co/elastic/logging/ecs-logging-core/1.6.0/ecs-logging-core-1.6.0.jar