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
						<#if entries?has_content>
							<#assign
								imagesIndex=1
								imageOverflowUrls = []
							/>

							<#list images as curImage>
								<#assign
									imageOverflowUrls = imageOverflowUrls + [" ${simpleCPTypeDisplayContext.getImageURL(curImage.getFileEntry(), themeDisplay)} "]
								/>

								<#if imagesIndex gt 3>
									<#if imagesIndex == 4>
										<div class="product-detail-thumbnail-container product-detail-thumbnail-text-container" id="<@portlet.namespace />thumbs-container">
											<a class="thumb thumb-text" data-toggle="modal" href="#<@portlet.namespace />ImageWidgetModal">
												+ ${images?size - 3}
											</a>
										</div>
									</#if>
								<#else>
									<div class="product-detail-thumbnail-container" id="<@portlet.namespace />thumbs-container">

										<#--
											`data-standard` value should be same as img value
											`href` value should be the large version used for zoom
										-->

										<a class="thumb thumb-image" data-standard="${simpleCPTypeDisplayContext.getImageURL(curImage.getFileEntry(), themeDisplay)}" href="${simpleCPTypeDisplayContext.getImageURL(curImage.getFileEntry(), themeDisplay)}">
											<img class="img-fluid" src="${simpleCPTypeDisplayContext.getImageURL(curImage.getFileEntry(), themeDisplay)}">
										</a>
									</div>
								</#if>

								<#assign
									imagesIndex=imagesIndex + 1
								/>
							</#list>
						</#if>
					</div>

					<#if validator.isNotNull(defaultImage)>
						<#assign
							easyzoomCssClass = " easyzoom easyzoom--adjacent"
						/>

						<div class="product-detail-image-column">
							<div class="full-image product-detail-image-container">
								<div class="product-detail-image${easyzoomCssClass}">
									<a href="${simpleCPTypeDisplayContext.getImageURL(defaultImage.getFileEntry(), themeDisplay)}" tabindex="-1">
										<img class="img-fluid" id="<@portlet.namespace />full-image" src="${simpleCPTypeDisplayContext.getImageURL(defaultImage.getFileEntry(), themeDisplay)}">
									</a>
								</div>

								<div class="product-detail-info-compare">
									<@liferay_commerce["compare-product"] CPDefinitionId=cpDefinitionId />
								</div>
							</div>
						</div>
					</#if>
				</div>

				<div class="product-detail-social-navigation">
					<#-- Use @liferay.navigation_menu default_preferences=freeMarkerPortletPreferences.getPreferences("portletSetupPortletDecoratorId", "barebone") and set it up. The nav below is just a placeholder. -->

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

				<#if simpleCPTypeDisplayContext.renderOptions(renderRequest, renderResponse)??>
					<div class="product-detail-options">
						${simpleCPTypeDisplayContext.renderOptions(renderRequest, renderResponse)}
					</div>
				</#if>

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
								<#-- discount % and $ saved goes here -->

							</div>
						</div>
					</div>
				</div>
			</div>
		</div>

		<#if imageOverflowUrls?has_content>
			<div aria-hidden="true" aria-labelledby="" class="fade modal modal-hidden product-detail-image-widget-modal" id="<@portlet.namespace />ImageWidgetModal" role="dialog" tabindex="-1">
				<div class="modal-dialog modal-full-screen" role="document">
					<div class="modal-content">
						<div class="modal-header">
							<div class="modal-title">${cpDefinition.getName()}</div>
							<button aria-label="Close" class="close" data-dismiss="modal" type="button">
								<@liferay_aui.icon
									image="times"
									markupView="lexicon"
								/>
							</button>
						</div>

						<div class="modal-body">
							<div class="carousel" data-interval="false" data-ride="carousel" id="carouselExampleFade">
								<div class="carousel-inner">
									<#assign
										imageOverflowUrlsIndex = 1
									/>

									<#list imageOverflowUrls as url>
										<#assign
											activeClass=""
										/>

										<#if imageOverflowUrls[3]?? && imageOverflowUrls[3] == url>
											<#assign
												activeClass="active "
											/>
										</#if>

										<div class="${activeClass}carousel-item">
											<div class="carousel-item-image">
												<img alt="Product Image" class="img-fluid" src="${url}">
											</div>

											<div class="carousel-index">${imageOverflowUrlsIndex}/${imageOverflowUrls?size}</div>
										</div>

										<#assign
											imageOverflowUrlsIndex = imageOverflowUrlsIndex + 1
										/>
									</#list>

									<a class="carousel-control-prev" data-slide="prev" href="#carouselExampleFade" role="button">
										<@liferay_aui.icon
											image="angle-left"
											markupView="lexicon"
										/>

										<span class="sr-only">Previous</span>
									</a>

									<a class="carousel-control-next" data-slide="next" href="#carouselExampleFade" role="button">
										<@liferay_aui.icon
											image="angle-right"
											markupView="lexicon"
										/>

										<span class="sr-only">Next</span>
									</a>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</#if>
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

<script>
	var easyzoom = $('.easyzoom').easyZoom();
	var easyzoomApi = easyzoom.filter('.easyzoom').data('easyZoom');

	$('.product-detail-image-widget .thumb-image').on(
		'click',
		function(event) {
			var $this = $(this);

			event.preventDefault();

			easyzoomApi.swap($this.data('standard'), $this.attr('href'));
		}
	);
</script>

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