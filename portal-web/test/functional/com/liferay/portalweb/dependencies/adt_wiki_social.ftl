<#assign
	wikiPageClassName = "com.liferay.wiki.model.WikiPage"

	assetRenderer = assetEntry.getAssetRenderer()
/>

<div class="taglib-header">
	<h1 class="header-title">${entry.getTitle()}</h1>
</div>

<div style="float: right;">
	<@getEditIcon />

	<@getPageDetailsIcon />

	<@getPrintIcon />
</div>

<div class="wiki-body">
	<div class="wiki-info">
		<span class="stats">${assetEntry.getViewCount()} <@liferay.language key="views" /></span> |

		<span class="date"><@liferay.language key="last-modified" /> ${dateUtil.getDate(entry.getModifiedDate(), "dd MMM yyyy - HH:mm:ss", locale)}</span>

		<span class="author"><@liferay.language key="by" /> ${htmlUtil.escape(portalUtil.getUserName(entry.getUserId(), entry.getUserName()))}</span>
	</div>

	<div class="wiki-content">
		${formattedContent}
	</div>

	<div class="page-actions">
		<div class="article-actions">
			<@getAddChildPageIcon />

			<@getAttatchmentsIcon />
		</div>
	</div>

	 <br />

	<@getRatings
		cssClass="page-ratings"
		entry=entry
	/>

	<@getRelatedAssets />
</div>

<div class="page-categorization">
	<div class="page-categories">
		<#assign viewCategorizedPagesURL = renderResponse.createRenderURL() />

		${viewCategorizedPagesURL.setParameter("struts_action", "/wiki/view_categorized_pages")}
		${viewCategorizedPagesURL.setParameter("nodeId", entry.getNodeId()?string)}

		<@liferay_ui["asset-categories-summary"]
			className=wikiPageClassName
			classPK=entry.getResourcePrimKey()
			portletURL=viewCategorizedPagesURL
		/>
	</div>

	<div class="page-tags">
		<#assign viewTaggedPagesURL = renderResponse.createRenderURL() />

		${viewTaggedPagesURL.setParameter("struts_action", "/wiki/view_tagged_pages")}
		${viewTaggedPagesURL.setParameter("nodeId", entry.getNodeId()?string)}

		<@liferay_ui["asset-tags-summary"]
			className=wikiPageClassName
			classPK=entry.getResourcePrimKey()
			portletURL=viewTaggedPagesURL
		/>
	</div>
</div>

<#assign childPages = entry.getChildPages() />

<#if (childPages?has_content)>
	<div class="child-pages">
		<h2><@liferay.language key="children-pages" /></h2>

		<table class="taglib-search-iterator">
			<tr class="portlet-section-header results-header">
				<th>
					<@liferay.language key="page" />
				</th>
				<th>
					<@liferay.language key="last-modified" />
				</th>
				<th>
					<@liferay.language key="ratings" />
				</th>
			</tr>

			<#list childPages as childPage>
				<tr class="results-row">
					<#assign viewPageURL = renderResponse.createRenderURL() />

					${viewPageURL.setParameter("struts_action", "/wiki/view")}

					<#assign childNode = childPage.getNode() />

					${viewPageURL.setParameter("nodeName", childNode.getName())}
					${viewPageURL.setParameter("title", childPage.getTitle())}

					<td>
						<a href="${viewPageURL}">${childPage.getTitle()}</a>
					</td>
					<td>
						<a href="${viewPageURL}">${dateUtil.getDate(childPage.getModifiedDate(),"dd MMM yyyy - HH:mm:ss", locale)} <@liferay.language key="by" /> ${htmlUtil.escape(portalUtil.getUserName(childPage.getUserId(), childPage.getUserName()))}</a>
					</td>
					<td>
						<@getRatings
							cssClass="child-ratings"
							entry=childPage
						/>
					</td>
				</tr>
			</#list>
		</table>
	</div>
</#if>

<@getDiscussion />

