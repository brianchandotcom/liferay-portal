@echo off

pushd "%~dp0"

path %PATH%;%JAVA_HOME%\bin

java -jar com.liferay.portal.tools.db.partition.virtual.instance.migrator.jar %*

popd

@echo on
