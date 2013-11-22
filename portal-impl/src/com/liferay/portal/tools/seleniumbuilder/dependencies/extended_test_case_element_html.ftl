<#assign displayElement = testCaseExecuteElement>

<#assign lineNumber = testCaseExecuteElement.attributeValue("line-number")>

<li id="${testCaseName?uncap_first}TestCase${lineNumber}">
	<#include "element_open_html.ftl">

	<#assign testCase = testCaseExecuteElement.attributeValue("test-case")>

	<#assign x = testCase?last_index_of("#")>

	<#assign currentTestCaseCommand = testCase?substring(x + 1)>

	<#if currentTestCaseCommand == "set-up">
		<#assign void = blockLevelStack.push("testcase")>

		<#assign blockElement = extendedTestCaseRootElement.element("set-up")>

		<#include "block_element_html.ftl">

	<#elseif currentTestCaseCommand == "tear-down">
		<#assign void = blockLevelStack.push("testcase")>

		<#assign blockElement = extendedTestCaseRootElement.element("tear-down")>

		<#include "block_element_html.ftl">

		<#assign void = blockLevelStack.pop()>
	<#else>
		<#assign extendedTestCaseCommandElements = extendedTestCaseRootElement.elements("command")>

		<#list extendedTestCaseCommandElements as extendedTestCaseCommandElement>
			<#assign extendedTestCaseCommandName = extendedTestCaseCommandElement.attributeValue("name")>

			<#if extendedTestCaseCommandName == currentTestCaseCommand>
				<#assign void = blockLevelStack.push("testcase")>

				<#assign blockElement = extendedTestCaseCommandElement>

				<#include "block_element_html.ftl">

				<#assign void = blockLevelStack.pop()>

				<#break>
			</#if>
		</#list>
	</#if>

	<#assign displayElement = testCaseExecuteElement>

	<#include "element_close_html.ftl">
</li>