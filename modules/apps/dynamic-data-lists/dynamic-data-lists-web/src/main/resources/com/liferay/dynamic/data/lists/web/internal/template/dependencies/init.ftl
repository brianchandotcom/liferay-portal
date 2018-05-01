<#-- Common -->

<#assign
	localeVariable = "locale"

	themeDisplayVariable = "themeDisplay"
/>

<#if stringUtil.equals(language, "vm")>
	<#assign
		localeVariable = "$" + localeVariable

		themeDisplayVariable = "$" + themeDisplayVariable
	/>
</#if>

<#-- Field Value -->

<#assign fieldValueVariable = "cur_record.getDDMFormFieldValues(\"" + name + "\")?first" />

<#if stringUtil.equals(language, "vm")>
	<#assign fieldValueVariable = "$cur_record.getDDMFormFieldValues(\"" + name + "\").get(0)" />
</#if>

<#-- Util -->

<#function getVariableReferenceCode variableName>
	<#if stringUtil.equals(language, "ftl")>
		<#return "${" + variableName + "}">
	<#else>
		<#return "$" + variableName>
	</#if>
</#function>