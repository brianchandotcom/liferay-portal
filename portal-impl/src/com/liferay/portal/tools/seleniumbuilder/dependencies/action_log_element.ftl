<#assign action = actionElement.attributeValue("action")>

<#assign x = action?last_index_of("#")>

<#assign actionCommand = action?substring(x + 1)>

<#assign functionName = seleniumBuilderFileUtil.getObjectName(actionCommand)>

<#if testCaseName??>
	selenium
<#else>
	liferaySelenium
</#if>

.sendActionLogger(

"${seleniumBuilderFileUtil.getVariableName(action?substring(0, x))}Action#${actionCommand}",

new String[] {

<#list 1..seleniumBuilderContext.getFunctionLocatorCount(functionName) as i>
	<#if actionElement.attributeValue("locator${i}")??>
		<#assign actionLocator = actionElement.attributeValue("locator${i}")>

		<#if actionLocator?contains("${") && actionLocator?contains("}")>
			<#assign actionLocator = actionLocator?replace("${", "\" + commandScopeVariables.get(\"")>

			<#assign actionLocator = actionLocator?replace("}", "\") + \"")>
		</#if>

		"${actionLocator}"
	<#else>
		""
	</#if>

	,

	<#if actionElement.attributeValue("locator-key${i}")??>
		<#assign actionLocatorKey = actionElement.attributeValue("locator-key${i}")>

		<#if actionLocatorKey?contains("${") && actionLocatorKey?contains("}")>
			<#assign actionLocatorKey = actionLocatorKey?replace("${", "\" + commandScopeVariables.get(\"")>

			<#assign actionLocatorKey = actionLocatorKey?replace("}", "\") + \"")>
		</#if>

		"${actionLocatorKey}"
	<#else>
		""
	</#if>

	,

	<#if actionElement.attributeValue("value${i}")??>
		<#assign actionValue = actionElement.attributeValue("value${i}")>

		<#if actionValue?contains("${") && actionValue?contains("}")>
			<#assign actionValue = actionValue?replace("${", "\" + commandScopeVariables.get(\"")>

			<#assign actionValue = actionValue?replace("}", "\") + \"")>
		</#if>

		"${actionValue}"
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