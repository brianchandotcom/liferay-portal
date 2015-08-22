<#include "init.ftl">

<#if language == "ftl">
	${r"${"}ddlDisplayTemplateHelper.renderRecordFieldValue(${fieldValueVariable}, ${localeVariable})${r"}"}
<#else>
	$ddlDisplayTemplateHelper.renderRecordFieldValue(${fieldValueVariable}, ${localeVariable})
</#if>