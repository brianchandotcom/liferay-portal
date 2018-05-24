<#assign
	cpDefinition = simpleCPTypeDisplayContext.getCPDefinition()

	cpDefinitionId = cpDefinition.getCPDefinitionId()

	cpInstanceId = 0

	isIgnoreSKUCombinations = cpDefinition.isIgnoreSKUCombinations()

	images = simpleCPTypeDisplayContext.getImages()

	defaultImage = (simpleCPTypeDisplayContext.getDefaultImage())!""

	brandName = ""

	gtin = ""

	modelNumber = ""

	sku = ""

	categories = simpleCPTypeDisplayContext.getAssetCategories()
/>
<#if isIgnoreSKUCombinations>
	<#if simpleCPTypeDisplayContext.getDefaultCPInstance()??>
		<#assign cpInstance = simpleCPTypeDisplayContext.getDefaultCPInstance() />

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

<div class="container-fluid container-fluid-max-xl">
	<div class="product-detail" id="<@portlet.namespace />${cpDefinition.getCPDefinitionId()}ProductContent">
		<div class="commerce-component-header product-detail-header">
			<h2 class="component-title">${cpDefinition.getName()}</h2>
			<div class="autofit-float autofit-padded-no-gutters autofit-row autofit-row-center product-detail-subheader">
				<#if brandName?has_content>
					<div class="autofit-col">
						<div class="commerce-brand-name">${brandName}</div>
					</div>
				</#if>

				<div class="autofit-col">
					<div class="commerce-model-number">
						Model #<span data-text-cp-instance-manufacturer-part-number>${modelNumber}</span>
					</div>
				</div>

				<div class="autofit-col">
					<div class="commerce-sku">
						SKU #<span data-text-cp-instance-sku>${sku}</span>
					</div>
				</div>
			</div>
		</div>

		<div class="product-detail-body">
			<div class="product-detail-image-widget-column">
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

								<div class="product-detail-info-compare">
									<@liferay_commerce["compare-product"] CPDefinitionId=cpDefinitionId />
								</div>

								<a class="sticker sticker-top-right" href="#placeholder">
									<@liferay_aui.icon
										image="heart"
										markupView="lexicon"
									/>
								</a>

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

				<div class="product-detail-social-navigation">
					<!-- Use @liferay.navigation_menu default_preferences=freeMarkerPortletPreferences.getPreferences("portletSetupPortletDecoratorId", "barebone") and set it up. The nav below is just a placeholder. -->

					<ul class="nav">
						<li class="nav-item">
							<a href="#1">
								<@liferay_aui.icon
									image="social-facebook"
									markupView="lexicon"
								/>
							</a>
						</li>
						<li class="nav-item">
							<a href="#1">
								<@liferay_aui.icon
									image="twitter"
									markupView="lexicon"
								/>
							</a>
						</li>
						<li class="nav-item">
							<a href="#1">
								<@liferay_aui.icon
									image="social-linkedin"
									markupView="lexicon"
								/>
							</a>
						</li>
						<li class="nav-item">
							<a href="#1">
								<span class="icon-instagram"></span>
							</a>
						</li>
						<li class="nav-item">
							<a href="#1">
								<span class="icon-youtube-play"></span>
							</a>
						</li>
					</ul>
				</div>
			</div>

			<div class="product-detail-info">
				<div class="autofit-float autofit-row product-detail-info-header">
					<div class="autofit-col autofit-col-expand">
						<h2 class="commerce-price" data-text-cp-instance-price><#if cpInstance??><@liferay_commerce["price"] CPDefinitionId=cpDefinitionId CPInstanceId= cpInstanceId /></#if></h2>
					</div>

					<#if cpInstance??>
						<div class="autofit-col autofit-col-expand">
							<div class="autofit-section">
								was
								<strong data-text-cp-instance-price>
									<@liferay_commerce["price"]
										CPDefinitionId=cpDefinitionId
										CPInstanceId=cpInstanceId
									/>
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
					<@liferay_commerce["tier-price"]
						CPInstanceId=cpInstanceId
						taglibQuantityInputId=renderResponse.getNamespace() +  cpDefinition.getCPDefinitionId() + 'Quantity'
					/>
				</#if>

				<div class="product-detail-info-selections">
					<div class="autofit-float autofit-padded-no-gutters autofit-row autofit-row-center">
						<div class="autofit-col commerce-quantity-input">
							<@liferay_commerce["quantity-input"]
								CPDefinitionId=cpDefinitionId
								useSelect=false
							/>
						</div>

						<div class="autofit-col">
							<@liferay_commerce_cart["add-to-cart"]
								CPDefinitionId=cpDefinitionId
								CPInstanceId=cpInstanceId
								elementClasses="btn-primary text-truncate"
								productContentId=renderResponse.getNamespace() +  cpDefinition.getCPDefinitionId() + 'ProductContent'
								taglibQuantityInputId=renderResponse.getNamespace() +  cpDefinition.getCPDefinitionId() + 'Quantity'
							/>
						</div>

						<div class="autofit-col autofit-col-expand">
							<div class="autofit-section">
								8% Discount<br>
								$28.12 Saved
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<#assign
	cpDefinitionSpecificationOptionValues = simpleCPTypeDisplayContext.getCPDefinitionSpecificationOptionValues()

	cpOptionCategories = simpleCPTypeDisplayContext.getCPOptionCategories()

	cpAttachmentFileEntries = simpleCPTypeDisplayContext.getCPAttachmentFileEntries()
