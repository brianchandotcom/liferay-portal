<#include "init.ftl">

<#if stringUtil.equals(language, "ftl")>
${r"<#if"} getterUtil.getBoolean(${variableName})>
	[$CURSOR$]
${r"</#if>"}
<#else>
#if ($getterUtil.getBoolean($${variableName}))
	[$CURSOR$]
#end
</#if>