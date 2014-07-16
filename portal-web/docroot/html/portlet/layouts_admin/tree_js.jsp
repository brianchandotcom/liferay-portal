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

<%@ include file="/html/portlet/layouts_admin/init.jsp" %>

<%@ include file="/html/portlet/layouts_admin/init_attributes.jspf" %>

<%
String treeId = ParamUtil.getString(request, "treeId");
boolean checkContentDisplayPage = ParamUtil.getBoolean(request, "checkContentDisplayPage", false);
boolean defaultStateChecked = ParamUtil.getBoolean(request, "defaultStateChecked", false);
boolean draggableTree = ParamUtil.getBoolean(request, "draggableTree", true);
boolean expandFirstNode = ParamUtil.getBoolean(request, "expandFirstNode", true);
boolean saveState = ParamUtil.getBoolean(request, "saveState", true);
boolean selectableTree = ParamUtil.getBoolean(request, "selectableTree");
String selectedLayoutIds = ParamUtil.getString(request, "selectedLayoutIds");

PortletURL editLayoutURL = liferayPortletResponse.createRenderURL();

editLayoutURL.setParameter("struts_action", "/layouts_admin/edit_layouts");
editLayoutURL.setParameter("tabs1", tabs1);
editLayoutURL.setParameter("redirect", redirect);
editLayoutURL.setParameter("closeRedirect", closeRedirect);

if (portletName.equals(PortletKeys.LAYOUTS_ADMIN) || portletName.equals(PortletKeys.MY_ACCOUNT) || portletName.equals(PortletKeys.USERS_ADMIN)) {
	editLayoutURL.setParameter("backURL", backURL);
}

editLayoutURL.setParameter("groupId", String.valueOf(liveGroupId));
editLayoutURL.setParameter("viewLayout", Boolean.TRUE.toString());
%>

<liferay-ui:layouts-tree
	checkContentDisplayPage="<%= checkContentDisplayPage %>"
	defaultStateChecked="<%= defaultStateChecked %>"
	draggableTree="<%= draggableTree %>"
	expandFirstNode="<%= expandFirstNode %>"
	groupId="<%= groupId %>"
	portletURL="<%= editLayoutURL %>"
	privateLayout="<%= privateLayout %>"
	rootNodeName="<%= rootNodeName %>"
	saveState="<%= saveState %>"
	selPlid="<%= selPlid %>"
	selectableTree="<%= selectableTree %>"
	selectedLayoutIds="<%= selectedLayoutIds %>"
	treeId="<%= treeId %>"
/>