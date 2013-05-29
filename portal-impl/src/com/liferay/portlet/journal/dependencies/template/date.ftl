<#include "init.ftl">

<#if language == "ftl">
	${r"<#assign"} ${name}_DateObj = dateUtil.newDate(getterUtil.getLong(${variableName}))>
	${r"${"}dateUtil.getDate(${name}_DateObj, "dd MMM yyyy - HH:mm:ss", locale)}
<#else>
	#set ($${name}_DateObj = $dateUtil.newDate($getterUtil.getLong($${variableName})))
	$dateUtil.getDate($${name}_DateObj, "dd MMM yyyy - HH:mm:ss", $locale)
</#if>