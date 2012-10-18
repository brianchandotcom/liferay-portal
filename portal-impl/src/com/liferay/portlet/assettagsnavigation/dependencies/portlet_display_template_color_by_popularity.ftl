<#assign aui = taglibLiferayHash["/WEB-INF/tld/aui.tld"] />
<#assign liferay_portlet = taglibLiferayHash["/WEB-INF/tld/liferay-portlet.tld"] />
<#assign liferay_ui = taglibLiferayHash["/WEB-INF/tld/liferay-ui.tld"] />

<#assign asset_tag_service = serviceLocator.findService("com.liferay.portlet.asset.service.AssetTagService")>

<#if entries?has_content>
	<#assign class_name_id = getterUtil.getLong(classNameId, 0) />
	<#assign group_id = themeDisplay.getScopeGroupId() />

	<#assign max_count = 1 />
	<#assign min_count = 1 />
	<#assign multiplier = 1 />

	<#list entries as entry>
		<#if (class_name_id > 0)>
			<#assign count = asset_tag_service.getTagsCount(group_id, class_name_id, entry.getName()) />
		<#else>
			<#assign count = asset_tag_service.getTagsCount(group_id, entry.getName()) />
		</#if>

		<#assign max_count = max(max_count, count) />
		<#assign min_count = max(min_count, count) />
	</#list>

	<#if max_count != min_count>
		<#assign multiplier = 3 / (max_count - min_count) />
	</#if>

	<#assign count = 0 />

	<ul class="tag-items tag-list">
		<#list entries as entry>
			<#assign tagURL = renderResponse.createRenderURL() />

			${tagURL.setParameter("resetCur", "true")}
			${tagURL.setParameter("tag", entry.getName())}

			<#if (class_name_id > 0)>
				<#assign count = asset_tag_service.getTagsCount(group_id, class_name_id, entry.getName()) />
			<#else>
				<#assign count = asset_tag_service.getTagsCount(group_id, entry.getName()) />
			</#if>

			<#assign popularity = (1 + ((max_count - (max_count - (count - min_count))) * multiplier)) />

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
						${count}
					</#if>
				</a>
			</li>
		</#list>
	</ul>

	<br style="clear: both;">
</#if>

<#function max x y>
	<#if (x<y)><#return y><#else><#return x></#if>
</#function>