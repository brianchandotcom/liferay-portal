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
	<img data-fileentryid="${getVariableReferenceCode(variableFieldEntryId)}" alt="${getVariableReferenceCode(variableAltName)}" src="${getVariableReferenceCode(variableName)}" />
${r"</#if>"}
<#else>
#if ($${variableName} && $${variableName} != "")
	<img data-fileentryid="${getVariableReferenceCode(variableFieldEntryId)}" alt="${getVariableReferenceCode(variableAltName)}" src="${getVariableReferenceCode(variableName)}" />
#end
</#if>