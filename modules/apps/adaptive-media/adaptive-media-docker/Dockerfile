FROM liferay/portal-tomcat-mysql:master-7227093900666b12759b383063ac60a03956fa6c
MAINTAINER Sergio González <sergio.gonzalez@liferay.com>

COPY *.?ar $LIFERAY_HOME/osgi/modules/
COPY supervisord-tomcat.conf /etc/supervisor/conf.d/supervisord-tomcat.conf
COPY portal-ext.properties /liferay/portal-ext.properties