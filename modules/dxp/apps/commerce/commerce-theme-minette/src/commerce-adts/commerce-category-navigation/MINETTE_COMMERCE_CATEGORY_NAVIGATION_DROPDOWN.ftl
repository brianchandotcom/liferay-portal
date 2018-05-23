<#if entries?has_content>
	<#assign hasSubcategories = false />

	<div class="autofit-row" id="<@portlet.namespace />cpAssetCategoryNavigation">
		<div class="autofit-col autofit-col-expand" style="min-width: 200px;padding:3rem;">
			<ul class="nav nav-stacked">
				<#list entries as curAssetCategory>
					<li class="nav-item">
						<#assign nextAssetCategories = cpAssetCategoriesNavigationDisplayContext.getChildAssetCategories(curAssetCategory.getCategoryId()) />

						<#if nextAssetCategories?size != 0>
							<#assign hasSubcategories = true />
						</#if>

						<a class="nav-link" href="${cpAssetCategoriesNavigationDisplayContext.getFriendlyURL(curAssetCategory.getCategoryId(), themeDisplay)}" id="category_${curAssetCategory.getCategoryId()}">${curAssetCategory.getName()}</a>
					</li>
				</#list>
			</ul>
		</div>

		<#if hasSubcategories>
			<#assign hasSubcategories = false />

			<div class="autofit-col autofit-col-expand" style="min-width: 200px;padding:3rem;">
				<#list entries as curAssetCategory>
					<#assign nextAssetCategories = cpAssetCategoriesNavigationDisplayContext.getChildAssetCategories(curAssetCategory.getCategoryId()) />

					<ul class="d-none nav nav-stacked" id="category_${curAssetCategory.getCategoryId()}_subcategories" style="border-left:1px solid #DDD;">
						<li class="nav-item"><a href="${cpAssetCategoriesNavigationDisplayContext.getFriendlyURL(curAssetCategory.getCategoryId(), themeDisplay)}">${curAssetCategory.getName()}</a></li>

						<#list nextAssetCategories as curAssetCategory>
							<#assign nextButOneAssetCategories = cpAssetCategoriesNavigationDisplayContext.getChildAssetCategories(curAssetCategory.getCategoryId()) />

							<#if nextButOneAssetCategories?size != 0>
								<#assign hasSubcategories = true />
							</#if>

							<li class="nav-item">
								<a class="nav-link" id="category_${curAssetCategory.getCategoryId()}" href="${cpAssetCategoriesNavigationDisplayContext.getFriendlyURL(curAssetCategory.getCategoryId(), themeDisplay)}">${curAssetCategory.getName()}</a>
							</li>
						</#list>
					</ul>
				</#list>
			</div>
		</#if>

		<#if hasSubcategories>
			<#assign hasSubcategories = false />

			<div class="autofit-col autofit-col-expand" style="min-width: 200px;padding:3rem;">
				<#list entries as curAssetCategory>
					<#assign nextAssetCategories = cpAssetCategoriesNavigationDisplayContext.getChildAssetCategories(curAssetCategory.getCategoryId()) />

					<#list nextAssetCategories as curAssetCategory>
						<#assign nextButOneAssetCategories = cpAssetCategoriesNavigationDisplayContext.getChildAssetCategories(curAssetCategory.getCategoryId()) />

						<ul class="d-none nav nav-stacked" id="category_${curAssetCategory.getCategoryId()}_subcategories">
							<li class="nav-item"><a href="${cpAssetCategoriesNavigationDisplayContext.getFriendlyURL(curAssetCategory.getCategoryId(), themeDisplay)}">${curAssetCategory.getName()}</a></li>

							<#list nextButOneAssetCategories as curAssetCategory>
								<#assign nextButTwoAssetCategories = cpAssetCategoriesNavigationDisplayContext.getChildAssetCategories(curAssetCategory.getCategoryId()) />

								<#if nextButTwoAssetCategories?size != 0>
									<#assign hasSubcategories = true />
								</#if>

								<li class="nav-item">
									<a class="nav-link" id="category_${curAssetCategory.getCategoryId()}" href="${cpAssetCategoriesNavigationDisplayContext.getFriendlyURL(curAssetCategory.getCategoryId(), themeDisplay)}">${curAssetCategory.getName()}</a>
								</li>
							</#list>
						</ul>
					</#list>
				</#list>
			</div>
		</#if>

		<div class="autofit-col" style="min-width: 450px;padding:3rem;border-left: 1px solid #DDD;">
			<h2>FEATURED</h2>
		</div>
	</div>
</#if>