<style type="text/css">
	.adt-solutions-search-results .cards-container {
		display: grid;
		grid-column-gap: 1rem;
		grid-row-gap: 1.5rem;
		grid-template-columns: repeat(3, minmax(0, 1fr));
	}

	.banner__product-tag {
		background-color: #e6ebf5;
		color: #1c3667;
		font-size: 0.8125rem;
		overflow: hidden;
		text-overflow: ellipsis;
		white-space: nowrap;
		width: fit-content;
	}

	.developer-name-text {
		font-size: 16px;
		font-weight: 400;
		line-height: 24px;
		letter-spacing: 0;
		text-align: left;
	}

	.image-container {
		min-height: 200px;
		min-width: 293px;
		overflow: hidden;
	}

	.product-name {
		text-overflow: ellipsis;
		overflow: hidden;
		white-space: nowrap;
	}

	.solution-search-image {
		min-height: 200px;
		min-width: 293px;
		object-fit: contain;
	}

	.solution-search-results-card {
		border-radius: 10px;
		border: 1px solid #E7EFFF;
		height: 462px;
		overflow: hidden;
		width: 293px;
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

	.solution-search-results-card .card-image-title-container .developer-name {
		color: #545d69;
	}

	.adt-solutions-search-results .labels .category-label {
		background-color: #ebeef2;
		color: #545D69;
		font-size: smaller;
		overflow: hidden;
		text-overflow: ellipsis;
		white-space: nowrap;
	}

	.adt-solutions-search-results .labels .category-label-remainder:hover .category-names {
		display: block;
	}

	.adt-solutions-search-results .solutions-search-results-card:hover {
		color: var(--black);
	}

	.productSpec {
		color: #545d69;
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

<#if searchContainer?has_content>
	<div class="color-neutral-3 d-md-block d-none pb-4">
		<strong class="color-black">
			${searchContainer.getTotal()}
		</strong>
		Solutions Available
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
						product = restClient.get("/headless-commerce-delivery-catalog/v1.0/channels/"+ channelId +"/products/"+ productId +"?accountId=-1&images.accountId=-1&nestedFields=productSpecifications,categories,images")
						productImage = (product.images![])?filter(item -> item.tags?seq_contains("app icon"))![]
						remainingCategoriesText = []
						catalogName = product.catalogName
						productSpecifications = product.productSpecifications![]
					/>

					<#if product.categories?has_content>
						<#assign
							productCategories = product.categories?filter(productCategory -> productCategory.vocabulary == "marketplace-solution-category")![]
							categoriesListSize = productCategories?size-1
						/>
					</#if>

					<#if productCategories?has_content>
						<#assign
							principalCategory = productCategories[0]
							remainingCategories = productCategories?filter(category -> category.name != principalCategory.name)
						/>

						<#list remainingCategories as category>
							<#assign remainingCategoriesText = remainingCategoriesText + [category.name] />
						</#list>
					</#if>

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

					<#if productImage?has_content>
						<#assign productThumbnail = productImage[0].src?split("/o") />
						<#if productThumbnail?has_content && productThumbnail?size gte 2>
							<#assign productThumbnail1 = "/o/${productThumbnail[1]}"!"" />
						<#else>
							<#assign productThumbnail1 = "/o/commerce-media/default/?groupId=${scopeGroupId}" />
						</#if>
					<#else>
						<#if product.urlImage?has_content>
							<#assign productThumbnail = product.urlImage?split("/o/") />
							<#if productThumbnail?has_content && productThumbnail?size gte 2>
								<#assign productThumbnail1 = "/o/${productThumbnail[1]}" />
							<#else>
								<#assign productThumbnail1 = "/o/commerce-media/default/?groupId=${scopeGroupId}" />
							</#if>
						<#else>
							<#assign productThumbnail1 = "/o/commerce-media/default/?groupId=${scopeGroupId}" />
						</#if>
					</#if>

					<#if product.urls?has_content>
						<#assign productURL = portalURL?replace("solutions-marketplace", "p") + "/" + product.urls.en_US />
					<#else>
						<#assign productURL = "" />
					</#if>

					<a class="solution-search-results-card bg-white d-flex flex-column mb-0 text-dark text-decoration-none" href=${productURL}>
						<div class="align-items-center d-flex image-container mb-3">
							<img
								alt="${productName}"
								class="solution-search-image"
								src=${productThumbnail1}
							/>
						</div>

						<div class="d-flex flex-column font-size-paragraph-small h-100 justify-content-between p-4">
							<div class="card-image-title-container d-flex flex-column">
								<div>
									<#if catalogName?has_content>
										<span class="developer-name-text">
											${catalogName}
										</span>
									</#if>

									<div class="font-weight-semi-bold h2 mt-1 product-name" title="${productName}">
										${productName}
									</div>
								</div>

								<div class="font-weight-normal mb-2">
									${productDescription}
								</div>
							</div>

							<#if principalCategory?has_content>
								<div>
									<span class="banner__product-tag rounded py-1 px-2 mr-2" title="${principalCategory.name}">
										${principalCategory.name}
									</span>
									<#if categoriesListSize?has_content && remainingCategoriesText?has_content>
										<span class="banner__product-tag rounded py-1 px-2" title="${remainingCategoriesText?join('\n')}">
											+ ${categoriesListSize}
										</span>
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