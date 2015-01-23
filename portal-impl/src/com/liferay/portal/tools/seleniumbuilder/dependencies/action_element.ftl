<#assign action = actionElement.attributeValue("action")>

<#if actionElement.getName() == "execute" && action?contains("#is")>
	return
</#if>

<#assign x = action?last_index_of("#")>

<#assign actionCommand = action?substring(x + 1)>

<#assign functionLocatorCount = seleniumBuilderContext.getFunctionLocatorCount(seleniumBuilderFileUtil.getObjectName(actionCommand))>

<#if !action?contains("#is") && !action?ends_with("#confirm")>
	${seleniumBuilderFileUtil.getVariableName(action?substring(0, x))}Action.${actionCommand}Description(

	<#list 1..functionLocatorCount as i>
		<#if actionElement.attributeValue("locator${i}")??>
			<#assign actionLocator = actionElement.attributeValue("locator${i}")>

			RuntimeVariables.evaluateVariable("${actionLocator}", ${variableContext})
		<#else>
			null
		</#if>

		,

		<#if actionElement.attributeValue("locator-key${i}")??>
			<#assign actionLocatorKey = actionElement.attributeValue("locator-key${i}")>

			RuntimeVariables.evaluateVariable("${actionLocatorKey}", ${variableContext})
		<#else>
			""
		</#if>

		,

		<#if actionElement.attributeValue("value${i}")??>
			<#assign actionValue = actionElement.attributeValue("value${i}")>

			RuntimeVariables.evaluateVariable("${seleniumBuilderFileUtil.escapeJava(actionValue)}", ${variableContext})
		<#else>
			""
		</#if>

		<#if i_has_next>
			,
		</#if>
	</#list>

	, ${variableContext});
</#if>

${seleniumBuilderFileUtil.getVariableName(action?substring(0, x))}Action.${actionCommand}(
	<#list 1..functionLocatorCount as i>
		<#if actionElement.attributeValue("locator${i}")??>
			<#assign actionLocator = actionElement.attributeValue("locator${i}")>

			RuntimeVariables.evaluateVariable("${actionLocator}", ${variableContext})
		<#else>
			null
		</#if>

		,

		<#if actionElement.attributeValue("locator-key${i}")??>
			<#assign actionLocatorKey = actionElement.attributeValue("locator-key${i}")>

			RuntimeVariables.evaluateVariable("${actionLocatorKey}", ${variableContext})
		<#else>
			""
		</#if>

		,

		<#if actionElement.attributeValue("value${i}")??>
			<#assign actionValue = actionElement.attributeValue("value${i}")>

			RuntimeVariables.evaluateVariable("${seleniumBuilderFileUtil.escapeJava(actionValue)}", ${variableContext})
		<#else>
			""
		</#if>

		<#if i_has_next>
			,
		</#if>
	</#list>
, ${variableContext})

<#if actionElement.getName() == "execute">
	;
</#if>