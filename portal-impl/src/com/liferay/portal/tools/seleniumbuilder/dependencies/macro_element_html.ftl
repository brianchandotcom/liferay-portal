<#assign macro = macroElement.attributeValue("macro")>

<div>
	<span class="arrow">&lt;</span><span class="tag">execute</span>
	<span class="attribute">macro</span><span class="arrow">=</span><span class="quote">&quot;${macro}&quot;</span>
	<span class="arrow">&gt;</span>
</div>

<#assign x = macro?last_index_of("#")>

<#assign macroName = macro?substring(0, x)>

<#assign macroCommand = macro?substring(x + 1)>

<#assign void = macroNameStack.push(macroName)>

<#assign macroRootElement = seleniumBuilderContext.getMacroRootElement(macroName)>

<#assign macroCommandElements = macroRootElement.elements("command")>

<ul>
	<#list macroCommandElements as macroCommandElement>
		<#assign macroCommandName = macroCommandElement.attributeValue("name")>

		<#if macroCommandName == macroCommand>
			<#assign macroRootVarElements = macroRootElement.elements("var")>

			<#list macroRootVarElements as macroRootVarElement>
				<#assign lineNumber = macroRootVarElement.attributeValue("line-number")>

				<li id="${macroNameStack.peek()}Macro__${lineNumber}">
					<#assign varName = macroRootVarElement.attributeValue("name")>
					<#assign varValue = macroRootVarElement.attributeValue("value")>

					<div>
						<span class="arrow">&lt;</span><span class="tag">var</span>
						<span class="attribute">name</span><span class="arrow">=</span><span class="quote">&quot;${varName}&quot;</span>
						<span class="attribute">value</span><span class="arrow">=</span><span class="quote">&quot;${varValue}&quot;</span>
						<span class="arrow">/&gt;</span>
					</div>
				</li>
			</#list>

			<#assign macroVarElements = macroElement.elements("var")>

			<#list macroVarElements as macroVarElement>
				<#assign lineNumber = macroVarElement.attributeValue("line-number")>

				<li id="${macroNameStack.peek()}Macro__${lineNumber}">
					<#assign varName = macroVarElement.attributeValue("name")>
					<#assign varValue = macroVarElement.attributeValue("value")>

					<div>
						<span class="arrow">&lt;</span><span class="tag">var</span>
						<span class="attribute">name</span><span class="arrow">=</span><span class="quote">&quot;${varName}&quot;</span>
						<span class="attribute">value</span><span class="arrow">=</span><span class="quote">&quot;${varValue}&quot;</span>
						<span class="arrow">/&gt;</span>
					</div>
				</li>
			</#list>

			<#assign macroBlockElement = macroCommandElement>

			<#include "macro_block_element_html.ftl">

			<#break>
		</#if>
	</#list>
</ul>

<#assign void = macroNameStack.pop()>

<div>
	<span class="arrow">&lt;/</span><span class="tag">execute</span><span class="arrow">&gt;</span>
</div>