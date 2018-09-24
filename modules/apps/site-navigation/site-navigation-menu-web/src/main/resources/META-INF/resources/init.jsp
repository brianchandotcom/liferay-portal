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
taglib uri="http://liferay.com/tld/ddm" prefix="liferay-ddm" %><%@
taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %><%@
taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %><%@
taglib uri="http://liferay.com/tld/site-navigation" prefix="liferay-site-navigation" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="com.liferay.item.selector.constants.ItemSelectorPortletKeys" %><%@
page import="com.liferay.petra.string.StringPool" %><%@
page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.model.Group" %><%@
page import="com.liferay.portal.kernel.theme.NavItem" %><%@
page import="com.liferay.portal.kernel.util.Constants" %><%@
page import="com.liferay.portal.kernel.util.GetterUtil" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.ParamUtil" %><%@
page import="com.liferay.portal.kernel.util.PortalUtil" %><%@
page import="com.liferay.site.navigation.constants.SiteNavigationConstants" %><%@
page import="com.liferay.site.navigation.menu.web.internal.constants.SiteNavigationMenuWebKeys" %><%@
page import="com.liferay.site.navigation.menu.web.internal.display.context.SiteNavigationMenuDisplayContext" %><%@
page import="com.liferay.site.navigation.model.SiteNavigationMenu" %><%@
page import="com.liferay.site.navigation.model.SiteNavigationMenuItem" %><%@
page import="com.liferay.site.navigation.service.SiteNavigationMenuItemLocalServiceUtil" %><%@
page import="com.liferay.site.navigation.service.SiteNavigationMenuLocalServiceUtil" %><%@
page import="com.liferay.site.navigation.type.SiteNavigationMenuItemType" %><%@
page import="com.liferay.site.navigation.type.SiteNavigationMenuItemTypeRegistry" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
String portletResource = ParamUtil.getString(request, "portletResource");

SiteNavigationMenuDisplayContext siteNavigationMenuDisplayContext = new SiteNavigationMenuDisplayContext(request);
%>

<%@ include file="/init-ext.jsp" %>