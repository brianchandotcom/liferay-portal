<style>
	.liferay-online-link {
		bottom: 0;
		left: 0;
		right: 0;
		top: 0;
		display: none;
	}

	.liferay-online-item:hover .liferay-online-link,
	.liferay-online-item:focus .liferay-online-link,
	.liferay-online-item.active .liferay-online-link {
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
		<div class="liferay-online-list row">
			<#list entries as curCPCatalogEntry>
				<#assign
					image = cpContentHelper.getDefaultImageFileURL(commerceAccountId, curCPCatalogEntry.getCPDefinitionId())

					friendlyURL = cpContentHelper.getFriendlyURL(curCPCatalogEntry, themeDisplay)

					name = curCPCatalogEntry.getName()

					itemID = curCPCatalogEntry.CPDefinitionId

					cpSkus = curCPCatalogEntry.getCPSkus()

					cpSku = cpSkus?first

					cpInstanceId = cpSku.getCPInstanceId()

					group_id = commerceContext.getCommerceChannelGroupId()

					publicFriendlyURL = themeDisplay.getPortalURL() + themeDisplay.getPathFriendlyURLPublic() + themeDisplay.getSiteGroup().getFriendlyURL()

					user = themeDisplay.getUser()
				/>

				<div class="col-md-4 mb-5">
					<div class="liferay-online-item mb-3 position-relative" tabindex="0">
						<div class="aspect-ratio aspect-ratio-16-to-9">
							<img alt="thumbnail" class="aspect-ratio-item aspect-ratio-item-center-middle aspect-ratio-item-fluid" src="${htmlUtil.escapeAttribute(image)}">
						</div>

						<div class="component-link">
							<span class="align-items-center flex-column justify-content-center liferay-online-link position-absolute">
								<div class="mb-1">
									<a class="btn btn-secondary" href="${htmlUtil.escapeHREF(friendlyURL)}">
										Details
									</a>
								</div>

								<div class="mt-1">
									<a class="btn btn-primary" href="javascript:openItem('${user.getEmailAddress()}','${user.getFirstName()}','${user.getLastName()}',${commerceChannelId},${commerceAccountId},${cpInstanceId},${group_id},'${publicFriendlyURL}/my-site', '${cpSku.getSku()}')">
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