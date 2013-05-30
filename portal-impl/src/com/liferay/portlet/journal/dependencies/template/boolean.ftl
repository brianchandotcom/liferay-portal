<#include "init.ftl">

<#if language == "ftl">
	${r"<#if"} getterUtil.getBoolean(${variableName})>
	${r"</#if>"}
<#else>
	#if ($getterUtil.getBoolean($${variableName}))
	#end
</#if>