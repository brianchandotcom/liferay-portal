<#assign releaseModels = dataFactory.newReleaseModels() />

<#list releaseModels as releaseModel>
	${dataFactory.toInsertSQL(releaseModel)}
</#list>