<#if entries?has_content>
	<ul>
		<#list entries as curCPCatalogEntry>
			<li>${curCPCatalogEntry.getName()}</li>
		</#list>
	</ul>
</#if>