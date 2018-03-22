<#if entries?has_content>
	<div class="navbar" id="<@portlet.namespace />cpAssetCategoryNavigation">
		<ul class="nav navbar-blank navbar-nav navbar-site">
			<#list entries as curAssetCategory>
				<li class="lfr-nav-item nav-item">
					<a class="dropdown-toggle nav-link" href="#"><span>${curAssetCategory.getTitle(locale)}</span></a>

					<ul class="child-menu dropdown-menu" role="menu">
						<#assign assetCategoriesLevel1 = cpAssetCategoriesNavigationDisplayContext.getChildAssetCategories(curAssetCategory.getCategoryId()) />

						<#list assetCategoriesLevel1 as curAssetCategoryLevel1>
							<li class="child-menu-level-1">
								<ul class="link-list list-unstyled">
									<li class="dropdown-header">${curAssetCategoryLevel1.getTitle(locale)}</li>
									<li class="child-menu-level-2">
										<#assign assetCategoriesLevel2 = cpAssetCategoriesNavigationDisplayContext.getChildAssetCategories(curAssetCategoryLevel1.getCategoryId()) />

										<#list assetCategoriesLevel2 as curAssetCategoryLevel2>
											<li>
												<a class="dropdown-item" href="${cpAssetCategoriesNavigationDisplayContext.getFriendlyURL(curAssetCategoryLevel2.getCategoryId(), themeDisplay)}" role="menuitem">${curAssetCategoryLevel2.getTitle(locale)}</a>
											</li>
										</#list>
									</li>
								</ul>
							</li>
						</#list>

						<div class="category-description">
							<#assign imgURL = cpAssetCategoriesNavigationDisplayContext.getDefaultImageSrc(curAssetCategory.getCategoryId(), themeDisplay) />

							<#if imgURL??>
								<img src="${imgURL}" />
							</#if>

							<span class="dropdown-header">${curAssetCategory.getTitle(locale)}</span>
							<p class="description">${curAssetCategory.getDescription(locale)}</p>
						</div>
					</ul>
				</li>
			</#list>
		</ul>
	</div>

	<@liferay_aui.script use="liferay-navigation-interaction">
		var navigation = A.one('#<@portlet.namespace />cpAssetCategoryNavigation');

		Liferay.Data.NAV_INTERACTION_LIST_SELECTOR = '.navbar-site';
		Liferay.Data.NAV_LIST_SELECTOR = '.navbar-site';

		if (navigation) {
			navigation.plug(Liferay.NavigationInteraction);
		}
	</@>
</#if>