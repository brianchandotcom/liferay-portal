<#if entityFinder.where?? && entityFinder.DBWhere?? && (entityFinder.where != entityFinder.DBWhere)>
	<#assign entityFinderDBWhere = true />
<#else>
	<#assign entityFinderDBWhere = false />
</#if>

<#list entityColumns as entityColumn>
	<#if sqlQuery?? && sqlQuery && ((entityColumn.name != entityColumn.DBName) || entityFinderDBWhere)>
		<#assign finderFieldSuffix = finderFieldSQLSuffix />
	<#else>
		<#assign finderFieldSuffix = "" />
	</#if>

	<#include "persistence_impl_finder_col.ftl">
</#list>