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
DLItemSelectorViewDisplayContext dlItemSelectorViewDisplayContext = (DLItemSelectorViewDisplayContext)request.getAttribute(DLItemSelectorWebKeys.DL_ITEM_SELECTOR_VIEW_DISPLAY_CONTEXT);
%>

<liferay-item-selector:repository-entry-browser
	emptyResultsMessage='<%= LanguageUtil.get(request, "there-are-no-documents-or-media-files-in-this-folder") %>'
	extensions="<%= ListUtil.toList(dlItemSelectorViewDisplayContext.getExtensions()) %>"
	itemSelectedEventName="<%= dlItemSelectorViewDisplayContext.getItemSelectedEventName() %>"
	itemSelectorReturnTypeResolver="<%= dlItemSelectorViewDisplayContext.getItemSelectorReturnTypeResolver() %>"
	maxFileSize="<%= DLValidatorUtil.getMaxAllowableSize() %>"
	portletURL="<%= dlItemSelectorViewDisplayContext.getPortletURL(liferayPortletResponse) %>"
	repositoryEntries="<%= dlItemSelectorViewDisplayContext.getRepositoryEntries() %>"
	repositoryEntriesCount="<%= dlItemSelectorViewDisplayContext.getRepositoryEntriesCount() %>"
	showBreadcrumb="<%= true %>"
	tabName="<%= dlItemSelectorViewDisplayContext.getTitle(locale) %>"
	uploadURL="<%= dlItemSelectorViewDisplayContext.getUploadURL(liferayPortletResponse) %>"
/>