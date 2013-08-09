<#assign rootElement = seleniumBuilderContext.getTestSuiteRootElement(testSuiteName)>

<#assign executeElements = rootElement.elements("execute")>

<#list executeElements as executeElement>
	<#if executeElement.attributeValue("test-case")??>
		<#assign testCaseName = executeElement.attributeValue("test-case")>

		<#include "test_case_element_html.ftl">
	<#elseif executeElement.attributeValue("test-case-command")??>
		<#assign testCaseCommandAttribute = executeElement.attributeValue("test-case-command")>

		<#include "test_case_command_element_html.ftl">
	<#elseif executeElement.attributeValue("test-suite")??>
		<#assign testSuiteName = executeElement.attributeValue("test-suite")>

		<#include "test_suite_element_html.ftl">
	</#if>
</#list>