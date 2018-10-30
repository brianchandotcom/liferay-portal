<#if !entityColumn.isPrimitiveType()>
	boolean bind${entityColumn.methodName} = false;

	<#if stringUtil.equals(entityColumn.type, "String")>
		<#if !entityColumn.isConvertNull()>
			if (${entityColumn.name} == null) {
				query.append(_FINDER_COLUMN_${entityFinder.name?upper_case}_${entityColumn.name?upper_case}_1${finderFieldSuffix});
			}
			else
		</#if>
		if (${entityColumn.name}.isEmpty()) {
			query.append(_FINDER_COLUMN_${entityFinder.name?upper_case}_${entityColumn.name?upper_case}_3${finderFieldSuffix});
		}
	<#else>
		if (${entityColumn.name} == null) {
			query.append(_FINDER_COLUMN_${entityFinder.name?upper_case}_${entityColumn.name?upper_case}_1${finderFieldSuffix});
		}
	</#if>
	else {
		bind${entityColumn.methodName} = true;
</#if>

query.append(_FINDER_COLUMN_${entityFinder.name?upper_case}_${entityColumn.name?upper_case}_2${finderFieldSuffix});

<#if !entityColumn.isPrimitiveType()>
	}
</#if>