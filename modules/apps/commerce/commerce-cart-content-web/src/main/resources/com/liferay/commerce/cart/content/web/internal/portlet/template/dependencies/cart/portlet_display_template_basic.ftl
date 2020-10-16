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

					image = ''

					productURL = commerceCartContentDisplayContext.getCPDefinitionURL(cpDefinition.getCPDefinitionId(), themeDisplay)

					name = cpDefinition.getName(themeDisplay.getLanguageId())

					deleteURL = commerceCartContentDisplayContext.getDeleteURL(curCommerceOrderItem)
				/>

				<#if commerceCartContentDisplayContext.getCommerceOrderItemThumbnailSrc(curCommerceOrderItem)??>
					<#assign
						image = commerceCartContentDisplayContext.getCommerceOrderItemThumbnailSrc(curCommerceOrderItem)
					/>
				</#if>

				<tr>
					<td>
						<#if image?has_content>
							<img class="aspect-ratio-bg-cover sticker sticker-lg sticker-static" src="${htmlUtil.escapeAttribute(image)}">
						</#if>
					</td>
					<td>
						<a href="${htmlUtil.escapeAttribute(productURL)}">
							<strong>${htmlUtil.escape(name)}</strong>
						</a>
					</td>
					<td>
						<@commerce_ui["price"]
							CPDefinitionId=cpDefinition.getCPDefinitionId()
							CPInstanceId=curCommerceOrderItem.getCPInstanceId()
							showDiscount=false
						/>
					</td>
					<td>${curCommerceOrderItem.getQuantity()}</td>
					<td>
						<a href="${htmlUtil.escapeAttribute(deleteURL)}">
							${languageUtil.get(request, "delete")}
						</a>
					</td>
				</tr>
			</#list>
		</#if>
	</table>
</div>