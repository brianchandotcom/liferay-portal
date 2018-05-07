<#include "init.ftl">

<#assign localeVariable = "locale" />

<#if stringUtil.equals(language, "vm")>
	<#assign localeVariable = "$" + localeVariable />
</#if>

<#assign labelName = "languageUtil.format(" + localeVariable + ", \"download-x\", \"" + label + "\", false)" />

<a href="${getVariableReferenceCode(variableName)}">
	${getVariableReferenceCode(labelName)}
</a>