<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

<%@ page import="com.liferay.portal.kernel.plugin.PluginPackage" %>
<%@ page import="com.liferay.portal.kernel.util.UnicodeProperties" %>
<%@ page import="com.liferay.portal.model.Layout" %>
<%@ page import="com.liferay.portal.plugin.PluginUtil" %>
<%@ page import="com.liferay.portal.service.LayoutLocalServiceUtil" %>
<%@ page import="com.liferay.portal.service.permission.LayoutPermissionUtil" %>
<%@ page import="com.liferay.portlet.mobiledevicerules.model.MDRAction" %>
<%@ page import="com.liferay.portlet.mobiledevicerules.MDRPortletConstants" %>

<%@ include file="/html/portlet/mobile_device_rules_admin/init.jsp" %>

<%
MDRAction action = (MDRAction) request.getAttribute(
	MDRPortletConstants.MDR_ACTION);

boolean isAdd = Validator.isNull(action);

UnicodeProperties actionTypeSettings = null;
String className = null;
long classPK = 0l;

if (!isAdd) {
	actionTypeSettings = action.getTypeSettingsProperties();
	className = action.getClassName();
	classPK = action.getClassPK();
}
else {
	actionTypeSettings = new UnicodeProperties();
	className = ParamUtil.getString(request, MDRPortletConstants.CLASS_NAME);
	classPK = ParamUtil.getLong(request, MDRPortletConstants.CLASS_PK);
}

Layout selLayout = null;
LayoutSet selLayoutSet = null;

if (className.equals(LayoutSet.class.getName())) {
	selLayoutSet = LayoutSetLocalServiceUtil.getLayoutSet(classPK);
	groupId = selLayoutSet.getGroupId();
}
else if (className.equals(Layout.class.getName())) {
	selLayout = LayoutLocalServiceUtil.getLayout(classPK);
	groupId = selLayout.getGroupId();
}
%>