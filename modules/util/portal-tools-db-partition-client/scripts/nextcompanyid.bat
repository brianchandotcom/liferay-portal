@echo off

pushd "%~dp0"

path %PATH%;%JAVA_HOME%\bin

java %* -cp "*" com.liferay.portal.tools.db.partition.client.PartitionClient action nextCompanyId

popd

@echo on