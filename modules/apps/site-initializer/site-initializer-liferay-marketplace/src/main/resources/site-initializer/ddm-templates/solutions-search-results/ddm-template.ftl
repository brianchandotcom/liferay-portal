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

	solution-search-results-card .card-image-title-container .developer-name {
		color: #545d69;
	}

	.productSpec {
		color: #545d69;
	}

	.adt-solutions-search-results .solution-search-results-card .solution-search-image {
		height: 180px;
		object-fit: cover;
		width: 329px;
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

<#if themeDisplay?has_content>
	<#assign scopeGroupId = themeDisplay.getScopeGroupId() />
</#if>

<#assign channel = restClient.get("/headless-commerce-delivery-catalog/v1.0/channels?accountId=-1&filter=name eq 'Marketplace Channel' and siteGroupId eq '${scopeGroupId}'") />

<#if channel?has_content>
	<#assign channelId = channel.items[0].id />
</#if>

<div class="adt-solutions-search-results">
	<div class="cards-container pb-6">
		<#if entries?has_content>
			<#list entries as entry>
				<#if entry?has_content>
					<#assign
						portalURL = portalUtil.getLayoutURL(themeDisplay)
						productId = entry.getClassPK() + 1
						product = restClient.get("/headless-commerce-delivery-catalog/v1.0/channels/"+ channelId +"/products/"+ productId +"?accountId=-1&nestedFields=productSpecifications,categories")
						productCategories=product.categories![]
						productSpecifications = product.productSpecifications![]
					/>

					<#if product.name?has_content>
						<#assign productName = product.name />
					<#else>
						<#assign productName = "" />
					</#if>

					<#if product.description?has_content>
						<#assign productDescription = stringUtil.shorten(htmlUtil.stripHtml(product.description!""), 150, "...") />
					<#else>
						<#assign productDescription = "" />
					</#if>

					<#if product.urlImage?has_content>
						<#assign
							productThumbnail = product.urlImage?split("/o/")
						/>
					<#else>
						<#assign productThumbnail = "" />
					</#if>

					<#if product.urls?has_content>
						<#assign productURL = portalURL?replace("solutions-marketplace", "p") + "/" + product.urls.en_US />
					<#else>
						<#assign productURL = "" />
					</#if>

					<a class="solution-search-results-card bg-white border-radius-medium d-flex flex-column mb-0 rounded text-dark text-decoration-none" href=${productURL}>
						<div class="align-items-center d-flex image-container justify-content-center mb-3">
							<img
								alt=${productName}
								class="solution-search-image rounded"
								src="/o/${productThumbnail[1]}"
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
									${productName}
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