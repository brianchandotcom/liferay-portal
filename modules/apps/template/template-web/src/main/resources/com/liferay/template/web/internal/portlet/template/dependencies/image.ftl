<#include "init.ftl">

<#assign
	variableAltName = name + ".getAttribute(\"alt\")"
	variableFieldEntryId = name + ".getAttribute(\"fileEntryId\")"
/>

<#if stringUtil.equals(language, "ftl")>
${r"<#if"} (${variableName})?? && ${variableName} != "">
	<img src="${getVariableReferenceCode(variableName)}" />
${r"</#if>"}
<#else>
#if ($${variableName} && $${variableName} != "")
	<img src="${getVariableReferenceCode(variableName)}" />
#end
</#if>