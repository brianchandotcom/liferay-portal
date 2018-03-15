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

<%@ include file="/dynamic_include/init.jsp" %>

<portlet:renderURL var="openIdConnectURL" windowState="<%= WindowState.MAXIMIZED.toString() %>">
	<portlet:param name="mvcRenderCommandName" value="<%= OpenIdConnectWebKeys.OPEN_ID_CONNECT_REQUEST_ACTION_NAME %>" />
</portlet:renderURL>

<liferay-ui:icon
	message="openid-connect"
	src='<%= themeDisplay.getPathThemeImages() + "/common/openid_connect.gif" %>'
	url="<%= openIdConnectURL %>"
/>