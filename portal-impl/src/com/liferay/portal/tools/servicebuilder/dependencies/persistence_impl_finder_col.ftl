<#if !finderCol.isPrimitiveType()>
	if (${finderCol.name} == null) {
		query.append(_FINDER_COLUMN_${finder.name?upper_case}_${finderCol.name?upper_case}_1);
	}
	else {
</#if>

<#if finderCol.type == "String">
	if (${finderCol.name}.equals(StringPool.BLANK)) {
		query.append(_FINDER_COLUMN_${finder.name?upper_case}_${finderCol.name?upper_case}_3);
	}
	else {
		query.append(_FINDER_COLUMN_${finder.name?upper_case}_${finderCol.name?upper_case}_2);
	}
<#else>
	query.append(_FINDER_COLUMN_${finder.name?upper_case}_${finderCol.name?upper_case}_2);
</#if>

<#if !finderCol.isPrimitiveType()>
	}
</#if>