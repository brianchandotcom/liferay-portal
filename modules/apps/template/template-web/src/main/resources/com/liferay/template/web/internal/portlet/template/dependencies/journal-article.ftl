<#include "init.ftl">

<#assign
	variableData = name + ".getData()"
	variableFriendlyUrl = name + ".getFriendlyUrl()"
/>

${r"<#assign"}
	webContentData = jsonFactoryUtil.createJSONObject(${variableData})
${r"/>"}

${r"<#if"} webContentData?? && webContentData.title??>
	<a href="${getVariableReferenceCode(variableFriendlyUrl)}">
		${r"${webContentData.title}"}
	</a>
${r"</#if>"}