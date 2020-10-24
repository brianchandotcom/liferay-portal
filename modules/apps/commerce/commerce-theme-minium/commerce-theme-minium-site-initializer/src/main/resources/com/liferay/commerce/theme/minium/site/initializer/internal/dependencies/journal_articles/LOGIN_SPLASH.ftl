<#if (splashImage.getData())?? && splashImage.getData() != "">
	<img alt="${htmlUtil.escapeAttribute(splashImage.getAttribute("alt"))}" data-fileentryid="${htmlUtil.escapeAttribute(splashImage.getAttribute("fileEntryId"))}" src="${htmlUtil.escapeAttribute(splashImage.getData())}" />
</#if>