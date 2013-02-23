<#setting number_format = "0">

<#assign counters = dataFactory.addCounters()>

<#list counters as counter>
	<#if ('${counter.name}' == 'com.liferay.counter.model.Counter')>
		update Counter set currentId = ${counter.currentId} where name = '${counter.name}';
	<#else>
		insert into Counter values ('${counter.name}', ${counter.currentId});
	</#if>
</#list>