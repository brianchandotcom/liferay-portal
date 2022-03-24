<div class="row">
	<#assign
		image = ''

		title = ''

		description = ''
	/>

	<#if cpCategoryContentDisplayContext.getDefaultImageSrc(themeDisplay)??>
		<#assign
			image = cpCategoryContentDisplayContext.getDefaultImageSrc(themeDisplay)
		/>
	</#if>

	<#if assetCategory??>
		<#assign
			title = assetCategory.getTitle(locale)

			description = assetCategory.getTitle(locale)
		/>
	</#if>

	<div>
		<img src="${htmlUtil.escapeAttribute(image)}">
	</div>

	<div class="container-fluid container-fluid-max-xl">
		<h1>${htmlUtil.escape(title)}</h1>

		<p>${htmlUtil.escape(description)}</p>
	</div>
</div>