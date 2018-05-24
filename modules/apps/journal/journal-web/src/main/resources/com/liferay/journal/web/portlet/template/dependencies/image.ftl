<#include "init.ftl">

<#assign
	variableAltName = name + ".getAttribute(\"alt\")"
	variableFieldEntryId = name + ".getAttribute(\"fileEntryId\")"
/>

<#if repeatable>
	<#assign
		variableAltName = "cur_" + variableAltName
		variableFieldEntryId = "cur_" + variableFieldEntryId
	/>
</#if>

<#if stringUtil.equals(language, "ftl")>
${r"<#if"} ${variableName}?? && ${variableName} != "">
	<img alt="${getVariableReferenceCode(variableAltName)}" data-fileentryid="${getVariableReferenceCode(variableFieldEntryId)}" src="${getVariableReferenceCode(variableName)}" />
${r"</#if>"}
<#else>
#if ($${variableName} && $${variableName} != "")
	<img alt="${getVariableReferenceCode(variableAltName)}" data-fileentryid="${getVariableReferenceCode(variableFieldEntryId)}" src="${getVariableReferenceCode(variableName)}" />
#end
</#if>