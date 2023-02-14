#!/bin/bash

echo "Generating Poshi Standalone files..."

curl -LSs -o gradlew https://raw.githubusercontent.com/gradle/gradle/ac2a884/gradlew
curl -LSs -o gradlew.bat https://raw.githubusercontent.com/gradle/gradle/ac2a884/gradlew.bat

chmod +x gradlew

mkdir -p gradle/wrapper

curl -LSs -o gradle/wrapper/gradle-wrapper.jar https://github.com/liferay/liferay-portal/raw/master/gradle/wrapper/gradle-wrapper.jar

echo -n "distributionBase=GRADLE_USER_HOME
distributionPath=wrapper/dists
distributionUrl=https\://github.com/liferay/liferay-binaries-cache-2020/raw/master/gradle-6.6.1.LIFERAY-PATCHED-1-bin.zip
zipStoreBase=GRADLE_USER_HOME
zipStorePath=wrapper/dists" > gradle/wrapper/gradle-wrapper.properties

curl -LSs -o build.gradle https://raw.githubusercontent.com/liferay/liferay-portal/master/modules/test/poshi/poshi-standalone/build.gradle
curl -LSs -o poshi.properties https://raw.githubusercontent.com/liferay/liferay-portal/master/modules/test/poshi/poshi-standalone/poshi.properties

touch poshi-ext.properties
touch settings.gradle

echo "Generated Poshi Standalone files."