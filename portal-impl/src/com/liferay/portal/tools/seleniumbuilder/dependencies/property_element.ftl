<#assign propertyName = propertyElement.attributeValue("name")>

<#assign propertyContext = variableContextStack.peek()>

<#if propertyElement.attributeValue("value")??>
	<#assign propertyValue = propertyElement.attributeValue("value")>
<#elseif propertyElement.getText()??>
	<#assign propertyValue = propertyElement.getText()>
</#if>

${propertyContext}.put("${propertyName}", RuntimeVariables.evaluateVariable("${seleniumBuilderFileUtil.escapeJava(propertyValue)}", ${propertyContext}));