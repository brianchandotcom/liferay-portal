<#assign forList = forElement.attributeValue("list")>

<#assign forParam = forElement.attributeValue("param")>

if (!RuntimeVariables.evaluateVariable("${forList}", executeScopeVariables).equals("")) {
	for (String ${forParam} : RuntimeVariables.evaluateVariable("${forList}", executeScopeVariables).split(",")) {
		RuntimeVariables.setForParameter("${forParam}", ${forParam});

		<#assign lineNumber = forElement.attributeValue("line-number")>

		<#assign blockElement = forElement>

		<#include "block_element.ftl">
	}
}

RuntimeVariables.resetForParameter();