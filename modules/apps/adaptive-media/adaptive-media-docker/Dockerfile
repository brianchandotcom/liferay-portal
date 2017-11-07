FROM liferay/portal-tomcat-mysql:master-2089294ff076c896108e5cffcf0efe66b165afad
MAINTAINER Sergio González <sergio.gonzalez@liferay.com>

RUN rm $LIFERAY_HOME/osgi/modules/com.liferay.adaptive.media*.jar
COPY *.jar $LIFERAY_HOME/osgi/modules/
COPY supervisord-tomcat.conf /etc/supervisor/conf.d/supervisord-tomcat.conf
COPY portal-ext.properties /liferay/portal-ext.properties