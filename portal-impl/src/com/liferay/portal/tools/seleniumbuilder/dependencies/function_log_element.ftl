<#if testCaseName??>
	selenium
<#else>
	liferaySelenium
</#if>

.sendActionLogger(

<#if (functionElement.getName() == "condition") || (functionElement.getName() == "execute")>
	<#assign function = functionElement.attributeValue("function")>

	<#assign x = function?last_index_of("#")>

	"${seleniumBuilderFileUtil.getObjectName(function?substring(0, x))}#${function?substring(x + 1)}",

	new String[] {

	<#assign functionLocatorCount = seleniumBuilderContext.getFunctionLocatorCount(seleniumBuilderFileUtil.getObjectName(function?substring(0, x)))>

	<#list 1..functionLocatorCount as i>
		"",

		<#if functionElement.attributeValue("locator${i}")??>
			<#assign locator = functionElement.attributeValue("locator${i}")>

			RuntimeVariables.evaluateVariable("${seleniumBuilderFileUtil.escapeJava(locator)}", ${variableContext})
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