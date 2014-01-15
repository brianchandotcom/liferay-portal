<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
--%>

<%@ include file="/html/portlet/wiki/init.jsp" %>

<%
boolean followRedirect = ParamUtil.getBoolean(request, "followRedirect", true);

WikiNode node = (WikiNode)request.getAttribute(WebKeys.WIKI_NODE);
WikiPage wikiPage = (WikiPage)request.getAttribute(WebKeys.WIKI_PAGE);

WikiPage originalPage = null;
WikiPage redirectPage = wikiPage.getRedirectPage();

if (followRedirect && (redirectPage != null)) {
	originalPage = wikiPage;
	wikiPage = redirectPage;
}

String title = wikiPage.getTitle();

String parentTitle = StringPool.BLANK;

if (wikiPage != null) {
	parentTitle = wikiPage.getParentTitle();
}

List childPages = wikiPage.getViewableChildPages();

int attachmentsFileEntriesCount = 0;

if (wikiPage != null) {
	attachmentsFileEntriesCount = wikiPage.getAttachmentsFileEntriesCount();
}

boolean preview = false;
boolean print = ParamUtil.getString(request, "viewMode").equals(Constants.PRINT);

PortletURL viewPageURL = renderResponse.createRenderURL();

if (portletName.equals(PortletKeys.WIKI_DISPLAY)) {
	viewPageURL.setParameter("struts_action", "/wiki/view_page");
}
else {
	viewPageURL.setParameter("struts_action", "/wiki/view");
}

viewPageURL.setParameter("nodeName", node.getName());
viewPageURL.setParameter("title", title);

PortletURL viewParentPageURL = null;

if (Validator.isNotNull(parentTitle)) {
	viewParentPageURL = PortletURLUtil.clone(viewPageURL, renderResponse);

	viewParentPageURL.setParameter("title", parentTitle);

	parentTitle = StringUtil.shorten(parentTitle, 20);
}

PortletURL addPageURL = renderResponse.createRenderURL();

addPageURL.setParameter("struts_action", "/wiki/edit_page");
addPageURL.setParameter("redirect", currentURL);
addPageURL.setParameter("nodeId", String.valueOf(node.getNodeId()));
addPageURL.setParameter("title", StringPool.BLANK);
addPageURL.setParameter("editTitle", "1");

if (wikiPage != null) {
	addPageURL.setParameter("parentTitle", wikiPage.getTitle());
}

PortletURL editPageURL = renderResponse.createRenderURL();

editPageURL.setParameter("struts_action", "/wiki/edit_page");
editPageURL.setParameter("redirect", currentURL);
editPageURL.setParameter("nodeId", String.valueOf(node.getNodeId()));
editPageURL.setParameter("title", title);

PortletURL printPageURL = PortletURLUtil.clone(viewPageURL, renderResponse);

printPageURL.setParameter("viewMode", Constants.PRINT);
printPageURL.setWindowState(LiferayWindowState.POP_UP);

PortletURL categorizedPagesURL = renderResponse.createRenderURL();

categorizedPagesURL.setParameter("struts_action", "/wiki/view_categorized_pages");
categorizedPagesURL.setParameter("nodeId", String.valueOf(node.getNodeId()));

PortletURL taggedPagesURL = renderResponse.createRenderURL();

taggedPagesURL.setParameter("struts_action", "/wiki/view_tagged_pages");
taggedPagesURL.setParameter("nodeId", String.valueOf(node.getNodeId()));

PortletURL viewAttachmentsURL = PortletURLUtil.clone(viewPageURL, renderResponse);

viewAttachmentsURL.setParameter("struts_action", "/wiki/view_page_attachments");
viewAttachmentsURL.setParameter("redirect", currentURL);

AssetEntryServiceUtil.incrementViewCounter(WikiPage.class.getName(), wikiPage.getResourcePrimKey());

if (Validator.isNotNull(ParamUtil.getString(request, "title"))) {
	AssetUtil.addLayoutTags(request, AssetTagLocalServiceUtil.getTags(WikiPage.class.getName(), wikiPage.getResourcePrimKey()));
}

AssetEntry layoutAssetEntry = null;

if (wikiPage != null) {
	layoutAssetEntry = AssetEntryLocalServiceUtil.getEntry(WikiPage.class.getName(), wikiPage.getResourcePrimKey());

	request.setAttribute(WebKeys.LAYOUT_ASSET_ENTRY, layoutAssetEntry);
}
%>

<c:choose>
	<c:when test="<%= print %>">
		<div class="popup-print">
			<liferay-ui:icon
				image="print"
				label="<%= true %>"
				url="javascript:print();"
			/>
		</div>

		<aui:script>
			print();
		</aui:script>
	</c:when>
	<c:otherwise>
		<aui:script>
			function <portlet:namespace />printPage() {
				window.open('<%= printPageURL %>', '', "directories=0,height=480,left=80,location=1,menubar=1,resizable=1,scrollbars=yes,status=0,toolbar=0,top=180,width=640");
			}
		</aui:script>
	</c:otherwise>
