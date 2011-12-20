<#include "../init.ftl">

<#assign selected = (fieldStructure.value == fieldRawValue)>

<#if parentType == "select">
	<#assign multiple = parentFieldStructure.multiple!"false">

	<#if multiple == "true">
		<#assign selected = (fieldRawValue?split(","))?seq_contains(fieldStructure.value)>
	</#if>

	<@aui.option cssClass=cssClass label=fieldStructure.label selected=selected value=fieldStructure.value />
<#else>
	<@aui.input checked=selected cssClass=cssClass label=fieldStructure.label name=namespacedParentName type="radio" value=fieldStructure.value />
</#if>