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
		height: 11.25rem;
	}

	.adt-solutions-search-results .card-image-title-container .image-container img {
		object-fit: cover;
		object-position: top;
	}

	.adt-solutions-search-results .labels .category-names {
		bottom: 26px;
		display: none;
		left: -97px;
		width: 14.5rem;
	}

	.adt-solutions-search-results .labels .category-names::after {
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

	.adt-solutions-search-results .labels .category-label-remainder:hover .category-names {
		display: block;
	}

	@media screen and (max-width: 599px) {
		.adt-solutions-search-results .cards-container {
			grid-template-columns: 288px;
			grid-row-gap: 1rem;
			justify-content: center;
		}
	}

	@media screen and (min-width:600px) and (max-width: 899px) {
		.adt-solutions-search-results .cards-container {
			grid-template-columns: repeat(2, minmax(0, 1fr));
		}
	}

	@media screen and (max-width: 1450px) {
		.adt-solutions-search-results .labels .category-names {
			left: -180px;
		}

		.adt-solutions-search-results .labels .category-names::after {
			left: 168px;
		}
	}
</style>

<#assign
	assetCategoryLocalService = serviceLocator.findService("com.liferay.asset.kernel.service.AssetCategoryLocalService")
	searchContainer = cpSearchResultsDisplayContext.getSearchContainer()

	ACCOUNT_ID_GUEST = -1
	COMMERCE_PRODUCT_CLASS_NAME = "com.liferay.commerce.product.model.CPDefinition"
	MARKETPLACE_PRICE_VOCABULARY_ID = 449511429
	MARKETPLACE_SOLUTIONS_CATEGORIES_VOCABULARY_ID = 449603954
/>

<div class="adt-solutions-search-results">
	<div class="app-count color-neutral-3 d-md-block d-none pb-4">
		<#if entries?has_content>
			${languageUtil.format(locale, "x-solutions-available", "<strong class='color-black'>${searchContainer.getTotal()}</strong>")}
		</#if>
	</div>

	<div class="cards-container pb-6">
		<#if entries?has_content>
			<#list entries as curCPCatalogEntry>
				<#assign
					categories = assetCategoryLocalService.getCategories(COMMERCE_PRODUCT_CLASS_NAME, curCPCatalogEntry.getCPDefinitionId())
					cpDefinitionId = curCPCatalogEntry.getCPDefinitionId()
					developerName = expandoValueLocalService.getData(companyId, COMMERCE_PRODUCT_CLASS_NAME, "CUSTOM_FIELDS", "Developer Name", curCPCatalogEntry.getCPDefinitionId(), "")!""
					productImageURL = cpContentHelper.getDefaultImageFileURL(ACCOUNT_ID_GUEST, cpDefinitionId)
					productName = curCPCatalogEntry.getName()
					productDescription = (stringUtil.shorten(htmlUtil.stripHtml(curCPCatalogEntry.getDescription()), 150,"..."))
					friendlyURL = cpContentHelper.getFriendlyURL(curCPCatalogEntry, themeDisplay)
					solutionsCategories = categories?filter(category -> category.getVocabularyId() == MARKETPLACE_SOLUTIONS_CATEGORIES_VOCABULARY_ID)
				/>

				<a class="solutions-search-results-card bg-white border-radius-medium color-black flex flex-column mb-0 text-decoration-none" href=${friendlyURL}>
					<div class="card-image-title-container flex flex-column">
						<#if productImageURL?has_content>
							<div class="border-radius-medium image-container mb-3 overflow-hidden w-100">
								<img
									alt=${productName}
									class="h-100 image w-100"
									src="${productImageURL}"
								/>
							</div>
						</#if>

						<div class="align-center developer-icon-name-container flex mb-2 px-4">
							<div class="color-neutral-3 developer-name font-size-paragraph-small font-weight-normal">
								${developerName}
							</div>
						</div>
					</div>

					<div class="font-size-heading-f5 pb-1 px-4 title">
						${productName}
					</div>

					<div class="color-black description-price-container flex flex-column font-size-paragraph-small h-100 justify-content-between pb-4 px-4">
						<div class="description-price-text">
							<div class="description font-weight-normal mb-2">
								${productDescription}
							</div>
						</div>

						<#if solutionsCategories?has_content>
							<div class="align-center flex labels">
								<div class="bg-neutral-8 border-radius-small category-label color-neutral-3 font-size-paragraph-small font-weight-semi-bold px-1">
									${solutionsCategories[0].getName()}
								</div>

								<#if (solutionsCategories?size > 1)>
									<div class="category-label-remainder color-primary pl-2 position-relative">
										+${solutionsCategories?size - 1}

										<div class="bg-neutral-1 border-radius-medium category-names color-white font-size-paragraph-base p-4 position-absolute">
											<#list solutionsCategories as category>
												<#if !category?is_first>
													${category.getName()}<#sep>, </#sep>
												</#if>
											</#list>
										</div>
									</div>
								</#if>
							</div>
						</#if>
					</div>
				</a>
			</#list>
		</#if>
	</div>
</div>