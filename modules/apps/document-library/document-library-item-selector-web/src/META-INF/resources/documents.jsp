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

PortletURL portletURL = dlItemSelectorViewDisplayContext.getPortletURL();

SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, "curDocuments", SearchContainer.DEFAULT_DELTA, portletURL, null, null);

ItemSelectorCriterion dlItemSelectorCriterion = (ItemSelectorCriterion)dlItemSelectorViewDisplayContext.getDLItemSelectorCriterion();

long repositoryId = dlItemSelectorViewDisplayContext.getRepositoryId(request);
long folderId = dlItemSelectorViewDisplayContext.getFolderId(request);
String[] mimeTypes = dlItemSelectorViewDisplayContext.getMimeTypes();

searchContainer.setTotal(DLAppServiceUtil.getFileEntriesCount(repositoryId, folderId, mimeTypes));
searchContainer.setResults(DLAppServiceUtil.getFileEntries(repositoryId, folderId, mimeTypes, searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator()));
%>

<item-selector-ui:browser
	displayStyle="<%= dlItemSelectorViewDisplayContext.getDisplayStyle(request) %>"
	idPrefix="<%= dlItemSelectorViewDisplayContext.getTitle(locale) %>"
	itemSelectedEventName="<%= dlItemSelectorViewDisplayContext.getItemSelectedEventName() %>"
	returnType="<%= ReturnType.parseFirst(dlItemSelectorCriterion.getDesiredReturnTypes()) %>"
	searchContainer="<%= searchContainer %>"
	tabName="<%= dlItemSelectorViewDisplayContext.getTitle(locale) %>"
/>