<#if !finderCol.isPrimitiveType()>
	boolean bind${finderCol.methodName} = false;

	if (${finderCol.name} == null) {
		query.append(_FINDER_COLUMN_${finder.name?upper_case}_${finderCol.name?upper_case}_1${finderFieldSuffix});
	}
	<#if finderCol.type == "String">
		else if (${finderCol.name}.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_${finder.name?upper_case}_${finderCol.name?upper_case}_3${finderFieldSuffix});
		}
	</#if>
	else {
		bind${finderCol.methodName} = true;
</#if>

query.append(_FINDER_COLUMN_${finder.name?upper_case}_${finderCol.name?upper_case}_2${finderFieldSuffix});

<#if !finderCol.isPrimitiveType()>
	}
</#if>