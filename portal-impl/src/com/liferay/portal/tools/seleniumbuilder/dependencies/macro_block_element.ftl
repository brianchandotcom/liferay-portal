<#assign elements = blockElement.elements()>

<#list elements as element>
	<#assign name = element.getName()>

	<#if name == "echo">
		<#assign message = element.attributeValue("message")>

		<#if message?contains("${") && message?contains("}")>
			<#assign message = message?replace("${", "\" + commandScopeVariables.get(\"")>

			<#assign message = message?replace("}", "\") + \"")>
		</#if>

		liferaySelenium.echo("${message}");
	<#elseif name == "execute">
		<#if element.attributeValue("action")??>
			<#assign actionElement = element>

			<#include "action_element.ftl">
		<#elseif element.attributeValue("macro")??>
			<#assign macroElement = element>

			<#include "macro_element.ftl">
		</#if>
	<#elseif name == "fail">
		<#assign message = element.attributeValue("message")>

		<#if message?contains("${") && message?contains("}")>
			<#assign message = message?replace("${", "\" + commandScopeVariables.get(\"")>

			<#assign message = message?replace("}", "\") + \"")>
		</#if>

		liferaySelenium.fail("${message}");
	<#elseif name == "if">
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
				<#assign blockElement = elseElement>

				<#include "macro_block_element.ftl">
			}
		</#if>
	<#elseif name == "var">
		<#assign varName = element.attributeValue("name")>

		<#assign varValue = element.attributeValue("value")>

		<#if varValue?contains("${") && varValue?contains("}")>
			<#assign varValue = varValue?replace("${", "\" + commandScopeVariables.get(\"")>

			<#assign varValue = varValue?replace("}", "\") + \"")>
		</#if>

		commandScopeVariables.put("${varName}", "${varValue}");
	<#elseif name == "while">
		while (
			<#assign actionElement = element.element("condition")>

			<#include "action_element.ftl">
		) {
			<#assign thenElement = element.element("then")>

			<#assign blockElement = thenElement>

			<#include "macro_block_element.ftl">
		}
	</#if>
</#list>