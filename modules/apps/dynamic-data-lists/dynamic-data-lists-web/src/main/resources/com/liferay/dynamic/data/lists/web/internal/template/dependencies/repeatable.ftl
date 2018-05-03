<#assign itemName = "curValue_" + name />

<#if stringUtil.equals(language, "ftl")>
	<#assign fieldValues = "cur_record.getDDMFormFieldValues(\"" + name + "\")" />

	${r"<#if"} ${fieldValues}?has_content>
		${r"<#list"} ${fieldValues} as ${itemName}>
		${r"</#list>"}
	${r"</#if>"}
<#else>
	<#assign fieldValues = "$cur_record.getDDMFormFieldValues(\"" + name + "\")" />

	#if (!${fieldValues}.isEmpty())
		#foreach ($${itemName} in ${fieldValues})
		#end
	#end
</#if>