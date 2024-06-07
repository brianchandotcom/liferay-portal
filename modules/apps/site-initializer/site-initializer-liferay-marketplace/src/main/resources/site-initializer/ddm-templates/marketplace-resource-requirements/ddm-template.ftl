<#if themeDisplay?has_content>
	<#assign scopeGroupId = themeDisplay.getScopeGroupId() />
</#if>

<#if currentURL?has_content>
	<#if currentURL?contains('web')>
		<#assign
			index = 2
			partsUrl = currentURL?split('/')
			siteName = partsUrl[index..index]?join('/')
		/>
	</#if>
</#if>

<#assign channel = restClient.get("/headless-commerce-delivery-catalog/v1.0/channels?accountId=-1&filter=name eq 'Marketplace Channel' and siteGroupId eq '${scopeGroupId}'") />

<#if channel?has_content>
	<#assign channelId = channel.items[0].id />
</#if>

<#if (CPDefinition_cProductId.getData())??>
	<#assign productId = CPDefinition_cProductId.getData() />
</#if>

<#assign
	product = restClient.get("/headless-commerce-delivery-catalog/v1.0/channels/"+ channelId +"/products/"+ productId +"?accountId=-1&nestedFields=productSpecifications")
	specifications = product.productSpecifications![]
/>

<div>
	<#if specifications?has_content>

		<#assign
			specificationCPUs = specifications?filter(item -> stringUtil.equals(item.specificationKey, "cpu"))
			specificationRAMs = specifications?filter(item -> stringUtil.equals(item.specificationKey, "ram"))
			cpuQuantity = ""
			memoryQuantity = ""
		/>

		<#if specificationCPUs?has_content>
			<script>
				var resourceRequirements = document.querySelector(".resource-requirements")

				if (resourceRequirements) {
					resourceRequirements.classList.remove("d-none")
				}
			</script>

		 	<#list specificationCPUs as cpu>
				<#assign cpuQuantity = cpu.value />

				<#if cpuQuantity?has_content>
					${cpuQuantity}
					<#if cpuQuantity?eval gt 1>
						CPUS
					</#if>

					<#if cpuQuantity?eval lt 2>
						CPU
					</#if>
				</#if>
		  	</#list>
		</#if>

		<#if specificationRAMs?has_content>
		  	<#list specificationRAMs as ram>
				<#assign memoryQuantity = ram.value />

				<#if cpuQuantity?has_content && memoryQuantity?has_content >, </#if>

				<#assign memoryQuantity = ram.value />

				<#if memoryQuantity?has_content>
					${memoryQuantity} GB RAM
				</#if>
		  	</#list>
		</#if>
	</#if>
</div>