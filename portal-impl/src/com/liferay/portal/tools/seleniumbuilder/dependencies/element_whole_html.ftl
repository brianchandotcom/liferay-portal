<div>
	<div class="non-expand-line">
		<span class="arrow">&lt;</span>
		<span class="tag">${displayElement.getName()}</span>

		<#assign displayElementAttributes = displayElement.attributes()>

		<#list displayElementAttributes as displayElementAttribute>
			<#if displayElementAttribute.getName() != "line-number">
				<span class="attribute">${displayElementAttribute.getName()}</span>
				<span class="arrow">=</span>
				<span class="quote">&quot;${seleniumBuilderFileUtil.escapeHtml(displayElementAttribute.getValue())}&quot;</span>
			</#if>
		</#list>

		<span class="arrow">/&gt;</span>

		<div class="line-number">${lineNumber}</div>
	</div>
</div>