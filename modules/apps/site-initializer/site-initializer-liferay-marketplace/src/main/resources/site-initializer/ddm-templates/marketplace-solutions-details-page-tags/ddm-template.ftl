<style>
	.solution-tag-container {
		font-size: 1rem;
		gap: 8px;
		line-height: 1.5;
	}

	.solution-tag-container .solution-tag {
		background-color: #E6EBF5;
		border-radius: 4px;
		color: #1C3667;
	}
</style>

<#assign VOCABULARY_NAME = "marketplace solution category" />

<#if themeDisplay?has_content>
	<#assign scopeGroupId = themeDisplay.getScopeGroupId() />
</#if>

<#assign channel = restClient.get("/headless-commerce-delivery-catalog/v1.0/channels?accountId=-1&filter=name eq 'Marketplace Channel' and siteGroupId eq '${scopeGroupId}'") />

<#if channel?has_content>
	<#assign channelId = channel.items[0].id />
</#if>

<#if (CPDefinition_cProductId.getData())??>
	<#assign
		productId = CPDefinition_cProductId.getData()
		product = restClient.get("/headless-commerce-delivery-catalog/v1.0/channels/"+ channelId +"/products/"+ productId +"?accountId=-1&nestedFields=categories")

		categories = product.categories
	/>

	<div class="color-neutral-3 d-flex flex-wrap font-size-paragraph-small solution-tag-container">
		<#list categories as category>
			<#if category.vocabulary == VOCABULARY_NAME>
				<div class="border-radius-small px-2 py-1 solution-tag">
					${category.name}
				</div>
			</#if>
		</#list>
	</div>
</#if>