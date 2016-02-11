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

<%@ include file="/init.jsp" %>

<%
String tabs1 = ParamUtil.getString(request, "tabs1", "entries");

PortletURL tabs1URL = renderResponse.createRenderURL();

tabs1URL.setParameter("mvcRenderCommandName", "/announcements/view");
tabs1URL.setParameter("tabs1", tabs1);

String tabs1Names = "entries";

if (AnnouncementsEntryPermission.contains(permissionChecker, layout, portletName, ActionKeys.ADD_ENTRY)) {
	tabs1Names += ",manage-entries";
}
%>

<liferay-ui:tabs
	names="<%= tabs1Names %>"
	type="tabs nav-tabs-default"
	url="<%= tabs1URL.toString() %>"
/>