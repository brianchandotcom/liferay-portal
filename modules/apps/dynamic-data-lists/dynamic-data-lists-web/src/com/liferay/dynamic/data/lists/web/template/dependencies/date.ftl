<#include "init.ftl">

<#if language == "ftl">
	${r"<#assign"} ${name}_DateObj = ${fieldValue}>

	${r"<#if"} (${name}_DateObj?has_content)>
		${r"${"}dateUtil.getDate(${name}_DateObj, "dd MMM yyyy", locale)}
	${r"</#if>"}
<#else>
	#set ($${name}_DateObj = $${fieldValue})

	#if ($${name}_DateObj != "")
		$dateUtil.getDate($${name}_DateObj, "dd MMM yyyy", $locale)
	#end
</#if>