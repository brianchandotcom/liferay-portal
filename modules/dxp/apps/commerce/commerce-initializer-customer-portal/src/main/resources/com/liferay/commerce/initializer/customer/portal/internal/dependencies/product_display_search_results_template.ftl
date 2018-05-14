<#if entries?has_content>
	<ul class="card-page product-detail-card-page">
		<#list entries as curDocument>
			<#assign
			cpDefinitionId = cpSearchResultsDisplayContext.getCPDefinitionId(curDocument)

			skus = cpSearchResultsDisplayContext.getSkus(curDocument)

			image = cpSearchResultsDisplayContext.getProductDefaultImage(curDocument, themeDisplay)

			isIgnoreSKUCombinations = cpSearchResultsDisplayContext.isIgnoreSkuCombinations(curDocument)

			friendlyURL = cpSearchResultsDisplayContext.getProductFriendlyURL(curDocument)

			name = cpSearchResultsDisplayContext.getName(curDocument)

			cpInstanceId = 0

			gtin = ""

			modelNumber = ""

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

						gtin = cpInstance.getGtin()
						modelNumber = cpInstance.getManufacturerPartNumber()
						sku = cpInstance.getSku()
						/>
					</#if>
				</#if>
			</#if>

			<li class="card-page-item-asset">
				<div class="card">
					<div class="product-image-container">
						<img class="product-image" src="${image}">
					</div>

					<div class="product-description product-expand">
						<div class="card-subtitle">
							Brand
						</div>

						<div class="card-title">
							<a href="${friendlyURL}">${name}</a>
						</div>

						<p class="card-subtitle product-sku">
							SKU:

							<#if stringUtil.equals(sku, "multiple")>
								<@liferay_ui["message"] key="multiple" />
							<#else>
								${sku}
							</#if>
						</p>

						<div class="autofit-float autofit-row autofit-row-center">
							<div class="autofit-col autofit-col-expand">
								<div>Model #${modelNumber}</div>
							</div>

							<div class="autofit-col">
								<div class="product-price">
									<#if isIgnoreSKUCombinations>
										<span class="commerce-price"><@liferay_commerce["price"] CPInstanceId= cpInstanceId /></span>
									<#else>
										<span>Price</span>
									</#if>

									<span class="commerce-suffix">/<@liferay_ui["message"] key="ea" /></span>
								</div>
							</div>
						</div>
					</div>

					<div class="product-footer">
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
										<div class="custom-checkbox custom-control">
											<label>
												<input class="custom-control-input" type="checkbox">
												<span class="custom-control-label">
													<span class="custom-control-label-text"><@liferay_ui["message"] key="compare" /></span>
												</span>
											</label>
										</div>
									</div>

									<div class="autofit-col">
										<a class="add-to-list-link" href="#placeholder"><@liferay_ui["message"] key="add-to-list" /> +</a>
									</div>
								</div>
							<#else>
								<span class="available-variants">${skus?size} <@liferay_ui["message"] key="variants-available" /></span>
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