FROM gradle:7-jdk11 as builder

ENV APP_HOME=/home/gradle/project
WORKDIR $APP_HOME

COPY . .
RUN gradle clean build

FROM openjdk:11
RUN addgroup -gid 1000 spring && adduser --uid 1000 --gid 1000 spring

USER spring:spring

ENV ARTIFACT_NAME=connector-0.0.1-SNAPSHOT.jar
ENV APP_HOME=/home/gradle/project

WORKDIR $APP_HOME
COPY --from=builder $APP_HOME/build/libs/$ARTIFACT_NAME .

ENV SERVER_PORT=8880 APPLICATION_DIR_TMP=/tmp
EXPOSE 8008 8880

CMD java -Duser.timezone=UTC -Djava.security.egd=file:/dev/./urandom -jar $ARTIFACT_NAME
