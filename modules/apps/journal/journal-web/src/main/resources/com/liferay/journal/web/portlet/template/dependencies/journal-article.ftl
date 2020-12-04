<#include "init.ftl">

<#assign
	variableData = name + ".getData()"
	variableFriendlyUrl = name + ".getFriendlyUrl()"
/>

<#if stringUtil.equals(language, "ftl")>
${r"<#assign"}
	webContentData = jsonFactoryUtil.createJSONObject(${variableData})
${r"/>"}
<#else>
#set ($webContentData = $jsonFactoryUtil.createJSONObject($${variableData}))
</#if>

${r"<#if"} webContentData?? && webContentData.title>
	<a href="${getVariableReferenceCode(variableFriendlyUrl)}">
		${r"${webContentData.title}"}
	</a>
${r"</#if>"}