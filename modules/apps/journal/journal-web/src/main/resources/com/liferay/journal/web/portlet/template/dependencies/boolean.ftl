<#include "init.ftl">

<#if stringUtil.equals(language, "ftl")>
${r"<#if"} getterUtil.getBoolean(${variableName})>
	${r"${"}languageUtil.get(locale, "yes")}
	[$CURSOR$]
${r"<#else>"}
	${r"${"}languageUtil.get(locale, "no")}
${r"</#if>"}
<#else>
#if ($getterUtil.getBoolean($${variableName}))
	$languageUtil.get($$locale, "yes")
	[$CURSOR$]
#else
	$languageUtil.get($$locale, "no")
#end
</#if>