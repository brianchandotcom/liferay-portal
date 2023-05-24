<#assign
	channels = restClient.get("/headless-commerce-delivery-catalog/v1.0/channels")
	channelId = ""
/>

<#list channels.items as channel>
	<#if channel.name == "Marketplace Channel">
		<#assign channelId = channel.id />
	</#if>
</#list>

<#if (CPDefinition_cProductId.getData())??>
	<#assign specifications = restClient.get("/headless-commerce-delivery-catalog/v1.0/channels/" + channelId + "/products/" + CPDefinition_cProductId.getData() + "/product-specifications") />
</#if>

<#if specifications?has_content && specifications.items?has_content>
	<#list specifications.items as specification>
		<#if specification.specificationKey?has_content && specification.specificationKey == "price-model">
			${specification.value}
		</#if>
	</#list>
</#if>