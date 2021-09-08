<#include "init.ftl">

<#assign
	localeVariable = "locale"

	labelName = "languageUtil.format(" + localeVariable + ", \"download-x\", \"" + label + "\", false)"
/>

<a href="${getVariableReferenceCode(variableName)}">
	${getVariableReferenceCode(labelName)}
</a>