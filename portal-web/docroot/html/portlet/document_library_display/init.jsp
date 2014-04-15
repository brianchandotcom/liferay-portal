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

<%@ page import="com.liferay.portal.kernel.repository.RepositoryException" %><%@
page import="com.liferay.portal.kernel.repository.model.Folder" %><%@
page import="com.liferay.portal.kernel.search.Document" %><%@
page import="com.liferay.portlet.documentlibrary.DLPortletInstanceSettings" %><%@
page import="com.liferay.portlet.documentlibrary.NoSuchFolderException" %><%@
page import="com.liferay.portlet.documentlibrary.model.DLFileEntryType" %><%@
page import="com.liferay.portlet.documentlibrary.model.DLFileShortcut" %><%@
page import="com.liferay.portlet.documentlibrary.model.DLFolder" %><%@
page import="com.liferay.portlet.documentlibrary.model.DLFolderConstants" %><%@
page import="com.liferay.portlet.documentlibrary.search.EntriesChecker" %><%@
page import="com.liferay.portlet.documentlibrary.service.DLAppServiceUtil" %><%@
page import="com.liferay.portlet.documentlibrary.service.DLFileEntryTypeLocalServiceUtil" %><%@
page import="com.liferay.portlet.documentlibrary.service.DLFileEntryTypeServiceUtil" %><%@
page import="com.liferay.portlet.documentlibrary.service.permission.DLFileEntryPermission" %><%@
page import="com.liferay.portlet.documentlibrary.service.permission.DLFolderPermission" %><%@
page import="com.liferay.portlet.journal.search.FileEntryDisplayTerms" %><%@
page import="com.liferay.portlet.journal.search.FileEntrySearch" %><%@
page import="com.liferay.portlet.journal.search.FileEntrySearchTerms" %>

<%
String portletResource = ParamUtil.getString(request, "portletResource");

if (layout.isTypeControlPanel()) {
	portletPreferences = PortletPreferencesLocalServiceUtil.getPreferences(themeDisplay.getCompanyId(), scopeGroupId, PortletKeys.PREFS_OWNER_TYPE_GROUP, 0, PortletKeys.DOCUMENT_LIBRARY, null);
}

DLPortletInstanceSettings dlPortletInstanceSettings = new DLPortletInstanceSettings(portletDisplay.getPortletInstanceSettings());

long rootFolderId = dlPortletInstanceSettings.getRootFolderId();
String rootFolderName = StringPool.BLANK;

if (rootFolderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
	try {
		Folder rootFolder = DLAppLocalServiceUtil.getFolder(rootFolderId);

		rootFolderName = rootFolder.getName();

		if (rootFolder.getGroupId() != scopeGroupId) {
			rootFolderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;
			rootFolderName = StringPool.BLANK;
		}
	}
	catch (NoSuchFolderException nsfe) {
		rootFolderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;
	}
}

boolean showFoldersSearch = dlPortletInstanceSettings.getShowFoldersSearch();
boolean showSubfolders = dlPortletInstanceSettings.getShowSubfolders();
int foldersPerPage = dlPortletInstanceSettings.getFoldersPerPage();

String allFolderColumns = "name,num-of-folders,num-of-documents";

String portletId = portletDisplay.getId();

if (portletId.equals(PortletKeys.PORTLET_CONFIGURATION)) {
	portletId = portletResource;
}

boolean showActions = dlPortletInstanceSettings.getShowActions();
boolean showAddFolderButton = false;
boolean showFolderMenu = dlPortletInstanceSettings.getShowFolderMenu();
boolean showTabs = dlPortletInstanceSettings.getShowTabs();

if (portletId.equals(PortletKeys.DOCUMENT_LIBRARY) || portletId.equals(PortletKeys.DOCUMENT_LIBRARY_ADMIN)) {
	showActions = true;
	showAddFolderButton = true;
	showFolderMenu = true;
	showTabs = true;
}

if (showActions) {
	allFolderColumns += ",action";
}

String[] folderColumns = StringUtil.split(dlPortletInstanceSettings.getFolderColumns());

if (!showActions) {
	folderColumns = ArrayUtil.remove(folderColumns, "action");
}

int fileEntriesPerPage = dlPortletInstanceSettings.getFileEntriesPerPage();

String allFileEntryColumns = "name,size";

if (PropsValues.DL_FILE_ENTRY_BUFFERED_INCREMENT_ENABLED) {
	allFileEntryColumns += ",downloads";
}

allFileEntryColumns += ",locked";

if (showActions) {
	allFileEntryColumns += ",action";
}

String[] fileEntryColumns = StringUtil.split(dlPortletInstanceSettings.getFileEntryColumns());

if (!showActions) {
	fileEntryColumns = ArrayUtil.remove(fileEntryColumns, "action");
}

boolean enableRatings = dlPortletInstanceSettings.getEnableRatings();
boolean enableCommentRatings = dlPortletInstanceSettings.getEnableCommentRatings();

boolean mergedView = false;

Format dateFormatDateTime = FastDateFormatFactoryUtil.getDateTime(locale, timeZone);
%>

<%@ include file="/html/portlet/document_library_display/init-ext.jsp" %>