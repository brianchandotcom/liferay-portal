<style>

		.badge {
			padding: 10px;
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
														<#if (categoryCount gt 0) >
															|
														</#if>
														<span class="product-category mb-1">${category.name}</span>
														<#assign categoryCount++ />
													</#list>
												</#if>

												<#if specifications?has_content>
													<#list specifications as specification>
														<#if featuredSpecificationKeys?seq_contains(specification.specificationKey) >
															<span class="badge badge-secondary">${specification.value}"</span>
														</#if>
													</#list>
												</#if>
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
									</div>
								</div>
							</#list>
						</#if>
					</div>