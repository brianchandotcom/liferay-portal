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
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %><%@
taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>

<%@ page import="com.liferay.marketplace.app.manager.web.internal.constants.BundleStateConstants" %><%@
page import="com.liferay.marketplace.app.manager.web.internal.dao.search.MarketplaceAppManagerResultRowSplitter" %><%@
page import="com.liferay.marketplace.app.manager.web.internal.display.context.AppManagerDisplayContext" %><%@
page import="com.liferay.marketplace.app.manager.web.internal.display.context.AppManagerSearchResultsManagementToolbarDisplayContext" %><%@
page import="com.liferay.marketplace.app.manager.web.internal.display.context.ViewAppsManagerManagementToolbarDisplayContext" %><%@
page import="com.liferay.marketplace.app.manager.web.internal.display.context.ViewModuleManagementToolbarDisplayContext" %><%@
page import="com.liferay.marketplace.app.manager.web.internal.display.context.ViewModulesManagementToolbarDisplayContext" %><%@
page import="com.liferay.marketplace.app.manager.web.internal.util.AppDisplay" %><%@
page import="com.liferay.marketplace.app.manager.web.internal.util.MarketplaceAppManagerUtil" %><%@
page import="com.liferay.marketplace.exception.FileExtensionException" %><%@
page import="com.liferay.petra.string.StringPool" %><%@
page import="com.liferay.portal.kernel.dao.search.SearchContainer" %><%@
page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.language.UnicodeLanguageUtil" %><%@
page import="com.liferay.portal.kernel.portlet.LiferayWindowState" %><%@
page import="com.liferay.portal.kernel.service.CompanyLocalServiceUtil" %><%@
page import="com.liferay.portal.kernel.upload.UploadException" %><%@
page import="com.liferay.portal.kernel.util.GetterUtil" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.ParamUtil" %><%@
page import="com.liferay.portal.kernel.util.PortalUtil" %><%@
page import="com.liferay.portal.kernel.util.StringUtil" %><%@
page import="com.liferay.portal.kernel.util.Validator" %><%@
page import="com.liferay.portal.kernel.util.WebKeys" %><%@
page import="com.liferay.taglib.search.ResultRow" %>

<%@ page import="java.util.Dictionary" %><%@
page import="java.util.List" %>

<%@ page import="javax.portlet.PortletURL" %>

<%@ page import="org.osgi.framework.Bundle" %><%@
page import="org.osgi.framework.Constants" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
AppManagerDisplayContext appManagerDisplayContext = new AppManagerDisplayContext(request, renderResponse);
%>