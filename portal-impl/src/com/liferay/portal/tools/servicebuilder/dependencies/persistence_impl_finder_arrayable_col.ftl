<#if !finderCol.isPrimitiveType()>
	if (${finderCol.name} == null) {
		query.append(_FINDER_COLUMN_${finder.name?upper_case}_${finderCol.name?upper_case}_4${finderFieldSuffix});
	}
	<#if finderCol.type == "String">
		else if (${finderCol.name}.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_${finder.name?upper_case}_${finderCol.name?upper_case}_6${finderFieldSuffix});
		}
	</#if>
	else {
</#if>

query.append(_FINDER_COLUMN_${finder.name?upper_case}_${finderCol.name?upper_case}_5${finderFieldSuffix});

<#if !finderCol.isPrimitiveType()>
	}
</#if>