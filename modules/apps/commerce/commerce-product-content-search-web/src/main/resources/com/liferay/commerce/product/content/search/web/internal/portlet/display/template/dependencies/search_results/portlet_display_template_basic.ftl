<#assign count = 0
/>

<#if entries?has_content>
	<div class="row">
		<#list entries as curCPCatalogEntry>
			<#assign
				image = curCPCatalogEntry.getDefaultImageFileUrl()

				friendlyURL = cpContentHelper.getFriendlyURL(curCPCatalogEntry, themeDisplay)

				name = curCPCatalogEntry.getName()
			/>

			<div class="col-md-4">
				<div>
					<img src="${htmlUtil.escapeAttribute(image)}">
				</div>

				<div>
					<a href="${htmlUtil.escapeHREF(friendlyURL)}">
						<strong>${htmlUtil.escape(name)}</strong>
					</a>
				</div>
			</div>

			<#assign count = count + 1 />

			<#if count gte 3>
				</div>

				<div class="row">

				<#assign count = 0 />
			</#if>
		</#list>
	</div>
<#else>
	<div class="alert alert-info">
		<@liferay_ui["message"] key="no-products-were-found" />
	</div>
</#if>