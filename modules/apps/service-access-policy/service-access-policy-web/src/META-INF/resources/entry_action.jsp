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

ServiceAccessPolicy serviceAccessPolicy = (ServiceAccessPolicy)row.getObject();
%>

<liferay-ui:icon-menu icon="<%= StringPool.BLANK %>" message="<%= StringPool.BLANK %>">
	<c:if test="<%= ServiceAccessPolicyPermission.contains(permissionChecker, serviceAccessPolicy, ActionKeys.UPDATE) %>">
		<portlet:renderURL var="editURL">
			<portlet:param name="mvcPath" value="/edit_entry.jsp" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="serviceAccessPolicyId" value="<%= String.valueOf(serviceAccessPolicy.getServiceAccessPolicyId()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			iconCssClass="icon-edit"
			message="edit"
			url="<%= editURL %>"
		/>
	</c:if>

	<c:if test="<%= ServiceAccessPolicyPermission.contains(permissionChecker, serviceAccessPolicy, ActionKeys.PERMISSIONS) %>">
		<liferay-security:permissionsURL
			modelResource="<%= ServiceAccessPolicy.class.getName() %>"
			modelResourceDescription="<%= serviceAccessPolicy.getTitle(locale) %>"
			resourcePrimKey="<%= String.valueOf(serviceAccessPolicy.getServiceAccessPolicyId()) %>"
			var="permissionsURL"
			windowState="<%= LiferayWindowState.POP_UP.toString() %>"
		/>

		<liferay-ui:icon
			iconCssClass="icon-lock"
			message="permissions"
			method="get"
			url="<%= permissionsURL %>"
			useDialog="<%= true %>"
		/>
	</c:if>

	<c:if test="<%= !serviceAccessPolicy.isDefaultServiceAccessPolicy() && ServiceAccessPolicyPermission.contains(permissionChecker, serviceAccessPolicy, ActionKeys.DELETE) %>">
		<portlet:actionURL name="deleteServiceAccessPolicy" var="deleteURL">
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="serviceAccessPolicyId" value="<%= String.valueOf(serviceAccessPolicy.getServiceAccessPolicyId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete url="<%= deleteURL %>" />
	</c:if>
</liferay-ui:icon-menu>