<h2>Supported Versions: </h2>
<#if SupportedVersions.getOptions()?has_content>
	<ul>
		<#list SupportedVersions.getOptions() as option>
			<li>${option}</li>
		</#list>
	</ul>
<#else>
	${SupportedVersions.getData()}
</#if>