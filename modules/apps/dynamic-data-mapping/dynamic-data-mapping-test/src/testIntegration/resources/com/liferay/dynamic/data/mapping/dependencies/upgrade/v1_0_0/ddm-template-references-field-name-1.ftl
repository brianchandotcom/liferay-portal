<h2>Supported Versions: </h2>
<#if Supported-Versions.getOptions()?has_content>
	<ul>
		<#list Supported-Versions.getOptions() as option>
			<li>${option}</li>
		</#list>
	</ul>
<#else>
	${Supported-Versions.getData()}
</#if>