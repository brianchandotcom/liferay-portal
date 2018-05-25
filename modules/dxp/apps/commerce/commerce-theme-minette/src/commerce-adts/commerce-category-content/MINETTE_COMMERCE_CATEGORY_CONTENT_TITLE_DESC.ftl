<#if cpCategoryContentDisplayContext.getAssetCategory()??>
	<#assign
		assetCategory = cpCategoryContentDisplayContext.getAssetCategory()
	/>

	<div class="category-detail">
		<h1 class="category-title">${assetCategory.getTitle(themeDisplay.getLanguageId())}</h1>
		<p class="category-description">${assetCategory.getDescription(themeDisplay.getLanguageId())}</p>
	</div>
</#if>