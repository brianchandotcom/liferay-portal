<#if entries?has_content>
	<ul class="tag-items tag-list">
		<#assign assetTagService = serviceLocator.findService("com.liferay.portlet.asset.service.AssetTagService")>

		<#assign scopeGroupId = getterUtil.getLong(scopeGroupId, themeDisplay.getScopeGroupId()) />
		<#assign classNameId = getterUtil.getLong(classNameId, 0) />

		<#assign maxCount = 1 />
		<#assign minCount = 1 />

		<#list entries as entry>
			<#if (classNameId > 0)>
				<#assign count = assetTagService.getTagsCount(scopeGroupId, classNameId, entry.getName()) />
			<#else>
				<#assign count = assetTagService.getTagsCount(scopeGroupId, entry.getName()) />
			</#if>

			<#assign maxCount = liferay.max(maxCount, count) />
			<#assign minCount = liferay.min(minCount, count) />
		</#list>

		<#assign multiplier = 1 />

		<#if maxCount != minCount>
			<#assign multiplier = 3 / (maxCount - minCount) />
		</#if>

		<#list entries as entry>
			<li class="taglib-asset-tags-summary">
				<#assign count = 0 />

				<#if (classNameId > 0)>
					<#assign count = assetTagService.getTagsCount(scopeGroupId, classNameId, entry.getName()) />
				<#else>
					<#assign count = assetTagService.getTagsCount(scopeGroupId, entry.getName()) />
				</#if>

				<#assign popularity = (maxCount - (maxCount - (count - minCount))) * multiplier />

				<#if popularity < 1>
					<#assign color = "green" />
				<#elseif (popularity >= 1) && (popularity < 2)>
					<#assign color = "orange" />
				<#else>
					<#assign color = "red" />
				</#if>

				<#assign tagURL = renderResponse.createRenderURL() />

				${tagURL.setParameter("resetCur", "true")}
				${tagURL.setParameter("tag", entry.getName())}

				<a class ="tag" style="color: ${color};" href="${tagURL}">
					${entry.getName()}

					<#if (showAssetCount == "true")>
						<span class="tag-asset-count">(${count})</span>
					</#if>
				</a>
			</li>
		</#list>
	</ul>

	<br style="clear: both;" />
</#if>