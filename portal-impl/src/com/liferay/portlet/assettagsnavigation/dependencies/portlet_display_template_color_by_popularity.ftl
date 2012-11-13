<#assign assetTagService = serviceLocator.findService("com.liferay.portlet.asset.service.AssetTagService")>

<#if entries?has_content>
	<#assign classNameId = getterUtil.getLong(classNameId, 0) />
	<#assign groupId = themeDisplay.getScopeGroupId() />

	<#assign maxCount = 1 />
	<#assign minCount = 1 />
	<#assign multiplier = 1 />

	<#list entries as entry>
		<#if (classNameId > 0)>
			<#assign count = assetTagService.getTagsCount(groupId, classNameId, entry.getName()) />
		<#else>
			<#assign count = assetTagService.getTagsCount(groupId, entry.getName()) />
		</#if>

		<#assign maxCount = max(maxCount, count) />
		<#assign minCount = max(minCount, count) />
	</#list>

	<#if maxCount != minCount>
		<#assign multiplier = 3 / (maxCount - minCount) />
	</#if>

	<#assign count = 0 />

	<ul class="tag-items tag-list">
		<#list entries as entry>
			<#assign tagURL = renderResponse.createRenderURL() />

			${tagURL.setParameter("resetCur", "true")}
			${tagURL.setParameter("tag", entry.getName())}

			<#if (classNameId > 0)>
				<#assign count = assetTagService.getTagsCount(groupId, classNameId, entry.getName()) />
			<#else>
				<#assign count = assetTagService.getTagsCount(groupId, entry.getName()) />
			</#if>

			<#assign popularity = (1 + ((maxCount - (maxCount - (count - minCount))) * multiplier)) />

			<#if popularity < 1>
				<#assign color = "green" />
			<#elseif (popularity >= 1) && (popularity < 2)>
				<#assign color = "orange" />
			<#else>
				<#assign color = "red" />
			</#if>

			<li class="taglib-asset-tags-summary">
				<a class ="tag" style="color:${color}" href="${tagURL}">
					${entry.getName()}

					<#if (showAssetCount == "true")>
						<span class="tag-asset-count">(${count})</span>
					</#if>
				</a>
			</li>
		</#list>
	</ul>

	<br style="clear: both;">
</#if>

<#function max x y>
	<#if (x<y)>
		<#return y>
	<#else>
		<#return x>
	</#if>
</#function>