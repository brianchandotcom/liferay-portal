<#include "init.ftl">

<#assign
	altExpression = "$" + "{htmlUtil.escapeAttribute(" + name + ".getAttribute(\"alt\"))}"
	variableFieldEntryId = name + ".getAttribute(\"fileEntryId\")"
/>

${r"<#if"} (${variableName})?? && ${variableName} != "">
	<img alt="${altExpression}" data-fileentryid="${getVariableReferenceCode(variableFieldEntryId)}" src="${getVariableReferenceCode(variableName)}" />
${r"</#if>"}