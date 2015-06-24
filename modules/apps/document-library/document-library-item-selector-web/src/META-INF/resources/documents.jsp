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

SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, "curDocuments", SearchContainer.DEFAULT_DELTA, dlItemSelectorViewDisplayContext.getPortletURL(), null, null);

long repositoryId = dlItemSelectorViewDisplayContext.getRepositoryId(request);
long folderId = dlItemSelectorViewDisplayContext.getFolderId(request);
String[] mimeTypes = dlItemSelectorViewDisplayContext.getMimeTypes();

searchContainer.setTotal(DLAppServiceUtil.getFileEntriesCount(repositoryId, folderId, mimeTypes));
searchContainer.setResults(DLAppServiceUtil.getFileEntries(repositoryId, folderId, mimeTypes, searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator()));
%>

<item-selector-ui:browser
	displayStyle="<%= dlItemSelectorViewDisplayContext.getDisplayStyle(request) %>"
	displayStyleURL="<%= dlItemSelectorViewDisplayContext.getPortletURL() %>"
	itemSelectedEventName="<%= dlItemSelectorViewDisplayContext.getItemSelectedEventName() %>"
	itemSelectorReturnType="<%= ItemSelectorBrowserReturnTypeUtil.getFirstAvailableItemSelectorReturnType(itemSelectorCriterion.getDesiredItemSelectorReturnTypes()) %>"
	searchContainer="<%= searchContainer %>"
	tabName="<%= dlItemSelectorViewDisplayContext.getTitle(locale) %>"
/>