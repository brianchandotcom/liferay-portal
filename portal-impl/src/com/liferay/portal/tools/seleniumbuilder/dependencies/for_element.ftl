<#assign forList = forElement.attributeValue("list")>

<#assign forParam = forElement.attributeValue("param")>

forScopeVariables = new HashMap<String, String>();

forScopeVariables.putAll(executeScopeVariables);

<#assign forListArray = stringUtil.split("${forList}", ",")>

<#assign forListString = "">

<#list forListArray as forListItem>
	<#assign forListString = "${forListString}${forListItem?trim}">

	<#if forListItem_has_next>
		<#assign forListString = "${forListString},">
	</#if>
</#list>

if (!RuntimeVariables.evaluateVariable("${forListString}", executeScopeVariables).equals("")) {
	for (String ${forParam} : RuntimeVariables.evaluateVariable("${forListString}", executeScopeVariables).split(",")) {
		forScopeVariables.put("${forParam}", ${forParam});

		<#assign void = forParameterStack.push("${forParam}")>

		<#assign lineNumber = forElement.attributeValue("line-number")>

		<#assign blockElement = forElement>

		<#assign void = variableContextStack.push("forScopeVariables")>

		<#include "block_element.ftl">

		<#assign void = variableContextStack.pop()>
	}
}

<#assign forParam = forParameterStack.pop()>

commandScopeVariables.putAll(forScopeVariables);

commandScopeVariables.remove("${forParam}");