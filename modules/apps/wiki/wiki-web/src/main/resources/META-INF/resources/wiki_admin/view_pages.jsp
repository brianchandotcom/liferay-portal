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

String emptyResultsMessage = null;

if (type.equals("all_pages")) {
	emptyResultsMessage = "there-are-no-pages";
}
else if (type.equals("draft_pages")) {
	emptyResultsMessage = "there-are-no-drafts";
}
else if (type.equals("frontpage")) {
	emptyResultsMessage = LanguageUtil.format(request, "there-is-no-x", new String[] {wikiGroupServiceConfiguration.frontPageName()}, false);
}
else if (type.equals("orphan_pages")) {
	emptyResultsMessage = "there-are-no-orphan-changes";
}
else if (type.equals("recent_changes")) {
	emptyResultsMessage = "there-are-no-recent-changes";
}

String orderByCol = ParamUtil.getString(request, "orderByCol");
String orderByType = ParamUtil.getString(request, "orderByType");

SearchContainer wikiPagesSearchContainer = new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA, currentURLObj, null, emptyResultsMessage);

wikiPagesSearchContainer.setOrderByType(orderByType);
wikiPagesSearchContainer.setOrderByCol(orderByCol);
wikiPagesSearchContainer.setOrderByComparator(WikiPortletUtil.getPageOrderByComparator(orderByCol, orderByType));

int pagesCount = 0;
List<WikiPage> pages = null;

if (type.equals("all_pages")) {
	pagesCount = WikiPageServiceUtil.getPagesCount(themeDisplay.getScopeGroupId(), node.getNodeId(), true, themeDisplay.getUserId(), true, WorkflowConstants.STATUS_APPROVED);

	pages = WikiPageServiceUtil.getPages(themeDisplay.getScopeGroupId(), node.getNodeId(), true, themeDisplay.getUserId(), true, WorkflowConstants.STATUS_APPROVED, wikiPagesSearchContainer.getStart(), wikiPagesSearchContainer.getEnd(), wikiPagesSearchContainer.getOrderByComparator());
}
else if (type.equals("draft_pages")) {
	long draftUserId = user.getUserId();

	if (permissionChecker.isContentReviewer(user.getCompanyId(), scopeGroupId)) {
		draftUserId = 0;
	}

	int status = WorkflowConstants.STATUS_DRAFT;

	pagesCount = WikiPageServiceUtil.getPagesCount(themeDisplay.getScopeGroupId(), draftUserId, node.getNodeId(), status);

	pages = WikiPageServiceUtil.getPages(themeDisplay.getScopeGroupId(), draftUserId, node.getNodeId(), status, wikiPagesSearchContainer.getStart(), wikiPagesSearchContainer.getEnd());
}
else if (type.equals("frontpage")) {

	WikiPage wikiPage = WikiPageServiceUtil.getPage(scopeGroupId, node.getNodeId(), wikiGroupServiceConfiguration.frontPageName());

	pagesCount = 1;

	pages.add(wikiPage);
}
else if (type.equals("orphan_pages")) {
	List<WikiPage> orphans = WikiPageServiceUtil.getOrphans(themeDisplay.getScopeGroupId(), node.getNodeId());

	pagesCount = orphans.size();

	pages = ListUtil.subList(orphans, wikiPagesSearchContainer.getStart(), wikiPagesSearchContainer.getEnd());
}
else if (type.equals("recent_changes")) {
	pagesCount = WikiPageServiceUtil.getRecentChangesCount(themeDisplay.getScopeGroupId(), node.getNodeId());

	pages = WikiPageServiceUtil.getRecentChanges(themeDisplay.getScopeGroupId(), node.getNodeId(), wikiPagesSearchContainer.getStart(), wikiPagesSearchContainer.getEnd());
}

WikiVisualizationHelper wikiVisualizationHelper = new WikiVisualizationHelper(wikiRequestHelper, wikiPortletInstanceSettingsHelper, wikiGroupServiceConfiguration);
WikiURLHelper wikiURLHelper = new WikiURLHelper(wikiRequestHelper, renderResponse, wikiGroupServiceConfiguration);
%>

<liferay-util:include page="/wiki_admin/pages_navigation.jsp" servletContext="<%= application %>" />

<div class="container-fluid-1280">
	<c:if test="<%= wikiVisualizationHelper.isUndoTrashControlVisible() %>">

		<%
		PortletURL undoTrashURL = wikiURLHelper.getUndoTrashURL();
		%>

		<liferay-ui:trash-undo portletURL="<%= undoTrashURL.toString() %>" />
	</c:if>

	<liferay-ui:search-container
		id="wikiPages"
		searchContainer="<%= wikiPagesSearchContainer %>"
		total="<%= pagesCount %>"
	>
		<liferay-ui:search-container-results
			results="<%= pages %>"
		/>

		<liferay-ui:search-container-row
			className="com.liferay.wiki.model.WikiPage"
			keyProperty="pageId"
			modelVar="curPage"
		>

			<%
			PortletURL rowURL = renderResponse.createRenderURL();

			if (!type.equals("draft_pages")) {
				rowURL.setParameter("mvcRenderCommandName", "/wiki/view");
				rowURL.setParameter("redirect", currentURL);
				rowURL.setParameter("nodeName", curPage.getNode().getName());
			}
			else {
				rowURL.setParameter("mvcRenderCommandName", "/wiki/edit_page");
				rowURL.setParameter("redirect", currentURL);
				rowURL.setParameter("nodeId", String.valueOf(curPage.getNodeId()));
			}

			rowURL.setParameter("title", curPage.getTitle());
			%>

			<liferay-ui:search-container-column-text
				href="<%= rowURL %>"
				name="title"
				value="<%= curPage.getTitle() %>"
			/>

			<liferay-ui:search-container-column-status
				href="<%= rowURL %>"
				name="status"
				status="<%= curPage.getStatus() %>"
			/>

			<%
			String revision = String.valueOf(curPage.getVersion());

			if (curPage.isMinorEdit()) {
				revision += " (" + LanguageUtil.get(request, "minor-edit") + ")";
			}
			%>

			<liferay-ui:search-container-column-text
				href="<%= rowURL %>"
				name="revision"
				value="<%= revision %>"
			/>

			<liferay-ui:search-container-column-text
				href="<%= rowURL %>"
				name="user"
				value="<%= PortalUtil.getUserName(curPage) %>"
			/>

			<liferay-ui:search-container-column-date
				href="<%= rowURL %>"
				name="date"
				value="<%= curPage.getModifiedDate() %>"
			/>

			<liferay-ui:search-container-column-jsp
				cssClass="entry-action"
				path="/wiki_admin/page_action.jsp"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator displayStyle="list" markupView="lexicon" />
	</liferay-ui:search-container>
</div>

<liferay-util:include page="/wiki_admin/add_page_button.jsp" servletContext="<%= application %>" />