<#if cpCatalogEntry??>
	<#assign
		cpAttachmentFileEntries = cpContentHelper.getCPMedias(cpCatalogEntry.getCPDefinitionId(), themeDisplay)
		cpDefinitionSpecificationOptionValues = cpContentHelper.getCPDefinitionSpecificationOptionValues(cpCatalogEntry.getCPDefinitionId())
		cpOptionCategories = cpContentHelper.getCPOptionCategories(themeDisplay.getCompanyId())
		defaultImageURL = cpContentHelper.getDefaultImageFileURL(account.getAccountEntryId(), cpCatalogEntry.getCPDefinitionId())
		productDescription = cpCatalogEntry.getDescription()
		productImages = cpContentHelper.getImages(cpCatalogEntry.getCPDefinitionId(), true, themeDisplay)
		productName = cpCatalogEntry.getName()
		productShortDescription = cpCatalogEntry.getShortDescription()
		productSkus = cpCatalogEntry.getCPSkus()
		stockQuantity = cpContentHelper.getStockQuantity(request)
	/>

	<style>
		.content {
			font-size: 1rem;
		}

		.content p {
			font-size: 12px;
		}

		.heart {
			align-items: center;
			background-color: #eaeaea;
			border-radius: 50%;
			display: flex;
			height: 29px;
			justify-content: center;
			width: 29px;
		}

		.main_image {
			align-items: center;
			border-bottom: 1px solid #eee;
			display: flex;
			height: 400px;
			justify-content: center;
			overflow: hidden;
			width: 100%;
		}

		.price.lfr-tooltip-scope {
			margin-bottom: 0.8rem;
		}

		.price-label {
			font-size: 1rem;
			font-weight: bold;
		}

		.right-side {
			position: relative;
		}

		.skus h6 {
			color: gray;
		}

		.tab-pane {
			padding-top: 1rem;
		}

		.thumbnail_images ul {
			align-items: center;
			display: flex;
			justify-content: center;
			list-style: none;
			margin-top: 10px;
		}

		.thumbnail_images ul li {
			border: 2px solid #eee;
			cursor: pointer;
			margin: 5px;
			padding: 10px;
			transition: all 0.5s;
		}

		.thumbnail_images ul li:hover {
			border: 2px solid #000;
		}
	</style>

	<div class="container mt-5 mb-5">
		<div class="">
			<div class="row g-0">
				<div class="col-md-6 border-end">
					<div class="d-flex flex-column justify-content-center">
						<div class="main_image">
							<img src="${defaultImageURL}" id="main_product_image" width="350" />
						</div>

						<div class="thumbnail_images">
							<ul id="thumbnail">
								<#if productImages?has_content>
									<#list productImages as currentImage>
										<li>
											<img onclick="changeImage(this)" src="${currentImage.getURL()}" width="70" />
										</li>
									</#list>
								</#if>
							</ul>
						</div>
					</div>
				</div>

				<div class="col-md-6">
					<div class="p-3 right-side">
						<header>
							<div class="d-flex justify-content-between">
								<@liferay_commerce_ui["availability-label"]
									CPCatalogEntry=cpCatalogEntry
									namespace=renderResponse.namespace
								/>

								<@liferay.language_format
									arguments="${stockQuantity}"
									key="x-in-stock"
								/>
							</div>
						</header>

						<div class="d-block justify-content-between align-items-center">
							<h4>${productName}</h4>

							<div class="skus">
								<#if productSkus?has_content>
									<#list productSkus as productSku>
										<h6 style="text-transform: uppercase;">${productSku.getSku()}</h6>
									</#list>
								</#if>
							</div>
						</div>

						<div class="mt-3 mb-5 pr-3 content">${productShortDescription}</div>

						<div class="mt-3 mb-5 pr-3 content">${productDescription}</div>

						<@liferay_commerce_ui["price"] CPCatalogEntry=cpCatalogEntry />
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="container mt-5 mb-5">
		<div class="card prdct mb-7">
			<div class="row">
				<div class="col-md-12 border-end">
					<div class="sheet-example">
						<h5 class="pb-0 pl-5 pt-5">Specifications</h5>
					</div>

					<div aria-labelledby="navUnderlineSpecificationsTab" class="tab-pane" id="navUnderlineSpecifications" role="tabpanel">
						<#assign cont = 0 />

						<dl class="p-4 specification-list">
							<#if cpDefinitionSpecificationOptionValues?has_content>
								<table class="p-4 table table-sm">
									<tbody>
										<#if cpDefinitionSpecificationOptionValues?has_content>
											<#assign cont = 1 />

											<#list cpDefinitionSpecificationOptionValues as cpDefinitionSpecificationOptionValue>
												<#assign cpSpecificationOption = cpDefinitionSpecificationOptionValue.getCPSpecificationOption() />

												<tr>
													<td class="specification-term table-cell-minw-150 table-title prdct"></td>

													<td class="specification-term">${cpSpecificationOption.getTitle(locale)}</td>

													<td class="specification-desc table-cell-expand prdct">${cpDefinitionSpecificationOptionValue.getValue(locale)}</td>
												</tr>
											</#list>
										</#if>
									</tbody>
								</table>
							</#if>

							<#if cpOptionCategories?has_content>
								<table class="table table-sm">
									<tbody>
										<#list cpOptionCategories as cpOptionCategory>
											<#assign categorizedCPDefinitionSpecificationOptionValues = cpContentHelper.getCategorizedCPDefinitionSpecificationOptionValues(cpDefinitionId, cpOptionCategory.getCPOptionCategoryId()) />

											<#if categorizedCPDefinitionSpecificationOptionValues?has_content>
												<#assign cont = 1 />

												<#list categorizedCPDefinitionSpecificationOptionValues as cpDefinitionSpecificationOptionValue>
													<#assign cpSpecificationOption = cpDefinitionSpecificationOptionValue.getCPSpecificationOption() />

													<tr>
														<td class="specification-term table-cell-minw-150 table-title prdct"></td>

														<td class="specification-term">${cpSpecificationOption.getTitle(locale)}</td>

														<td class="specification-desc table-cell-expand prdct">${cpDefinitionSpecificationOptionValue.getValue(locale)}</td>
													</tr>
												</#list>
											</#if>
										</#list>
									</tbody>
								</table>
							</#if>

							<#if cont=0>
								<dl class="p-4 m-3 specification-list text-center bg-w">
									No Specifications
								</dl>
							</#if>
						</dl>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="container mt-5 mb-5">
		<div class="card prdct mb-7">
			<div class="row">
				<div class="col-md-12 border-end">
					<div class="sheet-example">
						<h5 class="pb-0 pl-5 pt-5">Attachments</h5>
					</div>

					<div aria-labelledby="navUnderlineDocumentsTab" class="tab-pane" id="navUnderlineDocuments" role="tabpanel">
						<#if cpAttachmentFileEntries?has_content>
							<dl class="p-4 specification-list">
								<table class="table table-sm">
									<tbody>
										<#list cpAttachmentFileEntries as cpAttachmentFileEntry>
											<tr>
												<td class="specification-term table-cell-minw-150 table-title prdct"></td>

												<td class="specification-term">${cpAttachmentFileEntry.getTitle()}</td>

												<td class="specification-desc table-cell-expand prdct">
													<a href="${cpAttachmentFileEntry.getDownloadURL()}" target="_blank">
														<@clay["icon"] symbol="download" />
													</a>
												</td>
											</tr>
										</#list>
									</tbody>
								</table>
							</dl>
						<#else>
							<dl class="p-4 m-3 specification-list text-center bg-w">
								No Attachments
							</dl>
						</#if>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script>
		function changeImage(element) {
			var main_prodcut_image = document.getElementById('main_product_image');
			main_prodcut_image.src = element.src;
		}
	</script>
<#else>
	<div class="alert alert-info text-center my-7 mx-4">
	   We are sorry, but we were unable to find that product.
	</div>
</#if>