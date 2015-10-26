<#list finderColsList as finderCol>
	<#if sqlQuery?? && sqlQuery && (finderCol.name != finderCol.DBName)>
		<#assign finderFieldSuffix = finderFieldSQLSuffix>
	<#else>
		<#assign finderFieldSuffix = "">
	</#if>

	<#if finderCol.hasArrayableOperator()>
		if (${finderCol.names}.length > 0) {
			query.append(StringPool.OPEN_PARENTHESIS);

			<#if finderCol.type == "String">
				for (int i = 0; i < ${finderCol.names}.length; i++) {
					${finderCol.type} ${finderCol.name} = ${finderCol.names}[i];

					<#include "persistence_impl_finder_arrayable_col.ftl">

					if ((i + 1) < ${finderCol.names}.length) {
						query.append(<#if finderCol.isArrayableAndOperator()>WHERE_AND<#else>WHERE_OR</#if>);
					}
				}
			<#else>
				query.append(_FINDER_COLUMN_${finder.name?upper_case}_${finderCol.name?upper_case}_7${finderFieldSuffix});

				query.append(StringUtil.merge(${finderCol.names}));

				query.append(StringPool.CLOSE_PARENTHESIS);
			</#if>

			query.append(StringPool.CLOSE_PARENTHESIS);

			<#if finderCol_has_next>
				query.append(WHERE_AND);
			</#if>
		}
	<#else>
		<#include "persistence_impl_finder_col.ftl">
	</#if>
</#list>

<#if finder.where?? && validator.isNotNull(finder.getWhere())>
	query.append("${finder.where}");
<#else>
	query.setStringAt(removeConjunction(query.stringAt(query.index() - 1)), query.index() - 1);
</#if>