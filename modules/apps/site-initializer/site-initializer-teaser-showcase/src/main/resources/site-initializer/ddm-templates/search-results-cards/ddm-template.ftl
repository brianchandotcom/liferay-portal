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
	accountEntry = commerceContext.getAccountEntry()
	accountEntryId = account.getAccountEntryId()
	commerceChannelId = commerceContext.getCommerceChannelId()
	commerceContext = renderRequest.getAttribute("COMMERCE_CONTEXT")
/>

<div class="product-card-tiles">
	<#if entries?has_content>
		<#list entries as curCPCatalogEntry>
			<#assign
				categories = productDetail.categories
				cpDefinitionId = curCPCatalogEntry.getCPDefinitionId()
				productSpecifications = productDetail.productSpecifications

				productDetail = restClient.get("/headless-commerce-delivery-catalog/v1.0/channels/${chanelId}/products/${productId}?accountId=${accountId}&nestedFields=categories,productSpecifications")

				cpDefaultImageFileVersion = cpContentHelper.getCPDefinitionImageFileVersion(cpDefinitionId, request)
				defaultImageFileURL = cpContentHelper.getDefaultImageFileURL(accountEntryId, cpDefinitionId)
				featuredSpecificationKeys = ["fit", "weight", "material"]
				friendlyURL = cpContentHelper.getFriendlyURL(curCPCatalogEntry, themeDisplay)
				isSuggested = false
				productDescription = curCPCatalogEntry.getDescription()
				productId = curCPCatalogEntry.getCProductId()
				productName = curCPCatalogEntry.getName()
				productShortDescription = curCPCatalogEntry.getShortDescription()
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

			<div class="cp-renderer">
				<div class="card d-flex flex-column product-card">
					<div class="card-item-first position-relative">
						<a href="${friendlyURL}">
							<img src="${defaultImageURL}" class="card-img-top" alt="${productName}" />
						</a>
					</div>

					<div class="card-body d-flex flex-column justify-content-between py-2 ${suggestedClass}">
						<div class="cp-information">
							<p class="card-title ${suggestedClass}" title="${productName}">
								<a class="${suggestedClass}" href="${friendlyURL}">
									<span class="text-truncate-inline">
										<span class="text-truncate">
											${productName}
										</span>
									</span>
								</a>
							</p>

							<#if categories?has_content>
								<#assign categoryCount = 0 />

								<#list categories as category>
									<#if categoryCount gt 0>
										|
									</#if>

									<span class="product-category mb-1">${category.name}</span>

									<#assign categoryCount++ />
								</#list>
							</#if>

							<#if specifications?has_content>
								<#list specifications as specification>
									<#if featuredSpecificationKeys?seq_contains(specification.specificationKey)>
										<span class="badge badge-secondary">${specification.value}"</span>
									</#if>
								</#list>
							</#if>
						</div>
					</div>

					<div class="autofit-float autofit-row autofit-row-center compare-wishlist">
						<div class="autofit-col autofit-col-expand compare-checkbox">
							<div class="autofit-section">
								<div class="custom-checkbox custom-control custom-control-primary">
									<div class="custom-checkbox custom-control">
										<@liferay_commerce_ui["compare-checkbox"]
											CPCatalogEntry=curCPCatalogEntry
											label="Compare"
										/>
									</div>
								</div>
							</div>
						</div>
					</div>

					<div class="autofit-col">
						<div class="autofit-section">
							<@liferay_commerce_ui["add-to-wish-list"]
								CPCatalogEntry=curCPCatalogEntry
								large=false
							/>
						</div>
					</div>
				</div>
			</div>
		</#list>
	</#if>
</div>