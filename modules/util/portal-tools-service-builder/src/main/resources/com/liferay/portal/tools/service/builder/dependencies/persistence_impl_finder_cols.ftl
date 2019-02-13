<#list entityColumns as entityColumn>
	<#if sqlQuery?? && sqlQuery && (entityColumn.name != entityColumn.DBName)>
		<#assign finderFieldSuffix = finderFieldSQLSuffix />
	<#else>
		<#assign finderFieldSuffix = "" />
	</#if>

	<#include "persistence_impl_finder_col.ftl">
</#list>