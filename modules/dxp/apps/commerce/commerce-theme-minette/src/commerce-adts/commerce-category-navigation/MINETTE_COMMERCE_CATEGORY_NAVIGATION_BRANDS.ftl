<#if cpAssetCategoriesNavigationDisplayContext.getAssetCategories()?has_content>
	<div class="row">
		<ul class="card-page commerce-categories-navigation-card-page commerce-simple-card">
			<#list cpAssetCategoriesNavigationDisplayContext.getAssetCategories() as curAssetCategory>
				<li class="card-page-item">
					<a class="card" href="${cpAssetCategoriesNavigationDisplayContext.getFriendlyURL(curAssetCategory.getCategoryId(), themeDisplay)}">
						<img class="card-image" src="http://localhost:8080/documents/38104/0/CATS-1815.jpg/5671e821-5fa8-2ee8-8b62-99361234f4ed?version=1.0&amp;t=1525461009855&amp;imageThumbnail=1">
						<#if cpAssetCategoriesNavigationDisplayContext.getDefaultImageSrc(curAssetCategory.getCategoryId(), themeDisplay)??>
							<img class="card-image" src="${cpAssetCategoriesNavigationDisplayContext.getDefaultImageSrc(curAssetCategory.getCategoryId(), themeDisplay)}">
						<#else>
							<span class="card-title">${curAssetCategory.name}</span>
						</#if>
					</a>
				</li>
			</#list>
		</ul>
	</div>
</#if>