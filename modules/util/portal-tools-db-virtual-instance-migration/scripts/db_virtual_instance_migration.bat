@echo off

pushd "%~dp0"

path %PATH%;%JAVA_HOME%\bin

java -jar com.liferay.portal.tools.db.virtual.instance.migration.jar %*

popd

@echo on