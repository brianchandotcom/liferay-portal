<#assign finderColsList = finder.getColumns()>

<#list finderColsList as finderCol>
	<#assign finderColName = finderCol.name finderFieldSuffix = "">

	<#include "persistence_impl_finder_field.ftl">

	<#if entity.isPermissionCheckEnabled(finder) && !entity.isPermissionedModel() && (finderCol.name != finderCol.DBName)>
		<#assign finderColName = finderCol.DBName finderFieldSuffix = finderFieldSQLSuffix>

		<#include "persistence_impl_finder_field.ftl">
	</#if>
</#list>