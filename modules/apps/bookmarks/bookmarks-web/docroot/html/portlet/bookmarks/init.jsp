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

<%@ include file="/html/portlet/init.jsp" %>

<%@ page import="com.liferay.bookmarks.exception.EntryURLException" %><%@
page import="com.liferay.bookmarks.exception.FolderNameException" %><%@
page import="com.liferay.bookmarks.exception.NoSuchEntryException" %><%@
page import="com.liferay.bookmarks.exception.NoSuchFolderException" %><%@
page import="com.liferay.bookmarks.model.BookmarksEntry" %><%@
page import="com.liferay.bookmarks.model.BookmarksFolder" %><%@
page import="com.liferay.bookmarks.model.BookmarksFolderConstants" %><%@
page import="com.liferay.bookmarks.search.BookmarksSearcher" %><%@
page import="com.liferay.bookmarks.service.BookmarksEntryServiceUtil" %><%@
page import="com.liferay.bookmarks.service.BookmarksFolderLocalServiceUtil" %><%@
page import="com.liferay.bookmarks.service.BookmarksFolderServiceUtil" %><%@
page import="com.liferay.bookmarks.service.permission.BookmarksEntryPermission" %><%@
page import="com.liferay.bookmarks.service.permission.BookmarksFolderPermission" %><%@
page import="com.liferay.bookmarks.settings.BookmarksSettings" %><%@
page import="com.liferay.bookmarks.web.portlet.util.BookmarksUtil" %>

<%
BookmarksSettings bookmarksSettings = BookmarksSettings.getInstance(scopeGroupId);

PortalPreferences portalPreferences = PortletPreferencesFactoryUtil.getPortalPreferences(request);

String portletResource = ParamUtil.getString(request, "portletResource");

long rootFolderId = bookmarksSettings.getRootFolderId();
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

if (portletId.equals(PortletKeys.PORTLET_CONFIGURATION)) {
	portletId = portletResource;
}

String allFolderColumns = "folder,num-of-folders,num-of-entries";

if (portletId.equals(BookmarksPortletKeys.BOOKMARKS) || portletId.equals(BookmarksPortletKeys.BOOKMARKS_ADMIN)) {
	allFolderColumns += ",action";
}

String[] folderColumns = bookmarksSettings.getFolderColumns();

if (!portletId.equals(BookmarksPortletKeys.BOOKMARKS) && !portletId.equals(BookmarksPortletKeys.BOOKMARKS_ADMIN)) {
	folderColumns = ArrayUtil.remove(folderColumns, "action");
}

String allEntryColumns = "name,url,visits,modified-date";

if (portletId.equals(BookmarksPortletKeys.BOOKMARKS) || portletId.equals(BookmarksPortletKeys.BOOKMARKS_ADMIN)) {
	allEntryColumns += ",action";
}

String[] entryColumns = bookmarksSettings.getEntryColumns();

if (!portletId.equals(BookmarksPortletKeys.BOOKMARKS) && !portletId.equals(BookmarksPortletKeys.BOOKMARKS_ADMIN)) {
	entryColumns = ArrayUtil.remove(entryColumns, "action");
}

Format dateFormatDate = FastDateFormatFactoryUtil.getDate(locale, timeZone);
%>

<%@ include file="/html/portlet/bookmarks/init-ext.jsp" %>