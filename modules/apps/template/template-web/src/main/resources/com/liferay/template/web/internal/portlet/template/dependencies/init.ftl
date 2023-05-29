<#-- Common -->

<#if repeatable>
	<#assign name = "cur_" + stringUtil.replace(name, ".", "_") />
</#if>

<#if stringUtil.contains(name, "#", "") || stringUtil.contains(name, "@", "")>
	<#assign name = ".data_model[\"" + name + "\"]" />
</#if>

<#assign variableName = name + ".getData()" />

<#-- Util -->

<#function getVariableReferenceCode variableName>
	<#return "${" + variableName + "}">
</#function>