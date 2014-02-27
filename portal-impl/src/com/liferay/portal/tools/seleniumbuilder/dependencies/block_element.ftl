<#if blockLevel == "testcase">
	<#assign lineId = "testCaseName">

	<#assign selenium = "selenium">
<#elseif blockLevel == "macro">
	<#assign lineId = "\"${macroName?uncap_first}Macro\"">

	<#assign selenium = "liferaySelenium">
</#if>

<#assign variableContext = variableContextStack.peek()>

<#assign elements = blockElement.elements()>

<#assign void = elementsStack.push(elements)>

<#list elements as element>
	<#assign elements = elementsStack.peek()>

	<#assign name = element.getName()>

	<#assign lineNumber = element.attributeValue("line-number")>

	${selenium}.sendLogger(${lineId} + "${lineNumber}", "pending");

	<#if name =="description">
		<#assign variableContext = variableContextStack.peek()>

		<#assign message = element.attributeValue("message")>

		${selenium}.sendMacroDescriptionLogger(RuntimeVariables.evaluateVariable("${seleniumBuilderFileUtil.escapeJava(message)}", ${variableContext}));

		<#assign lineNumber = element.attributeValue("line-number")>

		${selenium}.sendLogger(${lineId} + "${lineNumber}", "pass");
	<#elseif name == "echo">
		<#assign variableContext = variableContextStack.peek()>

		<#assign message = element.attributeValue("message")>

		<#assign actionElement = element>

		<#include "action_log_element.ftl">

		${selenium}.echo(RuntimeVariables.evaluateVariable("${seleniumBuilderFileUtil.escapeJava(message)}", ${variableContext}));

		<#assign lineNumber = element.attributeValue("line-number")>

		${selenium}.sendLogger(${lineId} + "${lineNumber}", "pass");
	<#elseif name == "execute">
		<#assign variableContext = variableContextStack.peek()>

		<#if element.attributeValue("action")??>
			<#assign action = element.attributeValue("action")>

			<#if testCaseName??>
				selenium
			<#else>
				liferaySelenium
			</#if>

			.pauseLoggerCheck();

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
		<#assign variableContext = variableContextStack.peek()>

		<#assign message = element.attributeValue("message")>

		<#assign actionElement = element>

		<#include "action_log_element.ftl">

		${selenium}.fail(RuntimeVariables.evaluateVariable("${message}", commandScopeVariables));

		<#assign lineNumber = element.attributeValue("line-number")>

		${selenium}.sendLogger(${lineId} + "${lineNumber}", "pass");
	<#elseif name == "for">
		<#assign variableContext = variableContextStack.peek()>

		executeScopeVariables = new HashMap<String, String>();

		executeScopeVariables.putAll(${variableContext});

		<#assign forElement = element>

		<#include "for_element.ftl">

		<#assign lineNumber = element.attributeValue("line-number")>

		${selenium}.sendLogger(${lineId} + "${lineNumber}", "pass");
	<#elseif name == "if">
		executeScopeVariables = new HashMap<String, String>();

		executeScopeVariables.putAll(${variableContext});

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
	<#elseif name == "property">
		<#assign lineNumber = element.attributeValue("line-number")>

		${selenium}.sendLogger(${lineId} + "${lineNumber}", "pass");
	<#elseif name == "take-screenshot">
		<#assign variableContext = variableContextStack.peek()>

		executeScopeVariables = new HashMap<String, String>();

		executeScopeVariables.putAll(${variableContext});

		${selenium}.saveScreenshot();

		<#assign lineNumber = element.attributeValue("line-number")>

		${selenium}.sendLogger(${lineId} + "${lineNumber}", "pass");
	<#elseif name == "var">
		<#assign variableContext = variableContextStack.peek()>

		<#assign varElement = element>

		<#include "var_element.ftl">

		<#assign lineNumber = element.attributeValue("line-number")>

		${selenium}.sendLogger(${lineId} + "${lineNumber}", "pass");
	<#elseif name == "while">
		<#assign variableContext = variableContextStack.peek()>

		executeScopeVariables = new HashMap<String, String>();

		executeScopeVariables.putAll(${variableContext});

		_whileCount = 0;

		<#assign ifElement = element>

		<#include "if_element.ftl">

		<#assign lineNumber = element.attributeValue("line-number")>

		${selenium}.sendLogger(${lineId} + "${lineNumber}", "pass");
	</#if>
</#list>

<#assign void = elementsStack.pop()>