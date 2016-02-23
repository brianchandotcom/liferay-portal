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
RoleSearch searchContainer = (RoleSearch)request.getAttribute("liferay-ui:search:searchContainer");

String redirect = searchContainer.getIteratorURL().toString();

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

Role role = (Role)row.getObject();

String name = role.getName();
%>

<liferay-ui:icon-menu direction="left-side" icon="<%= StringPool.BLANK %>" markupView="lexicon" message="<%= StringPool.BLANK %>" showWhenSingleIcon="<%= true %>">
	<c:if test="<%= RolePermissionUtil.contains(permissionChecker, role.getRoleId(), ActionKeys.UPDATE) %>">
		<portlet:renderURL var="editURL">
			<portlet:param name="mvcPath" value="/edit_role.jsp" />
			<portlet:param name="redirect" value="<%= redirect %>" />
			<portlet:param name="roleId" value="<%= String.valueOf(role.getRoleId()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			message="edit"
			url="<%= editURL %>"
		/>
	</c:if>

	<c:if test="<%= !name.equals(RoleConstants.OWNER) && RolePermissionUtil.contains(permissionChecker, role.getRoleId(), ActionKeys.PERMISSIONS) %>">

		<%
		int[] roleTypes = {role.getType()};

		if (role.getType() != RoleConstants.TYPE_REGULAR) {
			roleTypes = new int[] {RoleConstants.TYPE_REGULAR, role.getType()};
		}
		%>

		<liferay-security:permissionsURL
			modelResource="<%= Role.class.getName() %>"
			modelResourceDescription="<%= role.getTitle(locale) %>"
			resourcePrimKey="<%= String.valueOf(role.getRoleId()) %>"
			roleTypes="<%= roleTypes %>"
			var="permissionsURL"
			windowState="<%= LiferayWindowState.POP_UP.toString() %>"
		/>

		<liferay-ui:icon
			message="permissions"
			method="get"
			url="<%= permissionsURL %>"
			useDialog="<%= true %>"
		/>
	</c:if>

	<c:if test="<%= !name.equals(RoleConstants.ADMINISTRATOR) && !name.equals(RoleConstants.SITE_OWNER) && !name.equals(RoleConstants.ORGANIZATION_ADMINISTRATOR) && !name.equals(RoleConstants.ORGANIZATION_OWNER) && !name.equals(RoleConstants.OWNER) && !name.equals(RoleConstants.SITE_ADMINISTRATOR) && RolePermissionUtil.contains(permissionChecker, role.getRoleId(), ActionKeys.DEFINE_PERMISSIONS) %>">
		<portlet:renderURL var="editRolePermissionsURL">
			<portlet:param name="mvcPath" value="/edit_role_permissions.jsp" />
			<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.VIEW %>" />
			<portlet:param name="redirect" value="<%= redirect %>" />
			<portlet:param name="roleId" value="<%= String.valueOf(role.getRoleId()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon message="define-permissions" url="<%= editRolePermissionsURL %>" />
	</c:if>

	<c:if test="<%= !role.isSystem() && RolePermissionUtil.contains(permissionChecker, role.getRoleId(), ActionKeys.DELETE) %>">
		<portlet:actionURL name="deleteRole" var="deleteRoleURL">
			<portlet:param name="mvcPath" value="/edit_role.jsp" />
			<portlet:param name="redirect" value="<%= redirect %>" />
			<portlet:param name="roleId" value="<%= String.valueOf(role.getRoleId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete url="<%= deleteRoleURL %>" />
	</c:if>
</liferay-ui:icon-menu>