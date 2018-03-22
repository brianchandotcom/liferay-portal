<#if entries?has_content>
	<ul class="card-page catalog-card-page">
		<#list entries as curDocument>
				<#assign
				cpDefinitionId = cpSearchResultsDisplayContext.getCPDefinitionId(curDocument)

				skus = cpSearchResultsDisplayContext.getSkus(curDocument)

				image = cpSearchResultsDisplayContext.getProductDefaultImage(curDocument, themeDisplay)

				isIgnoreSKUCombinations = cpSearchResultsDisplayContext.isIgnoreSkuCombinations(curDocument)

				friendlyURL = cpSearchResultsDisplayContext.getProductFriendlyURL(curDocument)

				title = cpSearchResultsDisplayContext.getTitle(curDocument)

				cpInstanceId = 0

				sku = "multiple"
				/>

				<#if isIgnoreSKUCombinations>
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

			<li class="card-page-item-asset">
				<div class="card">
					<div class="product-expand">
						<p class="card-subtitle product-sku">
								<#if stringUtil.equals(sku, "multiple")>
									<@liferay_ui["message"] key="multiple" />
								<#else>
									SKU: ${sku}
								</#if>
						</p>

						<div class="autofit-row product-description">
							<div class="autofit-col autofit-col-expand">
								<div class="card-title">
									<a href="${friendlyURL}">${title}</a>
								</div>
							</div>

							<div class="autofit-col">
									<span class="sticker sticker-xl">
										<span class="sticker-overlay">
											<img class="product-image sticker-img" src="${image}">
										</span>
									</span>
							</div>
						</div>
					</div>

					<div class="product-expand">
						<div class="product-price">
								<#if isIgnoreSKUCombinations>
									<span class="commerce-price"><@liferay_commerce["price"] CPInstanceId= cpInstanceId /></span>
								<#else>
									<span class="commerce-prefix"><@liferay_ui["message"] key="starting-at" /></span>
									<span class="commerce-price">$1,250.00</span>
								</#if>

							<span class="commerce-suffix">/<@liferay_ui["message"] key="ea" /></span>
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
											<div class="custom-control custom-checkbox">
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