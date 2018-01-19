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
String tabs1 = ParamUtil.getString(request, "tabs1");

String redirect = ParamUtil.getString(request, "redirect");

String backURL = ParamUtil.getString(request, "backURL", redirect);

long roleId = ParamUtil.getLong(request, "roleId");

Role role = RoleServiceUtil.fetchRole(roleId);

String[] tabs1Names = new String[0];
String[] tabs1URLs = new String[0];

String portletURL = ParamUtil.getString(request, "portletURL");

if (role != null) {

	// Edit

	PortletURL editRoleURL = renderResponse.createRenderURL();

	editRoleURL.setParameter("mvcPath", "/edit_role.jsp");
	editRoleURL.setParameter("tabs1", "details");
	editRoleURL.setParameter("redirect", backURL);
	editRoleURL.setParameter("roleId", String.valueOf(role.getRoleId()));

	// Define permissions

	PortletURL definePermissionsURL = renderResponse.createRenderURL();

	definePermissionsURL.setParameter("mvcPath", "/edit_role_permissions.jsp");
	definePermissionsURL.setParameter("tabs1", "define-permissions");
	definePermissionsURL.setParameter("redirect", backURL);
	definePermissionsURL.setParameter(Constants.CMD, Constants.VIEW);
	definePermissionsURL.setParameter("roleId", String.valueOf(role.getRoleId()));

	// Assign members

	PortletURL assignMembersURL = renderResponse.createRenderURL();

	assignMembersURL.setParameter("mvcPath", "/edit_role_assignments.jsp");
	assignMembersURL.setParameter("tabs1", "assignees");
	assignMembersURL.setParameter("redirect", backURL);
	assignMembersURL.setParameter("roleId", String.valueOf(role.getRoleId()));

	if (RolePermissionUtil.contains(permissionChecker, role.getRoleId(), ActionKeys.UPDATE)) {
		tabs1Names = ArrayUtil.append(tabs1Names, "details");
		tabs1URLs = ArrayUtil.append(tabs1URLs, editRoleURL.toString());
	}

	String name = role.getName();

	if (!name.equals(RoleConstants.ADMINISTRATOR) && !name.equals(RoleConstants.ORGANIZATION_ADMINISTRATOR) && !name.equals(RoleConstants.ORGANIZATION_OWNER) && !name.equals(RoleConstants.OWNER) && !name.equals(RoleConstants.SITE_ADMINISTRATOR) && !name.equals(RoleConstants.SITE_OWNER) && RolePermissionUtil.contains(permissionChecker, role.getRoleId(), ActionKeys.DEFINE_PERMISSIONS)) {
		tabs1Names = ArrayUtil.append(tabs1Names, "define-permissions");
		tabs1URLs = ArrayUtil.append(tabs1URLs, definePermissionsURL.toString());
	}

	boolean unassignableRole = false;

	if (name.equals(RoleConstants.GUEST) || name.equals(RoleConstants.OWNER) || name.equals(RoleConstants.USER)) {
		unassignableRole = true;
	}

	if (!unassignableRole && (role.getType() == RoleConstants.TYPE_REGULAR) && RolePermissionUtil.contains(permissionChecker, role.getRoleId(), ActionKeys.ASSIGN_MEMBERS)) {
		tabs1Names = ArrayUtil.append(tabs1Names, "assignees");
		tabs1URLs = ArrayUtil.append(tabs1URLs, assignMembersURL.toString());
	}
}

List<NavigationItem> navigationItems = new ArrayList<>();

if (role != null) {
	for (int i = 0; i < tabs1URLs.length; i++) {
		NavigationItem entriesNavigationItem = new NavigationItem();

		entriesNavigationItem.setActive(tabs1Names[i].equals(tabs1));
		entriesNavigationItem.setHref(tabs1URLs[i]);
		entriesNavigationItem.setLabel(LanguageUtil.get(request, tabs1Names[i]));

		navigationItems.add(entriesNavigationItem);
	}
}
else {
	NavigationItem detailsNavigationItem = new NavigationItem();

	detailsNavigationItem.setActive(true);
	detailsNavigationItem.setHref(currentURL);
	detailsNavigationItem.setLabel(LanguageUtil.get(request, "details"));

	navigationItems.add(detailsNavigationItem);

	NavigationItem definePermissionsNavigationItem = new NavigationItem();

	definePermissionsNavigationItem.setActive(false);
	definePermissionsNavigationItem.setDisabled(true);
	definePermissionsNavigationItem.setHref(StringPool.BLANK);
	definePermissionsNavigationItem.setLabel(LanguageUtil.get(request, "define-permissions"));

	navigationItems.add(definePermissionsNavigationItem);

	int type = ParamUtil.getInteger(request, "type", RoleConstants.TYPE_REGULAR);

	if (type == RoleConstants.TYPE_REGULAR) {
		NavigationItem assigneesNavigationItem = new NavigationItem();

		assigneesNavigationItem.setActive(false);
		assigneesNavigationItem.setDisabled(false);
		assigneesNavigationItem.setHref(StringPool.BLANK);
		assigneesNavigationItem.setLabel(LanguageUtil.get(request, "assignees"));

		navigationItems.add(assigneesNavigationItem);
	}
}
%>

<clay:navigation-bar
	inverted="<%= true %>"
	items="<%= navigationItems %>"
/>