<#assign function = functionElement.attributeValue("function")>

<#if functionElement.getName() == "execute" && function?starts_with("Is")>
	return
</#if>

<#assign x = function?last_index_of("#")>

${seleniumBuilderFileUtil.getVariableName(function?substring(0, x))}Function.${function?substring(x + 1)}(

<#if functionElement.attributeValue("ignore-javascript-error")??>
	<#assign ignoreJavaScriptError = functionElement.attributeValue("ignore-javascript-error")>

	"${ignoreJavaScriptError}",
<#elseif functionPathName??>
	null,
<#else>
	ignoreJavaScriptError,
</#if>

<#list 1..seleniumBuilderContext.getFunctionLocatorCount(functionPathName) as i>
	<#if functionElement.attributeValue("locator${i}")??>
		<#assign locator = functionElement.attributeValue("locator${i}")>

		<#if locator?contains("#")>
			<#assign y = locator?last_index_of("#")>

			<#assign pathLocatorKey = locator?substring(y + 1)>
			<#assign pathName = locator?substring(0, y)>

			${pathName}Path.getPathLocator("${pathLocatorKey}"),
		<#else>
			"${locator}",
		</#if>
	<#else>
		locator${i},
	</#if>

	<#if functionElement.attributeValue("value${i}")??>
		<#assign functionValue = functionElement.attributeValue("value${i}")>

		"${functionValue}"
	<#else>
		null
	</#if>

	<#if i_has_next>
		,
	</#if>
</#list>

)

<#if functionElement.getName() == "execute">
	;
</#if>