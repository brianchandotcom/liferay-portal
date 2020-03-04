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

	<#if entityColumn.hasArrayableOperator()>
		if (${entityColumn.pluralName}.length > 0) {
			sb.append("(");

			<#if stringUtil.equals(entityColumn.type, "String")>
				for (int i = 0; i < ${entityColumn.pluralName}.length; i++) {
					${entityColumn.type} ${entityColumn.name} = ${entityColumn.pluralName}[i];

					<#include "persistence_impl_finder_arrayable_col.ftl">

					if ((i + 1) < ${entityColumn.pluralName}.length) {
						sb.append(<#if entityColumn.isArrayableAndOperator()>WHERE_AND<#else>WHERE_OR</#if>);
					}
				}
			<#else>
				sb.append(_FINDER_COLUMN_${entityFinder.name?upper_case}_${entityColumn.name?upper_case}_7${finderFieldSuffix});

				sb.append(StringUtil.merge(${entityColumn.pluralName}));

				sb.append(")");
			</#if>

			sb.append(")");

			<#if entityColumn_has_next>
				sb.append(WHERE_AND);
			</#if>
		}
	<#else>
		<#include "persistence_impl_finder_col.ftl">
	</#if>
</#list>

sb.setStringAt(removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

<#if sqlQuery?? && sqlQuery && entityFinderDBWhere>
	sb.append(" AND ${entityFinder.DBWhere}");
<#elseif entityFinder.where?? && validator.isNotNull(entityFinder.getWhere())>
	sb.append(" AND ${entityFinder.where}");
</#if>