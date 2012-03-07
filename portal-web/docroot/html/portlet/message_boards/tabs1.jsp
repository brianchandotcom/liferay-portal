<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/message_boards/init.jsp" %>

<%
String tabs1 = ParamUtil.getString(request, "tabs1", "categories");

PortletURL tabs1URL = renderResponse.createRenderURL();

tabs1URL.setParameter("struts_action", "/message_boards/view");
tabs1URL.setParameter("tabs1", tabs1);

String tabs1Values = "categories,recent_posts,statistics";

if (themeDisplay.isSignedIn()) {
	tabs1Values = "categories,my_posts,my_subscriptions,recent_posts,statistics";

	if (MBPermission.contains(permissionChecker, scopeGroupId, ActionKeys.BAN_USER)) {
		tabs1Values += ",banned_users";
	}
}

String tabs1Names = StringUtil.replace(tabs1Values, StringPool.UNDERLINE, StringPool.DASH);
%>

<liferay-ui:tabs
	names="<%= tabs1Names %>"
	portletURL="<%= tabs1URL %>"
	tabsValues="<%= tabs1Values %>"
/>