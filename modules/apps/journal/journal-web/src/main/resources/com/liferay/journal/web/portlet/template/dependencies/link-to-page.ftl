<#include "init.ftl">

<#assign variableName = name + ".getFriendlyUrl()" />

<#if repeatable>
	<#assign variableName = "cur_" + variableName />
</#if>

<a href="${getVariableReferenceCode(variableName)}">
	${label}
</a>