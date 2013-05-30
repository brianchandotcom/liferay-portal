<#include "init-display.ftl">

<#assign labelName = "languageUtil.format(" + localeVariable + ", \"download-x\", \"" + label + "\")">

<a href="${getVariableReferenceCode(displayFieldValue)}">
	${getVariableReferenceCode(labelName)}
</a>