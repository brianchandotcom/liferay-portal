<#include "init.ftl">

<#if language == "ftl">
	${r"<#assign"} ${name}_Date = getterUtil.getLong(${variableName})>

	${r"<#if"} (${name}_Date > 0)>
		${r"<#assign"} ${name}_DateObj = dateUtil.newDate(${name}_Date)>

		${r"${"}dateUtil.getDate(${name}_DateObj, "dd MMM yyyy - HH:mm:ss", locale)}
	${r"</#if>"}
<#else>
	#set ($${name}_Date = $getterUtil.getLong($${variableName}))

	#if ($${name}_Date > 0)
		#set ($${name}_DateObj = $dateUtil.newDate($${name}_Date))

		$dateUtil.getDate($${name}_DateObj, "dd MMM yyyy - HH:mm:ss", $locale)
	#end
</#if>