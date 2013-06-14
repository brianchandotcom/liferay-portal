<#assign elements = testCaseBlockElement.elements()>

<ul id="Collapse--Toggle__${lineFolds}" class="collapse">
	<#assign lineFolds = lineFolds + 1>

	<#list elements as element>
		<#assign lineNumber = element.attributeValue("line-number")>

		<li id="${testCaseName}TestCase__${lineNumber}">
			<#if element.getName() == "execute">
				<#if element.attributeValue("action")??>
					<#assign actionElement = element>

					<#include "action_element_html.ftl">
				<#elseif element.attributeValue("macro")??>
					<#assign macroElement = element>

					<#include "macro_element_html.ftl">
				</#if>
			<#elseif element.getName() == "var">
				<#assign testCaseBlockVarElement = element>

				<#assign varName = testCaseBlockVarElement.attributeValue("name")>
				<#assign varValue = testCaseBlockVarElement.attributeValue("value")>

				<div>
					<span class="arrow">&lt;</span><span class="tag">var</span>
					<span class="attribute">name</span><span class="arrow">=</span><span class="quote">&quot;${varName}&quot;</span>
					<span class="attribute">value</span><span class="arrow">=</span><span class="quote">&quot;${varValue}&quot;</span>
					<span class="arrow">/&gt;</span>
				</div>
			</#if>
		</li>
	</#list>
</ul>