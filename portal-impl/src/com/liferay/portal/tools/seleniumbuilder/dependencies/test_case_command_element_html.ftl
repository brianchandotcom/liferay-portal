<#assign x = testCaseCommandAttribute?last_index_of("#")>

<#assign targetTestCaseName = testCaseCommandAttribute?substring(0, x)>

<#assign targetTestCaseCommand = testCaseCommandAttribute?substring(x + 1)>

<#assign testCaseRootElement = seleniumBuilderContext.getTestCaseRootElement(targetTestCaseName)>

<#assign testCaseCommandElements = testCaseRootElement.elements("command")>

<#list testCaseCommandElements as testCaseCommandElement>
	<#assign testCaseCommand = testCaseCommandElement.attributeValue("name")>

	<#if testCaseCommand == targetTestCaseCommand>
		<#assign testCaseName = targetTestCaseName>

		<#include "test_case_command_block_element_html.ftl">
	</#if>
</#list>