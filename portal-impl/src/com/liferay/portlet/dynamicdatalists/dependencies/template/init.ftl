<#-- Common -->

<#assign localeVariable = "locale">

<#if language == "vm">
	<#assign localeVariable = "$" + localeVariable>
</#if>

<#-- Field -->

<#assign fieldType = "cur_record.getFieldType(\"" + name + "\")">

<#assign fieldValue = "cur_record.getFieldValue(\"" + name + "\")">

<#if repeatable>
	<#assign fieldValue = "curValue_" + name>
</#if>

<#-- Util -->

<#function getVariableReferenceCode variableName>
	<#if language == "ftl">
		<#return "${" + variableName + "}">
	<#else>
		<#return "$" + variableName>
	</#if>
</#function>