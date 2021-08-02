<#-- Common -->

<#if repeatable>
	<#assign name = "cur_" + stringUtil.replace(name, ".", "_") />
</#if>

<#assign variableName = name + ".getData()" />

<#-- Util -->

<#function getVariableReferenceCode variableName>
	<#if stringUtil.equals(language, "ftl")>
		<#return "${" + variableName + "}">
	<#else>
		<#return "$" + variableName>
	</#if>
</#function>