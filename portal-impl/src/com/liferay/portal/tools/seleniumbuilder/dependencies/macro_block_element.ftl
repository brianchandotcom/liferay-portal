<#assign elements = blockElement.elements()>

<#assign void = macroElementsStack.push(elements)>

<#list elements as element>
	<#assign elements = macroElementsStack.peek()>

	<#assign name = element.getName()>

	<#assign lineNumber = element.attributeValue("line-number")>

	liferaySelenium.sendLogger("${macroName?uncap_first}Macro${lineNumber}", "pending");

	<#if name == "echo">
		<#assign message = element.attributeValue("message")>

		<#if message?contains("${") && message?contains("}")>
			<#assign message = message?replace("${", "\" + commandScopeVariables.get(\"")>

			<#assign message = message?replace("}", "\") + \"")>
		</#if>

		liferaySelenium.echo("${message}");

		<#assign lineNumber = element.attributeValue("line-number")>

		liferaySelenium.sendLogger("${macroName?uncap_first}Macro${lineNumber}", "pass");
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

					liferaySelenium.sendLogger("${macroName?uncap_first}Macro${lineNumber}", "pass");
				}
			<#else>
				<#assign lineNumber = element.attributeValue("line-number")>

				liferaySelenium.sendLogger("${macroName?uncap_first}Macro${lineNumber}", "pass");
			</#if>
		<#elseif element.attributeValue("macro")??>
			<#assign macroElement = element>

			<#include "macro_element.ftl">

			<#assign lineNumber = element.attributeValue("line-number")>

			liferaySelenium.sendLogger("${macroName?uncap_first}Macro${lineNumber}", "pass");
		</#if>
	<#elseif name == "fail">
		<#assign message = element.attributeValue("message")>

		<#if message?contains("${") && message?contains("}")>
			<#assign message = message?replace("${", "\" + commandScopeVariables.get(\"")>

			<#assign message = message?replace("}", "\") + \"")>
		</#if>

		liferaySelenium.fail("${message}");

		<#assign lineNumber = element.attributeValue("line-number")>

		liferaySelenium.sendLogger("${macroName?uncap_first}Macro${lineNumber}", "pass");
	<#elseif name == "if">
		executeScopeVariables = new HashMap<String, String>();

		executeScopeVariables.putAll(commandScopeVariables);

		<#assign ifElement = element>

		<#include "macro_if_element.ftl">

		<#assign elseifElements = element.elements("elseif")>

		<#list elseifElements as elseifElement>
			<#assign ifElement = elseifElement>

			<#include "macro_if_element.ftl">
		</#list>

		<#if element.element("else")??>
			<#assign elseElement = element.element("else")>

			else {
				<#assign lineNumber = elseElement.attributeValue("line-number")>

				liferaySelenium.sendLogger("${macroName?uncap_first}Macro${lineNumber}", "pending");

				<#assign blockElement = elseElement>

				<#include "macro_block_element.ftl">

				<#assign lineNumber = elseElement.attributeValue("line-number")>

				liferaySelenium.sendLogger("${macroName?uncap_first}Macro${lineNumber}", "pass");
			}
		</#if>

		<#assign lineNumber = element.attributeValue("line-number")>

		liferaySelenium.sendLogger("${macroName?uncap_first}Macro${lineNumber}", "pass");
	<#elseif name == "var">
		<#assign varElement = element>

		<#assign context = "commandScopeVariables">

		<#include "var_element.ftl">

		<#assign lineNumber = element.attributeValue("line-number")>

		liferaySelenium.sendLogger("${macroName?uncap_first}Macro${lineNumber}", "pass");
	<#elseif name == "while">
		executeScopeVariables = new HashMap<String, String>();

		executeScopeVariables.putAll(commandScopeVariables);

		<#assign ifElement = element>

		<#include "macro_if_element.ftl">

		<#assign lineNumber = element.attributeValue("line-number")>

		liferaySelenium.sendLogger("${macroName?uncap_first}Macro${lineNumber}", "pass");
	</#if>
</#list>

<#assign void = macroElementsStack.pop()>