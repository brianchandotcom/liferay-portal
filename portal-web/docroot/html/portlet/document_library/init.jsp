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

<%@ page import="com.liferay.portal.DuplicateLockException" %><%@
page import="com.liferay.portal.InvalidRepositoryException" %><%@
page import="com.liferay.portal.NoSuchRepositoryException" %><%@
page import="com.liferay.portal.NoSuchWorkflowDefinitionLinkException" %><%@
page import="com.liferay.portal.kernel.repository.RepositoryException" %><%@
page import="com.liferay.portal.kernel.repository.model.Folder" %><%@
page import="com.liferay.portal.kernel.search.Document" %><%@
page import="com.liferay.portal.kernel.search.SearchResult" %><%@
page import="com.liferay.portal.repository.util.RepositoryFactoryUtil" %><%@
page import="com.liferay.portlet.documentlibrary.DuplicateFileEntryTypeException" %><%@
page import="com.liferay.portlet.documentlibrary.DuplicateFileException" %><%@
page import="com.liferay.portlet.documentlibrary.DuplicateFolderNameException" %><%@
page import="com.liferay.portlet.documentlibrary.DuplicateRepositoryNameException" %><%@
page import="com.liferay.portlet.documentlibrary.FileExtensionException" %><%@
page import="com.liferay.portlet.documentlibrary.FileMimeTypeException" %><%@
page import="com.liferay.portlet.documentlibrary.FileNameException" %><%@
page import="com.liferay.portlet.documentlibrary.FileShortcutPermissionException" %><%@
page import="com.liferay.portlet.documentlibrary.FolderNameException" %><%@
page import="com.liferay.portlet.documentlibrary.InvalidFileVersionException" %><%@
page import="com.liferay.portlet.documentlibrary.InvalidFolderException" %><%@
page import="com.liferay.portlet.documentlibrary.NoSuchDirectoryException" %><%@
page import="com.liferay.portlet.documentlibrary.NoSuchFileEntryException" %><%@
page import="com.liferay.portlet.documentlibrary.NoSuchFileException" %><%@
page import="com.liferay.portlet.documentlibrary.NoSuchFolderException" %><%@
page import="com.liferay.portlet.documentlibrary.NoSuchMetadataSetException" %><%@
page import="com.liferay.portlet.documentlibrary.RepositoryNameException" %><%@
page import="com.liferay.portlet.documentlibrary.SourceFileNameException" %><%@
page import="com.liferay.portlet.documentlibrary.action.EditFileEntryAction" %><%@
page import="com.liferay.portlet.documentlibrary.antivirus.AntivirusScannerException" %><%@
page import="com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata" %><%@
page import="com.liferay.portlet.documentlibrary.model.DLFileEntryType" %><%@
page import="com.liferay.portlet.documentlibrary.model.DLFileEntryTypeConstants" %><%@
page import="com.liferay.portlet.documentlibrary.model.DLFileShortcut" %><%@
page import="com.liferay.portlet.documentlibrary.model.DLFileVersion" %><%@
page import="com.liferay.portlet.documentlibrary.model.DLFolder" %><%@
page import="com.liferay.portlet.documentlibrary.model.DLFolderConstants" %><%@
page import="com.liferay.portlet.documentlibrary.model.DLSearchConstants" %><%@
page import="com.liferay.portlet.documentlibrary.search.EntriesChecker" %><%@
page import="com.liferay.portlet.documentlibrary.service.DLAppServiceUtil" %><%@
page import="com.liferay.portlet.documentlibrary.service.DLFileEntryMetadataLocalServiceUtil" %><%@
page import="com.liferay.portlet.documentlibrary.service.DLFileEntryTypeLocalServiceUtil" %><%@
page import="com.liferay.portlet.documentlibrary.service.DLFileEntryTypeServiceUtil" %><%@
page import="com.liferay.portlet.documentlibrary.service.permission.DLFileEntryPermission" %><%@
page import="com.liferay.portlet.documentlibrary.service.permission.DLFileEntryTypePermission" %><%@
page import="com.liferay.portlet.documentlibrary.service.permission.DLFileShortcutPermission" %><%@
page import="com.liferay.portlet.documentlibrary.service.permission.DLFolderPermission" %><%@
page import="com.liferay.portlet.documentlibrary.service.permission.DLPermission" %><%@
page import="com.liferay.portlet.documentlibrary.util.AudioProcessorUtil" %><%@
page import="com.liferay.portlet.documentlibrary.util.DLProcessorRegistryUtil" %><%@
page import="com.liferay.portlet.documentlibrary.util.ImageProcessorUtil" %><%@
page import="com.liferay.portlet.documentlibrary.util.PDFProcessorUtil" %><%@
page import="com.liferay.portlet.documentlibrary.util.RawMetadataProcessor" %><%@
page import="com.liferay.portlet.documentlibrary.util.VideoProcessorUtil" %><%@
page import="com.liferay.portlet.dynamicdatamapping.RequiredStructureException" %><%@
page import="com.liferay.portlet.dynamicdatamapping.StorageFieldRequiredException" %><%@
page import="com.liferay.portlet.dynamicdatamapping.StructureDuplicateElementException" %><%@
page import="com.liferay.portlet.dynamicdatamapping.StructureNameException" %><%@
page import="com.liferay.portlet.dynamicdatamapping.search.StructureSearch" %><%@
page import="com.liferay.portlet.dynamicdatamapping.search.StructureSearchTerms" %><%@
page import="com.liferay.portlet.dynamicdatamapping.service.DDMStorageLinkLocalServiceUtil" %><%@
page import="com.liferay.portlet.dynamicdatamapping.storage.Fields" %><%@
page import="com.liferay.portlet.dynamicdatamapping.storage.StorageEngineUtil" %><%@
page import="com.liferay.portlet.dynamicdatamapping.util.DDMXSDUtil" %><%@
page import="com.liferay.portlet.dynamicdatamapping.util.comparator.StructureStructureKeyComparator" %><%@
page import="com.liferay.portlet.journal.search.FileEntryDisplayTerms" %><%@
page import="com.liferay.portlet.journal.search.FileEntrySearch" %>

