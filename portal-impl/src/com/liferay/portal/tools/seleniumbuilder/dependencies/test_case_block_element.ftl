<#assign elements = blockElement.elements()>

<#assign void = testCaseElementsStack.push(elements)>

<#list elements as element>
	<#assign elements = testCaseElementsStack.peek()>

	<#assign name = element.getName()>

	<#assign lineNumber = element.attributeValue("line-number")>

	selenium.sendLogger("${testCaseName?uncap_first}TestCase${lineNumber}", "pending");

	<#if name == "echo">
		<#assign message = element.attributeValue("message")>

		<#if message?contains("${") && message?contains("}")>
			<#assign message = message?replace("${", "\" + commandScopeVariables.get(\"")>

			<#assign message = message?replace("}", "\") + \"")>
		</#if>

		selenium.echo("${message}");

		<#assign lineNumber = element.attributeValue("line-number")>

		selenium.sendLogger("${testCaseName?uncap_first}TestCase${lineNumber}", "pass");
	<#elseif name == "execute">
		<#if element.attributeValue("action")??>
			<#assign action = element.attributeValue("action")>

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

					selenium.sendLogger("${testCaseName?uncap_first}TestCase${lineNumber}", "pass");
				}
			<#else>
				<#assign lineNumber = element.attributeValue("line-number")>

				selenium.sendLogger("${testCaseName?uncap_first}TestCase${lineNumber}", "pass");
			</#if>
		<#elseif element.attributeValue("macro")??>
			<#assign macroElement = element>

			<#include "macro_element.ftl">

			<#assign lineNumber = element.attributeValue("line-number")>

			selenium.sendLogger("${testCaseName?uncap_first}TestCase${lineNumber}", "pass");
		<#elseif element.attributeValue("test-case")??>
			<#assign lineNumber = element.attributeValue("line-number")>

			selenium.sendLogger("${testCaseName?uncap_first}TestCase${lineNumber}", "pending");

			<#assign testCaseElement = element>

			<#include "test_case_element.ftl">

			<#assign lineNumber = element.attributeValue ("line-number")>

			selenium.sendLogger("${testCaseName?uncap_first}TestCase${lineNumber}", "pass");
		</#if>
	<#elseif name == "fail">
		<#assign message = element.attributeValue("message")>

		<#if message?contains("${") && message?contains("}")>
			<#assign message = message?replace("${", "\" + commandScopeVariables.get(\"")>

			<#assign message = message?replace("}", "\") + \"")>
		</#if>

		selenium.fail("${message}");

		<#assign lineNumber = element.attributeValue("line-number")>

		selenium.sendLogger("${testCaseName?uncap_first}TestCase${lineNumber}", "pass");
	<#elseif name == "if">
		executeScopeVariables = new HashMap<String, String>();

		executeScopeVariables.putAll(commandScopeVariables);

		<#assign ifElement = element>

		<#include "test_case_if_element.ftl">

		<#assign elseifElements = element.elements("elseif")>

		<#list elseifElements as elseifElement>
			<#assign ifElement = elseifElement>

			<#include "test_case_if_element.ftl">
		</#list>

		<#if element.element("else")??>
			<#assign elseElement = element.element("else")>

			else {
				<#assign lineNumber = elseElement.attributeValue("line-number")>

				selenium.sendLogger("${testCaseName?uncap_first}TestCase${lineNumber}", "pending");

				<#assign blockElement = elseElement>

				<#include "test_case_block_element.ftl">

				<#assign lineNumber = elseElement.attributeValue("line-number")>

				selenium.sendLogger("${testCaseName?uncap_first}TestCase${lineNumber}", "pass");
			}
		</#if>

		<#assign lineNumber = element.attributeValue("line-number")>

		selenium.sendLogger("${testCaseName?uncap_first}TestCase${lineNumber}", "pass");
	<#elseif name == "var">
		<#assign varElement = element>

		<#assign context = "commandScopeVariables">

		<#include "var_element.ftl">

		<#assign lineNumber = element.attributeValue("line-number")>

		selenium.sendLogger("${testCaseName?uncap_first}TestCase${lineNumber}", "pass");
	<#elseif name == "while">
		executeScopeVariables = new HashMap<String, String>();

		executeScopeVariables.putAll(commandScopeVariables);

		<#assign ifElement = element>

		<#include "test_case_if_element.ftl">

		<#assign lineNumber = element.attributeValue("line-number")>

		selenium.sendLogger("${testCaseName?uncap_first}TestCase${lineNumber}", "pass");
	</#if>
</#list>

<#assign void = testCaseElementsStack.pop()>