<#assign
	image = cpCatalogEntry.getDefaultImageFileUrl()
	friendlyURL = cpContentHelper.getFriendlyURL(cpCatalogEntry, themeDisplay)
	name = cpCatalogEntry.getName()
/>

<div>
	<img src="${htmlUtil.escapeAttribute(image)}">
</div>

<div>
	<a href="${htmlUtil.escapeHREF(friendlyURL)}">
		<strong>${htmlUtil.escape(name)}</strong>
	</a>
</div>