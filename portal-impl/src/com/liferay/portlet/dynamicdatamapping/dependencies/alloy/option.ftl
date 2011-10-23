<#include "../init.ftl">

<#assign selected = (fieldStructure.value == fieldValue)>

<#if parentType == "select">
	<@aui.option cssClass=cssClass label=fieldStructure.label selected=selected value=fieldStructure.value />
<#else>
	<@aui.input checked=selected cssClass=cssClass label=fieldStructure.label name=namespacedParentName type="radio" value=fieldStructure.value />
</#if>