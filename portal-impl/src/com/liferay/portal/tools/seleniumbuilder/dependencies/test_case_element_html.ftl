<#assign testCaseRootElement = seleniumBuilderContext.getTestCaseRootElement(testCaseName)>

<#assign testCaseCommandElements = testCaseRootElement.elements("command")>

<#list testCaseCommandElements as testCaseCommandElement>
	<#assign testCaseCommand = testCaseCommandElement.attributeValue("name")>

	<#include "test_case_command_block_element_html.ftl">
</#list>