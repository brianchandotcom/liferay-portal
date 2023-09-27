<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/dynamic_include/init.jsp" %>

<portlet:renderURL var="openIdConnectURL" windowState="<%= WindowState.MAXIMIZED.toString() %>">
	<portlet:param name="saveLastPath" value="<%= Boolean.FALSE.toString() %>" />
	<portlet:param name="mvcRenderCommandName" value="<%= OpenIdConnectWebKeys.OPEN_ID_CONNECT_REQUEST_ACTION_NAME %>" />
	<portlet:param name="redirect" value='<%= ParamUtil.getString(renderRequest, "redirect") %>' />
</portlet:renderURL>

<liferay-ui:icon
	cssClass="text-4"
	message="openid-connect"
	url="<%= openIdConnectURL %>"
/>