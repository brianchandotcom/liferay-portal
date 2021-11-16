<#if entries?has_content>
	<ul class="tag-items tag-list">
		<#assign
			scopeGroupId = getterUtil.getLong(scopeGroupId, themeDisplay.getScopeGroupId())
			classNameId = getterUtil.getLong(classNameId, 0)
			scopedAssetCounts = assetTagsNavigationDisplayContext.getScopedAssetCounts()

			maxCount = 1
			minCount = 1
		/>

		<#list entries as entry>
			<#if scopedAssetCounts??>
				<#assign assetCount = scopedAssetCounts[entry.getName()] />
			<#else>
				<#assign assetCount = entry.getAssetCount() />
			</#if>

			<#if (assetCount > 0) || getterUtil.getBoolean(showZeroAssetCount)>
				<#assign
					maxCount = liferay.max(maxCount, assetCount)
					minCount = liferay.min(minCount, assetCount)
				/>
			</#if>
		</#list>

		<#assign multiplier = 1 />

		<#if maxCount != minCount>
			<#assign multiplier = 3 / (maxCount - minCount) />
		</#if>

		<#list entries as entry>
			<#if scopedAssetCounts??>
				<#assign assetCount = scopedAssetCounts[entry.getName()] />
			<#else>
				<#assign assetCount = entry.getAssetCount() />
			</#if>

			<#if (assetCount > 0) || getterUtil.getBoolean(showZeroAssetCount)>
				<li class="taglib-asset-tags-summary">
					<#assign popularity = (maxCount - (maxCount - (assetCount - minCount))) * multiplier />

					<#if popularity < 1>
						<#assign displayType = "success" />
					<#elseif (popularity >= 1) && (popularity < 2)>
						<#assign displayType = "warning" />
					<#else>
						<#assign displayType = "danger" />
					</#if>

					<#assign tagURL = renderResponse.createRenderURL() />

					${tagURL.setParameter("resetCur", "true")}
					${tagURL.setParameter("tag", entry.getName())}

					<a class="label label-${displayType} tag" href="${tagURL}">
						<span class="label-item label-item-expand">${entry.getName()}</span>

						<#if assetCount?? && getterUtil.getBoolean(showAssetCount)>
							<span class="label-item label-item-after tag-asset-count">(${assetCount})</span>
						</#if>
					</a>
				</li>
			</#if>
		</#list>
	</ul>

	<br style="clear: both;" />
</#if>