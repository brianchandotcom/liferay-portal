<#assign selenium = seleniumElement.attributeValue("selenium")>

<#if seleniumElement.getName() == "execute" && selenium?starts_with("is")>
	return
</#if>

liferaySelenium.${selenium}(

<#if seleniumBuilderContext.getSeleniumParameterCount(selenium) gte 1>
	<#if
		(selenium = "assertConfirmation") ||
		(selenium = "assertConsoleTextNotPresent") ||
		(selenium = "assertConsoleTextPresent") ||
		(selenium = "assertLocation") ||
		(selenium = "assertTextNotPresent") ||
		(selenium = "assertTextPresent") ||
		(selenium = "waitForConfirmation") ||
		(selenium = "waitForTextNotPresent") ||
		(selenium = "waitForTextPresent")
	>
		value1
	<#elseif selenium = "assertJavaScriptErrors">
		ignoreJavaScriptError
	<#elseif seleniumElement.attributeValue("argument1")??>
		<#assign argument1 = seleniumElement.attributeValue("argument1")>

		<#if argument1?contains("${") && argument1?contains("}")>
			<#assign x = argument1?index_of("${")>
			<#assign y = argument1?index_of("}")>

			"${argument1?substring(0, x)}" + ${argument1?substring(x + 2, y)} + "${argument1?substring(y + 1)}"
		<#else>
			"${argument1}"
		</#if>
	<#else>
		locator1
	</#if>
</#if>

<#if seleniumBuilderContext.getSeleniumParameterCount(selenium) == 2>
	,
	<#if seleniumElement.attributeValue("argument2")??>
		<#assign argument2 = seleniumElement.attributeValue("argument2")>

		<#if argument2?contains("${") && argument2?contains("}")>
			<#assign x = argument2?index_of("${")>
			<#assign y = argument2?index_of("}")>

			"${argument2?substring(0, x)}" + ${argument2?substring(x + 2, y)} + "${argument2?substring(y + 1)}"
		<#else>
			"${argument2}"
		</#if>
	<#else>
		value1
	</#if>
</#if>

)

<#if seleniumElement.getName() == "execute">
	;
</#if>