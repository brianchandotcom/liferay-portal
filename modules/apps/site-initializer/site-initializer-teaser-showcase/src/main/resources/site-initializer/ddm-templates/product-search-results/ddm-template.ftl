<style>
		.badge {
			/*padding: 10px;*/
			font-size: .8rem;
		}
		.card-title {
			font-size: 1rem;
		}
		.product-category {
			font-size: 0.8rem;
		}
		.suggested {
			background-color: #2e5aac !important;
			color: #ffffff !important;
		}
		.custom-checkbox label {
			padding-top:0.125rem;
		}
</style>

<#assign
	commerceContext = renderRequest.getAttribute("COMMERCE_CONTEXT")
	account = commerceContext.getAccountEntry()
	accountId = account.getAccountEntryId()
	chanelId = commerceContext.getCommerceChannelId()
/>

<div class="product-card-tiles">
	<#if entries?has_content>
		<#list entries as curCPCatalogEntry>				
				<#assign
					cpDefinitionId = curCPCatalogEntry.getCPDefinitionId()
					productId = curCPCatalogEntry.getCProductId()
					productName = curCPCatalogEntry.getName()
					productShortDescription = curCPCatalogEntry.getShortDescription()
					productDescription = curCPCatalogEntry.getDescription()
					friendlyURL = cpContentHelper.getFriendlyURL(curCPCatalogEntry, themeDisplay)
					defaultImageURL = cpContentHelper.getDefaultImageFileURL(accountId, cpDefinitionId)
					defaultImageFileVersion = cpContentHelper.getCPDefinitionImageFileVersion(cpDefinitionId, request)
					productDetail = restClient.get("/headless-commerce-delivery-catalog/v1.0/channels/${chanelId}/products/${productId}?accountId=${accountId}&nestedFields=categories,productSpecifications")
					categories = productDetail.categories
					specifications = productDetail.productSpecifications
					tags = productDetail.tags
					featuredSpecificationKeys = ["fit", "weight", "material"]
					isSuggested = false
					suggestedClass = ""
				/>
				
				<#if cpContentHelper.getDefaultCPSku(curCPCatalogEntry)?has_content>
					<#assign sku = cpContentHelper.getDefaultCPSku(curCPCatalogEntry).getSku()/>
				<#else>
					<#assign sku = "" />
				</#if>

				<#if tags?seq_contains("suggested")>
					<#assign isSuggested = true />
					<#assign suggestedClass = "suggested" />
				</#if>

				<a href="${friendlyURL}">
					<div class="card d-flex flex-column product-card shadow-none mb-4 mx-3">
						<img class="card-img-top rounded mb-3" src="${defaultImageURL}" alt="${productName}"/>
						<h5 class="card-title mb-1">${productName}</h5>
						<p>Product</p>		
						<list specifications as spec>
							<#if spec??>
								<div>${spec}</div>
							</#if>

						</list>
					</div>
				</a>
		</#list>
	</#if>
</div>