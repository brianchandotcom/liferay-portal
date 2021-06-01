<#include "init.ftl">

${r"<#if"} (${variableName})??>
	${getVariableReferenceCode(variableName)}
${r"</#if>"}