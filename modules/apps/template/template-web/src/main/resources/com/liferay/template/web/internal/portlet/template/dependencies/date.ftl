<#include "init.ftl">

<#assign encodedName = stringUtil.replace(name, ".", "_") />

<#if stringUtil.equals(language, "ftl")>
${r"<#assign"} ${encodedName}_Data = getterUtil.getString(${variableName})>

${r"<#if"} validator.isNotNull(${encodedName}_Data)>
	${r"<#assign"} ${encodedName}_DateObj = dateUtil.parseDate("yyyy-MM-dd", ${encodedName}_Data, locale)>

	${r"${"}dateUtil.getDate(${encodedName}_DateObj, "dd MMM yyyy - HH:mm:ss", locale)}
${r"</#if>"}
<#else>
#set ($${encodedName}_Data = $getterUtil.getString($${variableName}))

#if ($validator.isNotNull($${encodedName}_Data))
	#set ($${encodedName}_DateObj = $dateUtil.parseDate("yyyy-MM-dd",$${encodedName}_Data, $locale))

	$dateUtil.getDate($${encodedName}_DateObj, "dd MMM yyyy - HH:mm:ss", $locale)
#end
</#if>