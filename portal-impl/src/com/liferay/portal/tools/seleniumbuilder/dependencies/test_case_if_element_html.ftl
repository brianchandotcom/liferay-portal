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

<#include "test_case_if_conditional_element_html.ftl">

<#assign thenElement = ifElement.element("then")>

<#assign lineNumber = thenElement.attributeValue("line-number")>

<li id="${testCaseNameStack.peek()?uncap_first}TestCase${lineNumber}">
	<#assign displayElement = thenElement>

	<#include "element_open_html.ftl">

	<#assign testCaseBlockElement = thenElement>

	<#include "test_case_block_element_html.ftl">

	<#assign displayElement = thenElement>

	<#include "element_close_html.ftl">
</li>