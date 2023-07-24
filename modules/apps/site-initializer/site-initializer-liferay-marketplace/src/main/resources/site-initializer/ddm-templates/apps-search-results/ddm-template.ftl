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

	.adt-apps-search-results .labels .category-names {
		background-color: #2c3a4b;
		bottom: 26px;
		display: none;
		width: 14.5rem;
	}

	.adt-apps-search-results .labels .category-names::after {
		border-left: 9px solid transparent;
		border-right: 9px solid transparent;
		border-top: 8px solid var(--neutral-1);
		bottom: -7px;
		content:'';
		left: 0;
		margin: 0 auto;
		position: absolute;
		right: 0;
		width: 0;
	}

	.adt-apps-search-results .labels .category-label {
		background-color: #ebeef2;
		color: #545D69;
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

<#assign
	productsList = restClient.get("/headless-commerce-admin-catalog/v1.0/products?pageSize=-1").items
	numberFilteredProducts = 0
/>

<#function filterProductsByAppCategory productsList>
	<#return productsList.categories?filter(category -> stringUtil.equals(category.name, "App"))>
</#function>

<#list productsList as product>
	<#list filterProductsByAppCategory(product) as product>
		<#assign numberFilteredProducts = numberFilteredProducts + 1 />
	</#list>
</#list>

<div class="adt-apps-search-results">
	<#if productsList?has_content>
		<div class="color-neutral-3 d-md-block d-none pb-4">
			<strong class='color-black'>${numberFilteredProducts}</strong> Apps Available
		</div>

		<div class="cards-container pb-6">
			<#list productsList as product>
				<#assign
					productCategories = product.categories
					productDescription = stringUtil.shorten(htmlUtil.stripHtml(product.description.en_US), 150, "...")
					portalURL = portalUtil.getLayoutURL(themeDisplay)
					productURL = portalURL?replace("home", "p") + "/" + product.urls.en_US
				/>

				<#list filterProductsByAppCategory(product) as category>
				 	<a class="app-search-results-card bg-white border-radius-medium d-flex flex-column mb-0 p-3 text-dark text-decoration-none" href=${productURL}>
						<div class="align-items-center card-image-title-container d-flex pb-3">
							<div class="image-container rounded">
								<img
									alt=${product.name.en_US}
									class="h-100 mw-100"
									src="${product.thumbnail}"
								/>
							</div>

							<div class="pl-2">
								<div class="font-weight-semi-bold h2 mt-1">
									${product.name.en_US}
								</div>
							</div>
				 		</div>

						<div class="d-flex flex-column font-size-paragraph-small h-100 justify-content-between">
				  			<div>
								<div class="font-weight-normal mb-2">
						  			${productDescription}
						 		</div>

								<#if productCategories?has_content>
									<div class="align-center d-flex labels">
										<div class="border-radius-small category-label font-size-paragraph-small font-weight-semi-bold px-1">
											${productCategories[0].name}
										</div>

										<#if (productCategories?size > 1)>
											<div class="category-label-remainder pl-2 position-relative text-primary">
												+${productCategories?size - 1}

												<div class="category-names font-size-paragraph-base p-4 position-absolute rounded text-white">
													<#list productCategories as category>
														<#if !category?is_first>
															${category.name}<#sep>, </#sep>
														</#if>
													</#list>
												</div>
											</div>
										</#if>
									</div>
								</#if>
					 		</div>
				  		</div>
				 	</a>
				</#list>
			</#list>
		</div>
	</#if>
</div>