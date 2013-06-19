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

<%@ page import="com.liferay.portal.kernel.template.TemplateHandler" %><%@
page import="com.liferay.portal.kernel.template.TemplateHandlerRegistryUtil" %><%@
page import="com.liferay.portal.util.LayoutLister" %><%@
page import="com.liferay.portal.util.LayoutView" %><%@
page import="com.liferay.portlet.portletdisplaytemplate.util.PortletDisplayTemplateUtil" %>

<%
String rootLayoutUuid = GetterUtil.getString(portletPreferences.getValue("rootLayoutUuid", StringPool.BLANK));
int displayDepth = GetterUtil.getInteger(portletPreferences.getValue("displayDepth", StringPool.BLANK));
String displayStyle = GetterUtil.getString(portletPreferences.getValue("displayStyle", StringPool.BLANK));
boolean includeRootInTree = GetterUtil.getBoolean(portletPreferences.getValue("includeRootInTree", StringPool.BLANK));
boolean showCurrentPage = GetterUtil.getBoolean(portletPreferences.getValue("showCurrentPage", StringPool.BLANK));
boolean useHtmlTitle = GetterUtil.getBoolean(portletPreferences.getValue("useHtmlTitle", StringPool.BLANK));
boolean showHiddenPages = GetterUtil.getBoolean(portletPreferences.getValue("showHiddenPages", StringPool.BLANK));

Layout rootLayout = null;

long rootLayoutId = LayoutConstants.DEFAULT_PARENT_LAYOUT_ID;

if (Validator.isNotNull(rootLayoutUuid)) {
	rootLayout = LayoutLocalServiceUtil.getLayoutByUuidAndGroupId(rootLayoutUuid, scopeGroupId, layout.isPrivateLayout());

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

<%@ include file="/html/portlet/site_map/init-ext.jsp" %>