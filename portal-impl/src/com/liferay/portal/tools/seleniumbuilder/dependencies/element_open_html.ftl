<#assign collapsed = true>

<#assign displayElementName = displayElement.getName()>

<#if
	(displayElementName == "command") ||
	(displayElementName == "elseif") ||
	(displayElementName == "if")
>
	<#assign collapsed = false>
</#if>

<#assign lineFolds = lineFolds + 1>

<div>
	<#if collapsed>
		<div id="toggle${lineFolds}" class="expand-toggle">+</div>
	<#else>
		<div id="toggle${lineFolds}" class="expand-toggle">-</div>
	</#if>
</div>

<div>
	<div class="expand-line">
		<span class="arrow">&lt;</span>
		<span class="tag">${displayElementName}</span>

		<#assign displayElementAttributes = displayElement.attributes()>

		<#list displayElementAttributes as displayElementAttribute>
			<#if displayElementAttribute.getName() != "line-number">
				<span class="attribute">${displayElementAttribute.getName()}</span>
				<span class="arrow">=</span>
				<span class="quote">&quot;${displayElementAttribute.getValue()}&quot;</span>
			</#if>
		</#list>

		<span class="arrow">&gt;</span>
	</div>
</div>

<#if collapsed>
	<ul id="collapseToggle${lineFolds}" class="collapse">
<#else>
	<ul id="collapseToggle${lineFolds}">
</#if>