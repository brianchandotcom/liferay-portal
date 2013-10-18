<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

long rootFolderId = PrefsParamUtil.getLong(portletPreferences, request, "rootFolderId", DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);
String rootFolderName = StringPool.BLANK;

if (rootFolderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
	try {
		Folder rootFolder = DLAppLocalServiceUtil.getFolder(rootFolderId);

		rootFolderName = rootFolder.getName();
	}
	catch (NoSuchFolderException nsfe) {
	}
}

boolean showFoldersSearch = PrefsParamUtil.getBoolean(portletPreferences, request, "showFoldersSearch", true);
boolean showSubfolders = PrefsParamUtil.getBoolean(portletPreferences, request, "showSubfolders", true);
int foldersPerPage = PrefsParamUtil.getInteger(portletPreferences, request, "foldersPerPage", SearchContainer.DEFAULT_DELTA);

String defaultFolderColumns = "name,num-of-folders,num-of-documents";

String portletId = portletDisplay.getId();

if (portletId.equals(PortletKeys.PORTLET_CONFIGURATION)) {
	portletId = portletResource;
}

boolean showActions = PrefsParamUtil.getBoolean(portletPreferences, request, "showActions");
boolean showAddFolderButton = false;
boolean showFolderMenu = PrefsParamUtil.getBoolean(portletPreferences, request, "showFolderMenu");
boolean showTabs = PrefsParamUtil.getBoolean(portletPreferences, request, "showTabs");

if (portletId.equals(PortletKeys.DOCUMENT_LIBRARY)) {
	showActions = true;
	showAddFolderButton = true;
	showFolderMenu = true;
	showTabs = true;
}

if (showActions) {
	defaultFolderColumns += ",action";
}

String allFolderColumns = defaultFolderColumns;

String[] folderColumns = StringUtil.split(PrefsParamUtil.getString(portletPreferences, request, "folderColumns", defaultFolderColumns));

if (!showActions) {
	folderColumns = ArrayUtil.remove(folderColumns, "action");
}

int fileEntriesPerPage = PrefsParamUtil.getInteger(portletPreferences, request, "fileEntriesPerPage", SearchContainer.DEFAULT_DELTA);

String defaultFileEntryColumns = "name,size";

if (PropsValues.DL_FILE_ENTRY_BUFFERED_INCREMENT_ENABLED) {
	defaultFileEntryColumns += ",downloads";
}

defaultFileEntryColumns += ",locked";

if (showActions) {
	defaultFileEntryColumns += ",action";
}

String allFileEntryColumns = defaultFileEntryColumns;

String[] fileEntryColumns = StringUtil.split(PrefsParamUtil.getString(portletPreferences, request, "fileEntryColumns", defaultFileEntryColumns));

if (!showActions) {
	fileEntryColumns = ArrayUtil.remove(fileEntryColumns, "action");
}

boolean enableRatings = GetterUtil.getBoolean(portletPreferences.getValue("enableRatings", null), true);
boolean enableCommentRatings = GetterUtil.getBoolean(portletPreferences.getValue("enableCommentRatings", null), true);

boolean mergedView = false;

Format dateFormatDateTime = FastDateFormatFactoryUtil.getDateTime(locale, timeZone);
%>

<%@ include file="/html/portlet/document_library_display/init-ext.jsp" %>