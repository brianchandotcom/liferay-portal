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

<%@ page import="com.liferay.portal.kernel.search.Document" %><%@
page import="com.liferay.portlet.documentlibrary.DLPortletInstanceSettings" %><%@
page import="com.liferay.portlet.documentlibrary.NoSuchFolderException" %><%@
page import="com.liferay.portlet.documentlibrary.display.context.DLActionsDisplayContext" %><%@
page import="com.liferay.portlet.documentlibrary.model.DLFileShortcut" %><%@
page import="com.liferay.portlet.documentlibrary.service.permission.DLFileEntryPermission" %><%@
page import="com.liferay.portlet.documentlibrary.util.AudioProcessorUtil" %><%@
page import="com.liferay.portlet.documentlibrary.util.ImageProcessorUtil" %><%@
page import="com.liferay.portlet.documentlibrary.util.PDFProcessorUtil" %><%@
page import="com.liferay.portlet.documentlibrary.util.VideoProcessorUtil" %><%@
page import="com.liferay.portlet.imagegallerydisplay.display.context.logic.IGConfigurationDisplayContext" %><%@
page import="com.liferay.portlet.imagegallerydisplay.display.context.IGDisplayContextProviderUtil" %><%@
page import="com.liferay.portlet.imagegallerydisplay.display.context.IGViewFileVersionDisplayContext" %><%@
page import="com.liferay.portlet.imagegallerydisplay.display.context.util.IGRequestHelper" %><%@
page import="com.liferay.portlet.imagegallerydisplay.util.IGUtil" %>

<%
if (layout.isTypeControlPanel()) {
	portletPreferences = PortletPreferencesLocalServiceUtil.getPreferences(themeDisplay.getCompanyId(), scopeGroupId, PortletKeys.PREFS_OWNER_TYPE_GROUP, 0, PortletKeys.DOCUMENT_LIBRARY, null);
}

IGRequestHelper igRequestHelper = new IGRequestHelper(request);

String portletResource = igRequestHelper.getPortletResource();

DLPortletInstanceSettings dlPortletInstanceSettings = igRequestHelper.getDLPortletInstanceSettings();

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

String displayStyle = portletPreferences.getValue("displayStyle", StringPool.BLANK);
long displayStyleGroupId = GetterUtil.getLong(portletPreferences.getValue("displayStyleGroupId", null), themeDisplay.getScopeGroupId());

Format dateFormatDate = FastDateFormatFactoryUtil.getDate(locale, timeZone);
%>

<%@ include file="/html/portlet/image_gallery_display/init-ext.jsp" %>