<#assign finderColConjunction = "">

<#if finderCol_has_next>
	<#assign finderColConjunction = " AND ">
<#elseif finder.where?? && validator.isNotNull(finder.getWhere())>
	<#assign finderColConjunction = " AND " + finder.where>
</#if>

<#if entity.hasCompoundPK() && finderCol.isPrimary()>
	<#assign finderFieldName = entity.alias + ".id." + finderColName>
<#else>
	<#assign finderFieldName = entity.alias + "." + finderColName>
</#if>

<#if serviceBuilder.getSqlType(packagePath + ".model." + entity.getName(), finderCol.getName(), finderCol.getType()) == "CLOB">
	<#assign textFinderFieldName = "CAST_CLOB_TEXT(" + finderFieldName + ")">
<#else>
	<#assign textFinderFieldName = finderFieldName>
</#if>

<#if !finderCol.isPrimitiveType()>
	private static final String _FINDER_COLUMN_${finder.name?upper_case}_${finderCol.name?upper_case}_1${finderFieldSuffix} =

	<#if finderCol.comparator == "=">
		"${finderFieldName} IS NULL${finderColConjunction}"
	<#elseif finderCol.comparator == "<>" || finderCol.comparator == "!=">
		"${finderFieldName} IS NOT NULL${finderColConjunction}"
	<#else>
		"${finderFieldName} ${finderCol.comparator} NULL${finderColConjunction}"
	</#if>

	;
</#if>

<#if finderCol.type == "String" && !finderCol.isCaseSensitive()>
	<#assign finderColExpression = "lower(" + textFinderFieldName + ") " + finderCol.comparator + " ?">
<#else>
	<#assign finderColExpression = textFinderFieldName + " " + finderCol.comparator + " ?">
</#if>

private static final String _FINDER_COLUMN_${finder.name?upper_case}_${finderCol.name?upper_case}_2${finderFieldSuffix} = "${finderColExpression}${finderColConjunction}";

<#if finderCol.type == "String">
	<#assign finderColExpression = textFinderFieldName + " " + finderCol.comparator + " ''">

	private static final String _FINDER_COLUMN_${finder.name?upper_case}_${finderCol.name?upper_case}_3${finderFieldSuffix} = "(${finderFieldName} IS NULL OR ${finderColExpression})${finderColConjunction}";
</#if>

<#if finderCol.hasArrayableOperator() && (finderColConjunction != "") && (finderCol.type == "String")>
	private static final String _FINDER_COLUMN_${finder.name?upper_case}_${finderCol.name?upper_case}_4${finderFieldSuffix} = "(" + removeConjunction(_FINDER_COLUMN_${finder.name?upper_case}_${finderCol.name?upper_case}_1) + ")";

	private static final String _FINDER_COLUMN_${finder.name?upper_case}_${finderCol.name?upper_case}_5${finderFieldSuffix} = "(" + removeConjunction(_FINDER_COLUMN_${finder.name?upper_case}_${finderCol.name?upper_case}_2) + ")";

	private static final String _FINDER_COLUMN_${finder.name?upper_case}_${finderCol.name?upper_case}_6${finderFieldSuffix} = "(" + removeConjunction(_FINDER_COLUMN_${finder.name?upper_case}_${finderCol.name?upper_case}_3) + ")";
</#if>

<#if finderCol.hasArrayableOperator() && (finderCol.type != "String")>
	private static final String _FINDER_COLUMN_${finder.name?upper_case}_${finderCol.name?upper_case}_7${finderFieldSuffix} = "${finderFieldName}<#if finderCol.isArrayableAndOperator()> NOT IN (<#else> IN (</#if>";
</#if>