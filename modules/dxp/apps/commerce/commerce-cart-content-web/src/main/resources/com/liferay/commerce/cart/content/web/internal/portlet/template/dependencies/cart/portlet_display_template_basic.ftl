<div class="table-responsive">
	<table class="table">
		<#if entries?has_content>
			<tr>
				<th>${languageUtil.get(request, "product")}</th>
				<th>${languageUtil.get(request, "description")}</th>
				<th>${languageUtil.get(request, "price")}</th>
				<th>${languageUtil.get(request, "quantity")}</th>
				<th></th>
			</tr>

			<#list entries as curCommerceOrderItem>
				<#assign
				cpDefinition = curCommerceOrderItem.getCPDefinition()

				image = commerceCartContentDisplayContext.getCommerceOrderItemThumbnailSrc(curCommerceOrderItem, themeDisplay)

				productURL = commerceCartContentDisplayContext.getCPDefinitionURL(cpDefinition.getCPDefinitionId(), themeDisplay)

				title = cpDefinition.getTitle(locale)

				price = commerceCartContentDisplayContext.getFormattedPrice(curCommerceOrderItem)

				deleteURL = commerceCartContentDisplayContext.getDeleteURL(curCommerceOrderItem)
				/>

				<tr>
					<td>
						<#if image??>
							<img class="aspect-ratio-bg-cover sticker sticker-lg sticker-static" src="${image}">
						</#if>
					</td>
					<td>
						<a href="${productURL}">
							<strong>${title}</strong>
						</a>
					</td>
					<td>${price}</td>
					<td>${curCommerceOrderItem.getQuantity()}</td>
					<td>
						<a href="${deleteURL}">
							${languageUtil.get(request, "delete")}
						</a>
					</td>
				</tr>
			</#list>
		</#if>
	</table>
</div>