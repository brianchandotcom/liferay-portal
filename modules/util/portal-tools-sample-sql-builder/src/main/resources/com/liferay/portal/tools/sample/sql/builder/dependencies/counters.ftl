<#assign counterModels = dataFactory.newCounterModels()>

<#list counterModels as counterModel>
	<#if ('${counterModel.name}' == 'com.liferay.counter.model.Counter')>
		update Counter set currentId = ${counterModel.currentId} where name = '${counterModel.name}';
	<#else>
		insert into Counter values ('${counterModel.name}', ${counterModel.currentId});
	</#if>
</#list>