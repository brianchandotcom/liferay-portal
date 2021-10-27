<#assign
	image = cpCatalogEntry.getDefaultImageFileUrl()

	name = cpCatalogEntry.getName()

	description = cpCatalogEntry.getDescription()

	commerceContext = request.getAttribute("COMMERCE_CONTEXT")

	commerceChannelId = commerceContext.getCommerceChannelId()

	commerceAccount = commerceContext.getCommerceAccount()

	commerceAccountId = commerceAccount.getCommerceAccountId()

	itemID = cpCatalogEntry.CPDefinitionId

	cpSkus = cpCatalogEntry.getCPSkus()

	cpSku = cpSkus?first

	cpInstanceId = cpSku.getCPInstanceId()

	group_id = commerceContext.getCommerceChannelGroupId()
/>

<div class="d-flex mb-6">
	<div class="align-content-center flex-fill">
		<a class="btn btn-monospaced btn-sm" href="javascript:history.back()">
			<@liferay_aui.icon
				image="angle-left"
				markupView="lexicon"
				value="clear"
			/>
		</a>

		<h1 class="d-inline h3 ml-2 align-middle">${htmlUtil.escape(name)}</h1>
	</div>

	<div>
		<a class="btn btn-primary" href="javascript:openItem(${cpInstanceId},${commerceChannelId},${commerceAccountId},${group_id})">
			Start with this starterkit
		</a>
	</div>
</div>

<div class="container-fluid container-fluid-max-lg d-flex justify-content-center">
	<img alt="" class="img-fluid" src="${htmlUtil.escapeAttribute(image)}">
</div>

<div class="container-fluid container-fluid-max-lg d-flex justify-content-center">
	<@liferay_commerce_ui["gallery"]
		CPDefinitionId=cpCatalogEntry.getCPDefinitionId()
		namespace="${renderResponse.getNamespace()}"
	/>
</div>

<div class="container-fluid container-fluid-max-lg mt-5 overflow-hidden">
	${description}
</div>