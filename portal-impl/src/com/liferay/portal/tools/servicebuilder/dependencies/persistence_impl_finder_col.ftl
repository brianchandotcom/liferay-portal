<#if sqlQuery?? && sqlQuery && (finderCol.name != finderCol.DBName)>
	<#assign suffix = "_">
<#else>
	<#assign suffix = "">
</#if>

<#if !finderCol.isPrimitiveType()>
	if (${finderCol.name} == null) {
		query.append(_FINDER_COLUMN_${finder.name?upper_case}_${finderCol.name?upper_case}_1${suffix});
	}
	else {
</#if>

<#if finderCol.type == "String">
	if (${finderCol.name}.equals(StringPool.BLANK)) {
		query.append(_FINDER_COLUMN_${finder.name?upper_case}_${finderCol.name?upper_case}_3${suffix});
	}
	else {
		query.append(_FINDER_COLUMN_${finder.name?upper_case}_${finderCol.name?upper_case}_2${suffix});
	}
<#else>
	query.append(_FINDER_COLUMN_${finder.name?upper_case}_${finderCol.name?upper_case}_2${suffix});
</#if>

<#if !finderCol.isPrimitiveType()>
	}
</#if>