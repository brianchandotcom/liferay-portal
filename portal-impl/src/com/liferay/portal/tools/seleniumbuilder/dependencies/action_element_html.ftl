<#assign action = actionElement.attributeValue("action")>

<div>
	<span class="arrow">&lt;</span><span class="tag">${actionElement.getName()}</span>
	<span class="attribute">action</span><span class="arrow">=</span><span class="quote">&quot;${action}&quot;</span>

	<#assign x = action?last_index_of("#")>

	<#assign functionName = seleniumBuilderFileUtil.getObjectName(action?substring(x + 1))>

	<#list 1..seleniumBuilderContext.getFunctionLocatorCount(functionName) as i>
		<#if actionElement.attributeValue("locator${i}")??>
			<#assign actionLocator = actionElement.attributeValue("locator${i}")>

			<span class="attribute">locator${i}</span><span class="arrow">=</span><span class="quote">&quot;${actionLocator}&quot;</span>
		</#if>

		<#if actionElement.attributeValue("locator-key${i}")??>
			<#assign actionLocatorKey = actionElement.attributeValue("locator-key${i}")>

			<span class="attribute">locator-key${i}</span><span class="arrow">=</span><span class="quote">&quot;${actionLocatorKey}&quot;</span>
		</#if>

		<#if actionElement.attributeValue("value${i}")??>
			<#assign actionValue = actionElement.attributeValue("value${i}")>

			<span class="attribute">value${i}</span><span class="arrow">=</span><span class="quote">&quot;${actionValue}&quot;</span>
		</#if>
	</#list>

	<span class="arrow">/&gt;</span>
</div>