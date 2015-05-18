<#assign itemName = "curValue_" + name>

<#if language == "ftl">
	<#assign fieldValues = "cur_record.getFieldValues(\"" + name + "\", locale)">

	${r"<#if"} ${fieldValues}?has_content>
		${r"<#list"} ${fieldValues} as ${itemName}>
		${r"</#list>"}
	${r"</#if>"}
<#else>
	<#assign fieldValues = "$cur_record.getFieldValues(\"" + name + "\", $locale)">

	#if (!${fieldValues}.isEmpty())
		#foreach ($${itemName} in ${fieldValues})
		#end
	#end
</#if>