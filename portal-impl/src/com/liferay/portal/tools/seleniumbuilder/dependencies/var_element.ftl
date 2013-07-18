<#assign varName = varElement.attributeValue("name")>

<#if varElement.attributeValue("value")??>
	<#assign varValue = varElement.attributeValue("value")>
<#elseif varElement.getText()??>
	<#assign varValue = varElement.getText()>
</#if>

<#if varElement.attributeValue("path")??>
	<#if varElement.attributeValue("locator-key")??>
		<#assign pathRootElement = seleniumBuilderContext.getPathRootElement(varElement.attributeValue("path"))>

		<#assign locatorKey = varElement.attributeValue("locator-key")>

		<#assign locatorValue = seleniumBuilderContext.getPath(pathRootElement, locatorKey)>

		<#assign seleniumMethod = "getText" />

		<#if varElement.attributeValue("type")??>
			<#assign typeAttribute = varElement.attributeValue("type")>

			<#if typeAttribute == "value">
				<#assign seleniumMethod = "getValue" />
			<#elseif typeAttribute == "text">
				<#assign seleniumMethod = "getText" />
			</#if>
		</#if>

		${context}.put("${varName}", BaseTestCase.evaluateVariable(${selenium}.${seleniumMethod}("${locatorValue}"), ${context}));

	</#if>
<#else>
	${context}.put("${varName}", RuntimeVariables.evaluateVariable("${varValue}", ${context}));
</#if>