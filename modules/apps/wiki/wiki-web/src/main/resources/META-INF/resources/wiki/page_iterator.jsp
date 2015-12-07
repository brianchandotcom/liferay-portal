<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

<%@ include file="/wiki/init.jsp" %>

<%
WikiNode node = (WikiNode)request.getAttribute(WikiWebKeys.WIKI_NODE);
WikiPage wikiPage = (WikiPage)request.getAttribute(WikiWebKeys.WIKI_PAGE);

String type = ParamUtil.getString(request, "type");
long categoryId = ParamUtil.getLong(request, "categoryId");
String tagName = ParamUtil.getString(request, "tag");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("nodeName", node.getName());

if (wikiPage != null) {
	portletURL.setParameter("title", wikiPage.getTitle());
}

if (type.equals("all_pages")) {
	portletURL.setParameter("mvcRenderCommandName", "/wiki/view_all_pages");

	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "all-pages"), portletURL.toString());
}
else if (type.equals("categorized_pages")) {
	portletURL.setParameter("mvcRenderCommandName", "/wiki/view_categorized_pages");
	portletURL.setParameter("categoryId", String.valueOf(categoryId));
}
else if (type.equals("draft_pages")) {
	portletURL.setParameter("mvcRenderCommandName", "/wiki/view_draft_pages");

	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "draft-pages"), portletURL.toString());
}
else if (type.equals("history")) {
	PortletURL viewPageHistoryURL = PortletURLUtil.clone(portletURL, renderResponse);

	if (wikiPage != null) {
		portletURL.setParameter("mvcRenderCommandName", "/wiki/view");

		PortalUtil.addPortletBreadcrumbEntry(request, wikiPage.getTitle(), portletURL.toString());
	}

	viewPageHistoryURL.setParameter("mvcRenderCommandName", "/wiki/view_page_activities");

	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "history"), viewPageHistoryURL.toString());
}
else if (type.equals("incoming_links")) {
	if (wikiPage != null) {
		portletURL.setParameter("mvcRenderCommandName", "/wiki/view");

		PortalUtil.addPortletBreadcrumbEntry(request, wikiPage.getTitle(), portletURL.toString());
	}

	portletURL.setParameter("mvcRenderCommandName", "/wiki/view_page_incoming_links");

	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "incoming-links"), portletURL.toString());
}
else if (type.equals("orphan_pages")) {
	portletURL.setParameter("mvcRenderCommandName", "/wiki/view_orphan_pages");

	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "orphan-pages"), portletURL.toString());
}
else if (type.equals("outgoing_links")) {
	if (wikiPage != null) {
		portletURL.setParameter("mvcRenderCommandName", "/wiki/view");

		PortalUtil.addPortletBreadcrumbEntry(request, wikiPage.getTitle(), portletURL.toString());
	}

	portletURL.setParameter("mvcRenderCommandName", "/wiki/view_page_outgoing_links");

	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "outgoing-links"), portletURL.toString());
}
else if (type.equals("recent_changes")) {
	portletURL.setParameter("mvcRenderCommandName", "/wiki/view_recent_changes");

	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "recent-changes"), portletURL.toString());
}
else if (type.equals("tagged_pages")) {
	portletURL.setParameter("mvcRenderCommandName", "/wiki/view_tagged_pages");
	portletURL.setParameter("tag", tagName);
}

List<String> headerNames = new ArrayList<String>();

headerNames.add("page");
headerNames.add("status");
headerNames.add("revision");
headerNames.add("user");
headerNames.add("date");

if (type.equals("history") || type.equals("recent_changes")) {
	headerNames.add("summary");
}

if (type.equals("all_pages") || type.equals("categorized_pages") || type.equals("draft_pages") || type.equals("history") || type.equals("orphan_pages") || type.equals("recent_changes") || type.equals("tagged_pages")) {
	headerNames.add(StringPool.BLANK);
}

String emptyResultsMessage = null;

if (type.equals("all_pages")) {
	emptyResultsMessage = "there-are-no-pages";
}
else if (type.equals("categorized_pages")) {
	emptyResultsMessage = "there-are-no-pages-with-this-category";
}
else if (type.equals("draft_pages")) {
	emptyResultsMessage = "there-are-no-drafts";
}
else if (type.equals("incoming_links")) {
	emptyResultsMessage = "there-are-no-pages-that-link-to-this-page";
}
else if (type.equals("orphan_pages")) {
	emptyResultsMessage = "there-are-no-orphan-pages";
}
else if (type.equals("outgoing_links")) {
	emptyResultsMessage = "this-page-has-no-links";
}
else if (type.equals("pending_pages")) {
	emptyResultsMessage = "there-are-no-pages-submitted-by-you-pending-approval";
}
else if (type.equals("recent_changes")) {
	emptyResultsMessage = "there-are-no-recent-changes";
}
else if (type.equals("tagged_pages")) {
	emptyResultsMessage = "there-are-no-pages-with-this-tag";
}

