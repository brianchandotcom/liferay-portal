<#include "init.ftl">

<#assign href = "ddlDisplayTemplateHelper.getLayoutFriendlyURL(" + fieldValueVariable + ", " + themeDisplayVariable + ")" />

<a href="${getVariableReferenceCode(href)}">
	${label}
</a>