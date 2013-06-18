<#assign elements = testCaseBlockElement.elements()>

<#list elements as element>
	<#assign lineNumber = element.attributeValue("line-number")>

	<li id="${testCaseName?uncap_first}TestCase${lineNumber}">
		<#if element.getName() == "execute">
			<#if element.attributeValue("action")??>
				<#assign displayElement = element>

				<#include "element_whole_html.ftl">
			<#elseif element.attributeValue("macro")??>
				<#assign macroElement = element>

				<#include "macro_element_html.ftl">
			</#if>
		<#elseif element.getName() == "var">
			<#assign displayElement = element>

			<#include "element_whole_html.ftl">
		</#if>
	</li>
</#list>