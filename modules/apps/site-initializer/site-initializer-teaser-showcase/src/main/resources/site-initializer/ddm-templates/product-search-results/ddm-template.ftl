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
		font-size: 1rem;
	}

	.product-spec {
		font-size: 0.8rem;
	}

	.product-specs {
		min-height: 1.5rem;
	}
</style>

<div class="product-card-tiles">
	<div class="row">
		<#if entries?has_content>
			<#list entries as curCPCatalogEntry>
				<#assign
					commerceContext = renderRequest.getAttribute("COMMERCE_CONTEXT")
					friendlyURL = cpContentHelper.getFriendlyURL(curCPCatalogEntry, themeDisplay)
					productId = curCPCatalogEntry.getCProductId()
					productName = curCPCatalogEntry.getName()

					accountEntryId = commerceContext.getAccountEntry().getAccountEntryId()
					channelId = commerceContext.getCommerceChannelId()

					defaultImageURL = cpContentHelper.getDefaultImageFileURL(accountEntryId, curCPCatalogEntry.getCPDefinitionId())
					productDetail = restClient.get("/headless-commerce-delivery-catalog/v1.0/channels/${channelId}/products/${productId}?accountId=${accountEntryId}&nestedFields=productSpecifications,categories")

					productCategories = productDetail.categories
				/>

				<a class="col-4" href="${friendlyURL}">
					<div class="card product-card shadow-sm">
						<img class="card-img-top" src="${defaultImageURL}" alt="${productName}" />

						<div class="px-3 py-2">
							<h5 class="card-title text-truncate">${productName}</h5>

							<#if productCategories?has_content>
								<#assign categoryCount = 0 />

								<#list productCategories as category>
									<#if categoryCount gt 0>
										|
									</#if>

									<span class="product-category mb-1">${category.name}</span>

									<#assign categoryCount++ />
								</#list>
							</#if>
						</div>
					</div>
				</a>
			</#list>
		</#if>
	</div>
</div>