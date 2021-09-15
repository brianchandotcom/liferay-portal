<#include "init.ftl">

<#assign
	variableAltName = name + ".getAttribute(\"alt\")"
	variableFieldEntryId = name + ".getAttribute(\"fileEntryId\")"
/>

${r"<#if"} (${variableName})?? && ${variableName} != "">
	<img alt="${getVariableReferenceCode(variableAltName)}" data-fileentryid="${getVariableReferenceCode(variableFieldEntryId)}" src="${getVariableReferenceCode(variableName)}" />
${r"</#if>"}