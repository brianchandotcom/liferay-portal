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

<%@ include file="/init.jsp" %>

<%
DLItemSelectorViewDisplayContext dlItemSelectorViewDisplayContext = (DLItemSelectorViewDisplayContext)request.getAttribute(DLItemSelectorView.DL_ITEM_SELECTOR_VIEW_DISPLAY_CONTEXT);

ItemSelectorCriterion itemSelectorCriterion = dlItemSelectorViewDisplayContext.getItemSelectorCriterion();

int cur = ParamUtil.getInteger(request, SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_CUR);
int delta = ParamUtil.getInteger(request, SearchContainer.DEFAULT_DELTA_PARAM, SearchContainer.DEFAULT_DELTA);

int[] startAndEnd = SearchPaginationUtil.calculateStartAndEnd(cur, delta);

int start = startAndEnd[0];
int end = startAndEnd[1];

List repositoryEntries = null;
int repositoryEntriesCount = 0;

long folderId = dlItemSelectorViewDisplayContext.getFolderId(request);
String[] mimeTypes = dlItemSelectorViewDisplayContext.getMimeTypes();

if (dlItemSelectorViewDisplayContext.isSearch()) {
	SearchContext searchContext = SearchContextFactory.getInstance(request);

	searchContext.setAttribute("mimeTypes", mimeTypes);
	searchContext.setEnd(end);
	searchContext.setFolderIds(new long[] {dlItemSelectorViewDisplayContext.getFolderId(request)});
	searchContext.setStart(start);

	Hits hits = DLAppServiceUtil.search(themeDisplay.getScopeGroupId(), searchContext);

	repositoryEntriesCount = hits.getLength();

	Document[] docs = hits.getDocs();

	repositoryEntries = new ArrayList(docs.length);

	List<SearchResult> searchResultsList = SearchResultUtil.getSearchResults(hits, locale);

	for (int i = 0; i < searchResultsList.size(); i++) {
		SearchResult searchResult = searchResultsList.get(i);

		String className = searchResult.getClassName();

		if (className.equals(DLFileEntryConstants.getClassName()) || FileEntry.class.isAssignableFrom(Class.forName(className))) {
			repositoryEntries.add(DLAppServiceUtil.getFileEntry(searchResult.getClassPK()));
		}
		else if (className.equals(DLFileShortcutConstants.getClassName())) {
			repositoryEntries.add(DLAppServiceUtil.getFileShortcut(searchResult.getClassPK()));
		}
		else if (className.equals(DLFolderConstants.getClassName())) {
			repositoryEntries.add(DLAppServiceUtil.getFolder(searchResult.getClassPK()));
		}
		else {
			continue;
		}
	}
}
else {
	String orderByCol = ParamUtil.getString(request, "orderByCol", "title");
	String orderByType = ParamUtil.getString(request, "orderByType", "asc");

	repositoryEntriesCount = DLAppServiceUtil.getFoldersAndFileEntriesAndFileShortcutsCount(themeDisplay.getScopeGroupId(), folderId, WorkflowConstants.STATUS_APPROVED, mimeTypes, false);
	repositoryEntries = DLAppServiceUtil.getFoldersAndFileEntriesAndFileShortcuts(themeDisplay.getScopeGroupId(), folderId, WorkflowConstants.STATUS_APPROVED, mimeTypes, false, start, end, DLUtil.getRepositoryModelOrderByComparator(orderByCol, orderByType, true));
}
%>

<liferay-item-selector:repository-entry-browser
	desiredItemSelectorReturnTypes="<%= itemSelectorCriterion.getDesiredItemSelectorReturnTypes() %>"
	emptyResultsMessage='<%= LanguageUtil.get(request, "there-are-no-documents-or-media-files-in-this-folder") %>'
	itemSelectedEventName="<%= dlItemSelectorViewDisplayContext.getItemSelectedEventName() %>"
	portletURL="<%= dlItemSelectorViewDisplayContext.getPortletURL(request, liferayPortletResponse) %>"
	repositoryEntries="<%= repositoryEntries %>"
	repositoryEntriesCount="<%= repositoryEntriesCount %>"
	showBreadcrumb="<%= true %>"
	tabName="<%= dlItemSelectorViewDisplayContext.getTitle(locale) %>"
	uploadURL="<%= dlItemSelectorViewDisplayContext.getUploadURL(request, liferayPortletResponse) %>"
/>