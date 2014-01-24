<#assign forList = forElement.attributeValue("list")>

<#assign forParam = forElement.attributeValue("param")>

forScopeVariables = new HashMap<String, String>();

forScopeVariables.putAll(executeScopeVariables);

if (!RuntimeVariables.evaluateVariable("${forList}", executeScopeVariables).equals("")) {
	for (String ${forParam} : RuntimeVariables.evaluateVariable("${forList}", executeScopeVariables).split(",")) {
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