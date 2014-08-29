<div>
	<div class="non-expand-line">
		<span class="arrow">&lt;</span>
		<span class="tag">${displayElement.getName()}</span>

		<#assign displayElementAttributes = displayElement.attributes()>

		<#list displayElementAttributes as displayElementAttribute>
			<#if displayElementAttribute.getName() != "line-number">
				<span class="attribute">${displayElementAttribute.getName()}</span>
				<span class="arrow">=</span>
				<span class="quote" id="${displayElementAttribute.getName() + lineNumber}">&quot;${seleniumBuilderFileUtil.escapeHtml(displayElementAttribute.getValue())}&quot;</span>
			</#if>
		</#list>

		<#if testCaseRootElement.elements("var")?? && displayElement.getText()?has_content>
			<span class="attribute">value</span>
			<span class="arrow">=</span>
			<span class="quote" id="value${lineNumber}">&quot;${seleniumBuilderFileUtil.escapeHtml(displayElement.getText())}&quot;</span>
		</#if>

		<span class="arrow">/&gt;</span>

		<div class="line-number">${lineNumber}</div>
	</div>
</div>