<#if blockLevel == "testcase">
	<#assign lineId = "testCaseName">

	<#assign selenium = "selenium">
<#elseif blockLevel == "macro">
	<#assign lineId = "\"${macroName?uncap_first}Macro\"">

	<#assign selenium = "liferaySelenium">
</#if>

<#assign elements = blockElement.elements()>

<#assign void = elementsStack.push(elements)>

<#list elements as element>
	<#assign elements = elementsStack.peek()>

	<#assign name = element.getName()>

	<#assign lineNumber = element.attributeValue("line-number")>

	${selenium}.sendLogger(${lineId} + "${lineNumber}", "pending");

	<#if name == "echo">
		<#assign message = element.attributeValue("message")>

		<#if message?contains("${") && message?contains("}")>
			<#assign message = message?replace("${", "\" + commandScopeVariables.get(\"")>

			<#assign message = message?replace("}", "\") + \"")>
		</#if>

		${selenium}.echo("${message}");

		<#assign lineNumber = element.attributeValue("line-number")>

		${selenium}.sendLogger(${lineId} + "${lineNumber}", "pass");
	<#elseif name == "execute">
		<#if element.attributeValue("action")??>
			<#assign action = element.attributeValue("action")>

			<#if testCaseName??>
				selenium
			<#else>
				liferaySelenium
			</#if>

			.assertPausePlay();

			<#if action?contains("#is")>
				try {
			</#if>

			<#assign actionElement = element>

			<#if element_has_next>
				<#assign actionNextElement = elements[element_index + 1]>
			<#else>
				<#assign actionNextElement = element>
			</#if>

			<#include "action_log_element.ftl">

			<#if !(action?contains("#confirm")) && !(action?contains("#is"))>
				<#if testCaseName??>
					selenium
				<#else>
					liferaySelenium
				</#if>

				.saveScreenshot(commandScopeVariables.get("testCaseName"));
			</#if>

			<#include "action_element.ftl">

			<#if action?contains("#is")>
				}
				finally {
					<#assign lineNumber = element.attributeValue("line-number")>

					${selenium}.sendLogger(${lineId} + "${lineNumber}", "pass");
				}
			<#else>
				<#assign lineNumber = element.attributeValue("line-number")>

				${selenium}.sendLogger(${lineId} + "${lineNumber}", "pass");
			</#if>
		<#elseif element.attributeValue("macro")??>
			<#assign macroElement = element>

			<#include "macro_element.ftl">

			<#assign lineNumber = element.attributeValue("line-number")>

			${selenium}.sendLogger(${lineId} + "${lineNumber}", "pass");
		<#elseif element.attributeValue("test-case")??>
			<#assign testCaseElement = element>

			<#include "test_case_element.ftl">

			<#assign lineNumber = element.attributeValue ("line-number")>

			${selenium}.sendLogger(${lineId} + "${lineNumber}", "pass");
		</#if>
	<#elseif name == "fail">
		<#assign message = element.attributeValue("message")>

		<#if message?contains("${") && message?contains("}")>
			<#assign message = message?replace("${", "\" + commandScopeVariables.get(\"")>

			<#assign message = message?replace("}", "\") + \"")>
		</#if>

		${selenium}.fail("${message}");

		<#assign lineNumber = element.attributeValue("line-number")>

		${selenium}.sendLogger(${lineId} + "${lineNumber}", "pass");
	<#elseif name == "if">
		executeScopeVariables = new HashMap<String, String>();

		executeScopeVariables.putAll(commandScopeVariables);

		<#assign ifElement = element>

		<#include "if_element.ftl">

		<#assign elseifElements = element.elements("elseif")>

		<#list elseifElements as elseifElement>
			<#assign ifElement = elseifElement>

			<#include "if_element.ftl">
		</#list>

		<#if element.element("else")??>
			<#assign elseElement = element.element("else")>

			else {
				<#assign lineNumber = elseElement.attributeValue("line-number")>

				${selenium}.sendLogger(${lineId} + "${lineNumber}", "pending");

				<#assign blockElement = elseElement>

				<#include "block_element.ftl">

				<#assign lineNumber = elseElement.attributeValue("line-number")>

				${selenium}.sendLogger(${lineId} + "${lineNumber}", "pass");
			}
		</#if>

		<#assign lineNumber = element.attributeValue("line-number")>

		${selenium}.sendLogger(${lineId} + "${lineNumber}", "pass");
	<#elseif name == "var">
		<#assign varElement = element>

		<#assign context = "commandScopeVariables">

		<#include "var_element.ftl">

		<#assign lineNumber = element.attributeValue("line-number")>

		${selenium}.sendLogger(${lineId} + "${lineNumber}", "pass");
	<#elseif name == "while">
		executeScopeVariables = new HashMap<String, String>();

		executeScopeVariables.putAll(commandScopeVariables);

		<#assign ifElement = element>

		<#include "if_element.ftl">

		<#assign lineNumber = element.attributeValue("line-number")>

		${selenium}.sendLogger(${lineId} + "${lineNumber}", "pass");
	</#if>
</#list>

<#assign void = elementsStack.pop()>