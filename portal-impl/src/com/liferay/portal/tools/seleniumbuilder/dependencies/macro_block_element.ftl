<#assign elements = blockElement.elements()>

<#list elements as element>
	<#assign name = element.getName()>

	<#if name == "execute">
		<#if element.attributeValue("action")??>
			<#assign actionElement = element>

			<#include "action_element.ftl">
		<#elseif element.attributeValue("macro")??>
			<#assign macroElement = element>

			<#include "macro_element.ftl">
		</#if>
	<#elseif name == "if">
		<#assign conditionElement = element.element("condition")>

		if (
			<#assign actionElement = conditionElement>

			<#include "action_element.ftl">
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
	</#if>
</#list>