String orderByCol = ParamUtil.getString(request, "orderByCol");
String orderByType = ParamUtil.getString(request, "orderByType");

OrderByComparator<WikiPage> orderByComparator = WikiPortletUtil.getPageOrderByComparator(orderByCol, orderByType);

Map orderableHeaders = new HashMap();

SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA, currentURLObj, headerNames, emptyResultsMessage);

searchContainer.setOrderableHeaders(orderableHeaders);
searchContainer.setOrderByCol(orderByCol);
searchContainer.setOrderByType(orderByType);

if (type.equals("history")) {
	RowChecker rowChecker = new RowChecker(renderResponse);

	rowChecker.setAllRowIds(null);

	searchContainer.setRowChecker(rowChecker);
}

int total = 0;
List<WikiPage> results = null;

if (type.equals("all_pages")) {
	orderableHeaders.put("page", "title");
	orderableHeaders.put("date", "modifiedDate");

	total = WikiPageServiceUtil.getPagesCount(themeDisplay.getScopeGroupId(), node.getNodeId(), true, themeDisplay.getUserId(), true, WorkflowConstants.STATUS_APPROVED);

	searchContainer.setTotal(total);

	results = WikiPageServiceUtil.getPages(themeDisplay.getScopeGroupId(), node.getNodeId(), true, themeDisplay.getUserId(), true, WorkflowConstants.STATUS_APPROVED, searchContainer.getStart(), searchContainer.getEnd(), orderByComparator);
}
else if (type.equals("categorized_pages") || type.equals("tagged_pages")) {
	orderableHeaders.put("page", "title");
	orderableHeaders.put("date", "modifiedDate");

	AssetEntryQuery assetEntryQuery = new AssetEntryQuery(WikiPage.class.getName(), searchContainer);

	assetEntryQuery.setEnablePermissions(true);

	total = AssetEntryServiceUtil.getEntriesCount(assetEntryQuery);

	searchContainer.setTotal(total);

	assetEntryQuery.setEnd(searchContainer.getEnd());
	assetEntryQuery.setStart(searchContainer.getStart());

	List<AssetEntry> assetEntries = AssetEntryServiceUtil.getEntries(assetEntryQuery);

	results = new ArrayList<WikiPage>();

	for (AssetEntry assetEntry : assetEntries) {
		WikiPageResource pageResource = WikiPageResourceLocalServiceUtil.getPageResource(assetEntry.getClassPK());

		WikiPage assetPage = WikiPageLocalServiceUtil.getPage(pageResource.getNodeId(), pageResource.getTitle());

		results.add(assetPage);
	}
}
else if (type.equals("draft_pages") || type.equals("pending_pages")) {
	long draftUserId = user.getUserId();

	if (permissionChecker.isContentReviewer(user.getCompanyId(), scopeGroupId)) {
		draftUserId = 0;
	}

	int status = WorkflowConstants.STATUS_DRAFT;

	if (type.equals("pending_pages")) {
		status = WorkflowConstants.STATUS_PENDING;
	}

	total = WikiPageServiceUtil.getPagesCount(themeDisplay.getScopeGroupId(), draftUserId, node.getNodeId(), status);

	searchContainer.setTotal(total);

	results = WikiPageServiceUtil.getPages(themeDisplay.getScopeGroupId(), draftUserId, node.getNodeId(), status, searchContainer.getStart(), searchContainer.getEnd());
}
else if (type.equals("orphan_pages")) {
	List<WikiPage> orphans = WikiPageServiceUtil.getOrphans(themeDisplay.getScopeGroupId(), node.getNodeId());

	total = orphans.size();

	searchContainer.setTotal(total);

	results = ListUtil.subList(orphans, searchContainer.getStart(), searchContainer.getEnd());
}
else if (type.equals("history")) {
	total = WikiPageLocalServiceUtil.getPagesCount(wikiPage.getNodeId(), wikiPage.getTitle());

	searchContainer.setTotal(total);

	results = WikiPageLocalServiceUtil.getPages(wikiPage.getNodeId(), wikiPage.getTitle(), QueryUtil.ALL_POS, QueryUtil.ALL_POS, new PageVersionComparator());
}
else if (type.equals("incoming_links")) {
	List<WikiPage> links = WikiPageLocalServiceUtil.getIncomingLinks(wikiPage.getNodeId(), wikiPage.getTitle());

	total = links.size();

	searchContainer.setTotal(total);

	results = ListUtil.subList(links, searchContainer.getStart(), searchContainer.getEnd());
}
else if (type.equals("outgoing_links")) {
	List<WikiPage> links = WikiPageLocalServiceUtil.getOutgoingLinks(wikiPage.getNodeId(), wikiPage.getTitle());

	total = links.size();

	searchContainer.setTotal(total);

	results = ListUtil.subList(links, searchContainer.getStart(), searchContainer.getEnd());
}
else if (type.equals("recent_changes")) {
	total = WikiPageServiceUtil.getRecentChangesCount(themeDisplay.getScopeGroupId(), node.getNodeId());

	searchContainer.setTotal(total);

	results = WikiPageServiceUtil.getRecentChanges(themeDisplay.getScopeGroupId(), node.getNodeId(), searchContainer.getStart(), searchContainer.getEnd());
}

