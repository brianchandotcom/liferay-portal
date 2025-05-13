FROM liferay/jdk21:latest

USER root

ARG LABEL_BUILD_DATE
ARG LABEL_VCS_REF

ENV FARO_DEFAULT_WE_DEPLOY_KEY=osbasah.lfr.cloud \
	FARO_DEMO_CREATOR_METHOD=snapshot \
	FARO_PROJECT_ID=FARO-DEV-liferay \
	FARO_URL=http://localhost:8080 \
	JAVA_OPTS="-Djava.rmi.server.hostname=0.0.0.0 -Xdebug -Xrunjdwp:transport=dt_socket,address=*:8000,server=y,suspend=n -XX:+IgnoreUnrecognizedVMOptions" \
	LABEL_BUILD_DATE=${LABEL_BUILD_DATE} \
	LABEL_VCS_REF=${LABEL_VCS_REF} \
	OSB_API_PORT=443 \
	OSB_API_PROTOCOL=https \
	OSB_API_URL=webserver-lrkoroneiki-uat.lfr.cloud \
	OSB_ASAH_BACKEND_URL=http://osbasahbackend:8080 \
	OSB_ASAH_PUBLISHER_URL=http://osbasahpublisher:8080

EXPOSE 8000 8080 11311

HEALTHCHECK --interval=3s --timeout=3s CMD curl -f http://localhost:8080 || exit 1

COPY ./bundles /opt/liferay/

RUN mkdir -p /opt/liferay/tomcat/lib/org/apache/catalina/util && \
    echo "server.info=" > /opt/liferay/tomcat/lib/org/apache/catalina/util/ServerInfo.properties

COPY ./common/resources/context.xml /opt/liferay/tomcat/conf
COPY ./common/resources/lib/mysql.jar /opt/liferay/tomcat/webapps/ROOT/WEB-INF/shielded-container-lib
COPY ./common/resources/portal-ext.properties /opt/liferay
COPY ./common/resources/portal-setup-wizard.properties /opt/liferay
COPY ./common/resources/rewrite.config /opt/liferay/tomcat/webapps/ROOT/WEB-INF
COPY ./common/resources/system-ext.properties /opt/liferay/tomcat/webapps/ROOT/WEB-INF/classes

# Configs

COPY ./common/resources/configs /opt/liferay/osgi/configs/

# Log4j

COPY ./common/resources/log4j /opt/liferay/osgi/log4j/

# WAR

COPY *osb-faro*.war /opt/liferay/osgi/modules/

# Modules

COPY *osb.faro*.jar /opt/liferay/osgi/modules/

ENTRYPOINT ["/opt/liferay/tomcat/bin/catalina.sh", "run"]

WORKDIR /opt/liferay