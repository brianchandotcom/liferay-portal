<#if ifElement.element("and")??>
	<#assign ifConditionalElement = ifElement.element("and")>
<#elseif ifElement.element("condition")??>
	<#assign ifConditionalElement = ifElement.element("condition")>
<#elseif ifElement.element("contains")??>
	<#assign ifConditionalElement = ifElement.element("contains")>
<#elseif ifElement.element("equals")??>
	<#assign ifConditionalElement = ifElement.element("equals")>
<#elseif ifElement.element("isset")??>
	<#assign ifConditionalElement = ifElement.element("isset")>
<#elseif ifElement.element("not")??>
	<#assign ifConditionalElement = ifElement.element("not")>
<#elseif ifElement.element("or")??>
	<#assign ifConditionalElement = ifElement.element("or")>
</#if>

<#include "if_conditional_element_html.ftl">

<#assign thenElement = ifElement.element("then")>

<#assign blockLevel = blockLevelStack.peek()>

<#if blockLevel == "testcase">
	<#assign lineId = "${testCaseNameStack.peek()?uncap_first}TestCase">
<#elseif blockLevel == "macro">
	<#assign lineId = "${macroNameStack.peek()?uncap_first}Macro">
</#if>

<#assign lineNumber = thenElement.attributeValue("line-number")>

<li id="${lineId}${lineNumber}">
	<#assign displayElement = thenElement>

	<#include "element_open_html.ftl">

	<#assign blockElement = thenElement>

	<#include "block_element_html.ftl">

	<#assign displayElement = thenElement>

	<#include "element_close_html.ftl">
</li>