<%
PortalPreferences portalPreferences = PortletPreferencesFactoryUtil.getPortalPreferences(liferayPortletRequest);

String portletResource = ParamUtil.getString(request, "portletResource");

String[] displayViews = StringUtil.split(PrefsParamUtil.getString(portletPreferences, request, "displayViews", StringUtil.merge(PropsValues.DL_DISPLAY_VIEWS)));
int entriesPerPage = PrefsParamUtil.getInteger(portletPreferences, request, "entriesPerPage", SearchContainer.DEFAULT_DELTA);
int numberOfPages = PrefsParamUtil.getInteger(portletPreferences, request, "numberOfPages", PropsValues.SEARCH_CONTAINER_PAGE_ITERATOR_MAX_PAGES);

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

String portletId = portletDisplay.getId();

if (portletId.equals(PortletKeys.PORTLET_CONFIGURATION)) {
	portletId = portletResource;
	portletName = portletResource;
}

boolean showActions = PrefsParamUtil.getBoolean(portletPreferences, request, "showActions");
boolean showAssetMetadata = ParamUtil.getBoolean(request, "showAssetMetadata");
boolean showAddFolderButton = false;
boolean showComments = ParamUtil.getBoolean(request, "showComments", true);
boolean showFolderMenu = PrefsParamUtil.getBoolean(portletPreferences, request, "showFolderMenu");
boolean showHeader = ParamUtil.getBoolean(request, "showHeader", true);
boolean showMinimalActionButtons = ParamUtil.getBoolean(request, "showMinimalActionButtons");
boolean showTabs = PrefsParamUtil.getBoolean(portletPreferences, request, "showTabs");

if (portletName.equals(PortletKeys.DOCUMENT_LIBRARY)) {
	showActions = true;
	showAssetMetadata = true;
	showAddFolderButton = true;
	showFolderMenu = true;
	showTabs = true;
	showMinimalActionButtons = true;
}
else if (portletName.equals(PortletKeys.ASSET_PUBLISHER) || portletName.equals(PortletKeys.MEDIA_GALLERY_DISPLAY) || portletName.equals(PortletKeys.DOCUMENT_LIBRARY_DISPLAY) || portletName.equals(PortletKeys.TRASH)) {
	showAssetMetadata = true;
}

boolean enableRelatedAssets = GetterUtil.getBoolean(portletPreferences.getValue("enableRelatedAssets", null), true);

String defaultEntryColumns = "name,size,status";

if (PropsValues.DL_FILE_ENTRY_BUFFERED_INCREMENT_ENABLED) {
	defaultEntryColumns += ",downloads";
}

if (showActions) {
	defaultEntryColumns += ",action";
}

String allEntryColumns = defaultEntryColumns + ",modified-date,create-date";

String[] entryColumns = StringUtil.split(PrefsParamUtil.getString(portletPreferences, request, "entryColumns", defaultEntryColumns));

if (!showActions) {
	entryColumns = ArrayUtil.remove(entryColumns, "action");
}
else if (!portletId.equals(PortletKeys.DOCUMENT_LIBRARY) && !ArrayUtil.contains(entryColumns, "action")) {
	entryColumns = ArrayUtil.append(entryColumns, "action");
}

boolean enableRatings = GetterUtil.getBoolean(portletPreferences.getValue("enableRatings", null), true);
boolean enableCommentRatings = GetterUtil.getBoolean(portletPreferences.getValue("enableCommentRatings", null), true);

Format dateFormatDateTime = FastDateFormatFactoryUtil.getDateTime(locale, timeZone);
%>

<%@ include file="/html/portlet/document_library/init-ext.jsp" %>