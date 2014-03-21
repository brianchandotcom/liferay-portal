<#assign void = testCaseNameStack.push(testCaseName)>

<#assign testCaseRootElement = seleniumBuilderContext.getTestCaseRootElement(testCaseName)>

<#if extendedTestCase??>
	<#assign extendedTestCaseRootElement = seleniumBuilderContext.getTestCaseRootElement(extendedTestCase)>
</#if>

<#if testCaseCommandName??>
	<#include "test_case_command_element_html.ftl">
<#else>
	<#assign testCaseCommandNames = seleniumBuilderContext.getTestCaseCommandNames(testCaseName)>

	<#list testCaseCommandNames as testCaseCommandName>
		<#include "test_case_command_element_html.ftl">
	</#list>
</#if>

<#assign void = testCaseNameStack.pop()>