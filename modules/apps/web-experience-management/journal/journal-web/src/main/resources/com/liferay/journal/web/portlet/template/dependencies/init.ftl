<#-- Common -->

<#assign variableName = name + ".getData()">

<#if repeatable>
	<#assign variableName = "cur_" + variableName>
</#if>

<#-- Util -->

<#function getVariableReferenceCode variableName>
	<#if language == "ftl">
		<#return "${" + variableName + "}">
	<#else>
		<#return "$" + variableName>
	</#if>
</#function>