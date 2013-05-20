<html>
	<#include "head_html.ftl">

	<body>
		<h2>${seleniumBuilderContext.getTestSuiteClassName(testSuiteName)}</h2>

		<h3>WebDriver Log</h3>

		<div id="log">
		</div>

		<#assign testSuiteRootElement = seleniumBuilderContext.getTestSuiteRootElement(testSuiteName)>

		<#assign testSuiteExecuteElements = testSuiteRootElement.elements("execute")>

		<#list testSuiteExecuteElements as testSuiteExecuteElement>
			<#if testSuiteExecuteElement.attributeValue("test-case")??>
				<#assign testCaseName = testSuiteExecuteElement.attributeValue("test-case")>

				<#include "test_case_element_html.ftl">
			<#elseif testSuiteExecuteElement.attributeValue("test-suite")??>
				<#assign testSuiteName = testSuiteExecuteElement.attributeValue("test-suite")>

				<#include "test_suite_element_html.ftl">
			</#if>
		</#list>
	</body>
</html>