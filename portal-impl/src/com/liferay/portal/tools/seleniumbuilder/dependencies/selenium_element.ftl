<#assign selenium = seleniumElement.attributeValue("selenium")>

<#if seleniumElement.getName() == "execute" && selenium?starts_with("is")>
	return
</#if>

liferaySelenium.${selenium}(

<#if seleniumBuilderContext.getSeleniumParameterCount(selenium) gte 1>
	<#if
		(selenium = "assertConfirmation") ||
		(selenium = "assertLocation") ||
		(selenium = "assertTextNotPresent") ||
		(selenium = "assertTextPresent") ||
		(selenium = "waitForConfirmation") ||
		(selenium = "waitForTextNotPresent") ||
		(selenium = "waitForTextPresent")
	>
		value1
	<#elseif seleniumElement.attributeValue("argument1")??>
		<#assign argument1 = seleniumElement.attributeValue("argument1")>

		<#if argument1?starts_with("${")>
			${argument1?substring(2, argument1?length - 1)}
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

		<#if argument2?starts_with("${")>
			${argument2?substring(2, argument2?length - 1)}
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