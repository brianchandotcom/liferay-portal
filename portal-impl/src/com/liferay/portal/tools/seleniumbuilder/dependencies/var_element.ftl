<#assign varName = varElement.attributeValue("name")>

<#assign variableContext = variableContextStack.peek()>

<#if varElement.attributeValue("value")??>
	<#assign varValue = varElement.attributeValue("value")>
<#elseif varElement.getText()??>
	<#assign varValue = varElement.getText()>
</#if>

<#if macroName??>
	<#assign selenium="liferaySelenium" />
<#else>
	<#assign selenium="selenium" />
</#if>

<#if varElement.attributeValue("locator-key")?? && varElement.attributeValue("path")??>
	<#assign pathRootElement = seleniumBuilderContext.getPathRootElement(varElement.attributeValue("path"))>

	<#assign locatorKey = varElement.attributeValue("locator-key")>

	<#assign locatorValue = seleniumBuilderContext.getPath(pathRootElement, locatorKey)>
<#elseif varElement.attributeValue("locator")??>
	<#assign locatorValue = varElement.attributeValue("locator")>

	<#if locatorValue?contains("#")>
		<#assign x = locatorValue?last_index_of("#")>

		<#assign pathRootElement = seleniumBuilderContext.getPathRootElement(locatorValue?substring(0, x))>

		<#assign locatorKey = locatorValue?substring(x + 1)>

		<#assign locatorValue = seleniumBuilderContext.getPath(pathRootElement, locatorKey)>
	</#if>
</#if>

<#if (varElement.attributeValue("locator-key")?? && varElement.attributeValue("path")??) ||
	  varElement.attributeValue("locator")??>

	<#if varElement.attributeValue("attribute")??>
		<#assign attributeName = varElement.attributeValue("attribute")>

		${variableContext}.put("${varName}", ${selenium}.getAttribute(RuntimeVariables.evaluateLocator("${locatorValue}", ${variableContext}) + "@" + RuntimeVariables.evaluateVariable("${attributeName}", ${variableContext})));
	<#else>
		<#if locatorValue?contains("/input")>
			<#assign seleniumMethod = "getValue" />
		<#else>
			<#assign seleniumMethod = "getText" />
		</#if>

		${variableContext}.put("${varName}", RuntimeVariables.evaluateVariable(${selenium}.${seleniumMethod}(RuntimeVariables.evaluateVariable("${locatorValue}", ${variableContext})), ${variableContext}));
	</#if>
<#elseif varElement.attributeValue("method")??>
	<#assign method = varElement.attributeValue("method")>

	<#assign x = method?last_index_of("#")>
	<#assign y = method?last_index_of("(")>
	<#assign z = method?last_index_of(")")>

	${variableContext}.put("${varName}",

	<#assign booleanMethodNames = [
		"contains", "endsWith", "equalsIgnoreBreakLine", "equalsIgnoreCase",
		"isAlertPresent", "isChecked", "isConfirmation", "isConsoleTextPresent",
		"isElementNotPresent", "isElementPresent", "isElementPresentAfterWait",
		"isIgnorableErrorLine", "isLowerCase", "isMobileDeviceEnabled",
		"isNotChecked", "isNotPartialText", "isNotText", "isNotValue",
		"isNotVisible", "isTCatEnabled", "isTextNotPresent", "isTextPresent",
		"isVisible", "isUpperCase", "matches", "matchesIgnoreCase", "startsWith"
	]>

	<#assign methodName = method?substring(x + 1, y)>

	<#assign methodReturnType = "String">

	<#list booleanMethodNames as booleanMethodName>
		<#if "${methodName}" == "${booleanMethodName}">
			<#assign methodReturnType = "Boolean">

			<#break>
		</#if>
	</#list>

	<#assign integerMethodNames = [
		"count", "difference", "product", "quotient","startsWithWeight", "sum"
	]>

	<#list integerMethodNames as integerMethodName>
		<#if "${methodName}" == "${integerMethodName}">
			<#assign methodReturnType = "Integer">

			<#break>
		</#if>
	</#list>

	<#if "${methodReturnType}" != "String">
		${methodReturnType}.toString(
	</#if>

	<#assign methodParameters  = method?substring(y + 1, z)>

	<#if "${method}"?starts_with("MathUtil")>
		<#assign objectName = "MathUtil">
	<#elseif "${method}"?starts_with("StringUtil")>
		<#assign objectName = "StringUtil">
	<#elseif "${method}"?starts_with("TestPropsUtil")>
		<#assign objectName = "TestPropsUtil">
	<#else>
		<#assign objectName = "${selenium}">
	</#if>

	${objectName}.${methodName}(
		<#if "${methodParameters}" != "">
			<#list methodParameters?split(",") as methodParameter>
				<#if "${method}"?starts_with("MathUtil")>
					Integer.parseInt(
				</#if>

				<#assign parameter = methodParameter?replace("\"", "")>

				<#assign parameter = parameter?trim>

				RuntimeVariables.evaluateVariable("${seleniumBuilderFileUtil.escapeHtml(parameter)}", ${variableContext})

				<#if "${method}"?starts_with("MathUtil")>
					)
				</#if>

				<#if methodParameter_has_next>
					,
				</#if>
			</#list>
		</#if>
	)

	<#if "${methodReturnType}" != "String">
		)
	</#if>

	);
<#elseif varElement.attributeValue("input")??>
	<#assign input = varElement.attributeValue("input")>

	<#if varElement.attributeValue("pattern")??>
		<#assign pattern = varElement.attributeValue("pattern")>
	</#if>

	<#if varElement.attributeValue("group")??>
		<#assign group = varElement.attributeValue("group")>
	</#if>

	${variableContext}.put("${varName}", RuntimeVariables.replaceRegularExpression(RuntimeVariables.evaluateVariable("${input}", ${variableContext}), "${pattern}", ${group}));
<#elseif varElement.attributeValue("property-value")??>
	<#assign propertyValue = varElement.attributeValue("property-value")?upper_case>

	${variableContext}.put("${varName}", TestPropsValues.${propertyValue?replace(".", "_")});
<#else>
	${variableContext}.put("${varName}", RuntimeVariables.evaluateVariable("${seleniumBuilderFileUtil.escapeJava(varValue)}", ${variableContext}));
</#if>