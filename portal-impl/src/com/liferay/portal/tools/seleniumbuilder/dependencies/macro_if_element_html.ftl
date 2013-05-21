<#if ifElement.element("condition")??>
	<#assign conditionElement = ifElement.element("condition")>

	<#assign lineNumber = conditionElement.attributeValue("line-number")>

	<li id="${macroNameStack.peek()}Macro__${lineNumber}">
		<#if conditionElement.attributeValue("action")??>
			<#assign actionElement = conditionElement>

			<#include "action_element_html.ftl">
		<#elseif conditionElement.attributeValue("macro")??>
			<#assign macroElement = conditionElement>

			<#include "macro_element_html.ftl">
		</#if>
	</li>
<#elseif ifElement.element("contains")??>
	<#assign containsElement = ifElement.element("contains")>

	<#assign lineNumber = containsElement.attributeValue("line-number")>

	<li id="${macroNameStack.peek()}Macro__${lineNumber}">
		<div>
			<span class="arrow">&lt;</span><span class="tag">contains</span>

			<span class="attribute">string</span><span class="arrow">=</span><span class="quote">&quot;${containsElement.attributeValue("string")}&quot;</span>
			<span class="attribute">substring</span><span class="arrow">=</span><span class="quote">&quot;${containsElement.attributeValue("substring")}&quot;</span>

			<span class="arrow">/&gt;</span>
		</div>
	</li>
<#elseif ifElement.element("equals")??>
	<#assign equalsElement = ifElement.element("equals")>

	<#assign lineNumber = equalsElement.attributeValue("line-number")>

	<li id="${macroNameStack.peek()}Macro__${lineNumber}">
		<div>
			<span class="arrow">&lt;</span><span class="tag">equals</span>

			<span class="attribute">arg1</span><span class="arrow">=</span><span class="quote">&quot;${equalsElement.attributeValue("arg1")}&quot;</span>
			<span class="attribute">arg2</span><span class="arrow">=</span><span class="quote">&quot;${equalsElement.attributeValue("arg2")}&quot;</span>

			<span class="arrow">/&gt;</span>
		</div>
	</li>
<#elseif ifElement.element("isset")??>
	<#assign issetElement = ifElement.element("isset")>

	<#assign lineNumber = issetElement.attributeValue("line-number")>

	<li id="${macroNameStack.peek()}Macro__${lineNumber}">
		<div>
			<span class="arrow">&lt;</span><span class="tag">isset</span>

			<span class="attribute">var</span><span class="arrow">=</span><span class="quote">&quot;${issetElement.attributeValue("var")}&quot;</span>

			<span class="arrow">/&gt;</span>
		</div>
	</li>
</#if>

<#assign thenElement = ifElement.element("then")>

<#assign lineNumber = thenElement.attributeValue("line-number")>

<li id="${macroNameStack.peek()}Macro__${lineNumber}">
	<div>
		<span class="arrow">&lt;</span><span class="tag">then</span><span class="arrow">&gt;</span>
	</div>

	<ul>
		<#assign macroBlockElement = thenElement>

		<#include "macro_block_element_html.ftl">
	</ul>

	<div>
		<span class="arrow">&lt;/</span><span class="tag">then</span><span class="arrow">&gt;</span>
	</div>
</li>