searchContainer.setResults(results);

List resultRows = searchContainer.getResultRows();

for (int i = 0; i < results.size(); i++) {
	WikiPage curWikiPage = results.get(i);

	ResultRow row = new ResultRow(curWikiPage, String.valueOf(curWikiPage.getVersion()), i);

	PortletURL rowURL = renderResponse.createRenderURL();

	if (!curWikiPage.isNew() && !type.equals("draft_pages") && !type.equals("pending_pages")) {
		if (portletName.equals(WikiPortletKeys.WIKI_DISPLAY)) {
			rowURL.setParameter("mvcRenderCommandName", "/wiki/view_page");
		}
		else {
			rowURL.setParameter("mvcRenderCommandName", "/wiki/view");
		}

		rowURL.setParameter("redirect", currentURL);
		rowURL.setParameter("nodeName", curWikiPage.getNode().getName());
	}
	else {
		rowURL.setParameter("mvcRenderCommandName", "/wiki/edit_page");
		rowURL.setParameter("redirect", currentURL);
		rowURL.setParameter("nodeId", String.valueOf(curWikiPage.getNodeId()));
	}

	rowURL.setParameter("title", curWikiPage.getTitle());

	if (type.equals("history")) {
		rowURL.setParameter("version", String.valueOf(curWikiPage.getVersion()));
	}

	// Title

	row.addText(HtmlUtil.escape(curWikiPage.getTitle()), rowURL);

	// Status

	row.addStatus(curWikiPage.getStatus(), curWikiPage.getStatusByUserId(), curWikiPage.getStatusDate(), rowURL);

	// Revision

	if (!curWikiPage.isNew()) {
		String revision = String.valueOf(curWikiPage.getVersion());

		if (curWikiPage.isMinorEdit()) {
			revision += " (" + LanguageUtil.get(request, "minor-edit") + ")";
		}

		row.addText(revision, rowURL);
	}
	else {
		row.addText(StringPool.BLANK);
	}

	// User

	if (!curWikiPage.isNew()) {
		row.addText(HtmlUtil.escape(PortalUtil.getUserName(curWikiPage)), rowURL);
	}
	else {
		row.addText(StringPool.BLANK);
	}

	// Date

	if (!curWikiPage.isNew()) {
		row.addDate(curWikiPage.getCreateDate(), rowURL);
	}
	else {
		row.addText(StringPool.BLANK);
	}

	// Summary

	if (type.equals("history") || type.equals("recent_changes")) {
		if (Validator.isNotNull(curWikiPage.getSummary())) {
			row.addText(HtmlUtil.escape(curWikiPage.getSummary()));
		}
		else {
			row.addText(StringPool.BLANK);
		}
	}

	// Action

	if (type.equals("history")) {
		if (curWikiPage.isHead()) {
			row.addText(StringPool.BLANK);
		}
		else {
			row.addJSP("/wiki/page_history_action.jsp", "entry-action", application, request, response);
		}
	}

	if (type.equals("all_pages") || type.equals("categorized_pages") || type.equals("draft_pages") || type.equals("orphan_pages") || type.equals("recent_changes") || type.equals("tagged_pages")) {
		row.addJSP("/wiki/page_action.jsp", "entry-action", application, request, response);
	}

	// Add result row

	resultRows.add(row);
}
%>

