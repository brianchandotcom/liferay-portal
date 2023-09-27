<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
DLItemSelectorViewDisplayContext dlItemSelectorViewDisplayContext = (DLItemSelectorViewDisplayContext)request.getAttribute(DLItemSelectorWebKeys.DL_ITEM_SELECTOR_VIEW_DISPLAY_CONTEXT);
%>

<liferay-item-selector:repository-entry-browser
	allowedCreationMenuUIItemKeys="<%= dlItemSelectorViewDisplayContext.getAllowedCreationMenuUIItemKeys() %>"
	editImageURL="<%= dlItemSelectorViewDisplayContext.getEditImageURL(liferayPortletResponse) %>"
	emptyResultsMessage='<%= LanguageUtil.get(request, "there-are-no-documents-or-media-files-in-this-folder") %>'
	extensions="<%= ListUtil.fromArray(dlItemSelectorViewDisplayContext.getExtensions()) %>"
	folderId="<%= dlItemSelectorViewDisplayContext.getFolderId() %>"
	itemSelectedEventName="<%= dlItemSelectorViewDisplayContext.getItemSelectedEventName() %>"
	itemSelectorReturnTypeResolver="<%= dlItemSelectorViewDisplayContext.getItemSelectorReturnTypeResolver() %>"
	maxFileSize="<%= DLValidatorUtil.getMaxAllowableSize(themeDisplay.getScopeGroupId(), null) %>"
	mimeTypeRestriction="<%= dlItemSelectorViewDisplayContext.getMimeTypeRestriction() %>"
	portletURL="<%= dlItemSelectorViewDisplayContext.getPortletURL(liferayPortletResponse) %>"
	repositoryEntries="<%= dlItemSelectorViewDisplayContext.getRepositoryEntries() %>"
	repositoryEntriesCount="<%= dlItemSelectorViewDisplayContext.getRepositoryEntriesCount() %>"
	showBreadcrumb="<%= true %>"
	showDragAndDropZone="<%= dlItemSelectorViewDisplayContext.isShowDragAndDropZone() %>"
	tabName="<%= dlItemSelectorViewDisplayContext.getTitle() %>"
	uploadURL="<%= dlItemSelectorViewDisplayContext.getUploadURL(liferayPortletResponse) %>"
/>