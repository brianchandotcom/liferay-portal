<#assign
	itemName = "cur_" + stringUtil.replace(name, ".", "_")

	variableName = name + ".getSiblings()"
/>

${r"<#if"} ${variableName}?has_content>
	${r"<#list"} ${variableName} as ${itemName}>
		${templateContent}
	${r"</#list>"}
${r"</#if>"}