/>

<#if cpDefinition.getDescription(themeDisplay.getLanguageId())?has_content || simpleCPTypeDisplayContext.hasCPDefinitionSpecificationOptionValues() || cpAttachmentFileEntries?has_content>
	<div class="product-detail-description">
		<div class="container-fluid container-fluid-max-xl">
			<ul class="nav nav-underline product-detail-description-tabs" role="tablist">
				<#if cpDefinition.getDescription(themeDisplay.getLanguageId())?has_content>
					<li class="nav-item" role="presentation">
						<a aria-controls="<@portlet.namespace />description" aria-expanded="true" class="active nav-link" data-toggle="tab" href="#<@portlet.namespace />description" role="tab">
							${languageUtil.get(request, "description")}
						</a>
					</li>
				</#if>

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
				<#if cpDefinition.getDescription(themeDisplay.getLanguageId())?has_content>
					<div class="active fade show tab-pane" id="<@portlet.namespace />description">
						<p>${cpDefinition.getDescription(themeDisplay.getLanguageId())}</p>
					</div>
				</#if>

				<#if simpleCPTypeDisplayContext.hasCPDefinitionSpecificationOptionValues()>
					<div class="fade tab-pane" id="<@portlet.namespace />specification">
						<#if cpDefinitionSpecificationOptionValues?has_content>
							<dl class="autofit-float autofit-row autofit-row-center specification-list">
								<#list cpDefinitionSpecificationOptionValues as cpDefinitionSpecificationOptionValue>
									<#assign cpSpecificationOption = cpDefinitionSpecificationOptionValue.getCPSpecificationOption() />

									<dt class="autofit-col specification-term">
										${cpSpecificationOption.getTitle(themeDisplay.getLanguageId())}
									</dt>
									<dd class="autofit-col specification-desc">
										${cpDefinitionSpecificationOptionValue.getValue(themeDisplay.getLanguageId())}
									</dd>

									<#if !cpDefinitionSpecificationOptionValue?has_next>
										<#if (cpDefinitionSpecificationOptionValues?size % 2) != 0>
											<dt class="autofit-col specification-term specification-empty"></dt>
											<dd class="autofit-col specification-desc specification-empty"></dd>
										</#if>
									</#if>
								</#list>
							</dl>
						</#if>

						<#list cpOptionCategories as cpOptionCategory>
							<#assign categorizedCPDefinitionSpecificationOptionValues = simpleCPTypeDisplayContext.getCategorizedCPDefinitionSpecificationOptionValues(cpOptionCategory.getCPOptionCategoryId()) />

							<#if categorizedCPDefinitionSpecificationOptionValues?has_content>
								<dl class="autofit-float autofit-row autofit-row-center specification-list">
									<#list categorizedCPDefinitionSpecificationOptionValues as cpDefinitionSpecificationOptionValue>
										<#assign cpSpecificationOption = cpDefinitionSpecificationOptionValue.getCPSpecificationOption() />

										<dt class="autofit-col specification-term">
											${cpSpecificationOption.getTitle(themeDisplay.getLanguageId())}
										</dt>
										<dd class="autofit-col specification-desc">
											${cpDefinitionSpecificationOptionValue.getValue(themeDisplay.getLanguageId())}
										</dd>

										<#if !cpDefinitionSpecificationOptionValue?has_next>
											<#if (categorizedCPDefinitionSpecificationOptionValues?size % 2) != 0>
												<dt class="autofit-col specification-term specification-empty"></dt>
												<dd class="autofit-col specification-desc specification-empty"></dd>
											</#if>
										</#if>
									</#list>
								</dl>
							</#if>
						</#list>
					</div>
				</#if>

				<#if cpAttachmentFileEntries?has_content>
					<div class="fade tab-pane" id="<@portlet.namespace />attachments">
						<dl class="autofit-float autofit-row autofit-row-center specification-list">
							<#list cpAttachmentFileEntries as curCPAttachmentFileEntry>
								<#assign fileEntry = curCPAttachmentFileEntry.getFileEntry() />

								<dt class="autofit-col specification-term">
									${curCPAttachmentFileEntry.getTitle(themeDisplay.getLanguageId())}
								</dt>
								<dd class="autofit-col specification-desc">
									<@liferay_aui.icon
										cssClass="icon-monospaced"
										image="download"
										markupView="lexicon"
										url="${simpleCPTypeDisplayContext.getDownloadFileEntryURL(fileEntry)}"
									/>
								</dd>

								<#if !curCPAttachmentFileEntry?has_next>
									<#if (cpAttachmentFileEntries?size % 2) != 0>
										<dt class="autofit-col specification-term specification-empty"></dt>
										<dd class="autofit-col specification-desc specification-empty"></dd>
									</#if>
								</#if>
							</#list>
						</dl>
					</div>
				</#if>
			</div>
		</div>
	</div>
</#if>

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