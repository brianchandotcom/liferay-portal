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
		<img src="${image}">
	</div>

	<div class="container-fluid container-fluid-max-xl">
		<h1>${title}</h1>

		<p>${description}</p>
	</div>
</div>