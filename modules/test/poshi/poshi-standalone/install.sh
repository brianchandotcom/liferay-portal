#!/bin/bash

curl -o build.gradle https://raw.githubusercontent.com/liferay/liferay-portal/master/modules/test/poshi/poshi-standalone/build.gradle
curl -o poshi.properties https://raw.githubusercontent.com/liferay/liferay-portal/master/modules/test/poshi/poshi-standalone/poshi.properties

touch poshi-ext.properties
touch settings.gradle