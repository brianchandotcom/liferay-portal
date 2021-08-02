<#include "init.ftl">

<#assign variableName = name + ".getFriendlyUrl()" />

<a data-senna-off="true" href="${getVariableReferenceCode(variableName)}">
	${label}
</a>