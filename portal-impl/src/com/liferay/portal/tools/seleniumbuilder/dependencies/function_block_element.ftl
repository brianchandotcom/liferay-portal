<#assign elements = blockElement.elements()>

<#list elements as element>
	<#assign name = element.getName()>

	<#if name == "execute">
		<#if element.attributeValue("function")??>
			<#assign functionElement = element>

			<#include "function_element.ftl">
		<#elseif element.attributeValue("selenium")??>
			<#assign seleniumElement = element>

			<#include "selenium_element.ftl">
		</#if>
	<#elseif name == "if">
		<#assign conditionElement = element.element("condition")>

		if (
			<#assign seleniumElement = conditionElement>

			<#include "selenium_element.ftl">
		) {
			<#assign thenElement = element.element("then")>

			<#assign blockElement = thenElement>

			<#include "function_block_element.ftl">
		}

		<#if element.element("else")??>
			<#assign elseElement = element.element("else")>

			else {
				<#assign blockElement = elseElement>

				<#include "function_block_element.ftl">
			}
		</#if>
	</#if>
</#list>