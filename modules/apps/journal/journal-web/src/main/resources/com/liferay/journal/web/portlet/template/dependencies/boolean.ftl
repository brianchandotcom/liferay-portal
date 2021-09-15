<#include "init.ftl">

${r"<#if"} getterUtil.getBoolean(${variableName})>
	${r"${"}languageUtil.get(locale, "yes")}
${r"<#else>"}
	${r"${"}languageUtil.get(locale, "no")}
${r"</#if>"}