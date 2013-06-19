<#if ifElement.element("condition")??>
	<#assign conditionElement = ifElement.element("condition")>

	<#assign lineNumber = conditionElement.attributeValue("line-number")>

	<li id="${macroNameStack.peek()?uncap_first}Macro${lineNumber}">
		<#if conditionElement.attributeValue("action")??>
			<#assign displayElement = conditionElement>

			<#include "element_whole_html.ftl">
		<#elseif conditionElement.attributeValue("macro")??>
			<#assign macroElement = conditionElement>

			<#include "macro_element_html.ftl">
		</#if>
	</li>
<#elseif ifElement.element("contains")??>
	<#assign containsElement = ifElement.element("contains")>

	<#assign lineNumber = containsElement.attributeValue("line-number")>

	<li id="${macroNameStack.peek()?uncap_first}Macro${lineNumber}">
		<#assign displayElement = containsElement>

		<#include "element_whole_html.ftl">
	</li>
<#elseif ifElement.element("equals")??>
	<#assign equalsElement = ifElement.element("equals")>

	<#assign lineNumber = equalsElement.attributeValue("line-number")>

	<li id="${macroNameStack.peek()?uncap_first}Macro${lineNumber}">
		<#assign displayElement = equalsElement>

		<#include "element_whole_html.ftl">
	</li>
<#elseif ifElement.element("isset")??>
	<#assign issetElement = ifElement.element("isset")>

	<#assign lineNumber = issetElement.attributeValue("line-number")>

	<li id="${macroNameStack.peek()?uncap_first}Macro${lineNumber}">
		<#assign displayElement = issetElement>

		<#include "element_whole_html.ftl">
	</li>
</#if>

<#assign thenElement = ifElement.element("then")>

<#assign lineNumber = thenElement.attributeValue("line-number")>

<li id="${macroNameStack.peek()?uncap_first}Macro${lineNumber}">
	<#assign displayElement = thenElement>

	<#include "element_open_html.ftl">

	<#assign macroBlockElement = thenElement>

	<#include "macro_block_element_html.ftl">

	<#assign displayElement = thenElement>

	<#include "element_close_html.ftl">
</li>