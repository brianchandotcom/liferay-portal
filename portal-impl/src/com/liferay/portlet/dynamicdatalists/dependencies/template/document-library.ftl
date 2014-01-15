<#include "init-display.ftl">

<#assign labelName = "languageUtil.format(" + localeVariable + ", \"download-x\", \"" + label + "\", false)">

<a href="${getVariableReferenceCode(displayFieldValue)}">
	${getVariableReferenceCode(labelName)}
</a>