<#include "../init.ftl">

<#if !(fields?? && fields.get(fieldName)??) && (fieldRawValue == "")>
	<#assign fieldRawValue = predefinedValue>
</#if>

<#if fieldRawValue == "">
	<#assign fieldRawValue = "[]">
</#if>

<#assign selected = jsonFactoryUtil.looseDeserialize(fieldRawValue)?seq_contains(fieldStructure.value)>

<#if parentType == "select">
	<@aui.option cssClass=cssClass label=fieldStructure.label selected=selected value=fieldStructure.value />
<#else>
	<@aui.input checked=selected cssClass=cssClass label=fieldStructure.label name=namespacedParentName type="radio" value=fieldStructure.value />
</#if>