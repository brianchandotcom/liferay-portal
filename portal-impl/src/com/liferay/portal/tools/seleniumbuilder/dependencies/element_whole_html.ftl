<div>
	<span class="arrow">&lt;</span>
	<span class="tag">${displayElement.getName()}</span>

	<#assign displayElementAttributes = displayElement.attributes()>

	<#list displayElementAttributes as displayElementAttribute>
		<#if displayElementAttribute.getName() != "line-number">
			<span class="attribute">${displayElementAttribute.getName()}</span>
			<span class="arrow">=</span>
			<span class="quote">&quot;${displayElementAttribute.getValue()}&quot;</span>
		</#if>
	</#list>

	<span class="arrow">/&gt;</span>
</div>