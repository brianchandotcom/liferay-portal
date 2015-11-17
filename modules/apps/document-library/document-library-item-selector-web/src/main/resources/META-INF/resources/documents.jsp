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

SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, "curDocuments", SearchContainer.DEFAULT_DELTA, dlItemSelectorViewDisplayContext.getPortletURL(request, liferayPortletResponse), null, LanguageUtil.get(request, "there-are-no-documents-or-media-files-in-this-folder"));

String orderByCol = ParamUtil.getString(request, "orderByCol", "title");
String orderByType = ParamUtil.getString(request, "orderByType", "asc");

OrderByComparator<?> orderByComparator = DLUtil.getRepositoryModelOrderByComparator(orderByCol, orderByType, true);

searchContainer.setOrderByComparator(orderByComparator);

List results = null;
int total = 0;

long folderId = dlItemSelectorViewDisplayContext.getFolderId(request);
String[] mimeTypes = dlItemSelectorViewDisplayContext.getMimeTypes();

if (dlItemSelectorViewDisplayContext.isSearch()) {
	SearchContext searchContext = SearchContextFactory.getInstance(request);

	searchContext.setAttribute("mimeTypes", mimeTypes);
	searchContext.setEnd(searchContainer.getEnd());
	searchContext.setFolderIds(new long[] {dlItemSelectorViewDisplayContext.getFolderId(request)});
	searchContext.setStart(searchContainer.getStart());

	Hits hits = DLAppServiceUtil.search(themeDisplay.getScopeGroupId(), searchContext);

	total = hits.getLength();

	Document[] docs = hits.getDocs();

	results = new ArrayList(docs.length);

	List<SearchResult> searchResultsList = SearchResultUtil.getSearchResults(hits, locale);

	for (int i = 0; i < searchResultsList.size(); i++) {
		SearchResult searchResult = searchResultsList.get(i);

		String className = searchResult.getClassName();

		if (className.equals(DLFileEntryConstants.getClassName()) || FileEntry.class.isAssignableFrom(Class.forName(className))) {
			results.add(DLAppServiceUtil.getFileEntry(searchResult.getClassPK()));
		}
		else if (className.equals(DLFileShortcutConstants.getClassName())) {
			results.add(DLAppServiceUtil.getFileShortcut(searchResult.getClassPK()));
		}
		else if (className.equals(DLFolderConstants.getClassName())) {
			results.add(DLAppServiceUtil.getFolder(searchResult.getClassPK()));
		}
		else {
			continue;
		}
	}
}
else {
	total = DLAppServiceUtil.getFoldersAndFileEntriesAndFileShortcutsCount(themeDisplay.getScopeGroupId(), folderId, WorkflowConstants.STATUS_APPROVED, mimeTypes, false);
	results = DLAppServiceUtil.getFoldersAndFileEntriesAndFileShortcuts(themeDisplay.getScopeGroupId(), folderId, WorkflowConstants.STATUS_APPROVED, mimeTypes, false, searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator());
}

searchContainer.setTotal(total);
searchContainer.setResults(results);
%>

<liferay-item-selector:repository-entry-browser
	desiredItemSelectorReturnTypes="<%= itemSelectorCriterion.getDesiredItemSelectorReturnTypes() %>"
	itemSelectedEventName="<%= dlItemSelectorViewDisplayContext.getItemSelectedEventName() %>"
	portletURL="<%= dlItemSelectorViewDisplayContext.getPortletURL(request, liferayPortletResponse) %>"
	searchContainer="<%= searchContainer %>"
	showBreadcrumb="<%= true %>"
	tabName="<%= dlItemSelectorViewDisplayContext.getTitle(locale) %>"
	uploadURL="<%= dlItemSelectorViewDisplayContext.getUploadURL(request, liferayPortletResponse) %>"
/>