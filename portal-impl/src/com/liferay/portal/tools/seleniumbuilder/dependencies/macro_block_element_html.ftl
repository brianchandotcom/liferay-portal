<#assign elements = macroBlockElement.elements()>

<#list elements as element>
	<#assign lineNumber = element.attributeValue("line-number")>

	<li id="${macroNameStack.peek()?uncap_first}Macro${lineNumber}">
		<#if element.getName() == "echo" || element.getName() == "fail" || element.getName() == "var">
			<#assign displayElement = element>

			<#include "element_whole_html.ftl">
		<#elseif element.getName() == "execute">
			<#if element.attributeValue("action")??>
				<#assign displayElement = element>

				<#include "element_whole_html.ftl">
			<#elseif element.attributeValue("macro")??>
				<#assign macroElement = element>

				<#include "macro_element_html.ftl">
			</#if>
		<#elseif element.getName() == "if">
			<#assign displayElement = element>

			<#include "element_open_html.ftl">

			<#assign ifElement = element>

			<#include "macro_if_element_html.ftl">

			<#assign elseifElements = element.elements("elseif")>

			<#list elseifElements as elseifElement>
				<#assign lineNumber = elseifElement.attributeValue("line-number")>

				<li id="${macroNameStack.peek()?uncap_first}Macro${lineNumber}">
					<#assign displayElement = elseifElement>

					<#include "element_open_html.ftl">

					<#assign ifElement = elseifElement>

					<#include "macro_if_element_html.ftl">

					<#assign displayElement = elseifElement>

					<#include "element_close_html.ftl">
				</li>
			</#list>

			<#if element.element("else")??>
				<#assign elseElement = element.element("else")>

				<#assign lineNumber = elseElement.attributeValue("line-number")>

				<li id="${macroNameStack.peek()?uncap_first}Macro${lineNumber}">
					<#assign displayElement = elseElement>

					<#include "element_open_html.ftl">

					<#assign macroBlockElement = element.element("else")>

					<#include "macro_block_element_html.ftl">

					<#assign displayElement = elseElement>

					<#include "element_close_html.ftl">
				</li>
			</#if>

			<#assign displayElement = element>

			<#include "element_close_html.ftl">
		<#elseif element.getName() == "while">
			<#assign displayElement = element>

			<#include "element_open_html.ftl">

			<#assign ifElement = element>

			<#include "macro_if_element_html.ftl">

			<#assign displayElement = element>

			<#include "element_close_html.ftl">
		</#if>
	</li>
</#list>