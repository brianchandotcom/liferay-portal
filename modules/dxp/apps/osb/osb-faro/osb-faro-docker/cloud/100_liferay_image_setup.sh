#!/bin/bash

cp -R /home/liferay/configs/* ${LIFERAY_HOME}
cp -R /home/liferay/modules/* ${LIFERAY_HOME}/osgi/modules
cp -R /home/liferay/resources/context.xml $LIFERAY_HOME/tomcat/conf
cp -R /home/liferay/resources/log4j ${LIFERAY_HOME}/osgi/log4j
cp -R /home/liferay/resources/portal-log4j-ext.xml ${LIFERAY_HOME}/tomcat/webapps/ROOT/WEB-INF/classes/META-INF
cp -R /home/liferay/resources/rewrite.config ${LIFERAY_HOME}/tomcat/webapps/ROOT/WEB-INF
cp -R /home/liferay/resources/system-ext.properties ${LIFERAY_HOME}/tomcat/webapps/ROOT/WEB-INF/classes

rm ${LIFERAY_HOME}/deploy/trial-dxp-license-*.xml

sed -i '/<!-- APR library loader/i \ <Listener className=\"org.apache.catalina.security.SecurityListener\" checkedOsUsers=\"root\" minimumUmask=\"0007\" \/>\n' $LIFERAY_HOME/tomcat/conf/server.xml
sed -i '/<Connector port=\"8080\"/,/\/>/c \<Connector\n acceptorThreadCount=\"2\"\n bindOnInit=\"false\"\n connectionTimeout=\"20000\"\n enableLookups=\"false\"\n maxHttpHeaderSize=\"65536\"\n maxPostSize=\"4194304\"\n maxThreads=\"200\"\n minSpareThreads=\"50\"\n port=\"8080\"\n protocol=\"org.apache.coyote.http11.Http11Nio2Protocol\"\n server=\"\"\n URIEncoding=\"UTF-8\"\n \/>\n' $LIFERAY_HOME/tomcat/conf/server.xml
sed -i '/<\/Host>/i \ <Valve className=\"org.apache.catalina.valves.RemoteIpValve\" remoteIpHeader=\"X-Forwarded-For\" protocolHeader=\"X-Forwarded-Proto\" \/>' $LIFERAY_HOME/tomcat/conf/server.xml
sed -i s/"<Listener className=\"org.apache.catalina.core.AprLifecycleListener\" SSLEngine=\"on\" \/>"/"<Listener className=\"org.apache.catalina.core.AprLifecycleListener\" SSLEngine=\"off\" \/>"/ $LIFERAY_HOME/tomcat/conf/server.xml