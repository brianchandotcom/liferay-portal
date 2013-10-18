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

<%@ page import="com.liferay.portal.kernel.search.Document" %><%@
page import="com.liferay.portal.kernel.search.DocumentComparator" %><%@
page import="com.liferay.portal.plugin.PluginPackageUtil" %><%@
page import="com.liferay.portlet.softwarecatalog.DuplicateProductEntryModuleIdException" %><%@
page import="com.liferay.portlet.softwarecatalog.DuplicateProductVersionDirectDownloadURLException" %><%@
page import="com.liferay.portlet.softwarecatalog.FrameworkVersionNameException" %><%@
page import="com.liferay.portlet.softwarecatalog.LicenseNameException" %><%@
page import="com.liferay.portlet.softwarecatalog.NoSuchFrameworkVersionException" %><%@
page import="com.liferay.portlet.softwarecatalog.NoSuchLicenseException" %><%@
page import="com.liferay.portlet.softwarecatalog.NoSuchProductEntryException" %><%@
page import="com.liferay.portlet.softwarecatalog.NoSuchProductVersionException" %><%@
page import="com.liferay.portlet.softwarecatalog.ProductEntryAuthorException" %><%@
page import="com.liferay.portlet.softwarecatalog.ProductEntryLicenseException" %><%@
page import="com.liferay.portlet.softwarecatalog.ProductEntryNameException" %><%@
page import="com.liferay.portlet.softwarecatalog.ProductEntryPageURLException" %><%@
page import="com.liferay.portlet.softwarecatalog.ProductEntryScreenshotsException" %><%@
page import="com.liferay.portlet.softwarecatalog.ProductEntryShortDescriptionException" %><%@
page import="com.liferay.portlet.softwarecatalog.ProductEntryTypeException" %><%@
page import="com.liferay.portlet.softwarecatalog.ProductVersionChangeLogException" %><%@
page import="com.liferay.portlet.softwarecatalog.ProductVersionDownloadURLException" %><%@
page import="com.liferay.portlet.softwarecatalog.ProductVersionFrameworkVersionException" %><%@
page import="com.liferay.portlet.softwarecatalog.ProductVersionNameException" %><%@
page import="com.liferay.portlet.softwarecatalog.RequiredLicenseException" %><%@
page import="com.liferay.portlet.softwarecatalog.UnavailableProductVersionDirectDownloadURLException" %><%@
page import="com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion" %><%@
page import="com.liferay.portlet.softwarecatalog.model.SCLicense" %><%@
page import="com.liferay.portlet.softwarecatalog.model.SCProductEntry" %><%@
page import="com.liferay.portlet.softwarecatalog.model.SCProductScreenshot" %><%@
page import="com.liferay.portlet.softwarecatalog.model.SCProductVersion" %><%@
page import="com.liferay.portlet.softwarecatalog.service.SCFrameworkVersionLocalServiceUtil" %><%@
page import="com.liferay.portlet.softwarecatalog.service.SCFrameworkVersionServiceUtil" %><%@
page import="com.liferay.portlet.softwarecatalog.service.SCLicenseLocalServiceUtil" %><%@
page import="com.liferay.portlet.softwarecatalog.service.SCProductEntryLocalServiceUtil" %><%@
page import="com.liferay.portlet.softwarecatalog.service.SCProductScreenshotLocalServiceUtil" %><%@
page import="com.liferay.portlet.softwarecatalog.service.SCProductVersionServiceUtil" %><%@
page import="com.liferay.portlet.softwarecatalog.service.permission.SCFrameworkVersionPermission" %><%@
page import="com.liferay.portlet.softwarecatalog.service.permission.SCLicensePermission" %><%@
page import="com.liferay.portlet.softwarecatalog.service.permission.SCPermission" %><%@
page import="com.liferay.portlet.softwarecatalog.service.permission.SCProductEntryPermission" %><%@
page import="com.liferay.portlet.softwarecatalog.util.SCUtil" %>

<%
PortalPreferences portalPreferences = PortletPreferencesFactoryUtil.getPortalPreferences(request);

Format dateFormatDateTime = FastDateFormatFactoryUtil.getDateTime(locale, timeZone);
%>

<%@ include file="/html/portlet/software_catalog/init-ext.jsp" %>