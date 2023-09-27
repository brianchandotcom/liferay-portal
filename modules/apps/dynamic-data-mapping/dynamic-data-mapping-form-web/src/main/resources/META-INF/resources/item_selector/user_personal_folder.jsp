<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/item_selector/init.jsp" %>

<%
DDMUserPersonalFolderItemSelectorViewDisplayContext ddmUserPersonalFolderItemSelectorViewDisplayContext = (DDMUserPersonalFolderItemSelectorViewDisplayContext)request.getAttribute(DDMUserPersonalFolderItemSelectorViewDisplayContext.class.getName());
%>

<liferay-item-selector:repository-entry-browser
	editImageURL="<%= ddmUserPersonalFolderItemSelectorViewDisplayContext.getEditImageURL(liferayPortletResponse) %>"
	emptyResultsMessage='<%= LanguageUtil.get(request, "there-are-no-documents-or-media-files-in-this-folder") %>'
	folderId="<%= ddmUserPersonalFolderItemSelectorViewDisplayContext.getFolderId() %>"
	itemSelectedEventName="<%= ddmUserPersonalFolderItemSelectorViewDisplayContext.getItemSelectedEventName() %>"
	itemSelectorReturnTypeResolver="<%= ddmUserPersonalFolderItemSelectorViewDisplayContext.getItemSelectorReturnTypeResolver() %>"
	maxFileSize="<%= DLValidatorUtil.getMaxAllowableSize(themeDisplay.getScopeGroupId(), null) %>"
	portletURL="<%= ddmUserPersonalFolderItemSelectorViewDisplayContext.getPortletURL() %>"
	repositoryEntries="<%= ddmUserPersonalFolderItemSelectorViewDisplayContext.getPortletFileEntries() %>"
	repositoryEntriesCount="<%= ddmUserPersonalFolderItemSelectorViewDisplayContext.getPortletFileEntriesCount() %>"
	showSearch="<%= false %>"
	tabName="<%= ddmUserPersonalFolderItemSelectorViewDisplayContext.getTitle(locale) %>"
	uploadURL="<%= ddmUserPersonalFolderItemSelectorViewDisplayContext.getUploadURL(liferayPortletResponse) %>"
/>