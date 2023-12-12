@echo off

pushd "%~dp0"

path %PATH%;%JAVA_HOME%\bin

java -jar com.liferay.portal.tools.db.partition.virtual.instance.migrator.extractor.jar %*

popd

@echo on