<#assign displayElement = testCaseExecuteElement>

<#assign lineNumber = testCaseExecuteElement.attributeValue("line-number")>

<li id="${testCaseName?uncap_first}TestCase${lineNumber}">
	<#include "element_open_html.ftl">

	<#assign testCase = testCaseExecuteElement.attributeValue("test-case")>

	<#assign x = testCase?last_index_of("#")>

	<#assign currentTestCaseCommand = testCase?substring(x + 1)>

	<#if currentTestCaseCommand == "set-up">
		<#assign testCaseBlockElement = extendedTestCaseRootElement.element("set-up")>

		<#include "test_case_block_element_html.ftl">
	<#elseif currentTestCaseCommand == "tear-down">
		<#assign testCaseBlockElement = extendedTestCaseRootElement.element("tear-down")>

		<#include "test_case_block_element_html.ftl">
	<#else>
		<#assign extendedTestCaseCommandElements = extendedTestCaseRootElement.elements("command")>

		<#list extendedTestCaseCommandElements as extendedTestCaseCommandElement>
			<#assign extendedTestCaseCommandName = extendedTestCaseCommandElement.attributeValue("name")>

			<#if extendedTestCaseCommandName == currentTestCaseCommand>
				<#assign testCaseBlockElement = extendedTestCaseCommandElement>

				<#include "test_case_block_element_html.ftl">

				<#break>
			</#if>
		</#list>
	</#if>

	<#assign displayElement = testCaseExecuteElement>

	<#include "element_close_html.ftl">
</li>