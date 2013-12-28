<#if testCaseName??>
	selenium
<#else>
	liferaySelenium
</#if>

.sendActionLogger(

<#if actionElement.getName() == "condition" || actionElement.getName() == "execute">
	<#assign action = actionElement.attributeValue("action")>

	<#assign x = action?last_index_of("#")>

	<#assign actionCommand = action?substring(x + 1)>

	<#assign functionName = seleniumBuilderFileUtil.getObjectName(actionCommand)>

	"${seleniumBuilderFileUtil.getVariableName(action?substring(0, x))}Action#${actionCommand}",

	new String[] {

	<#list 1..seleniumBuilderContext.getFunctionLocatorCount(functionName) as i>
		<#if actionElement.attributeValue("locator${i}")??>
			<#assign actionLocator = actionElement.attributeValue("locator${i}")>

			RuntimeVariables.evaluateVariable("${actionLocator}", commandScopeVariables)
		<#else>
			""
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

	})

	<#if actionElement.getName() == "execute">
		;
	</#if>
<#elseif actionElement.getName() == "echo" || actionElement.getName() == "fail">
	"${actionElement.getName()} message: '" + RuntimeVariables.evaluateVariable("${message}", commandScopeVariables) + "'",

	new String[] {"", "", ""});
</#if>