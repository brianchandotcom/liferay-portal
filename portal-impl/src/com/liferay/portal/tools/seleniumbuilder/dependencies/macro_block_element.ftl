<#assign elements = blockElement.elements()>

<#list elements as element>
	<#assign name = element.getName()>

	<#assign lineNumber = element.attributeValue("line-number")>

	liferaySelenium.sendLogger("${macroName}Macro__${lineNumber}", "pending");

	<#if name == "echo">
		<#assign message = element.attributeValue("message")>

		<#if message?contains("${") && message?contains("}")>
			<#assign message = message?replace("${", "\" + commandScopeVariables.get(\"")>

			<#assign message = message?replace("}", "\") + \"")>
		</#if>

		liferaySelenium.echo("${message}");

		<#assign lineNumber = element.attributeValue("line-number")>

		liferaySelenium.sendLogger("${macroName}Macro__${lineNumber}", "pass");
	<#elseif name == "execute">
		<#if element.attributeValue("action")??>
			<#assign action = element.attributeValue("action")>

			<#if action?contains("#is")>
				try {
			</#if>

			<#assign actionElement = element>

			<#include "action_element.ftl">

			<#if action?contains("#is")>
				}
				finally {
					<#assign lineNumber = element.attributeValue("line-number")>

					liferaySelenium.sendLogger("${macroName}Macro__${lineNumber}", "pass");
				}
			<#else>
				<#assign lineNumber = element.attributeValue("line-number")>

				liferaySelenium.sendLogger("${macroName}Macro__${lineNumber}", "pass");
			</#if>
		<#elseif element.attributeValue("macro")??>
			<#assign macroElement = element>

			<#include "macro_element.ftl">

			<#assign lineNumber = element.attributeValue("line-number")>

			liferaySelenium.sendLogger("${macroName}Macro__${lineNumber}", "pass");
		</#if>
	<#elseif name == "fail">
		<#assign message = element.attributeValue("message")>

		<#if message?contains("${") && message?contains("}")>
			<#assign message = message?replace("${", "\" + commandScopeVariables.get(\"")>

			<#assign message = message?replace("}", "\") + \"")>
		</#if>

		liferaySelenium.fail("${message}");

		<#assign lineNumber = element.attributeValue("line-number")>

		liferaySelenium.sendLogger("${macroName}Macro__${lineNumber}", "pass");
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

				liferaySelenium.sendLogger("${macroName}Macro__${lineNumber}", "pending");

				<#assign blockElement = elseElement>

				<#include "macro_block_element.ftl">

				<#assign lineNumber = elseElement.attributeValue("line-number")>

				liferaySelenium.sendLogger("${macroName}Macro__${lineNumber}", "pass");
			}
		</#if>

		<#assign lineNumber = element.attributeValue("line-number")>

		liferaySelenium.sendLogger("${macroName}Macro__${lineNumber}", "pass");
	<#elseif name == "var">
		<#assign varName = element.attributeValue("name")>

		<#assign varValue = element.attributeValue("value")>

		<#if varValue?contains("${") && varValue?contains("}")>
			<#assign varValue = varValue?replace("${", "\" + commandScopeVariables.get(\"")>

			<#assign varValue = varValue?replace("}", "\") + \"")>
		</#if>

		commandScopeVariables.put("${varName}", "${varValue}");

		<#assign lineNumber = element.attributeValue("line-number")>

		liferaySelenium.sendLogger("${macroName}Macro__${lineNumber}", "pass");
	<#elseif name == "while">
		executeScopeVariables = new HashMap<String, String>();

		executeScopeVariables.putAll(commandScopeVariables);

		<#assign ifElement = element>

		<#include "macro_if_element.ftl">

		<#assign lineNumber = element.attributeValue("line-number")>

		liferaySelenium.sendLogger("${macroName}Macro__${lineNumber}", "pass");
	</#if>
</#list>