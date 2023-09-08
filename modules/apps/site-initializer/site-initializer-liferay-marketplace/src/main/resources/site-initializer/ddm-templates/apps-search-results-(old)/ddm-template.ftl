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

	.pagination-bar .dropdown-entries {
		cursor: pointer;
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
	const replaceQueryParamURL = (param, newValue, search) => {
		var regex = new RegExp("([?;&])" + param + "[^&;]*[;&]?");
		var query = search.replace(regex, "$1").replace(/&$/, '');

		return (query.length > 2 ? query + "&" : "?") + (newValue ? param + "=" + newValue : '');
	}

	const changePageSize = (value) => {
		let locationSearch = replaceQueryParamURL('delta', value, location.search);
	 	locationSearch = replaceQueryParamURL('start', 1, locationSearch);
		location.search = locationSearch;
	}

	const changePage = (value) => {
		let locationSearch = replaceQueryParamURL('start', value, location.search);
		location.search = locationSearch;
	}

	window.onload = () => {
		var paginationBar = document.querySelector(".pagination-bar");
		paginationBar.classList.remove("d-none");

		const queryString = window.location.search;
		const urlParams = new URLSearchParams(queryString);
		const pageNumber = urlParams.get('start') || 1;
		const pageSize = urlParams.get('delta') || 15;

		const productsCountTotal = document.querySelector("#product-count-total").value;
		let productsCountPage = document.querySelector("#product-count-page").value;
		const quantityPages = Math.ceil(productsCountTotal / pageSize);

		const dropdownEntries = document.querySelector(".pagination-bar .dropdown-entries p");

		if (pageSize) {
			dropdownEntries.textContent = pageSize + " Entries";
		}

		let firstNumber = (pageNumber == '1' || !pageNumber) ? 1 : (parseInt(pageNumber - 1) * parseInt(pageSize)) + 1;

		let secondNumber = 0;

		if (pageNumber) {
			if (parseInt(pageSize) > productsCountPage) {
				secondNumber = productsCountTotal;
			} else {
			 	secondNumber = productsCountPage * pageNumber
			}

			if (pageNumber > quantityPages) {
			   	secondNumber = productsCountTotal;
				firstNumber = 1;
			}

			if ((pageNumber > quantityPages) && (parseInt(pageSize) == productsCountPage)) {
			   	secondNumber = productsCountPage;
				firstNumber = 1;
			}
		}

		const paginationResults = document.querySelector(".pagination-bar .pagination-results");
		paginationResults.innerText = "Showing " + firstNumber + " to " + secondNumber + " of " + productsCountTotal + " entries.";

		dropdownEntries.onclick = () => {
			const entryList = document.querySelector(".dropdown.pagination-items-per-page ul")
			entryList.classList.toggle("show");
		}
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
		productsList = restClient.get("/headless-commerce-admin-catalog/v1.0/products?filter=categoryIds/any(params:${filterCategoriesByUrlParams} or (params eq '${vocabularyCategory[0].id}'))&pageSize=" + pageSize + "&page=" + page)
	/>
</#if>

<#if productsList.items?has_content>
	<#list productsList.items as productList>
		<#assign numberFilteredProducts = numberFilteredProducts + 1 />
	</#list>
</#if>

<div class="adt-apps-search-results">
	<#if productsList.items?has_content>
		<input id="product-count-total" type="hidden" value="${productsList.totalCount}" />
		<input id="product-count-page" type="hidden" value="${productsList.items?size}" />

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
		</div>

		<#assign
			pathThemeImages = themeDisplay.getPathThemeImages()
			spritemap = "${pathThemeImages}/clay/icons.svg"
			pageSizeList = [4, 8, 20, 40, 60]
		/>

		<div class="d-none pagination-bar">
			<div class="dropdown pagination-items-per-page">
				<a
					aria-expanded="false"
					aria-haspopup="true"
					class="dropdown-toggle dropdown-entries"
					data-toggle="dropdown"
					role="button"
				>
					<p class="m-0">
						${pageSize} entries
					</p>

					<svg
						class="lexicon-icon lexicon-icon-caret-double-l"
						focusable="false"
						role="presentation"
					>
						<use href="${spritemap}#caret-double-l"></use>
					</svg>
				</a>

				<ul class="dropdown-menu dropdown-menu-top">
					<li><a class="dropdown-item" onclick="changePageSize(${pageSizeList[0]})">${pageSizeList[0]}</a></li>
					<li><a class="dropdown-item" onclick="changePageSize(${pageSizeList[1]})">${pageSizeList[1]}</a></li>
					<li><a class="dropdown-item" onclick="changePageSize(${pageSizeList[2]})">${pageSizeList[2]}</a></li>
					<li><a class="dropdown-item" onclick="changePageSize(${pageSizeList[3]})">${pageSizeList[3]}</a></li>
					<li><a class="dropdown-item" onclick="changePageSize(${pageSizeList[4]})">${pageSizeList[4]}</a></li>
				</ul>
			</div>

			<div class="pagination-results"></div>

			<ul class="pagination">
				<li class="${(page?number > 1)?then('', 'disabled')} page-item">
					<a class="page-link" onclick="changePage(${page?number - 1})" role="button" tabindex="-1">
						<svg
							class="lexicon-icon lexicon-icon-angle-left"
							focusable="false"
							role="presentation"
						>
							<use href="${spritemap}#angle-left"></use>
						</svg>

						<span class="sr-only">Previous</span>
					</a>
				</li>

				<#assign pageQuantity = (productsList.totalCount / pageSize?number)?ceiling />

				<#list 1..pageQuantity as iteration>
					<#if iteration <= 3>
						<li class="page-item page-item-${iteration}">
							<a class="${((page?number==iteration)?then('active', ''))} page-link" onclick="changePage(${iteration})" tabindex="-1">${iteration}</a>
						</li>
					</#if>
					<#if (iteration > 3) && (iteration < pageQuantity)>
						<#if iteration == page?number>
							<li class="dropdown page-item">...</li>
							<li class="page-item page-item-${iteration}">
								<a class="${((page?number==iteration)?then('active', ''))} page-link" onclick="changePage(${iteration})" tabindex="-1">${iteration}</a>
							</li>
						</#if>
					</#if>
					<#if (iteration == pageQuantity) && (pageQuantity > 3)>
						<#if iteration != 1>
							<li class="dropdown page-item">...</li>
						</#if>
						<li class="page-item page-item-${iteration}">
							<a class="${((page?number==iteration)?then('active', ''))} page-link" onclick="changePage(${iteration})" tabindex="-1">${iteration}</a>
						</li>
					</#if>
				</#list>

				<li class="${(page?number==pageQuantity)?then('disabled', '')} page-item">
					<a class="page-link" onclick="changePage(${page?number + 1})" role="button">
						<svg
							class="lexicon-icon lexicon-icon-angle-right"
							focusable="false"
							role="presentation"
						>
							<use href="${spritemap}#angle-right"></use>
						</svg>

						<span class="sr-only">Next</span>
					</a>
				</li>
			</ul>
		</div>
	</#if>
</div>