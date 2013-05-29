<#-- Common Variable -->

<#assign variableName = name + ".getData()">

<#if isRepeatable>
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