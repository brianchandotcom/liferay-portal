<style type="text/css">
	.adt-solutions-search-results .cards-container {
		display: grid;
		grid-column-gap: 1rem;
		grid-row-gap: 1.5rem;
		grid-template-columns: repeat(3, minmax(0, 1fr));
	}

	.adt-solutions-search-results .solutions-search-results-card:hover {
		color: var(--black);
	}

	.adt-solutions-search-results .card-image-title-container .image-container {
		height: 3rem;
		min-width: 3rem;
	}

	.adt-solutions-search-results .labels .category-names {
		background-color: #2c3a4b;
		bottom: 26px;
		display: none;
		right: 0;
		width: 14.5rem;
	}

	.adt-solutions-search-results .labels .category-names::after {
		border-left: 9px solid transparent;
		border-right: 9px solid transparent;
		border-top: 8px solid var(--neutral-1);
		bottom: -7px;
		content: '';
		left: 0;
		margin: 0 auto;
		position: absolute;
		right: 0;
		width: 0;
	}

	.adt-solutions-search-results .labels .category-label {
		background-color: #ebeef2;
		color: #545D69;
	}

	.adt-solutions-search-results .labels .category-label-remainder:hover .category-names {
		display: block;
	}

	.developer-name {
		color: #545d69;
	}

	.productSpec {
		color: #545d69;
	}

	.solution-search-image {
		object-fit: cover;
	}

	@media screen and (max-width: 599px) {
		.adt-solutions-search-results .cards-container {
			grid-row-gap: 1rem;
			grid-template-columns: 288px;
			justify-content: center;
		}
	}

	@media screen and (min-width: 600px) and (max-width: 899px) {
		.adt-solutions-search-results .cards-container {
			grid-template-columns: repeat(2, minmax(0, 1fr));
		}
	}
</style>

<#assign categoryName = "Solution" />

<#if searchContainer?has_content>
	<div class="color-neutral-3 d-md-block d-none pb-4">
		<strong class="color-black">
			${searchContainer.getTotal()}
		</strong>
		${categoryName}s Available
	</div>
</#if>

<div class="adt-solutions-search-results">
	<div class="cards-container pb-6">
		<#if entries?has_content>
			<#list entries as entry>
				<#if entry?has_content>
					<#assign
						portalURL = portalUtil.getLayoutURL(themeDisplay)
						productId = entry.getClassPK() + 1
						product = restClient.get("/headless-commerce-admin-catalog/v1.0/products/" + productId + "?nestedFields=productSpecifications,attachments")
						productAttachments = product.attachments![]
						productCategories=product.categories![]
						productDescription = stringUtil.shorten(htmlUtil.stripHtml(product.description.en_US!""), 150, "...")
						productSpecifications = product.productSpecifications![]
						productURL = portalURL?replace("solutions-marketplace", "p") + "/" + product.urls.en_US
					/>

					<a class="solution-search-results-card bg-white border-radius-medium d-flex flex-column mb-0 rounded text-dark text-decoration-none" href=${productURL}>
						<div class="align-items-center d-flex image-container justify-content-center mb-3">
							<img
								width="329" height="180"
								alt=${product.name.en_US}
								class="solution-search-image rounded"
								src="${product.thumbnail}"
							/>
						</div>

						<div class="align-items-center card-image-title-container d-flex">
							<div class="pl-2">
								<#if productSpecifications?has_content>
									<#assign productPriceModels = productSpecifications?filter(item -> item.specificationKey == "developer-name") />

									<#list productPriceModels as productPriceModel>
										<div class="developer-name font-size-paragraph-small">
											${productPriceModel.value.en_US}
										</div>
									</#list>
								</#if>

								<div class="font-weight-semi-bold h2 mt-1">
									${product.name.en_US}
								</div>
							</div>
						</div>

						<div class="d-flex flex-column font-size-paragraph-small h-100 justify-content-between p-2">
							<div class="font-weight-normal mb-2">
								${productDescription}
							</div>

							<#if productCategories?has_content>
								<#assign solutionCategories = productCategories?filter(category -> category.vocabulary == 'marketplace solution category') />

								<div class="align-items-center d-flex labels">
									<#list solutionCategories as category>
										<div class="border-radius-small category-label font-size-paragraph-small font-weight-semi-bold px-1 rounded">
											${category.name}
										</div>
										<#break>
									</#list>

									<#assign productCategoriesItems = solutionCategories?map(category -> category.name) />

									<#if (productCategoriesItems?size > 1)>
										<div class="category-label-remainder pl-2 position-relative text-primary">
											+${productCategoriesItems?size - 1}
											<div class="category-names font-size-paragraph-base p-4 position-absolute rounded text-white">
												<#list productCategoriesItems as category>
													<#if category_index != 0>
														${category}
														<#sep>,</#sep>
													</#if>
												</#list>
											</div>
										</div>
									</#if>
								</div>
							</#if>
						</div>
					</a>
				</#if>
			</#list>
		</#if>
	</div>
</div>