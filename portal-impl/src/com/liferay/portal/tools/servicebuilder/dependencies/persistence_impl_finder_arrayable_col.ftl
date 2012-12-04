<#if sqlQuery?? && sqlQuery && (finderCol.name != finderCol.DBName)>
	<#assign suffix = "_">
<#else>
	<#assign suffix = "">
</#if>

<#if !finderCol.isPrimitiveType()>
	if (${finderCol.name} == null) {
		query.append(_FINDER_COLUMN_${finder.name?upper_case}_${finderCol.name?upper_case}_4${suffix});
	}
	else {
</#if>

<#if finderCol.type == "String">
	if (${finderCol.name}.equals(StringPool.BLANK)) {
		query.append(_FINDER_COLUMN_${finder.name?upper_case}_${finderCol.name?upper_case}_6${suffix});
	}
	else {
		query.append(_FINDER_COLUMN_${finder.name?upper_case}_${finderCol.name?upper_case}_5${suffix});
	}
<#else>
	query.append(_FINDER_COLUMN_${finder.name?upper_case}_${finderCol.name?upper_case}_5${suffix});
</#if>

<#if !finderCol.isPrimitiveType()>
	}
</#if>