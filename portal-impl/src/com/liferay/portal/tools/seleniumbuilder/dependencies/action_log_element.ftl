<#assign action = actionElement.attributeValue("action")>

<#assign x = action?last_index_of("#")>

<#assign actionCommand = action?substring(x + 1)>

<#assign functionName = seleniumBuilderFileUtil.getObjectName(actionCommand)>

<#list 1..seleniumBuilderContext.getFunctionLocatorCount(functionName) as i>
	<#if actionElement.attributeValue("locator-key${i}")??>
		<#assign actionLocatorKey = actionElement.attributeValue("locator-key${i}")>

		<#if actionLocatorKey?contains("${") && actionLocatorKey?contains("}")>
			<#assign actionLocatorKey = actionLocatorKey?replace("${", "\" + commandScopeVariables.get(\"")>

			<#assign actionLocatorKey = actionLocatorKey?replace("}", "\") + \"")>

		</#if>

		<#if actionElement.attributeValue("value${i}")??>
			<#assign actionValue = actionElement.attributeValue("value${i}")>

			<#if actionValue?contains("${") && actionValue?contains("}")>
				<#assign actionValue = actionValue?replace("${", "\" + commandScopeVariables.get(\"")>

				<#assign actionValue = actionValue?replace("}", "\") + \"")>
			</#if>

			<#if testCaseName??>
				selenium
			<#else>
				liferaySelenium
			</#if>
			.sendActionLogger(
				"${seleniumBuilderFileUtil.getVariableName(action?substring(0, x))}Action",
				"${actionCommand}", "${actionLocatorKey}", "${actionValue}")
			<#if actionElement.getName() == "execute">
				;
			</#if>
		<#else>
			<#if testCaseName??>
				selenium
			<#else>
				liferaySelenium
			</#if>
			.sendActionLogger(
				"${seleniumBuilderFileUtil.getVariableName(action?substring(0, x))}Action",
				"${actionCommand}", "${actionLocatorKey}", "")
			<#if actionElement.getName() == "execute">
				;
			</#if>
		</#if>
	<#else>
		<#if actionElement.attributeValue("value${i}")??>
			<#assign actionValue = actionElement.attributeValue("value${i}")>

			<#if actionValue?contains("${") && actionValue?contains("}")>
				<#assign actionValue = actionValue?replace("${", "\" + commandScopeVariables.get(\"")>

				<#assign actionValue = actionValue?replace("}", "\") + \"")>
			</#if>

			<#if testCaseName??>
				selenium
			<#else>
				liferaySelenium
			</#if>
			.sendActionLogger(
				"${seleniumBuilderFileUtil.getVariableName(action?substring(0, x))}Action",
				"${actionCommand}", "", "${actionValue}")
			<#if actionElement.getName() == "execute">
				;
			</#if>
		<#else>
			<#if testCaseName??>
				selenium
			<#else>
				liferaySelenium
			</#if>
			.sendActionLogger(
				"${seleniumBuilderFileUtil.getVariableName(action?substring(0, x))}Action",
				"${actionCommand}", "", "")
			<#if actionElement.getName() == "execute">
				;
			</#if>
		</#if>
	</#if>
</#list>