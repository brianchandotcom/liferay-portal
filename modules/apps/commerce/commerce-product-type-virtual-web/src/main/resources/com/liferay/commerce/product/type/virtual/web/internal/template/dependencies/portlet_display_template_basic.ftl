<#assign
	cpDefinition = virtualCPTypeDisplayContext.getCPDefinition()

	cpInstance = virtualCPTypeDisplayContext.getDefaultCPInstance()
/>

<div class="container-fluid product-detail" id="<@portlet.namespace />${cpDefinition.getCPDefinitionId()}ProductContent">
	<div class="row">
		<div class="product-detail-header">
			<div class="col-lg-6 col-md-7">
				<div class="row">
					<div class="col-2 col-lg-2 col-md-3">
						<#assign images = virtualCPTypeDisplayContext.getImages() />

						<#if images?has_content>
							<#list images as curImage>
								<#assign url = virtualCPTypeDisplayContext.getImageURL(curImage.getFileEntry(), themeDisplay) />

								<div class="card thumb" data-url="${url}">
									<img class="center-block img-fluid" src="${url}">
								</div>
							</#list>
						</#if>
					</div>

					<div class="col-10 col-lg-10 col-md-9 full-image">
						<#assign cpAttachmentFileEntry = virtualCPTypeDisplayContext.getDefaultImage() />

						<#if cpAttachmentFileEntry??>
							<img class="center-block img-fluid" id="<@portlet.namespace />full-image" src="${virtualCPTypeDisplayContext.getImageURL(cpAttachmentFileEntry.getFileEntry(), themeDisplay)}">
						</#if>
					</div>
				</div>
			</div>

			<div class="col-lg-6 col-md-5">
				<h1>${HtmlUtil.escape(cpDefinition.getName())}</h1>

				<#if cpInstance??>
					<h4 class="sku">${HtmlUtil.escape(cpInstance.getSku())}</h4>

					<div class="price">
						<@liferay_commerce["price"]
							CPDefinitionId=cpDefinition.getCPDefinitionId()
							CPInstanceId=cpInstance.getCPInstanceId()
						/>
					</div>

					<div class="availability">${virtualCPTypeDisplayContext.getAvailabilityLabel()}</div>

					<div class="availabilityEstimate">${virtualCPTypeDisplayContext.getAvailabilityEstimateLabel()}</div>

					<div class="stockQuantity">${virtualCPTypeDisplayContext.getStockQuantityLabel()}</div>
				<#else>
					<h4 class="sku" data-text-cp-instance-sku=""></h4>

					<div class="price" data-text-cp-instance-price="" ></div>

					<div class="availability" data-text-cp-instance-availability="" ></div>

					<div class="availabilityEstimate" data-text-cp-instance-availability-estimate="" ></div>

					<div class="stockQuantity" data-text-cp-instance-stock-quantity="" ></div>
				</#if>

				<div class="row">
					<div class="col-md-12">
						<#if virtualCPTypeDisplayContext.hasSampleURL()>
							<a href="${virtualCPTypeDisplayContext.getSampleURL(themeDisplay)}">
								<@liferay_ui["message"] key="sample" />
							</a>
						</#if>
					</div>
				</div>

				<div class="row">
					<div class="col-md-12">
						<div class="options">
							${virtualCPTypeDisplayContext.renderOptions(renderRequest, renderResponse)}
						</div>
					</div>

					<div class="row">
						<div class="col-md-12">
							<@liferay_util["dynamic-include"] key="com.liferay.commerce.product.content.web#/add_to_cart#" />
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<#assign
		cpDefinitionSpecificationOptionValues = virtualCPTypeDisplayContext.getCPDefinitionSpecificationOptionValues()

		cpOptionCategories = virtualCPTypeDisplayContext.getCPOptionCategories()

		cpAttachmentFileEntries = virtualCPTypeDisplayContext.getCPAttachmentFileEntries()
	/>

	<div class="row">
		<div class="product-detail-body">
			<div class="nav-tabs-centered">
				<ul class="nav nav-tabs" role="tablist">
					<li class="nav-item" role="presentation">
						<a aria-controls="<@portlet.namespace />description" aria-expanded="true" class="nav-link" data-toggle="tab" href="#<@portlet.namespace />description" role="tab">
							${languageUtil.get(resourceBundle, "description")}
						</a>
					</li>

					<#if virtualCPTypeDisplayContext.hasCPDefinitionSpecificationOptionValues()>
						<li class="nav-item" role="presentation">
							<a aria-controls="<@portlet.namespace />specification" aria-expanded="false" class="nav-link" data-toggle="tab" href="#<@portlet.namespace />specification" role="tab">
								${languageUtil.get(resourceBundle, "specification-options")}
							</a>
						</li>
					</#if>

					<#if cpAttachmentFileEntries?has_content>
						<li class="nav-item" role="presentation">
							<a aria-controls="<@portlet.namespace />attachments" aria-expanded="false" class="nav-link" data-toggle="tab" href="#<@portlet.namespace />attachments" role="tab">
								${languageUtil.get(resourceBundle, "attachments")}
							</a>
						</li>
					</#if>
				</ul>

				<div class="tab-content">
					<div class="active tab-pane" id="<@portlet.namespace />description">
						<p>${cpDefinition.getDescription(themeDisplay.getLanguageId())}</p>
					</div>

					<#if virtualCPTypeDisplayContext.hasCPDefinitionSpecificationOptionValues()>
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
								<#assign categorizedCPDefinitionSpecificationOptionValues = virtualCPTypeDisplayContext.getCategorizedCPDefinitionSpecificationOptionValues(cpOptionCategory.getCPOptionCategoryId()) />

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
														url="${virtualCPTypeDisplayContext.getDownloadFileEntryURL(fileEntry)}"
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
					$("#<@portlet.namespace />full-image").attr("src", $(this).attr("data-url"));
				}
			);
		}
	);
</script>

<@liferay_aui.script use="liferay-commerce-product-content">
	var productContent = new Liferay.Portlet.ProductContent(
		{
			cpDefinitionId: ${virtualCPTypeDisplayContext.getCPDefinitionId()},
			fullImageSelector : '#<@portlet.namespace />full-image',
			namespace: '<@portlet.namespace />',
			productContentSelector: '#<@portlet.namespace />${cpDefinition.getCPDefinitionId()}ProductContent',
			thumbsContainerSelector : '#<@portlet.namespace />thumbs-container',
			viewAttachmentURL: '${virtualCPTypeDisplayContext.getViewAttachmentURL().toString()}'
		}
	);

	Liferay.component('<@portlet.namespace />${cpDefinition.getCPDefinitionId()}ProductContent', productContent);
</@>