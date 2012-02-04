<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

<%@ page import="com.liferay.portal.deploy.DeployUtil" %><%@
page import="com.liferay.portal.kernel.plugin.License" %><%@
page import="com.liferay.portal.kernel.plugin.PluginPackage" %><%@
page import="com.liferay.portal.kernel.plugin.RemotePluginPackageRepository" %><%@
page import="com.liferay.portal.kernel.plugin.Screenshot" %><%@
page import="com.liferay.portal.kernel.search.Document" %><%@
page import="com.liferay.portal.kernel.search.DocumentComparator" %><%@
page import="com.liferay.portal.plugin.PluginPackageException" %><%@
page import="com.liferay.portal.plugin.PluginPackageImpl" %><%@
page import="com.liferay.portal.plugin.PluginPackageUtil" %><%@
page import="com.liferay.portal.plugin.RepositoryReport" %>

<%
PortalPreferences portalPreferences = PortletPreferencesFactoryUtil.getPortalPreferences(request);

Format dateFormatDateTime = FastDateFormatFactoryUtil.getDateTime(locale, timeZone);
%>

<%@ include file="/html/portlet/plugin_installer/init-ext.jsp" %>