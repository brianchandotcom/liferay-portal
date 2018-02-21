<#assign count = 0 />

<#if entries?has_content>
	<div class="row">
		<#assign subTotal = commerceCartContentMiniDisplayContext.getCommerceOrderSubtotal() />

		<div class="col-md-12">
			<strong>Total: ${subTotal}</strong>
		</div>

		<#list entries as curCommerceOrderItem>
			<#assign
				cpDefinition = curCommerceOrderItem.getCPDefinition()

				image = cpDefinition.getDefaultImageThumbnailSrc(themeDisplay)

				productURL = commerceCartContentMiniDisplayContext.getCPDefinitionURL(cpDefinition.getCPDefinitionId(), themeDisplay)

				title = cpDefinition.getTitle(locale)

				price = commerceCartContentMiniDisplayContext.getFormattedPrice(curCommerceOrderItem)
			/>

			<div class="col-md-6">
				<div class="row">
					<img src="${image}">
				</div>

				<div class="row">
					<a href="${productURL}">
						<strong>${title}</strong>
					</a>
				</div>

				<div class="row">
					${price}
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