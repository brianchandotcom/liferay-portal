<#include "init.ftl">

<#if language == "ftl">
	${r"<#assign"} ${name}_DateObj = ${fieldValue}>
	${r"${"}dateUtil.getDate(${name}_DateObj, "dd MMM yyyy", locale)}
<#else>
	#set ($${name}_DateObj = $${fieldValue})
	$dateUtil.getDate($${name}_DateObj, "dd MMM yyyy", $locale)
</#if>