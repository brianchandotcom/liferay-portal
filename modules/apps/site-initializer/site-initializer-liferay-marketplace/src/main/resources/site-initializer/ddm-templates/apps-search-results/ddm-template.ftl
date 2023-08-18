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

<script>
	window.onload = () => {
		const numberOfProductsInfo = document.querySelector("#ocerSearchContainerPageIterator_ariaPaginationResults");
		const productsCount = document.querySelector("#freemarkervar").value;
		let infoList = numberOfProductsInfo.textContent.split(" ");
		infoList[infoList.length - 2] = productsCount;
		const newInfo = infoList.join(" ");
		numberOfProductsInfo.textContent = newInfo;
	}
</script>

<#assign siteURL = (themeDisplay.getURLCurrent()?keep_after("?"))! />

<#function getFilterByUrlParams siteURL>
	<#if siteURL??>
		<#assign urlParams = "" />
		<#list siteURL?split("&") as params>
			<#if !params?contains("delta") && !params?contains("start")>
				<#assign categoryId = params?keep_after("=") />
				<#if categoryId?has_content>
					<#assign urlParams = urlParams + " (params eq '" + categoryId + "') and" />
				</#if>
			</#if>
		</#list>
	</#if>

	<#return urlParams?keep_before_last(" ")?trim />
</#function>

<#if siteURL??>
	<#list siteURL?split("&") as params>
		<#if params?contains("delta")>
			<#assign pageSize = params?keep_after("=") />
		</#if>
		<#if params?contains("start")>
			<#assign page = params?keep_after("=") />
		</#if>
	</#list>
</#if>

<#assign
	pageSize = pageSize?has_content?then(pageSize, 15)
	page = page?has_content?then(page, 1)
	taxonomyVocabularyName = "Marketplace Product Type"
	categoryName = "App"
	taxonomyVocabulary = restClient.get("/headless-admin-taxonomy/v1.0/sites/${themeDisplay.getCompanyGroupId()}/taxonomy-vocabularies?fields=id&filter=name eq '${taxonomyVocabularyName}'").items
	vocabularyCategory = restClient.get("/headless-admin-taxonomy/v1.0/taxonomy-vocabularies/${taxonomyVocabulary[0].id}/taxonomy-categories?fields=id&filter=name eq '${categoryName}'").items
	productsList = restClient.get("/headless-commerce-admin-catalog/v1.0/products?filter=categoryIds/any(params:params eq '${vocabularyCategory[0].id}')&pageSize=" + pageSize + "&page=" + page)
	numberFilteredProducts = 0
	filterCategoriesByUrlParams = getFilterByUrlParams(siteURL)
/>

<#if filterCategoriesByUrlParams?has_content>
	<#assign
		productsList = restClient.get("/headless-commerce-admin-catalog/v1.0/products?filter=categoryIds/any(params:${filterCategoriesByUrlParams} and (params eq '${vocabularyCategory[0].id}'))&pageSize=" + pageSize + "&page=" + page)
	/>
</#if>

<#if productsList.items?has_content>
	<#list productsList.items as productList>
		<#assign numberFilteredProducts = numberFilteredProducts + 1 />
	</#list>
</#if>

<div class="adt-apps-search-results">
	<#if productsList.items?has_content>
		<input id="freemarkervar" type="hidden" value="${productsList.totalCount}" />

		<div class="color-neutral-3 d-md-block d-none pb-4">
			<strong class='color-black'>${numberFilteredProducts!}</strong> ${categoryName}s Available
		</div>

		<div class="cards-container pb-6">
			<#list productsList.items as product>
				<#assign
					productAttachments = restClient.get("/headless-commerce-admin-catalog/v1.0/products/" + product.productId + "/attachments").items
					productDescription = stringUtil.shorten(htmlUtil.stripHtml(product.description.en_US), 150, "...")
					productSpecifications = restClient.get("/headless-commerce-admin-catalog/v1.0/products/" + product.productId + "/productSpecifications").items
					portalURL = portalUtil.getLayoutURL(themeDisplay)
					productURL = portalURL?replace("home", "p") + "/" + product.urls.en_US
				/>

				<#list filterProductsByAppCategory(product) as category>
				 	<a class="app-search-results-card bg-white border-radius-medium d-flex flex-column mb-0 p-3 text-dark text-decoration-none" href=${productURL}>
						<div class="align-items-center card-image-title-container d-flex pb-3">
							<div class="image-container rounded">
								<#if productAttachments?has_content>
									<#list productAttachments as attachmentFields>
										<#list attachmentFields.customFields as field>
											<#if (field.name == "App Icon") && (field.customValue.data[0] == "Yes")>
												<#assign srcName = attachmentFields.src?keep_after("liferay.com") />

												<img
									   				alt=${product.name.en_US}
									   				class="h-100 mw-100"
									   				src="${srcName}"
												/>
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
										<#assign productPriceModel = productSpecifications?filter(item -> item.specificationKey == "developer-name") />

										<#list productPriceModel as product>
											<div class="color-neutral-3 font-size-paragraph-small mt-1">
												${product.value.en_US}
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
									<#assign productPriceModel = productSpecifications?filter(item -> item.specificationKey == "price-model") />

									<#list productPriceModel as product>
										<div class="font-weight-semi-bold mt-1">
						   		 			${product.value.en_US}
										</div>
									</#list>
								</#if>
					 		</div>
				  		</div>
				 	</a>
				</#list>
			</#list>
		</div>
	</#if>
</div>