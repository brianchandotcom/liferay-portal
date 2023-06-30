<style type="text/css">
	.adt-solutions-search-results .cards-container {
		display: grid;
		grid-column-gap: 1rem;
		grid-row-gap: 1.5rem;
		grid-template-columns: repeat(3, minmax(0, 1fr));
	}

	.adt-solutions-search-results .app-solutions-results-card:hover {
		color: var(--black);
	}

	.adt-solutions-search-results .card-image-title-container .image-container {
		height: 3rem;
		min-width: 3rem;
	}

	@media screen and (max-width: 599px) {
		.adt-solutions-search-results .cards-container {
			grid-template-columns: 288px;
			grid-row-gap: 1rem;
			justify-content: center;
		}

		.adt-solutions-search-results .solutions-search-results-card {
			height: 281px;
		}
	}

	@media screen and (min-width: 600px) and (max-width: 899px) {
		.adt-solutions-search-results .cards-container {
			grid-template-columns: repeat(2, minmax(0, 1fr));
		}
	}
</style>

<#assign searchContainer = cpSearchResultsDisplayContext.getSearchContainer() />

<div class="adt-solutions-search-results">
	<div class="color-neutral-3 d-md-block d-none pb-4 solutions-count">
		<#if entries?has_content>
			<strong class='color-black'>${searchContainer.getTotal()}</strong> App(s) Available
		</#if>
	</div>

	<div class="cards-container pb-6">
		<#if entries?has_content>
			<#list entries as curCPCatalogEntry>
				<#assign
					channelId = ""
					channels = restClient.get("/headless-commerce-delivery-catalog/v1.0/channels")
					cpDefinitionId = curCPCatalogEntry.getCPDefinitionId()
					developerName = ""
					productName = curCPCatalogEntry.getName()
					productDescription = (stringUtil.shorten(htmlUtil.stripHtml(curCPCatalogEntry.getDescription()), 150,"..."))
					friendlyURL = cpContentHelper.getFriendlyURL(curCPCatalogEntry, themeDisplay)
					priceModel = ""
					productImageURL = "https://www.liferay.com/documents/448812852/0/icon.png/5da637ed-9593-5531-a6f0-bcd1c5ad20d8/icon.png?t=1656341514206"
					images = cpContentHelper.getImages(cpDefinitionId, themeDisplay)
				/>

				<#list channels.items as channel>
					<#if channel.name == "Marketplace Channel">
						<#assign channelId = channel.id />
					</#if>
				</#list>

				<#if (curCPCatalogEntry.getCProductId())??>
					<#assign specifications = restClient.get("/headless-commerce-delivery-catalog/v1.0/channels/" + channelId + "/products/" + curCPCatalogEntry.getCProductId() + "/product-specifications") />
				</#if>

				<#if specifications?has_content && specifications.items?has_content>
					<#list specifications.items as specification>
						<#if specification.specificationKey?has_content && specification.specificationKey == "developer-name">
							<#assign developerName = specification.value />
						<#elseif specification.specificationKey?has_content && specification.specificationKey == "price-model">
							<#assign priceModel = specification.value />
						</#if>
					</#list>
				</#if>

				<#list images as image>
					<#assign title = image.getTitle()!"" />

					<#if title?contains("App Icon")>
						<#assign productImageURL = image.getURL() />
					</#if>
				</#list>

				<a class="solutions-search-results-card bg-white d-flex flex-column mb-0 p-3 rounded text-decoration-none" href=${friendlyURL} style="color: var(--black);">
					<div class="card-image-title-container d-flex pb-3">
						<#if productImageURL?has_content>
							<div class="image-container rounded">
								<img
									alt=${productName}
									class="h-100 image mw-100"
									src="${productImageURL}"
								/>
							</div>
						</#if>

						<div class="pl-2 title-description-text">
							<h5 class="font-weight-semi-bold mb-0 title" style="font-size: 1.375rem; line-height: 1.244;">
								${productName}
							</h5>

							<div class="developer-name font-size-paragraph-small font-weight-normal" style="color: var(--gray-700);">
								${developerName}
							</div>
						</div>
					</div>

					<div class="d-flex description-price-container flex-column font-size-paragraph-small h-100 justify-content-between" style="color: var(--black);">
						<div class="description-price-text">
							<div class="description font-weight-normal mb-2">
								${productDescription}
							</div>

							<div class="font-weight-semi-bold price-model">
								${priceModel}
							</div>
						</div>
					</div>
				</a>
			</#list>
		</#if>
	</div>
</div>