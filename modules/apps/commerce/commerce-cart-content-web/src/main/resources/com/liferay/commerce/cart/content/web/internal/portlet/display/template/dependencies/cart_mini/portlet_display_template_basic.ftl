<#assign count = 0 />

<#if entries?has_content>
	<div class="row">
		<#assign
			orderPrice = commerceCartContentMiniDisplayContext.getCommerceOrderPrice()

			subTotal = orderPrice.getSubtotal()
		/>

		<div class="col-md-12">
			<strong>Total: ${subTotal.format(themeDisplay.getLocale())}</strong>
		</div>

		<#list entries as curCommerceOrderItem>
			<#assign
				cpDefinition = curCommerceOrderItem.getCPDefinition()

				image = ''

				productURL = commerceCartContentMiniDisplayContext.getCPDefinitionURL(cpDefinition.getCPDefinitionId(), themeDisplay)

				name = cpDefinition.getName(themeDisplay.getLanguageId())
			/>

			<#if commerceCartContentMiniDisplayContext.getCommerceOrderItemThumbnailSrc(curCommerceOrderItem)??>
				<#assign
					image = commerceCartContentMiniDisplayContext.getCommerceOrderItemThumbnailSrc(curCommerceOrderItem)
				/>
			</#if>

			<div class="col-md-6">
				<div class="row-fluid">
					<img class="w-100" src="${htmlUtil.escapeAttribute(image)}">
				</div>

				<div class="row-fluid">
					<a href="${htmlUtil.escapeAttribute(productURL)}">
						<strong>${htmlUtil.escape(name)}</strong>
					</a>

					<div class="commerce-price-section d-inline float-right">
						<@commerce_ui["price"]
							CPDefinitionId=cpDefinition.getCPDefinitionId()
							CPInstanceId=curCommerceOrderItem.getCPInstanceId()
							showDiscount=false
						/>
					</div>
				</div>
			</div>

			<#assign count = count + 1 />

			<#if count gte 2>
				</div>

				<div class="row">

				<#assign count = 0 />
			</#if>
		</#list>
	</div>
</#if>