<#macro getAddChildPageIcon>
	<#if assetRenderer.hasEditPermission(themeDisplay.getPermissionChecker())>
		<#assign addPageURL = renderResponse.createRenderURL() />

		${addPageURL.setParameter("struts_action", "/wiki/edit_page")}
		${addPageURL.setParameter("redirect", currentURL)}
		${addPageURL.setParameter("nodeId", entry.getNodeId()?string)}
		${addPageURL.setParameter("title", "")}
		${addPageURL.setParameter("editTitle", "1")}
		${addPageURL.setParameter("parentTitle", entry.getTitle())}

		<@liferay_ui["icon"]
			iconCssClass="icon-plus"
			label=true
			message="add-child-page"
			url=addPageURL?string
		/>
	</#if>
</#macro>

<#macro getAttatchmentsIcon>
	<#assign viewPageAttachmentsURL = renderResponse.createRenderURL() />

	${viewPageAttachmentsURL.setParameter("struts_action", "/wiki/view_page_attachments") }

	<@liferay_ui["icon"]
		iconCssClass="icon-paperclip"
		label=true
		message='${entry.getAttachmentsFileEntriesCount() + languageUtil.get(locale, "attachments")}'
		url=viewPageAttachmentsURL?string
	/>
</#macro>

<#macro getDiscussion>
	<#if validator.isNotNull(assetRenderer.getDiscussionPath()) && wikiPortletInstanceConfiguration.enableComments()>
		<br />

		<#assign discussionURL = renderResponse.createActionURL() />

		${discussionURL.setParameter("struts_action", "/wiki/" + assetRenderer.getDiscussionPath())}

		<@liferay_comment["discussion"]
			className=wikiPageClassName
			classPK=entry.getResourcePrimKey()
			formAction=discussionURL?string
			formName="fm2"
			ratingsEnabled=wikiPortletInstanceConfiguration.enableCommentRatings()
			redirect=currentURL
			subject=assetRenderer.getTitle(locale)
			userId=assetRenderer.getUserId()
		/>
	</#if>
</#macro>

<#macro getEditIcon>
	<#if assetRenderer.hasEditPermission(themeDisplay.getPermissionChecker())>
		<#assign editPageURL = renderResponse.createRenderURL() />

		${editPageURL.setParameter("struts_action", "/wiki/edit_page")}
		${editPageURL.setParameter("redirect", currentURL)}
		${editPageURL.setParameter("nodeId", entry.getNodeId()?string)}
		${editPageURL.setParameter("title", entry.getTitle())}

		<@liferay_ui["icon"]
			iconCssClass="icon-edit"
			message=entry.getTitle()
			url=editPageURL?string
		/>
	</#if>
</#macro>

<#macro getPageDetailsIcon>
	<#assign viewPageDetailsURL = renderResponse.createRenderURL() />

	${viewPageDetailsURL.setParameter("struts_action", "/wiki/view_page_details")}
	${viewPageDetailsURL.setParameter("redirect", currentURL)}

	<@liferay_ui["icon"]
		iconCssClass="icon-file-alt"
		message="details"
		url=viewPageDetailsURL?string
	/>
</#macro>

<#macro getPrintIcon>
	<#assign printURL = renderResponse.createRenderURL() />

	${printURL.setParameter("viewMode", "print")}
	${printURL.setWindowState("pop_up")}

	<#assign
		title = languageUtil.format(locale, "print-x-x", ["hide-accessible", htmlUtil.escape(assetRenderer.getTitle(locale))], false)
		taglibPrintURL = "javascript:Liferay.Util.openWindow({dialog: {width: 960}, id:'" + renderResponse.getNamespace() + "printAsset', title: '" + title + "', uri: '" + htmlUtil.escapeURL(printURL.toString()) + "'});"
	/>

	<@liferay_ui["icon"]
		iconCssClass="icon-print"
		message="print"
		url=taglibPrintURL
	/>
</#macro>

<#macro getRatings
	cssClass
	entry
>
	<#if wikiPortletInstanceConfiguration.enablePageRatings()>
		<div class="${cssClass}">
			<@liferay_ui["ratings"]
				className=wikiPageClassName
				classPK=entry.getResourcePrimKey()
			/>
		</div>
	</#if>
</#macro>

<#macro getRelatedAssets>
	<#if assetEntry?? && wikiPortletInstanceConfiguration.enableRelatedAssets()>
		<@liferay_asset["asset-links"] assetEntryId=assetEntry.getEntryId() />
	</#if>
</#macro>