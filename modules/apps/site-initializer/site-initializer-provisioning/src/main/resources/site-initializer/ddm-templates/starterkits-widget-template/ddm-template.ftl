<style>
	.provisioning-link {
		bottom: 0;
		left: 0;
		right: 0;
		top: 0;
		display: none;
	}

	.provisioning-item:hover .provisioning-link,
	.provisioning-item:focus .provisioning-link,
	.provisioning-item.active .provisioning-link {
		background-color: rgba(255, 255, 255, 0.8);
		display: flex;
	}
</style>

<#assign count = 0
/>

<#if entries?has_content>
	<#assign
		commerceContext = renderRequest.getAttribute("COMMERCE_CONTEXT")

		commerceChannelId = commerceContext.getCommerceChannelId()

		commerceAccount = commerceContext.getCommerceAccount()

		commerceAccountId = commerceAccount.getCommerceAccountId()
	/>

	<div class="container">
		<div class="provisioning-list row">
			<#list entries as curCPCatalogEntry>
				<#assign
					image = curCPCatalogEntry.getDefaultImageFileUrl()

					friendlyURL = cpContentHelper.getFriendlyURL(curCPCatalogEntry, themeDisplay)

					name = curCPCatalogEntry.getName()

					itemID = curCPCatalogEntry.CPDefinitionId

					cpSkus = curCPCatalogEntry.getCPSkus()

					cpSku = cpSkus?first

					cpInstanceId = cpSku.getCPInstanceId()

					group_id = commerceContext.getCommerceChannelGroupId()

					publicFriendlyURL = themeDisplay.getPathFriendlyURLPublic() + themeDisplay.getSiteGroup().getFriendlyURL()
				/>

				<div class="col-md-4 mb-5">
					<div class="mb-3 position-relative provisioning-item" tabindex="0">
						<div class="aspect-ratio aspect-ratio-16-to-9">
							<img alt="thumbnail" class="aspect-ratio-item aspect-ratio-item-center-middle aspect-ratio-item-fluid" src="${htmlUtil.escapeAttribute(image)}">
						</div>

						<div class="component-link">
							<span class="align-items-center flex-column justify-content-center position-absolute provisioning-link">
								<div class="mb-1">
									<a class="btn btn-secondary" href="${htmlUtil.escapeHREF(friendlyURL)}">
										Details
									</a>
								</div>

								<div class="mt-1">
									<a class="btn btn-primary" href="javascript:openItem(${cpInstanceId},${commerceChannelId},${commerceAccountId},${group_id},'${publicFriendlyURL}/my-site')">
										Select
									</a>
								</div>
							</span>
						</div>
					</div>

					<strong>${htmlUtil.escape(name)}</strong>
				</div>

				<#assign cosunt = count + 1 />

				<#if count gte 3>
					</div>

					<div class="row">

					<#assign count = 0 />
				</#if>
			</#list>
		</div>
	</div>
<#else>
	<div class="alert alert-info">
		<@liferay_ui["message"] key="no-products-were-found" />
	</div>
</#if>