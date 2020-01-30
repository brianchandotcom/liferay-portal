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

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/clay" prefix="clay" %><%@
taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %><%@
taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %><%@
taglib uri="http://liferay.com/tld/react" prefix="react" %><%@
taglib uri="http://liferay.com/tld/security" prefix="liferay-security" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %><%@
taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>

<%@ page import="com.liferay.petra.string.StringPool" %><%@
page import="com.liferay.portal.kernel.dao.search.ResultRow" %><%@
page import="com.liferay.portal.kernel.exception.NoSuchLayoutException" %><%@
page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.language.UnicodeLanguageUtil" %><%@
page import="com.liferay.portal.kernel.model.Group" %><%@
page import="com.liferay.portal.kernel.model.ModelHintsUtil" %><%@
page import="com.liferay.portal.kernel.model.Portlet" %><%@
page import="com.liferay.portal.kernel.portlet.LiferayWindowState" %><%@
page import="com.liferay.portal.kernel.security.permission.ActionKeys" %><%@
page import="com.liferay.portal.kernel.service.PortletLocalServiceUtil" %><%@
page import="com.liferay.portal.kernel.util.GetterUtil" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.ParamUtil" %><%@
page import="com.liferay.portal.kernel.util.PortalUtil" %><%@
page import="com.liferay.portal.kernel.util.Validator" %><%@
page import="com.liferay.portal.kernel.util.WebKeys" %><%@
page import="com.liferay.site.navigation.admin.web.internal.constants.SiteNavigationAdminWebKeys" %><%@
page import="com.liferay.site.navigation.admin.web.internal.display.context.SiteNavigationAdminDisplayContext" %><%@
page import="com.liferay.site.navigation.admin.web.internal.display.context.SiteNavigationAdminManagementToolbarDisplayContext" %><%@
page import="com.liferay.site.navigation.admin.web.internal.security.permission.resource.SiteNavigationMenuPermission" %><%@
page import="com.liferay.site.navigation.constants.SiteNavigationConstants" %><%@
page import="com.liferay.site.navigation.exception.InvalidSiteNavigationMenuItemOrderException" %><%@
page import="com.liferay.site.navigation.exception.SiteNavigationMenuItemNameException" %><%@
page import="com.liferay.site.navigation.model.SiteNavigationMenu" %><%@
page import="com.liferay.site.navigation.model.SiteNavigationMenuItem" %><%@
page import="com.liferay.site.navigation.service.SiteNavigationMenuItemLocalServiceUtil" %><%@
page import="com.liferay.site.navigation.type.SiteNavigationMenuItemType" %><%@
page import="com.liferay.site.navigation.type.SiteNavigationMenuItemTypeRegistry" %><%@
page import="com.liferay.taglib.aui.AUIUtil" %><%@
page import="com.liferay.taglib.servlet.PipingServletResponse" %>

<%@ page import="java.util.Date" %><%@
page import="java.util.HashMap" %><%@
page import="java.util.List" %><%@
page import="java.util.Map" %><%@
page import="java.util.Objects" %>

<%@ page import="javax.portlet.ActionRequest" %><%@
page import="javax.portlet.PortletURL" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%@ include file="/init-ext.jsp" %>

<%
SiteNavigationAdminDisplayContext siteNavigationAdminDisplayContext = (SiteNavigationAdminDisplayContext)renderRequest.getAttribute(SiteNavigationAdminWebKeys.SITE_NAVIGATION_MENU_ADMIN_DISPLAY_CONTEXT);

SiteNavigationMenuItemTypeRegistry siteNavigationMenuItemTypeRegistry = siteNavigationAdminDisplayContext.getSiteNavigationMenuItemTypeRegistry();
%>