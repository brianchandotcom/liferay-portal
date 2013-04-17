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
		if (
			<#if element.element("condition")??>
				<#assign actionElement = element.element("condition")>

				<#include "action_element.ftl">
			<#elseif element.element("contains")??>
				<#assign containsElement = element.element("contains")>

				<#assign string = containsElement.attributeValue("string")>

				<#if string?contains("${") && string?contains("}")>
					<#assign string = string?replace("${", "\" + commandScopeVariables.get(\"")>

					<#assign string = string?replace("}", "\") + \"")>
				</#if>

				<#assign substring = containsElement.attributeValue("substring")>

				<#if substring?contains("${") && substring?contains("}")>
					<#assign substring = substring?replace("${", "\" + commandScopeVariables.get(\"")>

					<#assign substring = substring?replace("}", "\") + \"")>
				</#if>

				("${string}").contains("${substring}")
			<#elseif element.element("equals")??>
				<#assign equalsElement = element.element("equals")>

				<#assign arg1 = equalsElement.attributeValue("arg1")>

				<#if arg1?contains("${") && arg1?contains("}")>
					<#assign arg1 = arg1?replace("${", "\" + commandScopeVariables.get(\"")>

					<#assign arg1 = arg1?replace("}", "\") + \"")>
				</#if>

				<#assign arg2 = equalsElement.attributeValue("arg2")>

				<#if arg2?contains("${") && arg2?contains("}")>
					<#assign arg2 = arg2?replace("${", "\" + commandScopeVariables.get(\"")>

					<#assign arg2 = arg2?replace("}", "\") + \"")>
				</#if>

				("${arg1}").equals("${arg2}")
			<#elseif element.element("isset")??>
				<#assign equalsElement = element.element("isset")>

				<#assign var = equalsElement.attributeValue("var")>

				<#if var?contains("${") && var?contains("}")>
					<#assign var = var?replace("${", "\" + commandScopeVariables.get(\"")>

					<#assign var = var?replace("}", "\") + \"")>
				</#if>

				commandScopeVariables.containsKey("${var}")
			</#if>
		) {
			<#assign thenElement = element.element("then")>

			<#assign blockElement = thenElement>

			<#include "macro_block_element.ftl">
		}

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