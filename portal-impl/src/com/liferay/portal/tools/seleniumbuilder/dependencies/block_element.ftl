<#assign elements = blockElement.elements()>

<#list elements as element>
	<#assign name = element.getName()>

	<#if name == "execute">
		<#if element.attributeValue("function")??>
			<#assign functionElement = element>

			<#include "function_element.ftl">

			;
		<#elseif element.attributeValue("selenium")??>
			<#assign seleniumElement = element>

			<#assign selenium = seleniumElement.attributeValue("selenium")>

			<#if selenium?starts_with("is")>
				return
			</#if>

			<#include "selenium_element.ftl">

			;
		</#if>
	<#elseif name == "if">
		<#assign conditionElement = element.element("condition")>

		if (
			<#assign seleniumElement = conditionElement>

			<#include "selenium_element.ftl">
		) {
			<#assign thenElement = element.element("then")>

			<#assign blockElement = thenElement>

			<#include "block_element.ftl">
		}

		<#if element.element("else")??>
			<#assign elseElement = element.element("else")>

			else {
				<#assign blockElement = elseElement>

				<#include "block_element.ftl">
			}
		</#if>
	</#if>
</#list>