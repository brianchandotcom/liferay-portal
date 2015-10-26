<#assign hasConjunction = false>

<#if finderCol_has_next || (finder.where?? && validator.isNotNull(finder.getWhere()))>
	<#assign hasConjunction = true>
</#if>

if (${finderCol.name} == null) {
	<#if hasConjunction>
		query.append(_FINDER_COLUMN_${finder.name?upper_case}_${finderCol.name?upper_case}_4${finderFieldSuffix});
	<#else>
		query.append(_FINDER_COLUMN_${finder.name?upper_case}_${finderCol.name?upper_case}_1${finderFieldSuffix});
	</#if>
}
else if (${finderCol.name}.equals(StringPool.BLANK)) {
	<#if hasConjunction>
		query.append(_FINDER_COLUMN_${finder.name?upper_case}_${finderCol.name?upper_case}_6${finderFieldSuffix});
	<#else>
		query.append(_FINDER_COLUMN_${finder.name?upper_case}_${finderCol.name?upper_case}_3${finderFieldSuffix});
	</#if>
}
else {
	<#if hasConjunction>
		query.append(_FINDER_COLUMN_${finder.name?upper_case}_${finderCol.name?upper_case}_5${finderFieldSuffix});
	<#else>
		query.append(_FINDER_COLUMN_${finder.name?upper_case}_${finderCol.name?upper_case}_2${finderFieldSuffix});
	</#if>
}