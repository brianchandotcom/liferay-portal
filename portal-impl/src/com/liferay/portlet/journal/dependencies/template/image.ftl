<#include "init.ftl">

<#assign variableAltName = name + ".getAttribute(\"alt\")">

<#if repeatable>
	<#assign variableAltName = "cur_" + variableAltName>
</#if>

<img alt='${getVariableReferenceCode(variableAltName)}' src="${getVariableReferenceCode(variableName)}" />