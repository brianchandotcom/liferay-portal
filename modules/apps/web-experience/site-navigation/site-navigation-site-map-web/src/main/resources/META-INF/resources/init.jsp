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

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/ddm" prefix="liferay-ddm" %><%@
taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>

<%@ page import="com.liferay.portal.kernel.model.Layout" %><%@
page import="com.liferay.portal.kernel.model.LayoutSet" %><%@
page import="com.liferay.portal.kernel.service.LayoutLocalServiceUtil" %><%@
page import="com.liferay.portal.kernel.util.Constants" %><%@
page import="com.liferay.portal.kernel.util.StringPool" %><%@
page import="com.liferay.portal.kernel.util.Validator" %><%@
page import="com.liferay.portal.util.LayoutDescription" %><%@
page import="com.liferay.site.navigation.site.map.web.configuration.SiteNavigationSiteMapPortletInstanceConfiguration" %><%@
page import="com.liferay.site.navigation.site.map.web.internal.display.context.SiteNavigationSiteMapDisplayContext" %>

<%@ page import="java.util.HashMap" %><%@
page import="java.util.List" %><%@
page import="java.util.Map" %><%@
page import="java.util.Objects" %>

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
SiteNavigationSiteMapDisplayContext siteNavigationSiteMapDisplayContext = new SiteNavigationSiteMapDisplayContext(request);

SiteNavigationSiteMapPortletInstanceConfiguration siteNavigationSiteMapPortletInstanceConfiguration = siteNavigationSiteMapDisplayContext.getSiteNavigationSiteMapPortletInstanceConfiguration();
%>

<%@ include file="/init-ext.jsp" %>