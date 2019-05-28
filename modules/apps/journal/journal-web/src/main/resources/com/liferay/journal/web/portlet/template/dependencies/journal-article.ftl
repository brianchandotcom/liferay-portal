<#include "init.ftl">

<#assign
	variableData = name + ".getData()"
	variableFriendlyUrl = name + ".getFriendlyUrl()"
/>

<#if repeatable>
	<#assign
		variableData = "cur_" + variableData
		variableFriendlyUrl = "cur_" + variableFriendlyUrl
	/>
</#if>

<#if stringUtil.equals(language, "ftl")>
${r"<#assign"}
	webContentData = jsonFactoryUtil.createJSONObject(${variableData})
${r"/>"}
<#else>
#set ($webContentData = $jsonFactoryUtil.createJSONObject($${variableData}))
</#if>

<a href="${getVariableReferenceCode(variableFriendlyUrl)}">
	${r"${webContentData.title}"}
</a>