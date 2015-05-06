<#assign aui = taglibLiferayHash["/WEB-INF/tld/aui.tld"] />

<#if entries?has_content>
	<@aui.layout>
		<#list entries as entry>
			<@aui.column columnWidth=25>
				<div class="results-header">
					<h3>
						${entry.getUnambiguousTitle(entries, themeDisplay.getSiteGroupId(), themeDisplay.getLocale())}
					</h3>
				</div>

				<#assign categories = entry.getCategories()>

				<@displayCategories categories=categories />
			</@aui.column>
		</#list>
	</@aui.layout>
</#if>

<#macro displayCategories
	categories
>
	<#if categories?has_content>
		<ul class="categories">
			<#list categories as category>
				<li>
					<#assign categoryURL = renderResponse.createRenderURL()>

					${categoryURL.setParameter("resetCur", "true")}
					${categoryURL.setParameter("categoryId", category.getCategoryId()?string)}

					<a href="${categoryURL}">${category.getName()}</a>

					<#if serviceLocator??>
						<#assign assetCategoryService = serviceLocator.findService("com.liferay.portlet.asset.service.AssetCategoryService")>

						<#assign childCategories = assetCategoryService.getChildCategories(category.getCategoryId())>

						<@displayCategories categories=childCategories />
					</#if>
				</li>
			</#list>
		</ul>
	</#if>
</#macro>