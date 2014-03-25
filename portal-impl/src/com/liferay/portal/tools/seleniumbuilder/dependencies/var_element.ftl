<#assign varName = varElement.attributeValue("name")>

<#assign variableContext = variableContextStack.peek()>

<#if varElement.attributeValue("value")??>
	<#assign varValue = varElement.attributeValue("value")>
<#elseif varElement.getText()??>
	<#assign varValue = varElement.getText()>
</#if>

<#if varElement.attributeValue("method")??>
	<#assign methodValue = varElement.attributeValue("method")>
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
<#elseif methodValue??>
	<#assign z = methodValue?last_index_of("#")>
	<#assign methodCall = methodValue?substring(z + 1)>

	<#if "${methodValue}"?starts_with("StringUtil")>

		<#assign x = methodValue?last_index_of("(")>
		<#assign y = methodValue?last_index_of(")")>

		<#assign parameterValues  = methodValue?substring(x + 1, y)>

		<#assign methodType = methodValue?substring(z + 1, x)>

		${variableContext}.put("${varName}", StringUtil.${methodType}(

		<#list parameterValues?split(",") as parameter>
			<#assign result = parameter?replace("\"", "")>
			<#assign trimResult = result?trim>
			RuntimeVariables.evaluateVariable("${trimResult}", ${variableContext})
			<#if parameter_has_next>
				,
			</#if>
		</#list>

		));

	<#else>
		${variableContext}.put("${varName}", RuntimeVariables.evaluateVariable(${selenium}.${methodCall}, ${variableContext}));
	</#if>
<#else>
	${variableContext}.put("${varName}", RuntimeVariables.evaluateVariable("${seleniumBuilderFileUtil.escapeJava(varValue)}", ${variableContext}));
</#if>