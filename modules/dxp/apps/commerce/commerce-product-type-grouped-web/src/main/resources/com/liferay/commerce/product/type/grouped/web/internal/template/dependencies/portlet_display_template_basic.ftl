<#assign cpDefinition = groupedCPTypeDisplayContext.getCPDefinition() />

<div class="container-fluid product-detail">
	<div class="row">
		<div class="col-lg-6 col-md-7">
			<div class="row">
				<div class="col-lg-2 col-md-3 col-xs-2">
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

				<div class="col-lg-10 col-md-9 col-xs-10 full-image">
					<#assign cpAttachmentFileEntry = groupedCPTypeDisplayContext.getDefaultImage() />

					<#if cpAttachmentFileEntry??>
						<img class="center-block img-responsive" id="full-image" src="${groupedCPTypeDisplayContext.getImageURL(cpAttachmentFileEntry.getFileEntry(), themeDisplay)}">
					</#if>
				</div>
			</div>
		</div>

		<div class="col-lg-6 col-md-5">
			<h1>${cpDefinition.getTitle()}</h1>

			<h4>Code: ${cpDefinition.getBaseSKU()}</h4>

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
										${curCPDefinition.getTitle()}
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
					</div>
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