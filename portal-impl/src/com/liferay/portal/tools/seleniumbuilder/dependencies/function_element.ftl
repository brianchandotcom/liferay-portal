<#assign function = functionElement.attributeValue("function")>

<#if functionElement.getName() == "execute" && function?starts_with("Is")>
	return
</#if>

<#assign x = function?last_index_of("#")>

${seleniumBuilderFileUtil.getVariableName(function?substring(0, x))}Function.${function?substring(x + 1)}(

<#if functionElement.attributeValue("ignore-javascript-error")??>
	<#assign ignoreJavaScriptError = functionElement.attributeValue("ignore-javascript-error")>

	"${ignoreJavaScriptError}",
<#elseif actionName??>
	null,
<#else>
	ignoreJavaScriptError,
</#if>

<#assign functionLocatorCount = seleniumBuilderContext.getFunctionLocatorCount(seleniumBuilderFileUtil.getObjectName(function?substring(0, x)))>

<#list 1..functionLocatorCount as i>
	locator${i},

	<#if functionElement.attributeValue("value${i}")??>
		<#assign functionValue = functionElement.attributeValue("value${i}")>

		"${functionValue}"
	<#else>
		value${i}
	</#if>

	<#if i_has_next>
		,
	</#if>
</#list>

)

<#if functionElement.getName() == "execute">
	;
</#if>