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
		if (
			<#if element.element("condition")??>
				<#assign conditionElement = element.element("condition")>

				<#assign seleniumElement = conditionElement>

				<#include "selenium_element.ftl">
			<#elseif element.element("contains")??>
				<#assign conditionElement = element.element("contains")>

				<#assign string = conditionElement.attributeValue("string")>

				<#if string?starts_with("${") && string?ends_with("}")>
					${seleniumBuilderFileUtil.escapeJava(string?substring(2, string?length - 1))}
				<#else>
					"${seleniumBuilderFileUtil.escapeJava(string)}"
				</#if>

				.contains(

				<#assign substring = conditionElement.attributeValue("substring")>

				<#if substring?starts_with("${") && substring?ends_with("}")>
					${seleniumBuilderFileUtil.escapeJava(substring?substring(2, substring?length - 1))}
				<#else>
					"${seleniumBuilderFileUtil.escapeJava(substring)}"
				</#if>

				)
			</#if>
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