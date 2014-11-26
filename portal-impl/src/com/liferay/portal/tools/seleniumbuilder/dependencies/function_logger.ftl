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
		<#assign fullFunctionPath = functionElement.attributeValue("locator${i}")>

		<#if fullFunctionPath?contains("#")>
			<#assign y = fullFunctionPath?last_index_of("#")>

			<#assign functionPath = fullFunctionPath?substring(0, y)>
			<#assign functionPathLocator = fullFunctionPath?substring(y + 1)>

			${functionPath}Path.getPathLocator("${functionPathLocator}"),
		<#else>
			"${fullFunctionPath}",
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