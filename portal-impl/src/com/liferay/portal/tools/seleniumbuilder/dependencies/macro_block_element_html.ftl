<#assign elements = macroBlockElement.elements()>

<#list elements as element>
	<#assign lineNumber = element.attributeValue("line-number")>

	<li id="${macroNameStack.peek()}Macro__${lineNumber}">
		<#if element.getName() == "echo">
			<#assign message = element.attributeValue("message")>

			<div>
				<span class="arrow">&lt;</span><span class="tag">echo</span>
				<span class="attribute">message</span><span class="arrow">=</span><span class="quote">&quot;${message}&quot;</span>
				<span class="arrow">/&gt;</span>
			</div>
		<#elseif element.getName() == "execute">
			<#if element.attributeValue("action")??>
				<#assign actionElement = element>

				<#include "action_element_html.ftl">
			<#elseif element.attributeValue("macro")??>
				<#assign macroElement = element>

				<#include "macro_element_html.ftl">
			</#if>
		<#elseif element.getName() == "fail">
			<#assign message = element.attributeValue("message")>

			<div>
				<span class="arrow">&lt;</span><span class="tag">fail</span>
				<span class="attribute">message</span><span class="arrow">=</span><span class="quote">&quot;${message}&quot;</span>
				<span class="arrow">/&gt;</span>
			</div>
		<#elseif element.getName() == "var">
			<#assign varElement = element>

			<#assign varName = varElement.attributeValue("name")>
			<#assign varValue = varElement.attributeValue("value")>

			<div>
				<span class="arrow">&lt;</span><span class="tag">var</span>
				<span class="attribute">name</span><span class="arrow">=</span><span class="quote">&quot;${varName}&quot;</span>
				<span class="attribute">value</span><span class="arrow">=</span><span class="quote">&quot;${varValue}&quot;</span>
				<span class="arrow">/&gt;</span>
			</div>
		</#if>
	</li>
</#list>