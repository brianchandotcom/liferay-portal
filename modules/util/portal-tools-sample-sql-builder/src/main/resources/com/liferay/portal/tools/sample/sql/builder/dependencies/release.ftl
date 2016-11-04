<#assign releaseModels = dataFactory.newReleaseModels() />

<#list releaseModels as releaseModel>
	insert into Release_ values (${releaseModel.mvccVersion}, ${releaseModel.releaseId}, '${dataFactory.getDateString(releaseModel.createDate)}', '${dataFactory.getDateString(releaseModel.modifiedDate)}', '${releaseModel.servletContextName}', '${releaseModel.schemaVersion}', ${releaseModel.buildNumber}, '${dataFactory.getDateString(releaseModel.buildDate)}', ${releaseModel.verified?string}, ${releaseModel.state}, '${releaseModel.testString}');
</#list>