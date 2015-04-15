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
page import="com.liferay.portal.kernel.util.GetterUtil" %><%@
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
page import="com.liferay.portal.util.PortalUtil" %>

<%@ page import="java.util.List" %>

<liferay-theme:defineObjects />
<portlet:defineObjects />

<%
int displayDepth = GetterUtil.getInteger(portletPreferences.getValue("displayDepth", StringPool.BLANK));
String displayStyle = GetterUtil.getString(portletPreferences.getValue("displayStyle", StringPool.BLANK));
long displayStyleGroupId = GetterUtil.getLong(portletPreferences.getValue("displayStyleGroupId", null), themeDisplay.getScopeGroupId());
boolean includeRootInTree = GetterUtil.getBoolean(portletPreferences.getValue("includeRootInTree", StringPool.BLANK));
String rootLayoutUuid = GetterUtil.getString(portletPreferences.getValue("rootLayoutUuid", StringPool.BLANK));
boolean showCurrentPage = GetterUtil.getBoolean(portletPreferences.getValue("showCurrentPage", StringPool.BLANK));
boolean useHtmlTitle = GetterUtil.getBoolean(portletPreferences.getValue("useHtmlTitle", StringPool.BLANK));
boolean showHiddenPages = GetterUtil.getBoolean(portletPreferences.getValue("showHiddenPages", StringPool.BLANK));

Layout rootLayout = null;

long rootLayoutId = LayoutConstants.DEFAULT_PARENT_LAYOUT_ID;

if (Validator.isNotNull(rootLayoutUuid)) {
	rootLayout = LayoutLocalServiceUtil.fetchLayoutByUuidAndGroupId(rootLayoutUuid, scopeGroupId, false);

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