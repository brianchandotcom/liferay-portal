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

<%@ page import="com.liferay.portal.InvalidRepositoryException" %><%@
page import="com.liferay.portal.NoSuchRepositoryException" %><%@
page import="com.liferay.portal.kernel.lock.DuplicateLockException" %><%@
page import="com.liferay.portal.kernel.repository.LocalRepository" %><%@
page import="com.liferay.portal.kernel.repository.RepositoryException" %><%@
page import="com.liferay.portal.kernel.repository.RepositoryProviderUtil" %><%@
page import="com.liferay.portal.kernel.repository.capabilities.TemporaryFileEntriesCapability" %><%@
page import="com.liferay.portal.kernel.search.SearchResult" %><%@
page import="com.liferay.portal.kernel.servlet.taglib.ui.ToolbarItem" %><%@
page import="com.liferay.portal.repository.registry.RepositoryClassDefinition" %><%@
page import="com.liferay.portal.repository.registry.RepositoryClassDefinitionCatalogUtil" %><%@
page import="com.liferay.portlet.admin.util.PortalAdministrationApplicationType" %><%@
page import="com.liferay.portlet.documentlibrary.DLGroupServiceSettings" %><%@
page import="com.liferay.portlet.documentlibrary.DLPortletInstanceSettings" %><%@
page import="com.liferay.portlet.documentlibrary.DuplicateFileEntryTypeException" %><%@
page import="com.liferay.portlet.documentlibrary.DuplicateFolderNameException" %><%@
page import="com.liferay.portlet.documentlibrary.DuplicateRepositoryNameException" %><%@
page import="com.liferay.portlet.documentlibrary.FileMimeTypeException" %><%@
page import="com.liferay.portlet.documentlibrary.FileShortcutPermissionException" %><%@
page import="com.liferay.portlet.documentlibrary.FolderNameException" %><%@
page import="com.liferay.portlet.documentlibrary.InvalidFileVersionException" %><%@
page import="com.liferay.portlet.documentlibrary.InvalidFolderException" %><%@
page import="com.liferay.portlet.documentlibrary.NoSuchFileEntryException" %><%@
page import="com.liferay.portlet.documentlibrary.NoSuchFolderException" %><%@
page import="com.liferay.portlet.documentlibrary.NoSuchMetadataSetException" %><%@
page import="com.liferay.portlet.documentlibrary.RepositoryNameException" %><%@
page import="com.liferay.portlet.documentlibrary.RequiredFileEntryTypeException" %><%@
page import="com.liferay.portlet.documentlibrary.SourceFileNameException" %><%@
page import="com.liferay.portlet.documentlibrary.action.EditFileEntryMVCActionCommand" %><%@
page import="com.liferay.portlet.documentlibrary.display.context.DLDisplayContextProviderUtil" %><%@
page import="com.liferay.portlet.documentlibrary.display.context.DLEditFileEntryDisplayContext" %><%@
page import="com.liferay.portlet.documentlibrary.display.context.DLFilePicker" %><%@
page import="com.liferay.portlet.documentlibrary.display.context.DLViewFileVersionDisplayContext" %><%@
page import="com.liferay.portlet.documentlibrary.display.context.logic.DLPortletInstanceSettingsHelper" %><%@
page import="com.liferay.portlet.documentlibrary.display.context.logic.DLVisualizationHelper" %><%@
page import="com.liferay.portlet.documentlibrary.display.context.util.DLRequestHelper" %><%@
page import="com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata" %><%@
page import="com.liferay.portlet.documentlibrary.model.DLFileEntryType" %><%@
page import="com.liferay.portlet.documentlibrary.model.DLFileEntryTypeConstants" %><%@
page import="com.liferay.portlet.documentlibrary.model.DLFileShortcutConstants" %><%@
page import="com.liferay.portlet.documentlibrary.model.DLFileVersion" %><%@
page import="com.liferay.portlet.documentlibrary.search.EntriesChecker" %><%@
page import="com.liferay.portlet.documentlibrary.service.DLFileEntryMetadataLocalServiceUtil" %><%@
page import="com.liferay.portlet.documentlibrary.service.DLFileEntryTypeLocalServiceUtil" %><%@
page import="com.liferay.portlet.documentlibrary.service.DLFileEntryTypeServiceUtil" %><%@
page import="com.liferay.portlet.documentlibrary.service.permission.DLFileEntryPermission" %><%@
page import="com.liferay.portlet.documentlibrary.service.permission.DLFileEntryTypePermission" %><%@
page import="com.liferay.portlet.documentlibrary.service.permission.DLFileShortcutPermission" %><%@
page import="com.liferay.portlet.documentlibrary.service.permission.DLFolderPermission" %><%@
page import="com.liferay.portlet.documentlibrary.service.permission.DLPermission" %><%@
page import="com.liferay.portlet.documentlibrary.util.AudioProcessorUtil" %><%@
page import="com.liferay.portlet.documentlibrary.util.DLConstants" %><%@
page import="com.liferay.portlet.documentlibrary.util.DLProcessorRegistryUtil" %><%@
page import="com.liferay.portlet.documentlibrary.util.ImageProcessorUtil" %><%@
page import="com.liferay.portlet.documentlibrary.util.PDFProcessorUtil" %><%@
page import="com.liferay.portlet.documentlibrary.util.RawMetadataProcessor" %><%@
page import="com.liferay.portlet.documentlibrary.util.VideoProcessorUtil" %><%@
page import="com.liferay.portlet.dynamicdatamapping.StructureDefinitionException" %><%@
page import="com.liferay.portlet.dynamicdatamapping.StructureDuplicateElementException" %><%@
page import="com.liferay.portlet.dynamicdatamapping.StructureNameException" %><%@
page import="com.liferay.portlet.dynamicdatamapping.search.StructureSearch" %><%@
page import="com.liferay.portlet.dynamicdatamapping.search.StructureSearchTerms" %><%@
page import="com.liferay.portlet.dynamicdatamapping.service.DDMStorageLinkLocalServiceUtil" %><%@
page import="com.liferay.portlet.dynamicdatamapping.storage.DDMFormValues" %><%@
page import="com.liferay.portlet.dynamicdatamapping.storage.StorageEngineUtil" %><%@
page import="com.liferay.portlet.dynamicdatamapping.util.DDMUtil" %><%@
page import="com.liferay.portlet.dynamicdatamapping.util.comparator.StructureStructureKeyComparator" %>

<%
PortalPreferences portalPreferences = PortletPreferencesFactoryUtil.getPortalPreferences(liferayPortletRequest);

DLRequestHelper dlRequestHelper = new DLRequestHelper(request);

String portletId = dlRequestHelper.getResourcePortletId();

portletName = dlRequestHelper.getResourcePortletName();

String portletResource = dlRequestHelper.getPortletResource();

DLPortletInstanceSettings dlPortletInstanceSettings = dlRequestHelper.getDLPortletInstanceSettings();
DLGroupServiceSettings dlGroupServiceSettings = dlRequestHelper.getDLGroupServiceSettings();

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

boolean showComments = ParamUtil.getBoolean(request, "showComments", true);
boolean showHeader = ParamUtil.getBoolean(request, "showHeader", true);

Format dateFormatDateTime = FastDateFormatFactoryUtil.getDateTime(locale, timeZone);
%>

<%@ include file="/html/portlet/document_library/init-ext.jsp" %>