<div class="minette-commerce-cart-content">
	<div class="commerce-component-header">
		<h2 class="component-title">
			${languageUtil.get(request, "cart")}

			<#if commerceCartContentDisplayContext.getCommerceOrderId() != 0>
				#${commerceCartContentDisplayContext.getCommerceOrderId()}
			</#if>
		</h2>
	</div>

	<#if entries?has_content>
		<div class="table-responsive">
			<table class="table table-autofit table-list">
				<thead>
					<tr>
						<th class="image-column"></th>
						<th class="product-column table-cell-expand">${languageUtil.get(request, "product")}</th>
						<th class="quantity-column table-cell-expand">${languageUtil.get(request, "quantity")}</th>
						<th class="subtotal-column table-cell-expand">${languageUtil.get(request, "subtotal")}</th>
					</tr>
				</thead>

				<tbody>
					<#list entries as curCommerceOrderItem>
						<#assign
							cpDefinition = curCommerceOrderItem.getCPDefinition()

							image = ''

							productURL = commerceCartContentDisplayContext.getCPDefinitionURL(cpDefinition.getCPDefinitionId(), themeDisplay)

							title = cpDefinition.getName()

							deleteURL = commerceCartContentDisplayContext.getDeleteURL(curCommerceOrderItem)
						/>

						<#if commerceCartContentDisplayContext.getCommerceOrderItemThumbnailSrc(curCommerceOrderItem, themeDisplay)??>
							<#assign
								image = commerceCartContentDisplayContext.getCommerceOrderItemThumbnailSrc(curCommerceOrderItem, themeDisplay)
							/>
						</#if>

						<tr>
							<td class="image-column">
								<#if image?has_content>
									<img class="commerce-cart-content-image" src="${image}">
								</#if>
							</td>
							<td class="product-column table-cell-expand">
								<div class="table-title">
									<a href="${productURL}">${title}</a>
								</div>

								<div class="table-text">${languageUtil.get(request, "sku")} ${curCommerceOrderItem.getSku()}</div>
							</td>
							<td class="quantity-column table-cell-expand">
								<input class="custom-number custom-number-monospaced form-control" type="number" value="${curCommerceOrderItem.getQuantity()}">
							</td>
							<td class="subtotal-column table-cell-expand">
								<div class="commerce-cart-content-value subtotal-value">
									${curCommerceOrderItem.getQuantity()} x ${curCommerceOrderItem.getPrice()}
								</div>

								<@liferay_ui["icon-delete"]
									cssClass="commerce-cart-content-remove"
									label=true
									url=deleteURL
								/>
							</td>
						</tr>
					</#list>
				</tbody>
			</table>
		</div>
	<#else>
		<h3>${languageUtil.get(request, "there-are-no-items-in-your-cart")}.</h3>
	</#if>
</div>

<div class="autofit-float autofit-row autofit-row-center commerce-cart-content-actions">
	<div class="autofit-col autofit-col-expand">
		<div class="autofit-section">
			<a class="btn btn-secondary" href="${themeDisplay.getLayout().getGroup().getDisplayURL(themeDisplay, false)}">${languageUtil.get(request, "continue-shopping")}</a>
		</div>
	</div>
</div>