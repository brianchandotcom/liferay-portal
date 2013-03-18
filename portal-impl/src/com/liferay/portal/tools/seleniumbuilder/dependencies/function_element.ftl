<#assign function = functionElement.attributeValue("function")>

<#assign x = function?last_index_of("#")>

${seleniumBuilderFileUtil.getVariableName(function?substring(0, x))}Function.${function?substring(x + 1)}(

<#list 1..seleniumBuilderContext.getFunctionLocatorCount(functionName) as i>
	locator${i}, value${i}

	<#if i_has_next>
		,
	</#if>
</#list>

)