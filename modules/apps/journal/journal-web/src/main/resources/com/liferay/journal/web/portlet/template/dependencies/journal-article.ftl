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
	webcontent = jsonFactoryUtil.createJSONObject(${variableData})
${r"/>"}
<#else>
#set ($webcontent = $jsonFactoryUtil.createJSONObject($${variableData}))
</#if>

<a href="${getVariableReferenceCode(variableFriendlyUrl)}">
	${r"${webcontent.title}"}
</a>