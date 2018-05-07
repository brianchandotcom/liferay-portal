<#assign
	cpDefinition = groupedCPTypeDisplayContext.getCPDefinition()

	cpInstance = groupedCPTypeDisplayContext.getDefaultCPInstance()
/>

<div class="container-fluid product-detail" id="<@portlet.namespace />${cpDefinition.getCPDefinitionId()}ProductContent">
	<div class="row">
		<div class="product-detail-header">
			<div class="col-lg-6 col-md-7">
				<div class="row">
					<div class="col-lg-2 col-md-3 col-xs-2">
						<div id="<@portlet.namespace />thumbs-container">
							<#assign images = groupedCPTypeDisplayContext.getImages() />

							<#if images?has_content>
								<#list images as curImage>
									<#assign url = groupedCPTypeDisplayContext.getImageURL(curImage.getFileEntry(), themeDisplay) />

									<div class="card thumb" data-url="${url}">
										<img class="center-block img-responsive" src="${url}">
									</div>
								</#list>
							</#if>
						</div>
					</div>

					<div class="col-lg-10 col-md-9 col-xs-10 full-image">
						<#assign cpAttachmentFileEntry = groupedCPTypeDisplayContext.getDefaultImage() />

						<#if cpAttachmentFileEntry??>
							<img class="center-block img-responsive" id="full-image" src="${groupedCPTypeDisplayContext.getImageURL(cpAttachmentFileEntry.getFileEntry(), themeDisplay)}">
						</#if>
					</div>
				</div>
			</div>

			<div class="col-lg-6 col-md-5">
				<h1>${cpDefinition.getName()}</h1>

				<#if cpInstance??>
					<h4 class="sku">${cpInstance.getSku()}</h4>

					<div class="price">${cpInstance.getPrice()}</div>

					<div class="availability">${groupedCPTypeDisplayContext.getAvailabilityLabel()}</div>

					<div class="availabilityRange">${groupedCPTypeDisplayContext.getAvailabilityRangeLabel()}</div>

					<div class="stockQuantity">${groupedCPTypeDisplayContext.getStockQuantityLabel()}</div>
				<#else>
					<h4 class="sku" data-text-cp-instance-sku=""></h4>

					<div class="price" data-text-cp-instance-price="" ></div>

					<div class="availability" data-text-cp-instance-availability="" ></div>

					<div class="availabilityRange" data-text-cp-instance-availability-range="" ></div>

					<div class="stockQuantity" data-text-cp-instance-stock-quantity="" ></div>
				</#if>

				<div class="row">
					<div class="col-lg-12">
						<#assign cpDefinitionGroupedEntries = groupedCPTypeDisplayContext.getCPDefinitionGroupedEntry() />

						<#if cpDefinitionGroupedEntries?has_content>
							<#list cpDefinitionGroupedEntries as curCPDefinitionGroupedEntry>
								<#assign curCPDefinition = curCPDefinitionGroupedEntry.getEntryCPDefinition() />

								<div class="row">
									<div class="col-md-4">
										<img class="img-responsive" src="${curCPDefinition.getDefaultImageThumbnailSrc(themeDisplay)}">
									</div>

									<div class="col-md-8">
										<h5>
											${curCPDefinition.getName()}
										</h5>

										<h6>
											${groupedCPTypeDisplayContext.getLabel(locale, "quantity-x", curCPDefinitionGroupedEntry.getQuantity())}
										</h6>
									</div>
								</div>
							</#list>
						</#if>
					</div>
				</div>

				<div class="row">
					<div class="col-md-12">
						<div class="options">
							${groupedCPTypeDisplayContext.renderOptions(renderRequest, renderResponse)}
						</div>
					</div>

					<div class="row">
						<div class="col-md-12">
							<@liferay_util["dynamic-include"] key="com.liferay.commerce.product.content.web#/add_to_cart#" />

							<@liferay_util["dynamic-include"] key="com.liferay.commerce.product.content.web#/add_to_wish_list#" />
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<#assign
		cpDefinitionSpecificationOptionValues = groupedCPTypeDisplayContext.getCPDefinitionSpecificationOptionValues()

		cpOptionCategories = groupedCPTypeDisplayContext.getCPOptionCategories()

		cpAttachmentFileEntries = groupedCPTypeDisplayContext.getCPAttachmentFileEntries()
	/>

	<div class="row">
		<div class="product-detail-body">
			<div class="nav-tabs-centered">
				<ul class="nav nav-tabs" role="tablist">
					<li class="active" role="presentation">
						<a aria-controls="<@portlet.namespace />description" aria-expanded="true" data-toggle="tab" href="#<@portlet.namespace />description" role="tab">
							${languageUtil.get(resourceBundle, "description")}
						</a>
					</li>

					<#if groupedCPTypeDisplayContext.hasCPDefinitionSpecificationOptionValues()>
						<li role="presentation">
							<a aria-controls="<@portlet.namespace />specification" aria-expanded="false" data-toggle="tab" href="#<@portlet.namespace />specification" role="tab">
								${languageUtil.get(resourceBundle, "specification-options")}
							</a>
						</li>
					</#if>

					<#if cpAttachmentFileEntries?has_content>
						<li role="presentation">
							<a aria-controls="<@portlet.namespace />attachments" aria-expanded="false" data-toggle="tab" href="#<@portlet.namespace />attachments" role="tab">
								${languageUtil.get(resourceBundle, "attachments")}
							</a>
						</li>
					</#if>
				</ul>

				<div class="tab-content">
					<div class="active tab-pane" id="<@portlet.namespace />description">
						<p>${cpDefinition.getDescription(themeDisplay.getLanguageId())}</p>
					</div>

					<#if groupedCPTypeDisplayContext.hasCPDefinitionSpecificationOptionValues()>
						<div class="tab-pane" id="<@portlet.namespace />specification">
							<div class="table-responsive">
								<table class="table table-bordered table-striped">
									<#list cpDefinitionSpecificationOptionValues as cpDefinitionSpecificationOptionValue>
										<#assign cpSpecificationOption = cpDefinitionSpecificationOptionValue.getCPSpecificationOption() />

										<tr>
											<td>${cpSpecificationOption.getTitle(themeDisplay.getLanguageId())}</td>
											<td>${cpDefinitionSpecificationOptionValue.getValue(themeDisplay.getLanguageId())}</td>
										</tr>
									</#list>
								</table>
							</div>

							<#list cpOptionCategories as cpOptionCategory>
								<#assign categorizedCPDefinitionSpecificationOptionValues = groupedCPTypeDisplayContext.getCategorizedCPDefinitionSpecificationOptionValues(cpOptionCategory.getCPOptionCategoryId()) />

								<#if categorizedCPDefinitionSpecificationOptionValues?has_content>
									<div class="table-responsive">
										<table class="table table-bordered table-striped">
											<tr>
												<th>${cpOptionCategory.getTitle(themeDisplay.getLanguageId())}</th>
												<th></th>
											</tr>

											<#list categorizedCPDefinitionSpecificationOptionValues as cpDefinitionSpecificationOptionValue>
												<#assign cpSpecificationOption = cpDefinitionSpecificationOptionValue.getCPSpecificationOption() />

												<tr>
													<td>${cpSpecificationOption.getTitle(themeDisplay.getLanguageId())}</td>
													<td>${cpDefinitionSpecificationOptionValue.getValue(themeDisplay.getLanguageId())}</td>
												</tr>
											</#list>
										</table>
									</div>
								</#if>
							</#list>
						</div>
					</#if>

					<#if cpAttachmentFileEntries?has_content>
						<div class="tab-pane" id="<@portlet.namespace />attachments">
							<div class="table-responsive">
								<table class="table table-bordered table-striped">
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
														url="${groupedCPTypeDisplayContext.getDownloadFileEntryURL(fileEntry)}"
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
	</div>
</div>

<script>
	$(document).ready(
		function() {
			$(".thumb").click(
				function() {
					$("#full-image").attr("src", $(this).attr("data-url"));
				}
			);
		}
	);
</script>

<@liferay_aui.script use="liferay-commerce-product-content">
	var productContent = new Liferay.Portlet.ProductContent(
		{
			cpDefinitionId: ${groupedCPTypeDisplayContext.getCPDefinitionId()},
			fullImageSelector : '#<@portlet.namespace />full-image',
			namespace: '<@portlet.namespace />',
			productContentSelector: '#<@portlet.namespace />${cpDefinition.getCPDefinitionId()}ProductContent',
			thumbsContainerSelector : '#<@portlet.namespace />thumbs-container',
			viewAttachmentURL: '${groupedCPTypeDisplayContext.getViewAttachmentURL().toString()}'
		}
	);

	Liferay.component('<@portlet.namespace />${cpDefinition.getCPDefinitionId()}ProductContent', productContent);
</@>