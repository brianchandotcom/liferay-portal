<#setting number_format="computer">

<#assign aui = taglibLiferayHash["/WEB-INF/tld/aui.tld"] />
<#assign liferay_portlet = taglibLiferayHash["/WEB-INF/tld/liferay-portlet.tld"] />
<#assign liferay_ui = taglibLiferayHash["/WEB-INF/tld/liferay-ui.tld"] />

<#assign assetEntryLocalService = serviceLocator.findService("com.liferay.portlet.asset.service.AssetEntryLocalService") />
<#assign wikiPageClassName = "com.liferay.portlet.wiki.model.WikiPage" />
<#assign assetEntry = assetEntryLocalService.getEntry(wikiPageClassName, entry.getResourcePrimKey()) />
<#assign assetRenderer = assetEntry.getAssetRenderer() />

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
		<span class="stats">${assetEntry.getViewCount()} <@liferay.language key="views" /></span> |
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

	<@ratings entry=entry cssClass="page-ratings"/>
	<@related_assets />
</div>

<div class="page-categorization">
	<div class="page-categories">
		<#assign categorizedPagesURL = renderResponse.createRenderURL() />
		${categorizedPagesURL.setParameter("struts_action", "/wiki/view_categorized_pages")}
		${categorizedPagesURL.setParameter("nodeId", entry.getNodeId()?string)}

		<@liferay_ui["asset-categories-summary"]
			className=wikiPageClassName
			classPK=entry.getResourcePrimKey()
			portletURL=categorizedPagesURL
		/>
	</div>

	<div class="page-tags">
		<#assign taggedPagesURL = renderResponse.createRenderURL() />
		${taggedPagesURL.setParameter("struts_action", "/wiki/view_tagged_pages")}
		${taggedPagesURL.setParameter("nodeId", entry.getNodeId()?string)}

		<@liferay_ui["asset-tags-summary"]
			className=wikiPageClassName
			classPK=entry.getResourcePrimKey()
			portletURL=taggedPagesURL
		/>
	</div>
</div>

<#assign childPages = entry.getChildPages() />

<#if (childPages?has_content)>
	<div class="child-pages">
		<h2><@liferay.language key="children-pages" /></h2>

		<table class="taglib-search-iterator">
			<tr class="portlet-section-header results-header">
				<th><@liferay.language key="page" /></th>
				<th><@liferay.language key="last-modified" /></th>
				<th><@liferay.language key="ratings" /></th>
				<th><@liferay.language key="views" /></th>
			</tr>

			<#list childPages as childPage>
				<tr class="results-row">
					<#assign childAssetEntry = assetEntryLocalService.getEntry(wikiPageClassName, childPage.getResourcePrimKey()) />
					<#assign childPageViewURL = renderResponse.createRenderURL() />
					${childPageViewURL.setParameter("struts_action", "/wiki/view")}
					${childPageViewURL.setParameter("nodeName", childPage.getNode().getName())}
					${childPageViewURL.setParameter("title", childPage.getTitle())}
					<td><a href="${childPageViewURL}">${childPage.getTitle()}</a></td>
					<td><a href="${childPageViewURL}">${dateUtil.getDate(childPage.getModifiedDate(),"dd MMM yyyy - HH:mm:ss", locale)} <@liferay.language key="by" /> ${htmlUtil.escape(portalUtil.getUserName(childPage.getUserId(), childPage.getUserName()))}</a></td>
					<td>
						<@ratings entry=childPage cssClass="child-ratings"/>
					</td>
					<td><span class="stats">${childAssetEntry.getViewCount()}</span></td>
				</tr>
			</#list>
		</table>
	</div>
</#if>

<@discussion />

<#macro add_child_icon>
	<#if assetRenderer.hasEditPermission(themeDisplay.getPermissionChecker())>
		<#assign redirectURL = portalUtil.getCurrentURL(request) />
		<#assign addPageURL = renderResponse.createRenderURL() />
		${addPageURL.setParameter("struts_action", "/wiki/edit_page")}
		${addPageURL.setParameter("redirect", redirectURL)}
		${addPageURL.setParameter("nodeId", entry.getNodeId()?string)}
		${addPageURL.setParameter("title", "")}
		${addPageURL.setParameter("editTitle", "1")}
		${addPageURL.setParameter("parentTitle", entry.getTitle())}

		<@liferay_ui["icon"]
			image="add_article"
			label=true
			message="add-child-page"
			url=addPageURL?string
		/>
	</#if>
