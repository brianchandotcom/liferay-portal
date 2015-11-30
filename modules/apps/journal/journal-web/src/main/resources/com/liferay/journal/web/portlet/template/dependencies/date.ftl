<#include "init.ftl">

<#if language == "ftl">
${r"<#assign"} ${name}_Data = getterUtil.getString(${variableName})>

${r"<#if"} (validator.isNotNull(${name}_Data))>
	${r"<#assign"} ${name}_DateObj = dateUtil.parseDate("yyyy-MM-dd", ${name}_Data, locale)>

	${r"${"}dateUtil.getDate(${name}_DateObj, "dd MMM yyyy - HH:mm:ss", locale)}
${r"</#if>"}
<#else>
#set ($${name}_Data = $getterUtil.getString($${variableName}))

#if ($validator.isNotNull($${name}_Data))
	#set ($${name}_DateObj = $dateUtil.parseDate("yyyy-MM-dd",$${name}_Data, $locale))

	$dateUtil.getDate($${name}_DateObj, "dd MMM yyyy - HH:mm:ss", $locale)
#end
</#if>