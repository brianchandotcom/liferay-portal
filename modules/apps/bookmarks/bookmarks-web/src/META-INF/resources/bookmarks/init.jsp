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
BookmarksGroupServiceOverriddenConfiguration bookmarksGroupServiceOverriddenConfiguration = ConfigurationFactoryUtil.getConfiguration(BookmarksGroupServiceOverriddenConfiguration.class, new GroupServiceSettingsLocator(scopeGroupId, BookmarksConstants.SERVICE_NAME));

PortalPreferences portalPreferences = PortletPreferencesFactoryUtil.getPortalPreferences(request);

String portletResource = ParamUtil.getString(request, "portletResource");

long rootFolderId = bookmarksGroupServiceOverriddenConfiguration.rootFolderId();
String rootFolderName = StringPool.BLANK;

if (rootFolderId != BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
	try {
		BookmarksFolder rootFolder = BookmarksFolderLocalServiceUtil.getFolder(rootFolderId);

		rootFolderName = rootFolder.getName();

		if (rootFolder.getGroupId() != scopeGroupId) {
			rootFolderId = BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID;
			rootFolderName = StringPool.BLANK;
		}
	}
	catch (NoSuchFolderException nsfe) {
		rootFolderId = BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID;
	}
}

String portletId = portletDisplay.getId();

if (Validator.isNotNull(portletResource)) {
	portletId = portletResource;
}

String allFolderColumns = "folder,num-of-folders,num-of-entries";

if (portletId.equals(BookmarksPortletKeys.BOOKMARKS) || portletId.equals(BookmarksPortletKeys.BOOKMARKS_ADMIN)) {
	allFolderColumns += ",action";
}

String[] folderColumns = bookmarksGroupServiceOverriddenConfiguration.folderColumns();

if (!portletId.equals(BookmarksPortletKeys.BOOKMARKS) && !portletId.equals(BookmarksPortletKeys.BOOKMARKS_ADMIN)) {
	folderColumns = ArrayUtil.remove(folderColumns, "action");
}

String allEntryColumns = "name,url,visits,modified-date";

if (portletId.equals(BookmarksPortletKeys.BOOKMARKS) || portletId.equals(BookmarksPortletKeys.BOOKMARKS_ADMIN)) {
	allEntryColumns += ",action";
}

String[] entryColumns = bookmarksGroupServiceOverriddenConfiguration.entryColumns();

if (!portletId.equals(BookmarksPortletKeys.BOOKMARKS) && !portletId.equals(BookmarksPortletKeys.BOOKMARKS_ADMIN)) {
	entryColumns = ArrayUtil.remove(entryColumns, "action");
}

Format dateFormatDate = FastDateFormatFactoryUtil.getDate(locale, timeZone);
%>

<%@ include file="/bookmarks/init-ext.jsp" %>