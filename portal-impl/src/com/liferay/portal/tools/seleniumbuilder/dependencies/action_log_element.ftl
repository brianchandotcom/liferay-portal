<#if testCaseName??>
	selenium
<#else>
	liferaySelenium
</#if>

.sendActionLogger(

<#if elementType == "action">
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
<#else>
	<#if elementType == "if">
		<#if ifElement.getName() == "while">
			"WHILE loop",
		<#else>
			"IF statement",
		</#if>
	<#elseif elementType = "and">
		"AND clause",
	<#elseif elementType = "contains">
		<#assign containsElement = ifConditionalElement>

		<#assign containsString = containsElement.attributeValue("string")>

		<#assign containsSubString = containsElement.attributeValue("substring")>

		"CONTAINS condition - string: '" +
		RuntimeVariables.evaluateVariable("${containsString}", commandScopeVariables) +
		"', substring: '" +
		RuntimeVariables.evaluateVariable("${containsSubString}", commandScopeVariables) +
		"'",
	<#elseif elementType = "equals">
		<#assign equalsElement = ifConditionalElement>

		<#assign equalsArg1 = equalsElement.attributeValue("arg1")>

		<#assign equalsArg2 = equalsElement.attributeValue("arg2")>

		"EQUALS condition - arg1: '" +
		RuntimeVariables.evaluateVariable("${equalsArg1}", commandScopeVariables) +
		"', arg2: '" +
		RuntimeVariables.evaluateVariable("${equalsArg2}", commandScopeVariables) +
		"'",
	<#elseif elementType = "isset">
		<#assign issetElement = ifConditionalElement>

		<#assign issetVar = issetElement.attributeValue("var")>

		"ISSET condition - var: '" +
		RuntimeVariables.evaluateVariable("${issetVar}", commandScopeVariables) +
		"'",
	<#elseif elementType = "for">
		<#assign forList = forElement.attributeValue("list")>

		<#assign forParam = forElement.attributeValue("param")>

		"FOR loop - list: '" +
		RuntimeVariables.evaluateVariable("${forList}", commandScopeVariables) +
		"', param: '" +
		RuntimeVariables.evaluateVariable("${forParam}", commandScopeVariables) +
		"'",
	<#elseif elementType = "echo">
		"ECHO - message: '" + RuntimeVariables.evaluateVariable("${message}", commandScopeVariables) + "'",
	<#elseif elementType = "fail">
		"FAIL - message: '" + RuntimeVariables.evaluateVariable("${message}", commandScopeVariables) + "'",
	<#elseif elementType = "or">
		"OR clause",
	<#elseif elementType = "not">
		"NOT clause",
	</#if>

	new String[] {
		"",
		""
	})

	<#assign nonConditionalClauseTypes = ["echo", "fail", "for", "if"]>

	<#if nonConditionalClauseTypes?seq_contains(elementType)>
		;
	</#if>
</#if>