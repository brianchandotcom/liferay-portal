<#assign
	cpDefinition = simpleCPTypeDisplayContext.getCPDefinition()

	cpDefinitionId = cpDefinition.getCPDefinitionId()

	cpInstanceId = 0

	isIgnoreSKUCombinations = cpDefinition.isIgnoreSKUCombinations()

	images = simpleCPTypeDisplayContext.getImages()

	defaultImage = (simpleCPTypeDisplayContext.getDefaultImage())!""

	gtin = ""

	modelNumber = ""

	sku = "select-a-valid-product"

	categories = simpleCPTypeDisplayContext.getAssetCategories()
/>
<#if isIgnoreSKUCombinations>
	<#if simpleCPTypeDisplayContext.getDefaultCPInstance()??>
		<#assign
			cpInstance =  simpleCPTypeDisplayContext.getDefaultCPInstance()
		/>
		<#if cpInstance??>
			<#assign
				cpInstanceId =  cpInstance.getCPInstanceId()
				gtin = cpInstance.getGtin()
				modelNumber = cpInstance.getManufacturerPartNumber()
				sku = cpInstance.getSku()
			/>
		</#if>
	</#if>
</#if>

<div class="product-detail" id="<@portlet.namespace />${cpDefinition.getCPDefinitionId()}ProductContent">
	<div class="product-detail-header">
		<div class="commerce-brand-name">Brand Name</div>
		<h2 class="commerce-title">${cpDefinition.getName()}</h2>

		<div class="autofit-float autofit-row product-detail-secondary-info">
			<div class="autofit-col">
				<div class="commerce-model-number">
					Model #<span data-text-cp-instance-manufacturer-part-number>${modelNumber}</span>
				</div>
			</div>

			<div class="autofit-col">
				<div class="commerce-gtin">
					GTIN #<span data-text-cp-instance-gtin>${gtin}</span>
				</div>
			</div>

			<div class="autofit-col">
				<div class="commerce-sku">
					SKU:
					<span data-text-cp-instance-sku>${sku}</span>
				</div>
			</div>
		</div>

		<a class="single-link" href="#placeholder">View Product Family</a>
	</div>

	<div class="product-detail-body">
		<div class="product-detail-image-widget">
			<div class="product-detail-thumbnail-column">
				<div class="product-detail-thumbnail-container" id="<@portlet.namespace />thumbs-container">
					<#if entries?has_content>
						<#list images as curImage>
							<div class="thumb">
								<img class="img-fluid" src="${simpleCPTypeDisplayContext.getImageURL(curImage.getFileEntry(), themeDisplay)}">
							</div>
						</#list>
					</#if>
				</div>

				<div class="product-detail-thumbnail-container">
					<a class="thumb" href="#placeholder">
						<span class="rounded-circle sticker sticker-primary sticker-sm sticker-top-left">
							<@liferay_aui.icon
								image="play"
								markupView="lexicon"
							/>
						</span>
					</a>
				</div>

				<div class="product-detail-thumbnail-container">
					<a class="thumb" href="#placeholder">7 +</a>
				</div>
			</div>
			<#if validator.isNotNull(defaultImage)>
				<div class="product-detail-image-column">
					<div class="full-image product-detail-image-container">
						<img class="img-fluid" id="<@portlet.namespace />full-image" src="${simpleCPTypeDisplayContext.getImageURL(defaultImage.getFileEntry(), themeDisplay)}">
						<a class="sticker sticker-bottom-right" href="#placeholder">
							<@liferay_aui.icon
								image="search"
								markupView="lexicon"
							/>
						</a>
					</div>
				</div>
			</#if>
		</div>

		<div class="product-detail-info">
			<div class="autofit-float autofit-row product-detail-info-header">
				<div class="autofit-col autofit-col-expand">
					<h2 class="commerce-price" data-text-cp-instance-price><#if cpInstance??><@liferay_commerce["price"] CPInstanceId= cpInstanceId /></#if></h2>
				</div>

				<#if cpInstance??>
					<div class="autofit-col autofit-col-expand">
						<div class="autofit-section">
							was
							<strong data-text-cp-instance-price>
								<@liferay_commerce["price"] CPInstanceId=cpInstanceId />
							</strong>
						</div>
					</div>
				</#if>

				<#if cpInstance??>
					<div class="autofit-col autofit-col-expand">
						<div class="autofit-section">
							<strong>You save <span data-text-placeholder>0%</span></strong>
						</div>
					</div>
				</#if>

				<#if cpInstance??>
					<div class="autofit-col">
						<div class="autofit-section">
							<a href="#placeholder">
								more discount info
								<@liferay_aui.icon
									image="info-circle"
									markupView="lexicon"
								/>
							</a>
						</div>
					</div>
				</#if>
			</div>

			<#if cpInstance??>
				<div class="product-detail-info-multiples">
					<h6 class="commerce-title">Buy Multiples & Save</h6>

					<div class="form-group-autofit">
							<@liferay_commerce["tier-price"]
								CPInstanceId=cpInstanceId
								taglibQuantityInputId=renderResponse.getNamespace() +  cpDefinition.getCPDefinitionId() + 'Quantity'
							/>
					</div>
				</div>
			</#if>

			<div class="form-group-autofit product-detail-info-selections">
				<div class="commerce-quantity-input form-group-item">
					<@liferay_commerce["quantity-input"]
						CPDefinitionId=cpDefinitionId
						useSelect=false
					/>
				</div>

				<div class="commerce-size-input form-group-item">
					${simpleCPTypeDisplayContext.renderOptions(renderRequest, renderResponse)}
				</div>
			</div>

			<div class="autofit-float autofit-row autofit-row-center product-detail-info-actions">
				<div class="autofit-col">
					<@liferay_commerce_cart["add-to-cart"]
						CPDefinitionId=cpDefinitionId
						CPInstanceId=cpInstanceId
						elementClasses="btn-primary text-truncate"
						productContentId=renderResponse.getNamespace() +  cpDefinition.getCPDefinitionId() + 'ProductContent'
						taglibQuantityInputId=renderResponse.getNamespace() +  cpDefinition.getCPDefinitionId() + 'Quantity'
					/>
				</div>

				<div class="autofit-col">
					<a href="#placeholder">Add to List +</a>
				</div>
			</div>

			<div class="product-detail-info-compare">
				<@liferay_commerce["compare-product"] CPDefinitionId=cpDefinitionId />
			</div>
		</div>
	</div>

	<#assign
		cpDefinitionSpecificationOptionValues = simpleCPTypeDisplayContext.getCPDefinitionSpecificationOptionValues()

		cpOptionCategories = simpleCPTypeDisplayContext.getCPOptionCategories()

		cpAttachmentFileEntries = simpleCPTypeDisplayContext.getCPAttachmentFileEntries()
	/>

	<div class="product-detail-description">
		<ul class="nav nav-underline nav-underline-light" role="tablist">
			<li class="nav-item" role="presentation">
				<a aria-controls="<@portlet.namespace />description" aria-expanded="true" class="active nav-link" data-toggle="tab" href="#<@portlet.namespace />description" role="tab">
					${languageUtil.get(request, "description")}
				</a>
			</li>

			<#if simpleCPTypeDisplayContext.hasCPDefinitionSpecificationOptionValues()>
				<li class="nav-item" role="presentation">
					<a aria-controls="<@portlet.namespace />specification" aria-expanded="false" class="nav-link" data-toggle="tab" href="#<@portlet.namespace />specification" role="tab">
						${languageUtil.get(request, "specifications")}
					</a>
				</li>
			</#if>

			<#if cpAttachmentFileEntries?has_content>
				<li class="nav-item" role="presentation">
					<a aria-controls="<@portlet.namespace />attachments" aria-expanded="false" class="nav-link" data-toggle="tab" href="#<@portlet.namespace />attachments" role="tab">
						${languageUtil.get(request, "attachments")}
					</a>
				</li>
			</#if>
		</ul>

		<div class="tab-content">
			<div class="active fade show tab-pane" id="<@portlet.namespace />description">
				<p>${cpDefinition.getDescription(themeDisplay.getLanguageId())}</p>
			</div>

			<#if simpleCPTypeDisplayContext.hasCPDefinitionSpecificationOptionValues()>
				<div class="fade tab-pane" id="<@portlet.namespace />specification">
					<div class="row">
						<#assign columnClass = "col-md-12 specification-column" />

						<#if cpDefinitionSpecificationOptionValues?has_content>
							<#assign columnClass ="col-lg-6 " />

							<div class="${columnClass}">
								<div class="table-responsive">
									<table class="table">
										<#list cpDefinitionSpecificationOptionValues as cpDefinitionSpecificationOptionValue>
											<#assign cpSpecificationOption = cpDefinitionSpecificationOptionValue.getCPSpecificationOption() />

											<tr>
												<td class="table-title">${cpSpecificationOption.getTitle(themeDisplay.getLanguageId())}</td>
												<td class="table-value">${cpDefinitionSpecificationOptionValue.getValue(themeDisplay.getLanguageId())}</td>
											</tr>
										</#list>
									</table>
								</div>
							</div>
						</#if>

						<#list cpOptionCategories as cpOptionCategory>
							<#assign categorizedCPDefinitionSpecificationOptionValues = simpleCPTypeDisplayContext.getCategorizedCPDefinitionSpecificationOptionValues(cpOptionCategory.getCPOptionCategoryId()) />

							<#if categorizedCPDefinitionSpecificationOptionValues?has_content>
								<div class="${columnClass}">
									<div class="table-responsive">
										<table class="table">
											<#list categorizedCPDefinitionSpecificationOptionValues as cpDefinitionSpecificationOptionValue>
												<#assign cpSpecificationOption = cpDefinitionSpecificationOptionValue.getCPSpecificationOption() />

												<tr>
													<td class="table-title">${cpSpecificationOption.getTitle(themeDisplay.getLanguageId())}</td>
													<td class="table-value">${cpDefinitionSpecificationOptionValue.getValue(themeDisplay.getLanguageId())}</td>
												</tr>
											</#list>
										</table>
									</div>
								</div>
							</#if>
						</#list>
					</div>
				</div>
			</#if>

			<#if cpAttachmentFileEntries?has_content>
				<div class="fade tab-pane" id="<@portlet.namespace />attachments">
					<div class="table-responsive">
						<table class="table">
							<tr>
								<#assign count = 0 />

								<#list cpAttachmentFileEntries as curCPAttachmentFileEntry>
									<#assign fileEntry = curCPAttachmentFileEntry.getFileEntry() />

									<td>
										<span>${curCPAttachmentFileEntry.getTitle(themeDisplay.getLanguageId())}</span>

										<span>
											<@liferay_aui.icon
												cssClass="icon-monospaced"
												image="download"
												markupView="lexicon"
												url="${simpleCPTypeDisplayContext.getDownloadFileEntryURL(fileEntry)}"
											/>
										</span>
									</td>

									<#assign count = count + 1 />

									<#if count gte 2>
										</tr>
										<tr>

										<#assign count = 0 />
									</#if>
								</#list>
							<tr>
						</table>
					</div>
				</div>
			</#if>
		</div>
	</div>
</div>

<@liferay_aui.script use="liferay-commerce-product-content">
	var productContent = new Liferay.Portlet.ProductContent(
		{
			cpDefinitionId: ${simpleCPTypeDisplayContext.getCPDefinitionId()},
			fullImageSelector : '#<@portlet.namespace />full-image',
			namespace: '<@portlet.namespace />',
			productContentSelector: '#<@portlet.namespace />${cpDefinition.getCPDefinitionId()}ProductContent',
			thumbsContainerSelector : '#<@portlet.namespace />thumbs-container',
			viewAttachmentURL: '${simpleCPTypeDisplayContext.getViewAttachmentURL().toString()}'
		}
	);

	Liferay.component('<@portlet.namespace />${cpDefinition.getCPDefinitionId()}ProductContent', productContent);
</@>