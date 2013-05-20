<#assign macro = macroElement.attributeValue("macro")>

<div>
	<span class="arrow">&lt;</span><span class="tag">execute</span>
	<span class="attribute">macro</span><span class="arrow">=</span><span class="quote">&quot;${macro}&quot;</span>
	<span class="arrow">&gt;</span>
</div>

<#assign x = macro?last_index_of("#")>

<#assign macroName = macro?substring(0, x)>

<#assign macroCommand = macro?substring(x + 1)>

<#assign blank = macroNameStack.push(macroName)>

<#assign blank = macroNameStack.pop()>

<div>
	<span class="arrow">&lt;/</span><span class="tag">execute</span><span class="arrow">&gt;</span>
</div>