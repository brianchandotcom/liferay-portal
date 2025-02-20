<style>
	.badge {
		font-size: .8rem;
		padding: 10px;
	}

	.card-title {
		font-size: 1rem;
	}

	.custom-checkbox label {
		padding-top:0.125rem;
	}

	.product-category {
		font-size: 0.8rem;
	}

	.suggested {
		background-color: #2e5aac !important;
		color: #ffffff !important;
	}
</style>

<#assign
	accountEntryId = account.getAccountEntryId()
	commerceChannelId = commerceContext.getCommerceChannelId()
	commerceContext = renderRequest.getAttribute("COMMERCE_CONTEXT")
/>

<div class="product-card-tiles">
	<#if entries?has_content>
		<#list entries as curCPCatalogEntry>
			<#assign
				cpDefinitionId = curCPCatalogEntry.getCPDefinitionId()
				productDetail = restClient.get("/headless-commerce-delivery-catalog/v1.0/channels/${chanelId}/products/${productId}?accountId=${accountId}&nestedFields=categories,productSpecifications")

				categories = productDetail.categories
				cpDefaultImageFileVersion = cpContentHelper.getCPDefinitionImageFileVersion(cpDefinitionId, request)
				defaultImageFileURL = cpContentHelper.getDefaultImageFileURL(accountEntryId, cpDefinitionId)
				featuredSpecificationKeys = ["fit", "weight", "material"]
				friendlyURL = cpContentHelper.getFriendlyURL(curCPCatalogEntry, themeDisplay)
				isSuggested = false
				productDescription = curCPCatalogEntry.getDescription()
				productId = curCPCatalogEntry.getCProductId()
				productName = curCPCatalogEntry.getName()
				productShortDescription = curCPCatalogEntry.getShortDescription()
				productSpecifications = productDetail.productSpecifications
				suggestedClass = ""
				tags = productDetail.tags
			/>

			<#if cpContentHelper.getDefaultCPSku(curCPCatalogEntry)?has_content>
				<#assign sku = cpContentHelper.getDefaultCPSku(curCPCatalogEntry).getSku() />
			<#else>
				<#assign sku = "" />
			</#if>

			<#if tags?seq_contains("suggested")>
				<#assign
					isSuggested = true
					suggestedClass = "suggested"
				/>
			</#if>

			<a href="${friendlyURL}">
				<div class="card d-flex flex-column product-card shadow-none mb-4 mx-3">
					<img class="card-img-top rounded mb-3" src="${defaultImageURL}" alt="${productName}" />

					<h5 class="card-title mb-1">${productName}</h5>

					<p>Product</p>

					<#list specifications as spec>
						<#if spec??>
							<div>${spec}</div>
						</#if>
					</#list>
				</div>
			</a>
		</#list>
	</#if>
</div>