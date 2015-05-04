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

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="com.liferay.portal.kernel.util.Constants" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.StringBundler" %><%@
page import="com.liferay.portal.kernel.util.StringPool" %><%@
page import="com.liferay.portal.kernel.util.Validator" %><%@
page import="com.liferay.portal.model.Layout" %><%@
page import="com.liferay.portal.model.LayoutConstants" %><%@
page import="com.liferay.portal.model.LayoutSet" %><%@
page import="com.liferay.portal.security.permission.ActionKeys" %><%@
page import="com.liferay.portal.service.LayoutLocalServiceUtil" %><%@
page import="com.liferay.portal.service.permission.LayoutPermissionUtil" %><%@
page import="com.liferay.portal.theme.ThemeDisplay" %><%@
page import="com.liferay.portal.util.LayoutDescription" %><%@
page import="com.liferay.portal.util.LayoutListUtil" %><%@
page import="com.liferay.portal.util.PortalUtil" %><%@
page import="com.liferay.site.navigation.site.map.web.configuration.SiteMapPortletInstanceConfiguration" %>

<%@ page import="java.util.List" %>

<liferay-theme:defineObjects />
<portlet:defineObjects />

<%
SiteMapPortletInstanceConfiguration siteMapPortletInstanceConfiguration = portletDisplay.getPortletInstanceConfiguration(SiteMapPortletInstanceConfiguration.class);

int displayDepth = siteMapPortletInstanceConfiguration.displayDepth();
String displayStyle = siteMapPortletInstanceConfiguration.displayStyle();
long displayStyleGroupId = siteMapPortletInstanceConfiguration.displayStyleGroupId(themeDisplay.getScopeGroupId());
boolean includeRootInTree = siteMapPortletInstanceConfiguration.includeRootInTree();
String rootLayoutUuid = siteMapPortletInstanceConfiguration.rootLayoutUuid();
boolean showCurrentPage = siteMapPortletInstanceConfiguration.showCurrentPage();
boolean showHiddenPages = siteMapPortletInstanceConfiguration.showHiddenPages();
boolean useHtmlTitle = siteMapPortletInstanceConfiguration.useHtmlTitle();

Layout rootLayout = null;

long rootLayoutId = LayoutConstants.DEFAULT_PARENT_LAYOUT_ID;

if (Validator.isNotNull(rootLayoutUuid)) {
	rootLayout = LayoutLocalServiceUtil.fetchLayoutByUuidAndGroupId(rootLayoutUuid, scopeGroupId, layout.isPrivateLayout());

	if (rootLayout != null) {
		rootLayoutId = rootLayout.getLayoutId();
	}
}
else {
	includeRootInTree = false;
}

if (rootLayoutId == LayoutConstants.DEFAULT_PARENT_LAYOUT_ID) {
	includeRootInTree = false;
}
%>

<%@ include file="/init-ext.jsp" %>