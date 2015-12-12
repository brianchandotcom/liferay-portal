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

String type = ParamUtil.getString(request, "type", "all_pages");

PortletURL portletURL = PortletURLUtil.clone(currentURLObj, liferayPortletResponse);

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, type), portletURL.toString());

List<String> headerNames = new ArrayList<String>();

headerNames.add("page");
headerNames.add("status");
headerNames.add("revision");
headerNames.add("user");
headerNames.add("date");
headerNames.add(StringPool.BLANK);

String emptyResultsMessage = null;

if (type.equals("all_pages")) {
	emptyResultsMessage = "there-are-no-pages";
}
else if (type.equals("draft_pages")) {
	emptyResultsMessage = "there-are-no-drafts";
}
else if (type.equals("orphan_pages")) {
	emptyResultsMessage = "there-are-no-orphan-pages";
}
else if (type.equals("recent_changes")) {
	emptyResultsMessage = "there-are-no-recent-changes";
}

String orderByCol = ParamUtil.getString(request, "orderByCol");
String orderByType = ParamUtil.getString(request, "orderByType");

OrderByComparator<WikiPage> orderByComparator = WikiPortletUtil.getPageOrderByComparator(orderByCol, orderByType);

Map orderableHeaders = new HashMap();

SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA, currentURLObj, headerNames, emptyResultsMessage);

searchContainer.setOrderableHeaders(orderableHeaders);
searchContainer.setOrderByCol(orderByCol);
searchContainer.setOrderByType(orderByType);

int total = 0;
List<WikiPage> results = null;

if (type.equals("all_pages")) {
	orderableHeaders.put("page", "title");
	orderableHeaders.put("date", "modifiedDate");

	total = WikiPageServiceUtil.getPagesCount(themeDisplay.getScopeGroupId(), node.getNodeId(), true, themeDisplay.getUserId(), true, WorkflowConstants.STATUS_APPROVED);

	searchContainer.setTotal(total);

	results = WikiPageServiceUtil.getPages(themeDisplay.getScopeGroupId(), node.getNodeId(), true, themeDisplay.getUserId(), true, WorkflowConstants.STATUS_APPROVED, searchContainer.getStart(), searchContainer.getEnd(), orderByComparator);
}

else if (type.equals("draft_pages")) {
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

	if (!type.equals("draft_pages")) {
		rowURL.setParameter("mvcRenderCommandName", "/wiki/view");
		rowURL.setParameter("redirect", currentURL);
		rowURL.setParameter("nodeName", curWikiPage.getNode().getName());
	}
	else {
		rowURL.setParameter("mvcRenderCommandName", "/wiki/edit_page");
		rowURL.setParameter("redirect", currentURL);
		rowURL.setParameter("nodeId", String.valueOf(curWikiPage.getNodeId()));
	}

	rowURL.setParameter("title", curWikiPage.getTitle());

	// Title

	row.addText(HtmlUtil.escape(curWikiPage.getTitle()), rowURL);

	// Status

	row.addStatus(curWikiPage.getStatus(), curWikiPage.getStatusByUserId(), curWikiPage.getStatusDate(), rowURL);

	// Revision

	String revision = String.valueOf(curWikiPage.getVersion());

	if (curWikiPage.isMinorEdit()) {
		revision += " (" + LanguageUtil.get(request, "minor-edit") + ")";
	}

	row.addText(revision, rowURL);

	// User

	row.addText(HtmlUtil.escape(PortalUtil.getUserName(curWikiPage)), rowURL);

	// Date

	row.addDate(curWikiPage.getModifiedDate(), rowURL);

	// Summary

	if (type.equals("recent_changes")) {
		if (Validator.isNotNull(curWikiPage.getSummary())) {
			row.addText(HtmlUtil.escape(curWikiPage.getSummary()));
		}
		else {
			row.addText(StringPool.BLANK);
		}
	}

	// Action

	row.addJSP("/wiki/page_action.jsp", "entry-action", application, request, response);

	// Add result row

	resultRows.add(row);
}
%>

<liferay-ui:search-iterator paginate='<%= type.equals("history") ? false : true %>' searchContainer="<%= searchContainer %>" />

<liferay-util:include page="/wiki_admin/add_page_button.jsp" servletContext="<%= application %>" />