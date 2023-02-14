@echo off

echo Generating Poshi Standalone files...

curl -LSs -o gradlew https://raw.githubusercontent.com/gradle/gradle/ac2a884/gradlew
curl -LSs -o gradlew.bat https://raw.githubusercontent.com/gradle/gradle/ac2a884/gradlew.bat

mkdir gradle\wrapper

curl -LSs -o gradle\wrapper\gradle-wrapper.jar https://github.com/liferay/liferay-portal/raw/master/gradle/wrapper/gradle-wrapper.jar

echo distributionBase=GRADLE_USER_HOME> gradle\wrapper\gradle-wrapper.properties
echo distributionPath=wrapper/dists>> gradle\wrapper\gradle-wrapper.properties
echo distributionUrl=https\://github.com/liferay/liferay-binaries-cache-2020/raw/master/gradle-6.6.1.LIFERAY-PATCHED-1-bin.zip>> gradle\wrapper\gradle-wrapper.properties
echo zipStoreBase=GRADLE_USER_HOME>> gradle\wrapper\gradle-wrapper.properties
echo zipStorePath=wrapper/dists>> gradle/wrapper/gradle-wrapper.properties

curl -LSs -o build.gradle https://raw.githubusercontent.com/liferay/liferay-portal/master/modules/test/poshi/poshi-standalone/build.gradle
curl -LSs -o poshi.properties https://raw.githubusercontent.com/liferay/liferay-portal/master/modules/test/poshi/poshi-standalone/poshi.properties

type NUL >> poshi-ext.properties
type NUL >> settings.gradle

echo Generated Poshi Standalone files.