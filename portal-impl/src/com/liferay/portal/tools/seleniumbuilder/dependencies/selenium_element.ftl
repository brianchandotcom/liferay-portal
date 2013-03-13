<#assign selenium = seleniumElement.attributeValue("selenium")>

liferaySelenium.${selenium}(

<#if seleniumBuilderContext.getSeleniumParameterCount(selenium) \gte 1>
	<#if selenium = "assertConfirmation" || selenium = "assertTextNotPresent" || selenium = "assertTextPresent" || selenium = "waitForConfirmation" || selenium = "waitForTextNotPresent" || selenium = "waitForTextPresent">
		value1
	<#elseif seleniumElement.attributeValue("target")??>
		<#assign target = seleniumElement.attributeValue("target")>

		<#if target?starts_with("${")>
			${target?substring(2, target?length - 1)}
		<#else>
			"${target}"
		</#if>
	<#else>
		target1
	</#if>
</#if>

<#if seleniumBuilderContext.getSeleniumParameterCount(selenium) == 2>
	,
	<#if seleniumElement.attributeValue("value")??>
		<#assign value = seleniumElement.attributeValue("value")>

		<#if value?starts_with("${")>
			${value?substring(2, value?length - 1)}
		<#else>
			"${value}"
		</#if>
	<#else>
		value1
	</#if>
</#if>

)