</c:choose>

<liferay-util:include page="/html/portlet/wiki/top_links.jsp" />

<%
long portletDisplayDDMTemplateId = PortletDisplayTemplateUtil.getPortletDisplayTemplateDDMTemplateId(displayStyleGroupId, displayStyle);
%>

<c:choose>
	<c:when test="<%= portletDisplayDDMTemplateId > 0 %>">

		<%
		List entries = new ArrayList();

		entries.add(wikiPage);

		String formattedContent = null;

		try {
			formattedContent = WikiUtil.getFormattedContent(renderRequest, renderResponse, wikiPage, viewPageURL, editPageURL, title, preview);
		}
		catch (Exception e) {
			formattedContent = wikiPage.getContent();
		}

		Map<String, Object> contextObjects = new HashMap<String, Object>();

		contextObjects.put("assetEntry", layoutAssetEntry);
		contextObjects.put("formattedContent", formattedContent);
		%>

		<%= PortletDisplayTemplateUtil.renderDDMTemplate(pageContext, portletDisplayDDMTemplateId, entries, contextObjects) %>
	</c:when>
	<c:otherwise>

		<liferay-ui:header
			backLabel="<%= parentTitle %>"
			backURL="<%= (viewParentPageURL != null) ? viewParentPageURL.toString() : null %>"
			localizeTitle="<%= false %>"
			title="<%= title %>"
		/>

		<c:if test="<%= !print %>">
			<div class="page-actions top-actions">
				<c:if test="<%= WikiPagePermission.contains(permissionChecker, wikiPage, ActionKeys.UPDATE) %>">
					<c:if test="<%= followRedirect || (redirectPage == null) %>">
						<liferay-ui:icon
							image="edit"
							label="<%= true %>"
							url="<%= editPageURL.toString() %>"
						/>
					</c:if>
				</c:if>

				<%
				PortletURL viewPageDetailsURL = PortletURLUtil.clone(viewPageURL, renderResponse);

				viewPageDetailsURL.setParameter("struts_action", "/wiki/view_page_details");
				viewPageDetailsURL.setParameter("redirect", currentURL);
				%>

				<liferay-ui:icon
					image="history"
					label="<%= true %>"
					message="details"
					method="get"
					url="<%= viewPageDetailsURL.toString() %>"
				/>

				<liferay-ui:icon
					image="print"
					label="<%= true %>"
					url='<%= "javascript:" + renderResponse.getNamespace() + "printPage();" %>'
				/>
			</div>
		</c:if>

		<c:if test="<%= originalPage != null %>">

			<%
			PortletURL originalViewPageURL = renderResponse.createRenderURL();

			originalViewPageURL.setParameter("struts_action", "/wiki/view");
			originalViewPageURL.setParameter("nodeName", node.getName());
			originalViewPageURL.setParameter("title", originalPage.getTitle());
			originalViewPageURL.setParameter("followRedirect", "false");
			%>

			<div class="page-redirect" onClick="location.href = '<%= originalViewPageURL.toString() %>';">
				(<%= LanguageUtil.format(pageContext, "redirected-from-x", originalPage.getTitle(), false) %>)
			</div>
		</c:if>

		<c:if test="<%= !wikiPage.isHead() %>">
			<div class="page-old-version">
				(<liferay-ui:message key="you-are-viewing-an-archived-version-of-this-page" /> (<%= wikiPage.getVersion() %>), <aui:a href="<%= viewPageURL.toString() %>" label="go-to-the-latest-version" />)
			</div>
		</c:if>

		<div class="page-categorization">
			<div class="page-categories">
				<liferay-ui:asset-categories-summary
					className="<%= WikiPage.class.getName() %>"
					classPK="<%= wikiPage.getResourcePrimKey() %>"
					portletURL="<%= PortletURLUtil.clone(categorizedPagesURL, renderResponse) %>"
				/>
			</div>

			<div class="page-tags">
				<liferay-ui:asset-tags-summary
					className="<%= WikiPage.class.getName() %>"
					classPK="<%= wikiPage.getResourcePrimKey() %>"
					message="tags"
					portletURL="<%= PortletURLUtil.clone(taggedPagesURL, renderResponse) %>"
				/>
			</div>
		</div>

		<div>
			<%@ include file="/html/portlet/wiki/view_page_content.jspf" %>
		</div>

		<liferay-ui:custom-attributes-available className="<%= WikiPage.class.getName() %>">
			<liferay-ui:custom-attribute-list
				className="<%= WikiPage.class.getName() %>"
				classPK="<%= (wikiPage != null) ? wikiPage.getPrimaryKey() : 0 %>"
				editable="<%= false %>"
				label="<%= true %>"
			/>
		</liferay-ui:custom-attributes-available>

		<c:if test="<%= (wikiPage != null) && Validator.isNotNull(formattedContent) && (followRedirect || (redirectPage == null)) %>">
			<c:if test="<%= !childPages.isEmpty() %>">
				<div class="child-pages">
					<h2><liferay-ui:message key="children-pages" /></h2>

					<ul>

						<%
						PortletURL curPageURL = PortletURLUtil.clone(viewPageURL, renderResponse);

						for (int i = 0; i < childPages.size(); i++) {
							WikiPage curPage = (WikiPage)childPages.get(i);

							curPageURL.setParameter("title", curPage.getTitle());
						%>

							<c:if test="<%= Validator.isNull(curPage.getRedirectTitle()) %>">
								<li>
									<aui:a href="<%= curPageURL.toString() %>"><%= curPage.getTitle() %></aui:a>
								</li>
							</c:if>

						<%
						}
						%>

					</ul>
				</div>
			</c:if>

			<div class="page-actions">
				<div class="article-actions">
					<c:if test="<%= WikiNodePermission.contains(permissionChecker, node, ActionKeys.ADD_PAGE) %>">
						<liferay-ui:icon
							image="add_article"
							label="<%= true %>"
							message="add-child-page"
							method="get"
							url="<%= addPageURL.toString() %>"
						/>,
					</c:if>

					<liferay-ui:icon
						image="clip"
						label="<%= true %>"
						message='<%= attachmentsFileEntriesCount + " " + LanguageUtil.get(pageContext, (attachmentsFileEntriesCount == 1) ? "attachment" : "attachments") %>' method="get" url="<%= viewAttachmentsURL.toString() %>"
					/>
				</div>

				<div class="stats">

					<%
					AssetEntry assetEntry = AssetEntryLocalServiceUtil.getEntry(WikiPage.class.getName(), wikiPage.getResourcePrimKey());
					%>

					<c:choose>
						<c:when test="<%= assetEntry.getViewCount() == 1 %>">
							<%= assetEntry.getViewCount() %> <liferay-ui:message key="view" />
						</c:when>
						<c:when test="<%= assetEntry.getViewCount() > 1 %>">
							<%= assetEntry.getViewCount() %> <liferay-ui:message key="views" />
						</c:when>
					</c:choose>
				</div>
			</div>

			<c:if test="<%= enableRelatedAssets %>">
				<div class="entry-links">
					<liferay-ui:asset-links
						assetEntryId="<%= assetEntry.getEntryId() %>"
					/>
				</div>
			</c:if>

			<c:if test="<%= enablePageRatings %>">
				<div class="page-ratings">
					<liferay-ui:ratings
						className="<%= WikiPage.class.getName() %>"
						classPK="<%= wikiPage.getResourcePrimKey() %>"
					/>
				</div>
			</c:if>

			<c:if test="<%= enableComments %>">
				<liferay-ui:panel-container extended="<%= false %>" id="wikiCommentsPanelContainer" persistState="<%= true %>">
					<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="wikiCommentsPanel" persistState="<%= true %>" title="comments">
						<portlet:actionURL var="discussionURL">
							<portlet:param name="struts_action" value="/wiki/edit_page_discussion" />
						</portlet:actionURL>

						<liferay-ui:discussion
							className="<%= WikiPage.class.getName() %>"
							classPK="<%= wikiPage.getResourcePrimKey() %>"
							formAction="<%= discussionURL %>"
							formName="fm2"
							ratingsEnabled="<%= enableCommentRatings %>"
							redirect="<%= currentURL %>"
							userId="<%= wikiPage.getUserId() %>"
						/>
					</liferay-ui:panel>
				</liferay-ui:panel-container>
			</c:if>
		</c:if>
	</c:otherwise>