</#macro>

<#macro attatchments_icon>
	<#assign viewAttachmentsURL = renderResponse.createRenderURL() />
	${viewAttachmentsURL.setParameter("struts_action", "/wiki/view_page_attachments") }
	<#assign attachments = entry.getAttachmentsFiles() />

	<@liferay_ui["icon"]
		image="clip"
		label=true
		message='${attachments?size + languageUtil.get(locale, "attachments")}'
		url=viewAttachmentsURL?string
	/>
</#macro>

<#macro details_icon>
	<#assign redirectURL = portalUtil.getCurrentURL(request) />
	<#assign viewPageDetailsURL = renderResponse.createRenderURL() />
	${viewPageDetailsURL.setParameter("struts_action", "/wiki/view_page_details")}
	${viewPageDetailsURL.setParameter("redirect", redirectURL?string)}

	<@liferay_ui["icon"]
		image="history"
		message="details"
		url=viewPageDetailsURL?string
	/>
</#macro>

<#macro discussion>
	<#if validator.isNotNull(assetRenderer.getDiscussionPath()) && enableComments == "true">
		<br />

		<#assign discussionURL = renderResponse.createActionURL() />
		${discussionURL.setParameter("struts_action", "/wiki/" + assetRenderer.getDiscussionPath())}

		<@liferay_ui["discussion"]
			className=wikiPageClassName
			classPK=entry.getResourcePrimKey()
			formAction=discussionURL?string
			formName="fm2"
			ratingsEnabled=enableCommentRatings == "true"
			redirect=portalUtil.getCurrentURL(request)
			subject=assetRenderer.getTitle(locale)
			userId=assetRenderer.getUserId()
		/>
	</#if>
</#macro>

<#macro edit_icon>
	<#if assetRenderer.hasEditPermission(themeDisplay.getPermissionChecker())>
		<#assign redirectURL = portalUtil.getCurrentURL(request) />
		<#assign editPageURL = renderResponse.createRenderURL() />
		${editPageURL.setParameter("struts_action", "/wiki/edit_page")}
		${editPageURL.setParameter("redirect", redirectURL)}
		${editPageURL.setParameter("nodeId", entry.getNodeId()?string)}
		${editPageURL.setParameter("title", entry.getTitle())}

		<@liferay_ui["icon"]
			image="edit"
			message=entry.getTitle()
			url=editPageURL?string
		/>
	</#if>
</#macro>

<#macro print_icon>
	<#assign printPortletURL = renderResponse.createRenderURL() />
	${printPortletURL.setWindowState("pop_up")}
	${printPortletURL.setParameter("viewMode", "print")}
	<#assign formatParams = ["aui-helper-hidden-accessible", htmlUtil.escape(assetRenderer.getTitle(locale))] />
	<#assign title = languageUtil.format(locale, "print-x-x", formatParams) />
	<#assign taglibPrintURL = "javascript:Liferay.Util.openWindow({dialog: {width: 960}, id:'" + renderResponse.getNamespace() + "printAsset', title: '" + title + "', uri:'" + htmlUtil.escapeURL(printPortletURL.toString()) + "'});" />

	<@liferay_ui["icon"]
		image="print"
		message="print"
		url=taglibPrintURL
	/>
</#macro>

<#macro ratings entry cssClass>
	<#if enablePageRatings == "true">
		<div class="${cssClass}">
			<@liferay_ui["ratings"]
				className=wikiPageClassName
				classPK=entry.getResourcePrimKey()
			/>
		</div>
	</#if>
</#macro>

<#macro related_assets>
	<#if assetEntry?? && enableRelatedAssets == "true">
		<@liferay_ui["asset-links"]
			assetEntryId=assetEntry.getEntryId()
		/>
	</#if>
</#macro>