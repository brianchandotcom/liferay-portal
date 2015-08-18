<#include "init.ftl">

<#assign labelName = "languageUtil.format(" + localeVariable + ", \"download-x\", \"" + label + "\", false)">

<a href="${render()}">
	${getVariableReferenceCode(labelName)}
</a>