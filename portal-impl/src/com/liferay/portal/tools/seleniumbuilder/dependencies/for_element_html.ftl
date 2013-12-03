<#assign displayElement = forElement>

<#if blockLevel == "testcase">
	<#assign lineId = "${testCaseNameStack.peek()?uncap_first}TestCase">
<#elseif blockLevel == "macro">
	<#assign lineId = "${macroNameStack.peek()?uncap_first}Macro">
</#if>

<#assign lineNumber = forElement.attributeValue("line-number")>

<li id="${lineId}${lineNumber}">
	<#assign displayElement = forElement>

	<#include "element_open_html.ftl">

	<#assign blockElement = forElement>

	<#include "block_element_html.ftl">

	<#assign displayElement = forElement>

	<#include "element_close_html.ftl">
</li>