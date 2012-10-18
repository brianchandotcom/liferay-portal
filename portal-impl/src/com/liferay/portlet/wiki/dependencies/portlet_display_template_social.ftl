<#assign aui = taglibLiferayHash["/WEB-INF/tld/aui.tld"] />
<#assign liferay_portlet = taglibLiferayHash["/WEB-INF/tld/liferay-portlet.tld"] />
<#assign liferay_ui = taglibLiferayHash["/WEB-INF/tld/liferay-ui.tld"] />

<#assign asset_entry_local_service = serviceLocator.findService("com.liferay.portlet.asset.service.AssetEntryLocalService") />

<#assign wiki_page_class_name = "com.liferay.portlet.wiki.model.WikiPage" />

<#assign asset_entry = asset_entry_local_service.getEntry(wiki_page_class_name, entry.getResourcePrimKey()) />

<#assign asset_renderer = asset_entry.getAssetRenderer() />

<div class="taglib-header">
	<h1 class="header-title">${entry.getTitle()}</h1>
</div>

<div style="float:right">
	<@edit_icon />

	<@details_icon />

	<@print_icon />
</div>

<div class="wiki-body">
	<div class="wiki-info">
		<span class="stats">${asset_entry.getViewCount()} <@liferay.language key="views" /></span> |
		<span class="date"><@liferay.language key="last-modified" /> ${dateUtil.getDate(entry.getModifiedDate(), "dd MMM yyyy - HH:mm:ss", locale)}</span>
		<span class="author"><@liferay.language key="by" /> ${htmlUtil.escape(portalUtil.getUserName(entry.getUserId(), entry.getUserName()))}</span>
	</div>

	<div class="wiki-content">
		<@liferay_ui["social-bookmarks"]
			displayStyle="normal"
			target="_blank"
			title=entry.getTitle()
			url=viewURL
		/>

		${formattedContent}
	</div>

	<div class="page-actions">
		<div class="article-actions">
			<@add_child_icon />
			<@attatchments_icon />
		</div>
	</div>

	 <br />

	<@ratings entry=entry css_class="page-ratings"/>

	<@related_assets />
</div>

<div class="page-categorization">
	<div class="page-categories">
		<#assign categorized_pages_url = renderResponse.createRenderURL() />

		${categorized_pages_url.setParameter("struts_action", "/wiki/view_categorized_pages")}
		${categorized_pages_url.setParameter("nodeId", entry.getNodeId()?string)}

		<@liferay_ui["asset-categories-summary"]
			className=wiki_page_class_name
			classPK=entry.getResourcePrimKey()
			portletURL=categorized_pages_url
		/>
	</div>

	<div class="page-tags">
		<#assign tagged_pages_url = renderResponse.createRenderURL() />

		${tagged_pages_url.setParameter("struts_action", "/wiki/view_tagged_pages")}
		${tagged_pages_url.setParameter("nodeId", entry.getNodeId()?string)}

		<@liferay_ui["asset-tags-summary"]
			className=wiki_page_class_name
			classPK=entry.getResourcePrimKey()
			portletURL=tagged_pages_url
		/>
	</div>
</div>

<#assign child_pages = entry.getChildPages() />

<#if (child_pages?has_content)>
	<div class="child-pages">
		<h2><@liferay.language key="children-pages" /></h2>

		<table class="taglib-search-iterator">
			<tr class="portlet-section-header results-header">
				<th><@liferay.language key="page" /></th>
				<th><@liferay.language key="last-modified" /></th>
				<th><@liferay.language key="ratings" /></th>
				<th><@liferay.language key="views" /></th>
			</tr>

			<#list child_pages as child_page>
				<tr class="results-row">
					<#assign child_asset_entry = asset_entry_local_service.getEntry(wiki_page_class_name, child_page.getResourcePrimKey()) />
					<#assign child_page_view_url = renderResponse.createRenderURL() />

					${child_page_view_url.setParameter("struts_action", "/wiki/view")}
					${child_page_view_url.setParameter("nodeName", child_page.getNode().getName())}
					${child_page_view_url.setParameter("title", child_page.getTitle())}

					<td><a href="${child_page_view_url}">${child_page.getTitle()}</a></td>
					<td><a href="${child_page_view_url}">${dateUtil.getDate(child_page.getModifiedDate(),"dd MMM yyyy - HH:mm:ss", locale)} <@liferay.language key="by" /> ${htmlUtil.escape(portalUtil.getUserName(child_page.getUserId(), child_page.getUserName()))}</a></td>
					<td>
						<@ratings entry=child_page css_class="child-ratings"/>
					</td>
					<td><span class="stats">${child_asset_entry.getViewCount()}</span></td>
				</tr>
			</#list>
		</table>
	</div>
