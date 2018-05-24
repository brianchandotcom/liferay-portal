<#if entries?has_content>
	<ul class="card-page catalog-card-page">
		<#list entries as curDocument>
				<#assign
				cpDefinitionId = cpSearchResultsDisplayContext.getCPDefinitionId(curDocument)

				skus = cpSearchResultsDisplayContext.getSkus(curDocument)

				image = cpSearchResultsDisplayContext.getProductDefaultImage(curDocument, themeDisplay)

				isIgnoreSKUCombinations = cpSearchResultsDisplayContext.isIgnoreSkuCombinations(curDocument)

				friendlyURL = cpSearchResultsDisplayContext.getProductFriendlyURL(curDocument)

				name = cpSearchResultsDisplayContext.getName(curDocument)

				cpInstanceId = 0

				sku = "multiple"
				/>

				<#if isIgnoreSKUCombinations>
					<#if cpSearchResultsDisplayContext.getDefaultCPInstance(curDocument)??>
						<#assign
						cpInstance =  cpSearchResultsDisplayContext.getDefaultCPInstance(curDocument)
						/>

						<#if cpInstance??>
							<#assign
							cpInstanceId =  cpInstance.getCPInstanceId()

							sku = cpInstance.getSku()
							/>
						</#if>
					</#if>
				</#if>

			<li class="card-page-item-asset">
				<div class="card">
					<div class="product-image-container">
						<#if image?has_content>
							<img class="product-image" src="${image}">
						</#if>
					</div>

					<div class="product-expand">
						<p class="card-subtitle product-sku">
							<#if stringUtil.equals(sku, "multiple")>
								<@liferay_ui["message"] key="multiple" />
							<#else>
								${sku}
							</#if>
						</p>

						<div class="card-title">
							<a href="${friendlyURL}">${name}</a>
						</div>
					</div>

					<div class="product-footer">
						<div class="product-price">
							<@liferay_commerce["price"] CPDefinitionId=cpDefinitionId />
						</div>

						<div class="product-actions">
								<#if isIgnoreSKUCombinations>
									<div class="autofit-row">
										<div class="autofit-col">
											<@liferay_commerce["quantity-input"]
												CPDefinitionId=cpDefinitionId
												useSelect=false
											/>
										</div>

										<div class="autofit-col autofit-col-expand">
											<@liferay_commerce_cart["add-to-cart"]
												CPDefinitionId=cpDefinitionId
												CPInstanceId=cpInstanceId
												elementClasses="btn-block btn-primary text-truncate"
											/>
										</div>
									</div>
								<#else>
									<a href="${friendlyURL}" class="btn btn-block btn-outline-primary text-truncate"><@liferay_ui["message"] key="view-all-variants" /></a>
								</#if>
						</div>

						<div class="product-subactions">
								<#if isIgnoreSKUCombinations>
									<div class="autofit-row">
										<div class="autofit-col autofit-col-expand">
											<div class="product-list-info-compare">
												<@liferay_commerce["compare-product"] CPDefinitionId=cpDefinitionId />
											</div>
										</div>

										<div class="autofit-col">
											<a class="add-to-list-link" href="#placeholder"><@liferay_ui["message"] key="save-for-later" /></a>
										</div>
									</div>
								<#else>
									<div class="autofit-row">
										<div class="autofit-col autofit-col-expand">
											<div class="autofit-section">
												<span class="available-variants">${skus?size} <@liferay_ui["message"] key="variants-available" /></span>
											</div>
										</div>
									</div>
								</#if>
						</div>
					</div>
				</div>
			</li>
		</#list>
	</ul>
<#else>
	<div class="alert alert-info">
		<@liferay_ui["message"] key="no-products-were-found" />
	</div>
</#if>