<#assign displayElement = currentTestCaseElement>

<#assign lineNumber = currentTestCaseElement.attributeValue("line-number")>

<li id="${testCaseName?uncap_first}TestCase${lineNumber}">

<#include "element_open_html.ftl">

<#assign currentTestCase = currentTestCaseElement.attributeValue("test-case")>

<#assign x = currentTestCase?last_index_of("#")>

<#assign currentTestCaseCommand = currentTestCase?substring(x + 1)>

<#if currentTestCaseCommand == "set-up">
	<#assign extendedTestCaseSetUpElement = extendedTestCaseRootElement.element("set-up")>

	<#assign testCaseBlockElement = extendedTestCaseSetUpElement>

	<#include "extended_test_case_block_element_html.ftl">
<#elseif currentTestCaseCommand == "tear-down">
	<#assign extendedTestCaseTearDownElement = extendedTestCaseRootElement.element("tear-down")>

	<#assign testCaseBlockElement = extendedTestCaseTearDownElement>

	<#include "extended_test_case_block_element_html.ftl">
<#else>
	<#assign extendedTestCaseCommandElements = extendedTestCaseRootElement.elements("command")>

	<#list extendedTestCaseCommandElements as extendedTestCaseCommandElement>
		<#assign extendedTestCaseCommandName = extendedTestCaseCommandElement.attributeValue("name")>

		<#if extendedTestCaseCommandName == currentTestCaseCommand>
			<#assign testCaseBlockElement = extendedTestCaseCommandElement>

			<#include "extended_test_case_block_element_html.ftl">
		</#if>
	</#list>
</#if>

<#assign displayElement = currentTestCaseElement>

<#include "element_close_html.ftl">
</li>