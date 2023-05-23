FROM liferay/dxp:7.1.10-dxp-26-d4.1.0-20220613181358

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

RUN mkdir -p $LIFERAY_HOME/tomcat/lib/org/apache/catalina/util && \
    echo "server.info=" > $LIFERAY_HOME/tomcat/lib/org/apache/catalina/util/ServerInfo.properties

COPY --chown=liferay:liferay common/license $LIFERAY_HOME/deploy
COPY common/resources/portal-ext.properties $LIFERAY_HOME/
COPY common/resources/setenv.sh $LIFERAY_HOME/tomcat/bin/
COPY common/resources/system-ext.properties $LIFERAY_HOME/tomcat/webapps/ROOT/WEB-INF/classes
COPY common/resources/urlrewrite.xml $LIFERAY_HOME/tomcat/webapps/ROOT/WEB-INF/

# Configs

COPY common/resources/configs $LIFERAY_HOME/osgi/configs/

# Log4j

COPY common/resources/log4j $LIFERAY_HOME/osgi/log4j/

# WAR

COPY *.war $LIFERAY_HOME/osgi/war/

# Modules

COPY *.jar $LIFERAY_HOME/osgi/modules/