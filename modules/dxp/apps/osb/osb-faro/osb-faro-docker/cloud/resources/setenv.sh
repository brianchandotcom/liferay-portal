#!/bin/sh

if [ -z "$CATALINA_OPTS" ]
then
    CLASSPATH=$CLASSPATH:$CATALINA_BASE/lib/ecs-logging-core-1.6.0.jar:$CATALINA_BASE/lib/jul-ecs-formatter-1.6.0.jar
fi