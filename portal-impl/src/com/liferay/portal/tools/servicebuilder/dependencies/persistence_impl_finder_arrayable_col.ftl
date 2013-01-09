<#if !finderCol.isPrimitiveType()>
	boolean bind${finderCol.methodName} = false;

	if (${finderCol.name} == null) {
		query.append(_FINDER_COLUMN_${finder.name?upper_case}_${finderCol.name?upper_case}_4);
	}
	<#if finderCol.type == "String">
		else if (${finderCol.name}.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_${finder.name?upper_case}_${finderCol.name?upper_case}_6);
		}
	</#if>
	else {
		bind${finderCol.methodName} = true;
</#if>
		query.append(_FINDER_COLUMN_${finder.name?upper_case}_${finderCol.name?upper_case}_5);

<#if !finderCol.isPrimitiveType()>
	}
</#if>