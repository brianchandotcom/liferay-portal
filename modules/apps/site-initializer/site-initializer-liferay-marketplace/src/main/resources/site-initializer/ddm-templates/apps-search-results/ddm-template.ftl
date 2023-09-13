<style type="text/css">
	.adt-apps-search-results .cards-container {
		display: grid;
		grid-column-gap: 1rem;
		grid-row-gap: 1.5rem;
		grid-template-columns: repeat(3, minmax(0, 1fr));
	}

	.adt-apps-search-results .app-search-results-card:hover {
		color: var(--black);
	}

	.adt-apps-search-results .card-image-title-container .image-container {
		height: 3rem;
		min-width: 3rem;
	}

	.adt-apps-search-results .labels .category-label-remainder:hover .category-names {
		display: block;
	}

	@media screen and (max-width: 599px) {
		.adt-apps-search-results .cards-container {
			grid-row-gap: 1rem;
			grid-template-columns: 288px;
			justify-content: center;
		}

		.adt-apps-search-results .app-search-results-card {
			height: 281px;
		}
	}

	@media screen and (min-width:600px) and (max-width: 899px) {
		.adt-apps-search-results .cards-container {
			grid-template-columns: repeat(2, minmax(0, 1fr));
		}
	}
</style>

<#assign categoryName = "App" />

<#if searchContainer?has_content>
	<div class="color-neutral-3 d-md-block d-none pb-4">
		<strong class="color-black">
			${searchContainer.getTotal()}
		</strong>
		${categoryName}s Available
	</div>
</#if>

<div class="adt-apps-search-results">
	<div class="cards-container pb-6">
		<#if entries?has_content>
			<#list entries as entry>
				<#if entry?has_content>
					<#assign
						portalURL = portalUtil.getLayoutURL(themeDisplay)
						productId = entry.getClassPK() + 1
						product = restClient.get("/headless-commerce-admin-catalog/v1.0/products/" + productId + "?nestedFields=productSpecifications,attachments" )
						productAttachments = product.attachments![]
						productDescription = stringUtil.shorten(htmlUtil.stripHtml(product.description.en_US!""), 150, "..." )
						productSpecifications = product.productSpecifications![]
						productURL=portalURL?replace("home", "p" ) + "/" + product.urls.en_US />

					<a class="app-search-results-card bg-white border-radius-medium d-flex flex-column mb-0 p-3 text-dark text-decoration-none" href=${productURL}>
						<div class="align-items-center card-image-title-container d-flex pb-3">
							<div class="image-container rounded">
								<#if productAttachments?has_content>
									<#list productAttachments as attachmentFields>
										<#list attachmentFields.customFields as field>
											<#if (field.name=="App Icon" ) && (field.customValue.data[0]?lower_case == "Yes"?lower_case)>
												<#assign srcName = "/o/" + attachmentFields.src?keep_after("/o/") />

												<img
													alt=${product.name.en_US}
													class="h-100 mw-100"
													src="${srcName}" />
											</#if>
										</#list>
									</#list>
								</#if>
							</div>

							<div class="pl-2">
								<div class="font-weight-semi-bold h2 mt-1">
									${product.name.en_US}
								</div>
								<#if productSpecifications?has_content>
									<#assign productPriceModels = productSpecifications?filter(item -> item.specificationKey == "developer-name") />

									<#list productPriceModels as productPriceModel>
										<div class="color-neutral-3 font-size-paragraph-small mt-1">
											${productPriceModel.value.en_US}
										</div>
									</#list>
								</#if>
							</div>
						</div>

						<div class="d-flex flex-column font-size-paragraph-small h-100 justify-content-between">
							<div>
								<div class="font-weight-normal mb-2">
									${productDescription}
								</div>
								<#if productSpecifications?has_content>
									<#assign productPriceModels = productSpecifications?filter(item -> item.specificationKey == "price-model") />

									<#list productPriceModels as productPriceModel>
										<div class="font-weight-semi-bold mt-1">
											${productPriceModel.value.en_US}
										</div>
									</#list>
								</#if>
							</div>
						</div>
					</a>
				</#if>
			</#list>
		</#if>
	</div>
</div>