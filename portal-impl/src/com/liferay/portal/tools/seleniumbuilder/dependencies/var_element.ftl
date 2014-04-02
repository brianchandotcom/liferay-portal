<#assign varName = varElement.attributeValue("name")>

<#assign variableContext = variableContextStack.peek()>

<#if varElement.attributeValue("value")??>
	<#assign varValue = varElement.attributeValue("value")>
<#elseif varElement.getText()??>
	<#assign varValue = varElement.getText()>
</#if>

<#if varElement.attributeValue("locator-key")?? && varElement.attributeValue("path")??>
	<#assign pathRootElement = seleniumBuilderContext.getPathRootElement(varElement.attributeValue("path"))>

	<#assign locatorKey = varElement.attributeValue("locator-key")>

	<#assign locatorValue = seleniumBuilderContext.getPath(pathRootElement, locatorKey)>

	<#if macroName??>
		<#assign selenium="liferaySelenium" />
	<#else>
		<#assign selenium="selenium" />
	</#if>
<#elseif varElement.attributeValue("locator")??>
	<#assign locatorValue = varElement.attributeValue("locator")>
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

	<#if "${method}"?starts_with("StringUtil")>
		<#assign objectName = "StringUtil">
	<#else>
		<#assign objectName = "${selenium}">
	</#if>

	<#assign x = method?last_index_of("#")>
	<#assign y = method?last_index_of("(")>
	<#assign z = method?last_index_of(")")>

	<#assign methodParameters  = method?substring(y + 1, z)>

	<#assign booleanMethods = ["contains", "endsWith", "equalsIgnoreBreakLine", "equalsIgnoreCase",
								"isAlertPresent", "isChecked", "isConfirmation", "isConsoleTextPresent",
								"isElementNotPresent", "isElementPresentAfterWait", "isElementPresent",
								"isIgnorableErrorLine", "isLowerCase", "isNotChecked", "isNotPartialText",
								"isNotText", "isNotValue", "isNotVisible", "isTextNotPresent", "isTextPresent",
								"isVisible", "isUpperCase", "matches", "matchesIgnoreCase", "startsWith"]>

	<#list booleanMethods as booleanMethod>
		<#if ("${method?substring(x + 1, y)}" = "${booleanMethod}")>
			<#assign methodType = "Boolean.toString(">
		<#elseif ("${method?substring(x + 1, y)}" = "count") ||
				 ("${method?substring(x + 1, y)}" = "startsWithWeight")
		>
			<#assign methodType = "Integer.toString(">
		</#if>
	</#list>

	<#if methodType??>
		${variableContext}.put("${varName}", ${methodType}${objectName}.${method?substring(x + 1, y)}(
	<#else>
		${variableContext}.put("${varName}", ${objectName}.${method?substring(x + 1, y)}(
	</#if>

	<#if "${methodParameters}" != "">
		<#list methodParameters?split(",") as methodParameter>
			<#assign parameter = methodParameter?replace("\"", "")>

			<#assign parameter = parameter?trim>

			RuntimeVariables.evaluateVariable("${parameter}", ${variableContext})

			<#if methodParameter_has_next>
				,
			</#if>
		</#list>
	</#if>

	<#if methodType??>
	)
	</#if>
	));
<#else>
	${variableContext}.put("${varName}", RuntimeVariables.evaluateVariable("${seleniumBuilderFileUtil.escapeJava(varValue)}", ${variableContext}));
</#if>