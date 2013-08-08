<#if testCaseCommandPhrase??>
	<#assign x = testCaseCommandPhrase?last_index_of("#")>

	<#assign testCaseName = testCaseCommandPhrase?substring(0, x)>

	<#assign desiredMethod = testCaseCommandPhrase?substring(x + 1)>

	<#assign testCaseCommandClassName = seleniumBuilderContext.getTestCaseClassName(testCaseName)>

	<#assign testCaseRootElement = seleniumBuilderContext.getTestCaseRootElement(testCaseName)>

	<#assign testCaseCommandElements = testCaseRootElement.elements("command")>

	<#list testCaseCommandElements as testCaseCommandElement>
		<#assign testCaseCommand = testCaseCommandElement.attributeValue("name")>

		<#if testCaseCommand == desiredMethod>
			<#include "test_case_command_element_html.ftl">
		</#if>
	</#list>
<#else>
	<#assign testCaseRootElement = seleniumBuilderContext.getTestCaseRootElement(testCaseName)>

	<#assign testCaseCommandElements = testCaseRootElement.elements("command")>

	<#list testCaseCommandElements as testCaseCommandElement>
		<#assign testCaseCommand = testCaseCommandElement.attributeValue("name")>

		<#include "test_case_command_element_html.ftl">
	</#list>
</#if>