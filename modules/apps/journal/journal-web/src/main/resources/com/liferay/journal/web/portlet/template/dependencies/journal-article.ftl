<#include "init.ftl">

<#assign
	variableData = name + ".getData()"
	variableFriendlyUr = name + ".getFriendlyUrl()"
/>

<#if repeatable>
	<#assign
		variableData = "cur_" + variableData
		variableFriendlyUr = "cur_" + variableFriendlyUr
	/>
</#if>

<a href="${getVariableReferenceCode(variableFriendlyUr)}">
	${getVariableReferenceCode(variableData)}
</a>