<#assign void = testCaseNameStack.push(testCaseName)>

<#assign testCaseRootElement = seleniumBuilderContext.getTestCaseRootElement(testCaseName)>

<#if extendedTestCase??>
	<#assign extendedTestCaseRootElement = seleniumBuilderContext.getTestCaseRootElement(extendedTestCase)>

	<#assign testCaseCommandNames = seleniumBuilderContext.getTestCaseCommandNames(testCaseName)>

	<#assign extendedTestCaseCommandElements = extendedTestCaseRootElement.elements("command")>

	<#if testCaseRootElement.element("command")??>
		<#assign testCaseCommandElements = testCaseRootElement.elements("command")>

		<#assign testCaseCommandFound = false>

		<#list testCaseCommandNames as testCaseCommandName>
			<#list testCaseCommandElements as testCaseCommandElement>
				<#assign testCaseCommand = testCaseCommandElement.attributeValue("name")>

				<#if testCaseCommand == testCaseCommandName>
					<#include "test_case_command_block_element_html.ftl">

					<#assign testCaseCommandFound = true>

					<#break>
				</#if>
			</#list>

			<#if !testCaseCommandFound>
				<#list extendedTestCaseCommandElements as extendedTestCaseCommandElement>
					<#assign extendedTestCaseCommand = extendedTestCaseCommandElement.attributeValue("name")>

					<#if extendedTestCaseCommand = testCaseCommandName>
						<#assign void = testCaseNameStack.push(extendedTestCase)>

						<#assign testCaseCommandElement = extendedTestCaseCommandElement>

						<#include "test_case_command_block_element_html.ftl">

						<#assign void = testCaseNameStack.pop()>

						<#assign testCaseCommandFound = true>

						<#break>
					</#if>
				</#list>
			</#if>

			<#if testCaseCommandFound>
				<#assign testCaseCommandFound = false>
			</#if>
		</#list>
	<#else>
		<#list extendedTestCaseCommandElements as extendedTestCaseCommandElement>
			<#assign void = testCaseNameStack.push(extendedTestCase)>

			<#assign testCaseCommandElement = extendedTestCaseCommandElement>

			<#include "test_case_command_block_element_html.ftl">

			<#assign void = testCaseNameStack.pop()>
		</#list>
	</#if>
<#else>
	<#assign testCaseCommandElements = testCaseRootElement.elements("command")>

	<#list testCaseCommandElements as testCaseCommandElement>
		<#include "test_case_command_block_element_html.ftl">
	</#list>
</#if>

<#assign void = testCaseNameStack.pop()>