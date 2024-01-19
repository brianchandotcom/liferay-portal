<#assign
	channelId=""
	channels=restClient.get("/headless-commerce-delivery-catalog/v1.0/channels")
	filteredSpecifications=[]
/>

<#list channels.items as channel>
	<#if channel.name=="Marketplace Channel">
		<#assign channelId = channel.id />
	</#if>
</#list>

<#if (CPDefinition_cProductId.getData())??>
	<#assign specifications = restClient.get("/headless-commerce-delivery-catalog/v1.0/channels/" + channelId + "/products/" + CPDefinition_cProductId.getData() + "/product-specifications") />
</#if>

<#if specifications?has_content && specifications.items?has_content>

	<#assign
		cpuQuantity = ""
		memoryQuantity = ""
	/>

	<#list specifications.items?sort_by("specificationKey") as specification>
		<#if specification.specificationKey?has_content && (stringUtil.equals(specification.specificationKey, "cpu") || stringUtil.equals(specification.specificationKey, "ram"))>
			<#if stringUtil.equals(specification.specificationKey, "cpu" )>
				<#assign cpuQuantity = specification.value />

				<#if cpuQuantity?has_content>
					${cpuQuantity}
					<#if cpuQuantity?eval gt 1>
						CPUS
					</#if>
					<#if cpuQuantity?eval lt 2>
						CPU
					</#if>
				</#if>
			</#if>

			<#if stringUtil.equals(specification.specificationKey, "ram")>
				<#assign memoryQuantity = specification.value />

				<#if cpuQuantity?has_content && memoryQuantity?has_content >,</#if>
			</#if>

			<#if stringUtil.equals(specification.specificationKey, "ram")>
				<#assign memoryQuantity = specification.value />

				<#if memoryQuantity?has_content>
					${memoryQuantity} GB RAM
				</#if>
			</#if>
		</#if>
	</#list>
</#if>