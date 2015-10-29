<#include "init.ftl">

<#if language == "ftl">
${r"<#assign"} ${name}_Data = getterUtil.getLong(${variableName})>

${r"<#if"} (${name}_Data > 0)>
	${r"<#assign"} ${name}_DateObj = dateUtil.newDate(${name}_Data)>

	${r"${"}dateUtil.getDate(${name}_DateObj, "dd MMM yyyy - HH:mm:ss", locale)}
${r"</#if>"}
<#else>
#set ($${name}_Data = $getterUtil.getLong($${variableName}))

#if ($${name}_Data > 0)
	#set ($${name}_DateObj = $dateUtil.newDate($${name}_Data))

	$dateUtil.getDate($${name}_DateObj, "dd MMM yyyy - HH:mm:ss", $locale)
#end
</#if>