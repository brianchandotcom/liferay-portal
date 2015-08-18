<#-- Common -->

<#assign localeVariable = "locale">

<#if language == "vm">
	<#assign localeVariable = "$" + localeVariable>
</#if>

<#-- Util -->
<#function getVariableReferenceCode variableName>
	<#if language == "ftl">
		<#return "${" + variableName + "}">
	<#else>
		<#return "$" + variableName>
	</#if>
</#function>

<#function render>
	<#if language == "ftl">
		<#return "${" + "ddlHelper.render(cur_record, \"" + name + "\", " + localeVariable + ")}">
	<#else>
		<#return "$ddlHelper.render($cur_record, \"" + name + "\", " + localeVariable + ")">
	</#if>
</#function>