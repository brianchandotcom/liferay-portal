<#list finderColsList as finderCol>
	<#if finderCol.name != finderCol.DBName>
		<#assign finderColNameSuffix = "_">
	<#else>
		<#assign finderColNameSuffix = "">
	</#if>

	<#include "persistence_impl_finder_col.ftl">
</#list>