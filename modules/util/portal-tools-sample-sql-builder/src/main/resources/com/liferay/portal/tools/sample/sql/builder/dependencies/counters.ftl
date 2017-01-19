<#assign counterModels = dataFactory.newCounterModels() />

<#list counterModels as counterModel>
	<#if '${counterModel.name}' == 'com.liferay.counter.kernel.model.Counter'>
		update Counter set currentId = ${counterModel.currentId} where name = '${counterModel.name}';
	<#else>
		${dataFactory.toInsertSQL(counterModel)}
	</#if>
</#list>