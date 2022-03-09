<#assign hasCategories = false />

<#if entries?has_content>
	<@liferay_aui.row>
		<#list entries as entry>
			<#assign categories = entry.getCategories() />

			<#if categories?has_content>
				<#assign hasCategories = true />

				<@liferay_aui.col width=25>
					<div class="results-header">
						<h3>
							${entry.getUnambiguousTitle(entries, themeDisplay.getSiteGroupId(), themeDisplay.getLocale())}
						</h3>
					</div>

					<@displayCategories categories=categories />
				</@liferay_aui.col>
			</#if>
		</#list>

		<#if !hasCategories>
			${renderRequest.setAttribute("PORTLET_CONFIGURATOR_VISIBILITY", true)}

			<div class="alert alert-info w-100">
				<@liferay_ui.message key="there-are-no-categories" />
			</div>
		</#if>
	</@liferay_aui.row>
</#if>

<#macro displayCategories
	categories
>
	<#if categories?has_content>
		<ul class="categories">
			<#list categories as category>
				<li>
					<#assign categoryURL = renderResponse.createRenderURL() />

					${categoryURL.setParameter("resetCur", "true")}
					${categoryURL.setParameter("categoryId", category.getCategoryId()?string)}

					<a href="${categoryURL}">${category.getName()}</a>

					<#if serviceLocator??>
						<#assign
							assetCategoryService = serviceLocator.findService("com.liferay.asset.kernel.service.AssetCategoryService")

							childCategories = assetCategoryService.getChildCategories(category.getCategoryId())
						/>

						<@displayCategories categories=childCategories />
					</#if>
				</li>
			</#list>
		</ul>
	</#if>
</#macro>