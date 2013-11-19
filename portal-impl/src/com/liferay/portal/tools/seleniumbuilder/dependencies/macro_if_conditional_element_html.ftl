<#if (ifConditionalElement.getName() == "and") ||
	(ifConditionalElement.getName() == "not") ||
	(ifConditionalElement.getName() == "or")>

	<#assign lineNumber = ifConditionalElement.attributeValue("line-number")>

	<li id="${macroNameStack.peek()?uncap_first}Macro${lineNumber}">
		<#assign logicalOperatorElement = ifConditionalElement>

		<#assign void = logicalOperatorElementStack.push(logicalOperatorElement)>

		<#assign displayElement = logicalOperatorElement>

		<#include "element_open_html.ftl">

		<#assign ifConditionalElements = ifConditionalElement.elements()>

		<#list ifConditionalElements as ifConditionalElement>
			<#if ifConditionalElement.element("and")??>
				<#assign ifConditionalElement = ifConditionalElement.element("and")>
			<#elseif ifConditionalElement.element("condition")??>
				<#assign ifConditionalElement = ifConditionalElement.element("condition")>
			<#elseif ifConditionalElement.element("contains")??>
				<#assign ifConditionalElement = ifConditionalElement.element("contains")>
			<#elseif ifConditionalElement.element("equals")??>
				<#assign ifConditionalElement = ifConditionalElement.element("equals")>
			<#elseif ifConditionalElement.element("isset")??>
				<#assign ifConditionalElement = ifConditionalElement.element("isset")>
			<#elseif ifConditionalElement.element("not")??>
				<#assign ifConditionalElement = ifConditionalElement.element("not")>
			<#elseif ifConditionalElement.element("or")??>
				<#assign ifConditionalElement = ifConditionalElement.element("or")>
			</#if>

			<#include "macro_if_conditional_element_html.ftl">
		</#list>

		<#assign test = logicalOperatorElementStack.peek()>

		<#assign displayElement = logicalOperatorElementStack.pop()>

		<#include "element_close_html.ftl">
	</li>
<#elseif ifConditionalElement.getName() == "condition">
	<#assign lineNumber = ifConditionalElement.attributeValue("line-number")>

	<li id="${macroNameStack.peek()?uncap_first}Macro${lineNumber}">
		<#if ifConditionalElement.attributeValue("action")??>
			<#assign displayElement = ifConditionalElement>

			<#include "element_whole_html.ftl">
		<#elseif ifConditionalElement.attributeValue("macro")??>
			<#assign macroElement = ifConditionalElement>

			<#include "macro_element_html.ftl">
		</#if>
	</li>
<#elseif ifConditionalElement.getName() == "contains">
	<#assign lineNumber = ifConditionalElement.attributeValue("line-number")>

	<li id="${macroNameStack.peek()?uncap_first}Macro${lineNumber}">
		<#assign displayElement = ifConditionalElement>

		<#include "element_whole_html.ftl">
	</li>
<#elseif ifConditionalElement.getName() == "equals">
	<#assign lineNumber = ifConditionalElement.attributeValue("line-number")>

	<li id="${macroNameStack.peek()?uncap_first}Macro${lineNumber}">
		<#assign displayElement = ifConditionalElement>

		<#include "element_whole_html.ftl">
	</li>
<#elseif ifConditionalElement.getName() == "isset">
	<#assign lineNumber = ifConditionalElement.attributeValue("line-number")>

	<li id="${macroNameStack.peek()?uncap_first}Macro${lineNumber}">
		<#assign displayElement = ifConditionalElement>

		<#include "element_whole_html.ftl">
	</li>
<#elseif ifConditionalElement.getName() == "not">
	<#assign lineNumber = ifConditionalElement.attributeValue("line-number")>

	<li id="${macroNameStack.peek()?uncap_first}Macro${lineNumber}">
		<#assign notElement = ifConditionalElement>

		<#assign displayElement = notElement>

		<#include "element_open_html.ftl">

		<#if ifConditionalElement.element("condition")??>
			<#assign ifConditionalElement = ifConditionalElement.element("condition")>
		<#elseif ifConditionalElement.element("contains")??>
			<#assign ifConditionalElement = ifConditionalElement.element("contains")>
		<#elseif ifConditionalElement.element("equals")??>
			<#assign ifConditionalElement = ifConditionalElement.element("equals")>
		<#elseif ifConditionalElement.element("isset")??>
			<#assign ifConditionalElement = ifConditionalElement.element("isset")>
		<#elseif ifConditionalElement.element("not")??>
			<#assign ifConditionalElement = ifConditionalElement.element("not")>
		</#if>

		<#include "macro_if_conditional_element_html.ftl">

		<#assign displayElement = notElement>

		<#include "element_close_html.ftl">
	</li>
</#if>