<#assign action = actionElement.attributeValue("action")>

<#if actionElement.getName() == "execute" && action?contains("#is")>
	return
</#if>

<#assign x = action?last_index_of("#")>

<#assign actionCommand = action?substring(x + 1)>

${seleniumBuilderFileUtil.getVariableName(action?substring(0, x))}Action.${actionCommand}(
	<#assign functionName = seleniumBuilderFileUtil.getObjectName(actionCommand)>

	<#list 1..seleniumBuilderContext.getFunctionLocatorCount(functionName) as i>
		<#if actionElement.attributeValue("locator${i}")??>
			<#assign actionLocator = actionElement.attributeValue("locator${i}")>

			RuntimeVariables.evaluateVariable("${actionLocator}", commandScopeVariables)
		<#else>
			null
		</#if>

		,

		<#if actionElement.attributeValue("locator-key${i}")??>
			<#assign actionLocatorKey = actionElement.attributeValue("locator-key${i}")>

			RuntimeVariables.evaluateVariable("${actionLocatorKey}", commandScopeVariables)
		<#else>
			""
		</#if>

		,

		<#if actionElement.attributeValue("value${i}")??>
			<#assign actionValue = actionElement.attributeValue("value${i}")>

			RuntimeVariables.evaluateVariable("${seleniumBuilderFileUtil.escapeJava(actionValue)}", commandScopeVariables)
		<#else>
			""
		</#if>

		<#if i_has_next>
			,
		</#if>
	</#list>
, commandScopeVariables)

<#if actionElement.getName() == "execute">
	;

	<#if
		(actionNextElement??) &&
		(!action?contains("#is"))
	>
		<#if actionNextElement.attributeValue("action")??>
			<#assign actionNext = actionNextElement.attributeValue("action")>
		</#if>

		<#if (!actionNext??) ||
			 ((actionNext??) && (!actionNext?ends_with("#confirm")))>

			<#if testCaseName??>
				selenium
			<#else>
				liferaySelenium
			</#if>

			.assertJavaScriptErrors();

			<#if testCaseName??>
				selenium
			<#else>
				liferaySelenium
			</#if>

			.assertLiferayErrors();
		</#if>
	</#if>
</#if>