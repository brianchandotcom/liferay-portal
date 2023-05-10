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

	@media screen and (max-width: 599px) {

		.adt-apps-search-results .cards-container {
			grid-template-columns: 288px;
			grid-row-gap: 1rem;
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

<#if serviceLocator??>
	<#assign assetCategoryLocalService = serviceLocator.findService("com.liferay.asset.kernel.service.AssetCategoryLocalService") />
</#if>

<#assign
	searchContainer = cpSearchResultsDisplayContext.getSearchContainer()

	COMMERCE_PRODUCT_CLASS_NAME = "com.liferay.commerce.product.model.CPDefinition"
	MARKETPLACE_PRICE_VOCABULARY_ID = 449511429
/>

<div class="adt-apps-search-results">
	<div class="app-count color-neutral-3 d-md-block d-none pb-4">
		<#if entries?has_content>
			${languageUtil.format(locale, "x-applications-available", "<strong class='color-black'>${searchContainer.getTotal()}</strong>")}
		</#if>
	</div>

	<div class="cards-container pb-6">
		<#if entries?has_content>
			<#list entries as curCPCatalogEntry>

				<#if serviceLocator?? && assetCategoryLocalService??>
					<#assign categories = assetCategoryLocalService.getCategories(COMMERCE_PRODUCT_CLASS_NAME, curCPCatalogEntry.getCPDefinitionId()) />
				</#if>

				<#assign
					cpDefinitionId = curCPCatalogEntry.getCPDefinitionId()
					developerName = expandoValueLocalService.getData(companyId, COMMERCE_PRODUCT_CLASS_NAME, "CUSTOM_FIELDS", "Developer Name", curCPCatalogEntry.getCPDefinitionId(), "")!""
					productName = curCPCatalogEntry.getName()
					productDescription = (stringUtil.shorten(htmlUtil.stripHtml(curCPCatalogEntry.getDescription()), 150,"..."))
					friendlyURL = cpContentHelper.getFriendlyURL(curCPCatalogEntry, themeDisplay)
					productImageURL = "https://www.liferay.com/documents/448812852/0/icon.png/5da637ed-9593-5531-a6f0-bcd1c5ad20d8/icon.png?t=1656341514206"
					images = cpContentHelper.getImages(cpDefinitionId, themeDisplay)
				/>

				<#list images as image>
						<#assign title = image.getTitle()!"" />
						<#if title?contains("App Icon")>
							<#assign productImageURL = image.getURL() />
						</#if>
				</#list>

				<a class="app-search-results-card bg-white border-radius-medium color-black flex flex-column mb-0 p-3 text-decoration-none" href=${friendlyURL}>
					<div class="card-image-title-container flex pb-3">
						<#if productImageURL?has_content>
							<div class="border-radius-medium image-container">
								<img
									alt=${productName}
									class="h-100 image mw-100"
									src="${productImageURL}"
								/>
							</div>
						</#if>

						<div class="pl-2 title-description-text">
							<div class="font-size-heading-f5 title">
								${productName}
							</div>

							<div class="color-neutral-3 developer-name font-size-paragraph-small font-weight-normal">
								${developerName}
							</div>
						</div>
					</div>

					<div class="color-black description-price-container flex flex-column font-size-paragraph-small h-100 justify-content-between">
						<div class="description-price-text">
							<div class="description font-weight-normal mb-2">
								${productDescription}
							</div>

							<div class="font-weight-semi-bold price">
								<#if categories??>
									<#list categories as category>
										<#if category.getVocabularyId() == MARKETPLACE_PRICE_VOCABULARY_ID>
											${category.getName()}
										</#if>
									</#list>
								</#if>
							</div>
						</div>
					</div>
				</a>
			</#list>
		</#if>
	</div>
</div>