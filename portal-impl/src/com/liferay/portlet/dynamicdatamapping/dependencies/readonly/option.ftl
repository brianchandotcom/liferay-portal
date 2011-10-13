<#include "../init.ftl">

<#assign selected = (fieldStructure.value == fieldValue)>

<#if selected>
	${fieldStructure.label}
</#if>