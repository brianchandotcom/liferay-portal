<#if ifElement.element("condition")??>
	<#assign ifConditionalElement = ifElement.element("condition")>
<#elseif ifElement.element("contains")??>
	<#assign ifConditionalElement = ifElement.element("contains")>
<#elseif ifElement.element("equals")??>
	<#assign ifConditionalElement = ifElement.element("equals")>
<#elseif ifElement.element("isset")??>
	<#assign ifConditionalElement = ifElement.element("isset")>
<#elseif ifElement.element("not")??>
	<#assign ifConditionalElement = ifElement.element("not")>
</#if>

<#include "macro_if_conditional_element_html.ftl">

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