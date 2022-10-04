FROM adoptopenjdk/openjdk11:jre-11.0.14.1_1

ENV DEBUG_PORT=""
ENV TINI_VERSION v0.19.0

ADD https://github.com/krallin/tini/releases/download/${TINI_VERSION}/tini /tini

RUN chmod +x /tini

COPY *.jar app.jar
COPY ca.crt $JAVA_HOME/lib/security

RUN \
	cd $JAVA_HOME/lib/security \
	&& keytool -keystore cacerts -storepass changeit \
		-noprompt -trustcacerts -importcert -alias project-cert -file ca.crt

ENTRYPOINT /tini -- java -Xmx256m -agentlib:jdwp=transport=dt_socket,address=*:${DEBUG_PORT:-8001},server=y,suspend=n -jar /app.jar