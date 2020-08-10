<div class="wrapper" id="<@portlet.namespace />cpAssetCategoryNavigation">
	<#if entries?has_content>
		<#assign hasSubcategories = false />

		<#list entries as currentCategory>
			<#assign
				categoryId = currentCategory.getCategoryId()
				categoryName = currentCategory.getName()
				categoryHref = cpAssetCategoriesNavigationDisplayContext
				.getFriendlyURL(currentCategory.getCategoryId(), themeDisplay)
				subCategories = cpAssetCategoriesNavigationDisplayContext
				.getChildAssetCategories(currentCategory.getCategoryId())
			/>

<div class="speedwell-category-nav__category" id="category_${categoryId}">
<div class="category__title">
					<h3><a href="${categoryHref}">${categoryName}</h3>
				</div>

				<#if subCategories?has_content>
<div class="category__products">
						<ul>
							<#list subCategories as currentSubCategory>
								<#assign
									subcategoryId = currentSubCategory.getCategoryId()
									subcategoryName = currentSubCategory.getName()
									subcategoryHref = cpAssetCategoriesNavigationDisplayContext
									.getFriendlyURL(currentSubCategory.getCategoryId(), themeDisplay)
								/>

<li id="subcategory_${subcategoryId}">
									<a class="product-item" href="${subcategoryHref}">${subcategoryName}</a>
								</li>
							</#list>
						</ul>
					</div>
				</#if>
			</div>
		</#list>
	</#if>
</div>