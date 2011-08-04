<#include "../init.ftl">

<#assign selected = (field.value == fieldValue)>

<#if parentType == "select">
	<@aui.option cssClass=cssClass label=field.label selected=selected value=field.value />
<#else>
	<@aui.input checked=selected cssClass=cssClass label=field.label name=parentName type="radio" value=field.value />
</#if>