</#if>

<@discussion />

<#macro add_child_icon>
	<#if asset_renderer.hasEditPermission(themeDisplay.getPermissionChecker())>
		<#assign redirect_url = portalUtil.getCurrentURL(request) />

		<#assign add_page_url = renderResponse.createRenderURL() />

		${add_page_url.setParameter("struts_action", "/wiki/edit_page")}
		${add_page_url.setParameter("redirect", redirect_url)}
		${add_page_url.setParameter("nodeId", entry.getNodeId()?string)}
		${add_page_url.setParameter("title", "")}
		${add_page_url.setParameter("editTitle", "1")}
		${add_page_url.setParameter("parentTitle", entry.getTitle())}

		<@liferay_ui["icon"]
			image="add_article"
			label=true
			message="add-child-page"
			url=add_page_url?string
		/>
	</#if>
</#macro>

<#macro attatchments_icon>
	<#assign attachments = entry.getAttachmentsFiles() />

	<#assign view_attachments_url = renderResponse.createRenderURL() />

	${view_attachments_url.setParameter("struts_action", "/wiki/view_page_attachments") }

	<@liferay_ui["icon"]
		image="clip"
		label=true
		message='${attachments?size + languageUtil.get(locale, "attachments")}'
		url=view_attachments_url?string
	/>
</#macro>

<#macro details_icon>
	<#assign redirect_url = portalUtil.getCurrentURL(request) />

	<#assign view_page_details_url = renderResponse.createRenderURL() />

	${view_page_details_url.setParameter("struts_action", "/wiki/view_page_details")}
	${view_page_details_url.setParameter("redirect", redirect_url?string)}

	<@liferay_ui["icon"]
		image="history"
		message="details"
		url=view_page_details_url?string
	/>
</#macro>

<#macro discussion>
	<#if validator.isNotNull(asset_renderer.getDiscussionPath()) && enableComments == "true">
		<br />

		<#assign discussion_url = renderResponse.createActionURL() />

		${discussion_url.setParameter("struts_action", "/wiki/" + asset_renderer.getDiscussionPath())}

		<@liferay_ui["discussion"]
			className=wiki_page_class_name
			classPK=entry.getResourcePrimKey()
			formAction=discussion_url?string
			formName="fm2"
			ratingsEnabled=enableCommentRatings == "true"
			redirect=portalUtil.getCurrentURL(request)
			subject=asset_renderer.getTitle(locale)
			userId=asset_renderer.getUserId()
		/>
	</#if>
</#macro>

<#macro edit_icon>
	<#if asset_renderer.hasEditPermission(themeDisplay.getPermissionChecker())>
		<#assign redirect_url = portalUtil.getCurrentURL(request) />

		<#assign edit_page_url = renderResponse.createRenderURL() />

		${edit_page_url.setParameter("struts_action", "/wiki/edit_page")}
		${edit_page_url.setParameter("redirect", redirect_url)}
		${edit_page_url.setParameter("nodeId", entry.getNodeId()?string)}
		${edit_page_url.setParameter("title", entry.getTitle())}

		<@liferay_ui["icon"]
			image="edit"
			message=entry.getTitle()
			url=edit_page_url?string
		/>
	</#if>
</#macro>

<#macro print_icon>
	<#assign print_portlet_url = renderResponse.createRenderURL() />

	${print_portlet_url.setWindowState("pop_up")}
	${print_portlet_url.setParameter("viewMode", "print")}

	<#assign format_params = ["aui-helper-hidden-accessible", htmlUtil.escape(asset_renderer.getTitle(locale))] />

	<#assign title = languageUtil.format(locale, "print-x-x", format_params) />

	<#assign taglib_print_url = "javascript:Liferay.Util.openWindow({dialog: {width: 960}, id:'" + renderResponse.getNamespace() + "printAsset', title: '" + title + "', uri:'" + htmlUtil.escapeURL(print_portlet_url.toString()) + "'});" />

	<@liferay_ui["icon"]
		image="print"
		message="print"
		url=taglib_print_url
	/>
</#macro>

<#macro ratings entry css_class>
	<#if enablePageRatings == "true">
		<div class="${css_class}">
			<@liferay_ui["ratings"]
				className=wiki_page_class_name
				classPK=entry.getResourcePrimKey()
			/>
		</div>
	</#if>
</#macro>

<#macro related_assets>
	<#if asset_entry?? && enableRelatedAssets == "true">
		<@liferay_ui["asset-links"]
			assetEntryId=asset_entry.getEntryId()
		/>
	</#if>
</#macro>