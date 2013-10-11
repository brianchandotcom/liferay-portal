<html>
	<#include "head_html.ftl">

	<body>
		<div id="title">
			<h2>${seleniumBuilderContext.getTestSuiteClassName(testSuiteName)}</h2>
		</div>

		<div id="actionCommandLog">
		</div>

		<div id="seleniumCommandLog">
		</div>

		<div id="errorLog">
			<p><b id="errorCount">0</b> total error(s).</p>

			<p id="errorList">
			</p>
		</div>

		<div id="pageObjectXMLLog">
			<ul onclick="toggle(event);">
				<#assign lineFolds = 0>

				<#assign testSuiteRootElement = seleniumBuilderContext.getTestSuiteRootElement(testSuiteName)>

				<#assign testSuiteExecuteElements = testSuiteRootElement.elements("execute")>

				<#list testSuiteExecuteElements as testSuiteExecuteElement>
					<#if testSuiteExecuteElement.attributeValue("test-case")??>
						<#assign testCaseName = testSuiteExecuteElement.attributeValue("test-case")>

						<#include "test_case_element_html.ftl">
					<#elseif testSuiteExecuteElement.attributeValue("test-case-command")??>
						<#assign testCaseCommandAttribute = testSuiteExecuteElement.attributeValue("test-case-command")>

						<#include "test_case_command_element_html.ftl">
					<#elseif testSuiteExecuteElement.attributeValue("test-suite")??>
						<#assign testSuiteName = testSuiteExecuteElement.attributeValue("test-suite")>

						<#include "test_suite_element_html.ftl">
					</#if>
				</#list>
			</ul>
		</div>
	</body>
</html>