FROM liferay/dxp:7.4.13.nightly

USER root

ARG LABEL_BUILD_DATE
ARG LABEL_VCS_REF

ENV CATALINA_OPTS="-server -XX:NewSize=1024m -XX:MaxNewSize=1024m -Xms4096m -Xmx4096m -XX:SurvivorRatio=6 -XX:TargetSurvivorRatio=90 -XX:MaxTenuringThreshold=15 -XX:+UseLargePages -XX:LargePageSizeInBytes=256m -XX:+UseParNewGC -XX:ParallelGCThreads=16 -XX:+UseConcMarkSweepGC -XX:+CMSParallelRemarkEnabled -XX:+CMSCompactWhenClearAllSoftRefs -XX:CMSInitiatingOccupancyFraction=85 -XX:+CMSScavengeBeforeRemark -XX:+UseLargePages -XX:LargePageSizeInBytes=256m -XX:+UseCompressedOops -XX:+DisableExplicitGC -XX:-UseBiasedLocking -XX:+BindGCTaskThreadsToCPUs -XX:+UseFastAccessorMethods -Dorg.apache.tomcat.util.buf.UDecoder.ALLOW_ENCODED_SLASH=true" \
	FARO_DEFAULT_WE_DEPLOY_KEY=osbasah.lfr.cloud \
	FARO_DEMO_CREATOR_METHOD=snapshot \
	FARO_PROJECT_ID=FARO-DEV-liferay \
	FARO_URL=http://localhost:8080 \
	JAVA_OPTS="-Djava.rmi.server.hostname=0.0.0.0 -Xdebug -Xrunjdwp:transport=dt_socket,address=*:8000,server=y,suspend=n -XX:+IgnoreUnrecognizedVMOptions --add-opens java.base/java.lang.invoke=ALL-UNNAMED" \
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

COPY ./common/resources/context.xml $LIFERAY_HOME/tomcat/conf
COPY ./common/resources/lib/mysql.jar $LIFERAY_HOME/tomcat/webapps/ROOT/WEB-INF/shielded-container-lib
COPY ./common/resources/portal-ext.properties $LIFERAY_HOME
COPY ./common/resources/rewrite.config $LIFERAY_HOME/tomcat/webapps/ROOT/WEB-INF
COPY ./common/resources/system-ext.properties $LIFERAY_HOME/tomcat/webapps/ROOT/WEB-INF/classes

# Configs

COPY ./common/resources/configs $LIFERAY_HOME/osgi/configs/

# Log4j

COPY ./common/resources/log4j $LIFERAY_HOME/osgi/log4j/

# WAR

COPY *osb-faro*.war $LIFERAY_HOME/osgi/modules/

# Modules

COPY *osb.faro*.jar $LIFERAY_HOME/osgi/modules/