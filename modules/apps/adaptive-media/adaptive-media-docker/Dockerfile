FROM liferay/portal-tomcat-mysql:master-ae2014bf020d5042231e0ea754f3fb5914a5130b

MAINTAINER Sergio González <sergio.gonzalez@liferay.com>

RUN rm $LIFERAY_HOME/osgi/modules/com.liferay.adaptive.media*.jar

COPY *.jar $LIFERAY_HOME/osgi/modules/
COPY supervisord-tomcat.conf /etc/supervisor/conf.d/supervisord-tomcat.conf
COPY portal-ext.properties /liferay/portal-ext.properties