<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %><%@
taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %><%@
taglib uri="http://liferay.com/tld/template" prefix="liferay-template" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="com.liferay.portal.kernel.servlet.taglib.ui.LanguageEntry" %><%@
page import="com.liferay.portal.kernel.util.Constants" %><%@
page import="com.liferay.site.navigation.language.web.internal.configuration.SiteNavigationLanguagePortletInstanceConfiguration" %><%@
page import="com.liferay.site.navigation.language.web.internal.display.context.SiteNavigationLanguageDisplayContext" %>

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
SiteNavigationLanguageDisplayContext siteNavigationLanguageDisplayContext = new SiteNavigationLanguageDisplayContext(request);

SiteNavigationLanguagePortletInstanceConfiguration siteNavigationLanguagePortletInstanceConfiguration = siteNavigationLanguageDisplayContext.getSiteNavigationLanguagePortletInstanceConfiguration();
%>

<%@ include file="/init-ext.jsp" %>