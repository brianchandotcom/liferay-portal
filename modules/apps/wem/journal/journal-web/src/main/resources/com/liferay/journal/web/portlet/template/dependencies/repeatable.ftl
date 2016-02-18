<#assign itemName = "cur_" + name>

<#assign variableName = name + ".getSiblings()">

<#if language == "ftl">
${r"<#if"} ${variableName}?has_content>
	${r"<#list"} ${variableName} as ${itemName}>
		${templateContent}
	${r"</#list>"}
${r"</#if>"}
<#else>
#if (!$${variableName}.isEmpty())
	#foreach ($${itemName} in $${variableName})
		${templateContent}
	#end
#end
</#if>