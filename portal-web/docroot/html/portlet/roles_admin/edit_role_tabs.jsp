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

<%@ include file="/html/portlet/roles_admin/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

String backURL = ParamUtil.getString(request, "backURL", redirect);

long roleId = ParamUtil.getLong(request, "roleId");

Role role = RoleServiceUtil.fetchRole(roleId);

String portletResource = ParamUtil.getString(request, "portletResource");

// Edit

PortletURL editRoleURL = renderResponse.createRenderURL();

editRoleURL.setParameter("mvcPath", "/html/portlet/roles_admin/edit_role.jsp");
editRoleURL.setParameter("redirect", backURL);
editRoleURL.setParameter("roleId", String.valueOf(role.getRoleId()));

// Define permissions

PortletURL definePermissionsURL = renderResponse.createRenderURL();

definePermissionsURL.setParameter("mvcPath", "/html/portlet/roles_admin/edit_role_permissions.jsp");
definePermissionsURL.setParameter("redirect", backURL);
definePermissionsURL.setParameter(Constants.CMD, Constants.VIEW);
definePermissionsURL.setParameter("roleId", String.valueOf(role.getRoleId()));

// Assign members

PortletURL assignMembersURL = renderResponse.createRenderURL();

assignMembersURL.setParameter("mvcPath", "/html/portlet/roles_admin/edit_role_assignments.jsp");
assignMembersURL.setParameter("redirect", backURL);
assignMembersURL.setParameter("roleId", String.valueOf(role.getRoleId()));

String tabs1Names = StringPool.BLANK;
String[] tabs1URLs = new String[0];

if (RolePermissionUtil.contains(permissionChecker, role.getRoleId(), ActionKeys.UPDATE)) {
	tabs1Names += ",edit";

	tabs1URLs = ArrayUtil.append(tabs1URLs, editRoleURL.toString());
}

String name = role.getName();

if (!name.equals(RoleConstants.ADMINISTRATOR) && !name.equals(RoleConstants.ORGANIZATION_ADMINISTRATOR) && !name.equals(RoleConstants.ORGANIZATION_OWNER) && !name.equals(RoleConstants.OWNER) && !name.equals(RoleConstants.SITE_ADMINISTRATOR) && !name.equals(RoleConstants.SITE_OWNER) && RolePermissionUtil.contains(permissionChecker, role.getRoleId(), ActionKeys.DEFINE_PERMISSIONS)) {
	tabs1Names += ",define-permissions";

	tabs1URLs = ArrayUtil.append(tabs1URLs, definePermissionsURL.toString());
}

boolean unassignableRole = false;

if (name.equals(RoleConstants.GUEST) || name.equals(RoleConstants.OWNER) || name.equals(RoleConstants.USER)) {
	unassignableRole = true;
}

if (!unassignableRole && (role.getType() == RoleConstants.TYPE_REGULAR) && RolePermissionUtil.contains(permissionChecker, role.getRoleId(), ActionKeys.ASSIGN_MEMBERS)) {
	tabs1Names += ",assign-members";

	tabs1URLs = ArrayUtil.append(tabs1URLs, assignMembersURL.toString());
}

if (tabs1Names.startsWith(",")) {
	tabs1Names = tabs1Names.substring(1);
}

// Breadcrumbs

PortalUtil.addPortletBreadcrumbEntry(request, role.getTitle(locale), null);

request.setAttribute("edit_role_permissions.jsp-role", role);

request.setAttribute("edit_role_permissions.jsp-portletResource", portletResource);
%>

<liferay-ui:tabs names="<%= tabs1Names %>" urls="<%= tabs1URLs %>" />