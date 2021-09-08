<#-- Common -->

<#if repeatable>
	<#assign name = "cur_" + stringUtil.replace(name, ".", "_") />
</#if>

<#assign variableName = name + ".getData()" />

<#-- Util -->

<#function getVariableReferenceCode variableName>
	<#return "${" + variableName + "}">
</#function>