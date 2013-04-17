<#assign finderColsList = finder.getColumns()>

<#list finderColsList as finderCol>
	<#assign finderColConjunction = "">

	<#if finderCol_has_next>
		<#assign finderColConjunction = " AND ">
	<#elseif finder.where?? && validator.isNotNull(finder.getWhere())>
		<#assign finderColConjunction = " AND " + finder.where>
	</#if>

	<#assign finderColName = finderCol.name finderFieldSuffix = "">

	<#include "persistence_impl_finder_field.ftl">

	<#if entity.isPermissionCheckEnabled(finder) && !entity.isPermissionedModel() && (finderCol.name != finderCol.DBName)>
		<#assign finderColName = finderCol.DBName finderFieldSuffix = finderFieldSQLSuffix>

		<#include "persistence_impl_finder_field.ftl">
	</#if>
</#list>