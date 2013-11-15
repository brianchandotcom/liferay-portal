<#assign void = testCaseNameStack.push(testCaseName)>

<#assign testCaseRootElement = seleniumBuilderContext.getTestCaseRootElement(testCaseName)>

<#assign testCaseCommandNames = seleniumBuilderContext.getTestCaseCommandNames(testCaseName)>

<#if extendedTestCase??>
	<#assign extendedTestCaseRootElement = seleniumBuilderContext.getTestCaseRootElement(extendedTestCase)>
</#if>

<#list testCaseCommandNames as testCaseCommandName>
	<#assign testCaseCommandFound = false>

	<#if testCaseRootElement.elements("command")??>
		<#assign testCaseCommandElements = testCaseRootElement.elements("command")>

		<#list testCaseCommandElements as testCaseCommandElement>
			<#assign testCaseCommand = testCaseCommandElement.attributeValue("name")>

			<#if testCaseCommand == testCaseCommandName>
				<#include "test_case_command_block_element_html.ftl">

				<#assign testCaseCommandFound = true>

				<#break>
			</#if>
		</#list>
	</#if>

	<#if !testCaseCommandFound && extendedTestCaseRootElement?? && extendedTestCaseRootElement.elements("command")??>
		<#assign extendedTestCaseCommandElements = extendedTestCaseRootElement.elements("command")>

		<#list extendedTestCaseCommandElements as extendedTestCaseCommandElement>
			<#assign extendedTestCaseCommand = extendedTestCaseCommandElement.attributeValue("name")>

			<#if extendedTestCaseCommand = testCaseCommandName>
				<#assign void = testCaseNameStack.push(extendedTestCase)>

				<#assign testCaseCommandElement = extendedTestCaseCommandElement>

				<#include "test_case_command_block_element_html.ftl">

				<#assign void = testCaseNameStack.pop()>

				<#break>
			</#if>
		</#list>
	</#if>
</#list>

<#assign void = testCaseNameStack.pop()>