<#assign finderColsList = finder.getColumns()>

<#list finderColsList as finderCol>
	<#assign finderColConjunction = "">

	<#if finderCol_has_next>
		<#assign finderColConjunction = " AND ">
	<#elseif finder.where?? && validator.isNotNull(finder.getWhere())>
		<#assign finderColConjunction = " AND " + finder.where>
	</#if>

	<#assign finderColName = finderCol.name>
	<#assign suffix = "">

	<#list [0, 1] as index>
		<#if index == 1>
			<#if sqlQuery?? && (finderCol.name != finderCol.DBName)>
				<#assign finderColName = finderCol.DBName>
				<#assign suffix = "_">
			<#else>
				<#break>
			</#if>
		</#if>

		<#if !finderCol.isPrimitiveType()>
			private static final String _FINDER_COLUMN_${finder.name?upper_case}_${finderCol.name?upper_case}_1${suffix} =

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
				<#assign finderColExpression = "lower(" + entity.alias + ".id." + finderColName + ") " + finderCol.comparator + " lower(CAST_TEXT(?))">
			<#else>
				<#assign finderColExpression = "lower(" + entity.alias + "." + finderColName + ") " + finderCol.comparator + " lower(CAST_TEXT(?))">
			</#if>
		<#else>
			<#if entity.hasCompoundPK() && finderCol.isPrimary()>
				<#assign finderColExpression = entity.alias + ".id." + finderColName + " " + finderCol.comparator + " ?">
			<#else>
				<#assign finderColExpression = entity.alias + "." + finderColName + " " + finderCol.comparator + " ?">
			</#if>
		</#if>

		private static final String _FINDER_COLUMN_${finder.name?upper_case}_${finderCol.name?upper_case}_2${suffix} = "${finderColExpression}${finderColConjunction}";

		<#if finderCol.type == "String">
			private static final String _FINDER_COLUMN_${finder.name?upper_case}_${finderCol.name?upper_case}_3${suffix} = "(${entity.alias}<#if entity.hasCompoundPK() && finderCol.isPrimary()>.id</#if>.${finderCol.name} IS NULL OR ${finderColExpression})${finderColConjunction}";
		</#if>

		<#if finder.hasArrayableOperator()>
			<#if !finderCol.isPrimitiveType()>
				private static final String _FINDER_COLUMN_${finder.name?upper_case}_${finderCol.name?upper_case}_4${suffix} = "(" + _removeConjunction(_FINDER_COLUMN_${finder.name?upper_case}_${finderCol.name?upper_case}_1) + ")";
			</#if>

			private static final String _FINDER_COLUMN_${finder.name?upper_case}_${finderCol.name?upper_case}_5${suffix} = "(" + _removeConjunction(_FINDER_COLUMN_${finder.name?upper_case}_${finderCol.name?upper_case}_2) + ")";

			<#if finderCol.type == "String">
				private static final String _FINDER_COLUMN_${finder.name?upper_case}_${finderCol.name?upper_case}_6${suffix} = "(" + _removeConjunction(_FINDER_COLUMN_${finder.name?upper_case}_${finderCol.name?upper_case}_3) + ")";
			</#if>
		</#if>
	</#list>
</#list>