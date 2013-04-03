<#if !finderCol.isPrimitiveType()>
	private static final String _FINDER_COLUMN_${finder.name?upper_case}_${finderCol.name?upper_case}_1${finderFieldSuffix} =

	<#if finderCol.comparator == "=">
		"${entity.alias}<#if entity.hasCompoundPK() && finderCol.isPrimary()>.id</#if>.${finderColName} IS NULL${finderColConjunction}"
	<#elseif finderCol.comparator == "<>" || finderCol.comparator = "!=">
		"${entity.alias}<#if entity.hasCompoundPK() && finderCol.isPrimary()>.id</#if>.${finderColName} IS NOT NULL${finderColConjunction}"
	<#else>
		"${entity.alias}<#if entity.hasCompoundPK() && finderCol.isPrimary()>.id</#if>.${finderColName} ${finderCol.comparator} NULL${finderColConjunction}"
	</#if>

	;
</#if>

<#if finderCol.type == "String" && !finderCol.isCaseSensitive()>
	<#if entity.hasCompoundPK() && finderCol.isPrimary()>
		<#assign finderColExpression = "lower(" + entity.alias + ".id." + finderColName + ") " + finderCol.comparator + " ?">
	<#else>
		<#assign finderColExpression = "lower(" + entity.alias + "." + finderColName + ") " + finderCol.comparator + " ?">
	</#if>
<#else>
	<#if entity.hasCompoundPK() && finderCol.isPrimary()>
		<#assign finderColExpression = entity.alias + ".id." + finderColName + " " + finderCol.comparator + " ?">
	<#else>
		<#assign finderColExpression = entity.alias + "." + finderColName + " " + finderCol.comparator + " ?">
	</#if>
</#if>

private static final String _FINDER_COLUMN_${finder.name?upper_case}_${finderCol.name?upper_case}_2${finderFieldSuffix} = "${finderColExpression}${finderColConjunction}";

<#if finderCol.type == "String">
	<#if entity.hasCompoundPK() && finderCol.isPrimary()>
		<#assign finderColExpression = entity.alias + ".id." + finderColName + " " + finderCol.comparator + " ''">
	<#else>
		<#assign finderColExpression = entity.alias + "." + finderColName + " " + finderCol.comparator + " ''">
	</#if>

	private static final String _FINDER_COLUMN_${finder.name?upper_case}_${finderCol.name?upper_case}_3${finderFieldSuffix} = "(${entity.alias}<#if entity.hasCompoundPK() && finderCol.isPrimary()>.id</#if>.${finderColName} IS NULL OR ${finderColExpression})${finderColConjunction}";
</#if>

<#if finder.hasArrayableOperator()>
	<#if !finderCol.isPrimitiveType()>
		private static final String _FINDER_COLUMN_${finder.name?upper_case}_${finderCol.name?upper_case}_4${finderFieldSuffix} = "(" + removeConjunction(_FINDER_COLUMN_${finder.name?upper_case}_${finderCol.name?upper_case}_1) + ")";
	</#if>

	private static final String _FINDER_COLUMN_${finder.name?upper_case}_${finderCol.name?upper_case}_5${finderFieldSuffix} = "(" + removeConjunction(_FINDER_COLUMN_${finder.name?upper_case}_${finderCol.name?upper_case}_2) + ")";

	<#if finderCol.type == "String">
		private static final String _FINDER_COLUMN_${finder.name?upper_case}_${finderCol.name?upper_case}_6${finderFieldSuffix} = "(" + removeConjunction(_FINDER_COLUMN_${finder.name?upper_case}_${finderCol.name?upper_case}_3) + ")";
	</#if>
</#if>