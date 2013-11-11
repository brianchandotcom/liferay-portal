<#assign displayChildElements = displayElement.elements()>

<#assign displayChildElementsSize = displayChildElements?size>

<#if
	(displayElementName == "execute") &&
	(displayChildElementsSize != 0)
>
	<div class="parameter-border">
		<#list displayChildElements as displayChildElement>
			<span>&lt;</span>
			<span>${displayChildElement.getName()}</span>

			<#assign displayChildElementAttributes = displayChildElement.attributes()>

			<#list displayChildElementAttributes as displayChildElementAttribute>
				<#if displayChildElementAttribute.getName() != "line-number">
					<span>${displayChildElementAttribute.getName()}</span>
					<span>=</span>
					<span class="parameter-value">&quot;${displayChildElementAttribute.getValue()}&quot;</span>
				</#if>
			</#list>

			<span>/&gt;</span>

			<div class="line-number">
				<#list displayChildElementAttributes as displayChildElementAttribute>
					<#if displayChildElementAttribute.getName() == "line-number">
						${displayChildElementAttribute.getValue()}
					</#if>
				</#list>
			</div>

			<br />
		</#list>
	</div>
</#if>