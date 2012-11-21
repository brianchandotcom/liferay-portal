<#list finderColsList as finderCol>
	<#if sqlQuery && (finderCol.name != finderCol.DBName)>
		<#assign finderColNameSuffix = colNameEscapeSuffix>
	<#else>
		<#assign finderColNameSuffix = "">
	</#if>

	<#include "persistence_impl_finder_col.ftl">
</#list>

<#assign sqlQuery = false>