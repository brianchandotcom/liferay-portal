<#if !finderCol.isPrimitiveType()>
	if (${finderCol.name} == null) {
		query.append(_FINDER_COLUMN_${finder.name?upper_case}_${finderCol.name?upper_case}_4);
	}
	else {
</#if>

<#if finderCol.type == "String">
	if (${finderCol.name}.equals(StringPool.BLANK)) {
		query.append(_FINDER_COLUMN_${finder.name?upper_case}_${finderCol.name?upper_case}_6);
	}
	else {
		query.append(_FINDER_COLUMN_${finder.name?upper_case}_${finderCol.name?upper_case}_5);
	}
<#else>
	query.append(_FINDER_COLUMN_${finder.name?upper_case}_${finderCol.name?upper_case}_5);
</#if>

<#if !finderCol.isPrimitiveType()>
	}
</#if>