<#include "init.ftl">

<#assign
	href = "ddlDisplayTemplateHelper.getDocumentLibraryPreviewURL(" + fieldValueVariable + ", " + localeVariable + ")"
	label = "languageUtil.format(" + localeVariable + ", \"download-x\", \"" + label + "\", false)"
/>

<a href="${getVariableReferenceCode(href)}">
	${getVariableReferenceCode(label)}
</a>