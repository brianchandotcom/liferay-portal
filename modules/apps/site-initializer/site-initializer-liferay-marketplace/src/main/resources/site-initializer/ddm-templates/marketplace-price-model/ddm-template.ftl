<#assign
	channelId=""
	channels=restClient.get("/headless-commerce-delivery-catalog/v1.0/channels")
	filteredSpecifications=[] />

<#list channels.items as channel>
	<#if channel.name=="Marketplace Channel">
		<#assign channelId = channel.id />
	</#if>
</#list>

<#if (CPDefinition_cProductId.getData())??>
	<#assign specifications = restClient.get("/headless-commerce-delivery-catalog/v1.0/channels/" + channelId + "/products/" + CPDefinition_cProductId.getData() + "/product-specifications") />
</#if>

<#if specifications?has_content && specifications.items?has_content>
	<#list specifications.items?sort_by("specificationKey") as specification>
		<#if specification.specificationKey?has_content>
			<#if stringUtil.equals(specification.specificationKey, "price-model" )>
				<#assign priceModel = specification.value />
				<#if priceModel?has_content>
					${priceModel}
				</#if>
			</#if>
		</#if>
	</#list>
</#if>