</c:choose>

<aui:script use="aui-base">
	var toc = A.one('#p_p_id<portlet:namespace /> .toc');

	if (toc) {
		var index = toc.one('.toc-index');
		var trigger = toc.one('a.toc-trigger');

		trigger.on(
			'click',
			function(event) {
				index.toggle();
			}
		);
	}
</aui:script>

<%
if ((wikiPage != null) && !wikiPage.getTitle().equals(WikiPageConstants.FRONT_PAGE)) {
	if (!portletName.equals(PortletKeys.WIKI_DISPLAY)) {
		PortalUtil.setPageSubtitle(wikiPage.getTitle(), request);

		String description = wikiPage.getContent();

		if (wikiPage.getFormat().equals("html")) {
			description = HtmlUtil.stripHtml(description);
		}

		description = StringUtil.shorten(description, 200);

		PortalUtil.setPageDescription(description, request);
		PortalUtil.setPageKeywords(AssetUtil.getAssetKeywords(WikiPage.class.getName(), wikiPage.getResourcePrimKey()), request);
	}

	List<WikiPage> parentPages = wikiPage.getViewableParentPages();

	for (WikiPage curParentPage : parentPages) {
		viewPageURL.setParameter("title", curParentPage.getTitle());

		PortalUtil.addPortletBreadcrumbEntry(request, curParentPage.getTitle(), viewPageURL.toString());
	}

	viewPageURL.setParameter("title", wikiPage.getTitle());

	PortalUtil.addPortletBreadcrumbEntry(request, wikiPage.getTitle(), viewPageURL.toString());
}
%>