<c:if test='<%= type.equals("history") && (results.size() > 1) %>'>
	<aui:button-row>
		<aui:button cssClass="btn-primary" name="compare" value="compare-versions" />
	</aui:button-row>
</c:if>

<c:if test='<%= type.equals("all_pages") && WikiNodePermissionChecker.contains(permissionChecker, node.getNodeId(), ActionKeys.ADD_PAGE) %>'>
	<liferay-ui:app-view-toolbar>
		<aui:button-row cssClass="wiki-page-toolbar" id='<%= renderResponse.getNamespace() + "wikiPageToolbar" %>' />
	</liferay-ui:app-view-toolbar>

	<aui:script use="aui-toolbar">
		var buttonRow = A.one('#<portlet:namespace />wikiPageToolbar');

		var wikiPageButtonGroup = [];

		<%
		WikiListPagesDisplayContext wikiListPagesDisplayContext = wikiDisplayContextProvider.getWikiListPagesDisplayContext(request, response, node);

		for (ToolbarItem toolbarItem : wikiListPagesDisplayContext.getToolbarItems()) {
		%>

			<liferay-ui:toolbar-item toolbarItem="<%= toolbarItem %>" var="wikiPageButtonGroup" />

		<%
		}
		%>

		var wikiPageToolbar = new A.Toolbar(
			{
				boundingBox: buttonRow,
				children: [wikiPageButtonGroup]
			}
		).render();

		buttonRow.setData('wikiPageToolbar', wikiPageToolbar);
	</aui:script>
</c:if>

<liferay-ui:categorization-filter
	assetType="pages"
	portletURL="<%= portletURL %>"
/>

<liferay-ui:search-iterator paginate='<%= type.equals("history") ? false : true %>' searchContainer="<%= searchContainer %>" />

<c:if test='<%= type.equals("history") %>'>
	<aui:script>
		function <portlet:namespace />initRowsChecked() {
			var $ = AUI.$;

			var rowIds = $('input[name=<portlet:namespace />rowIds]');

			rowIds.slice(2).prop('checked', false);
		}

		function <portlet:namespace />updateRowsChecked(element) {
			var rowsChecked = AUI.$('input[name=<portlet:namespace />rowIds]:checked');

			if (rowsChecked.length > 2) {
				var index = 2;

				if (rowsChecked.eq(2).is(element)) {
					index = 1;
				}

				rowsChecked.eq(index).prop('checked', false);
			}
		}
	</aui:script>

	<aui:script sandbox="<%= true %>">
		<c:if test="<%= results.size() > 1 %>">

			<%
			WikiPage latestWikiPage = (WikiPage)results.get(1);
			%>

			$('#<portlet:namespace />compare').on(
				'click',
				function(event) {
					<portlet:renderURL var="compareVersionURL">
						<portlet:param name="mvcRenderCommandName" value="/wiki/compare_versions" />
						<portlet:param name="backURL" value="<%= currentURL %>" />
						<portlet:param name="tabs3" value="versions" />
						<portlet:param name="nodeId" value="<%= String.valueOf(node.getNodeId()) %>" />
						<portlet:param name="title" value="<%= wikiPage.getTitle() %>" />
						<portlet:param name="type" value="html" />
					</portlet:renderURL>

					var uri = '<%= compareVersionURL %>';

					var rowIds = $('input[name=<portlet:namespace />rowIds]:checked');

					var rowIdsSize = rowIds.length;

					if (rowIdsSize === 0 || rowIdsSize === 2) {
						if (rowIdsSize === 0) {
							uri = Liferay.Util.addParams('<portlet:namespace />sourceVersion=<%= latestWikiPage.getVersion() %>', uri);
							uri = Liferay.Util.addParams('<portlet:namespace />targetVersion=<%= wikiPage.getVersion() %>', uri);
						}
						else if (rowIdsSize === 2) {
							uri = Liferay.Util.addParams('<portlet:namespace />sourceVersion=' + rowIds.eq(1).val(), uri);
							uri = Liferay.Util.addParams('<portlet:namespace />targetVersion=' + rowIds.eq(0).val(), uri);
						}

						location.href = uri;
					}
				}
			);
		</c:if>

		<portlet:namespace />initRowsChecked();

		$('input[name=<portlet:namespace />rowIds]').on(
			'click',
			function(event) {
				<portlet:namespace />updateRowsChecked(event.currentTarget);
			}
		);
	</aui:script>
</c:if>