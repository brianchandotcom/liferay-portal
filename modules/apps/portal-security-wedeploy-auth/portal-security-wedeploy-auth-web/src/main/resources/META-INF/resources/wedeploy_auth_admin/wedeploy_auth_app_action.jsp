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
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

WeDeployAuthApp weDeployAuthApp = (WeDeployAuthApp)row.getObject();

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcRenderCommandName", "/wedeploy_auth_admin/view");
%>

<liferay-ui:icon-menu
	direction="right"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>
	<portlet:renderURL var="redirectURL">
		<portlet:param name="mvcRenderCommandName" value="/wedeploy_auth_admin/view" />
	</portlet:renderURL>

	<portlet:actionURL name="/wedeploy_auth_admin/edit_wedeploy_auth_app" var="deleteURL">
		<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
		<portlet:param name="redirect" value="<%= portletURL.toString() %>" />
		<portlet:param name="weDeployAuthAppId" value="<%= String.valueOf(weDeployAuthApp.getWeDeployAuthAppId()) %>" />
	</portlet:actionURL>

	<c:if test="<%= WeDeployAuthAppPermission.contains(permissionChecker, weDeployAuthApp, ActionKeys.DELETE) %>">
		<liferay-ui:icon-delete
			url="<%= deleteURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>