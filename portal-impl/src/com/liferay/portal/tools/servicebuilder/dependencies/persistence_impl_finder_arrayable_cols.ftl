boolean conjunctionable = false;

<#list finderColsList as finderCol>
	<#if sqlQuery?? && sqlQuery && (finderCol.name != finderCol.DBName)>
		<#assign finderFieldSuffix = finderFieldSQLSuffix>
	<#else>
		<#assign finderFieldSuffix = "">
	</#if>

	<#if finderCol.hasArrayableOperator()>
		if ((${finderCol.names} == null) || (${finderCol.names}.length > 0)) {
			if (conjunctionable) {
				query.append(WHERE_AND);
			}

			query.append(StringPool.OPEN_PARENTHESIS);

			for (int i = 0; i < ${finderCol.names}.length; i++) {
				<#if !finderCol.isPrimitiveType()>
					${finderCol.type} ${finderCol.name} = ${finderCol.names}[i];
				</#if>

				<#include "persistence_impl_finder_arrayable_col.ftl">

				if ((i + 1) < ${finderCol.names}.length) {
					query.append(<#if finderCol.isArrayableAndOperator()>WHERE_AND<#else>WHERE_OR</#if>);
				}
			}

			query.append(StringPool.CLOSE_PARENTHESIS);

			conjunctionable = true;
		}
	<#else>
		if (conjunctionable) {
			query.append(WHERE_AND);
		}

		<#include "persistence_impl_finder_arrayable_col.ftl">

		conjunctionable = true;
	</#if>
</#list>

<#if finder.where?? && validator.isNotNull(finder.getWhere())>
	if (conjunctionable) {
		query.append(WHERE_AND);
	}

	query.append("${finder.where}");
</#if>