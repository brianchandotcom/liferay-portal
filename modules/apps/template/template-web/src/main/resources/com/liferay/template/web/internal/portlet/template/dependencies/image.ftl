<#include "init.ftl">

${r"<#if"} (${variableName})?? && ${variableName} != "">
	<img src="${getVariableReferenceCode(variableName)}" />
${r"</#if>"}