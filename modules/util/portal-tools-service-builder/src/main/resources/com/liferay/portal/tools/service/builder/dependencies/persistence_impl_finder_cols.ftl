<#list finderColsList as finderCol>
	<#if sqlQuery?? && sqlQuery && (finderCol.name != finderCol.DBName)>
		<#assign finderFieldSuffix = finderFieldSQLSuffix>
	<#else>
		<#assign finderFieldSuffix = "">
	</#if>

	<#include "persistence_impl_finder_col.ftl">
</#list>