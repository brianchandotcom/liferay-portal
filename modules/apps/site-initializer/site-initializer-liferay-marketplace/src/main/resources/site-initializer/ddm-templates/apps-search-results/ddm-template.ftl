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

	.adt-apps-search-results .card-image-title-container .image-container .app-search-image {
		height: 100%;
		max-width: 100%;
	  	object-fit: cover;
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

<#if themeDisplay?has_content>
	<#assign scopeGroupId = themeDisplay.getScopeGroupId() />
</#if>

<#assign channel = restClient.get("/headless-commerce-delivery-catalog/v1.0/channels?accountId=-1&filter=name eq 'Marketplace Channel' and siteGroupId eq '${scopeGroupId}'") />

<#if channel?has_content>
	<#assign channelId = channel.items[0].id />
</#if>

<div class="adt-apps-search-results">
	<div class="cards-container pb-6">
		<#if entries?has_content>
			<#list entries as entry>
				<#if entry?has_content>
					<#assign
						portalURL = portalUtil.getLayoutURL(themeDisplay)
						productId = entry.getClassPK() + 1
						product = restClient.get("/headless-commerce-delivery-catalog/v1.0/channels/"+ channelId +"/products/"+ productId +"?accountId=-1&nestedFields=productSpecifications,categories")
						productSpecifications = product.productSpecifications![]
						productURL=portalURL?replace("home", "p" ) + "/" + product.urls.en_US
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

					<a class="app-search-results-card bg-white border-radius-medium d-flex flex-column mb-0 p-3 text-dark text-decoration-none" href=${productURL}>
						<div class="align-items-center card-image-title-container d-flex pb-3">
							<div class="image-container rounded">
								<img
									alt=${productName}
									class="app-search-image"
									src="/o/${productThumbnail[1]}"
								/>
							</div>

							<div class="pl-2">
								<div class="font-weight-semi-bold h2 mt-1">
									${productName}
								</div>
								<#if productSpecifications?has_content>
									<#assign productDeveloperName = productSpecifications?filter(item -> item.specificationKey == "developer-name") />

									<#list productDeveloperName as developerNameItem>
										<#if developerNameItem.value?has_content>
											<#assign developerName = developerNameItem.value />
										<#else>
											<#assign developerName = "" />
										</#if>
										<div class="color-neutral-3 font-size-paragraph-small mt-1">
											${developerName}
										</div>
									</#list>
								</#if>
							</div>
						</div>

						<div class="d-flex flex-column font-size-paragraph-small h-100 justify-content-between">
							<div class="font-weight-normal mb-2">
								${productDescription}
							</div>
							<#if productSpecifications?has_content>
								<#assign productPriceModels = productSpecifications?filter(item -> item.specificationKey == "price-model") />

								<#list productPriceModels as productPriceModel>
									<#if productPriceModel.value?has_content>
										<#assign priceModel = productPriceModel.value />
									<#else>
										<#assign priceModel = "" />
									</#if>
									<div class="font-weight-semi-bold mt-1">
										${priceModel}
									</div>
								</#list>
							</#if>
						</div>
					</a>
				</#if>
			</#list>
		</#if>
	</div>
</div>