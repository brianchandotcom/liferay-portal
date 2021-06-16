<#if entries?has_content>
	<ul class="tag-items tag-list">
		<#assign
			scopeGroupId = getterUtil.getLong(scopeGroupId, themeDisplay.getScopeGroupId())
			classNameId = getterUtil.getLong(classNameId, 0)

			maxCount = 1
			minCount = 1
		/>

		<#list entries as entry>
			<#assign
				maxCount = liferay.max(maxCount, entry.getAssetCount())
				minCount = liferay.min(minCount, entry.getAssetCount())
			/>
		</#list>

		<#assign multiplier = 1 />

		<#if maxCount != minCount>
			<#assign multiplier = 3 / (maxCount - minCount) />
		</#if>

		<#list entries as entry>
			<li class="taglib-asset-tags-summary">
				<#assign popularity = (maxCount - (maxCount - (entry.getAssetCount() - minCount))) * multiplier />

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

					<#if entry.getAssetCount()?? && getterUtil.getBoolean(showAssetCount)>
						<span class="label-item label-item-after tag-asset-count">(${entry.getAssetCount()})</span>
					</#if>
				</a>
			</li>
		</#list>
	</ul>

	<br style="clear: both;" />
</#if>