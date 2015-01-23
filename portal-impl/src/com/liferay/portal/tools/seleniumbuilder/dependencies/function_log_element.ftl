<#if testCaseName??>
	selenium
<#else>
	liferaySelenium
</#if>

.sendActionLogger(

<#if functionElement.getName() == "execute">
	<#assign function = functionElement.attributeValue("function")>

	<#assign x = function?last_index_of("#")>

	<#assign functionCommand = function?substring(x + 1)>

	<#assign functionLocatorCount = seleniumBuilderContext.getFunctionLocatorCount(seleniumBuilderFileUtil.getObjectName(function?substring(0, x)))>

	"${seleniumBuilderFileUtil.getVariableName(function?substring(0, x))}Function#${functionCommand}",

	new String[] {

	<#list 1..functionLocatorCount as i>
		"",

		<#if functionElement.attributeValue("locator${i}")??>
			<#assign locator = functionElement.attributeValue("locator${i}")>

			<#if locator?contains("#")>
				<#assign y = locator?last_index_of("#")>

				<#assign pathLocatorKey = locator?substring(y + 1)>
				<#assign pathName = locator?substring(0, y)>

				RuntimeVariables.evaluateVariable(${pathName}Path.getPathLocator("${seleniumBuilderFileUtil.escapeJava(pathLocatorKey)}"), ${variableContext})
			<#else>
				RuntimeVariables.evaluateVariable("${seleniumBuilderFileUtil.escapeJava(locator)}", ${variableContext})
			</#if>
		<#else>
			""
		</#if>

		,

		<#if functionElement.attributeValue("value${i}")??>
			<#assign functionValue = functionElement.attributeValue("value${i}")>

			RuntimeVariables.evaluateVariable("${seleniumBuilderFileUtil.escapeJava(functionValue)}", ${variableContext})
		<#else>
			""
		</#if>

		<#if i_has_next>
			,
		</#if>
	</#list>

	})

	<#if functionElement.getName() == "execute">
		;
	</#if>
<#elseif functionElement.getName() == "echo" || functionElement.getName() == "fail">
	"${functionElement.getName()} message: \"" + RuntimeVariables.evaluateVariable("${message}", ${variableContext}) + "\"", new String[] {"", "", ""});
</#if>