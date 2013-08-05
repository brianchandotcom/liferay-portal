<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/users_admin/init.jsp" %>

<%
String toolbarItem = ParamUtil.getString(request, "toolbarItem", "browse");

String usersListView = ParamUtil.get(request, "usersListView", UserConstants.LIST_VIEW_TREE);
%>

<aui:nav>
	<portlet:renderURL var="viewUsersTreeURL">
		<portlet:param name="struts_action" value="/users_admin/view" />
		<portlet:param name="toolbarItem" value="browse" />
		<portlet:param name="usersListView" value="<%= UserConstants.LIST_VIEW_TREE %>" />
		<portlet:param name="saveUsersListView" value="<%= Boolean.TRUE.toString() %>" />
	</portlet:renderURL>

	<aui:nav-item href="<%= viewUsersTreeURL %>" label="browse" selected='<%= toolbarItem.equals("browse") %>' />

	<portlet:renderURL var="viewOrganizationsFlatURL">
		<portlet:param name="struts_action" value="/users_admin/view" />
		<portlet:param name="toolbarItem" value="view-all-organizations" />
		<portlet:param name="usersListView" value="<%= UserConstants.LIST_VIEW_FLAT_ORGANIZATIONS %>" />
		<portlet:param name="saveUsersListView" value="<%= Boolean.TRUE.toString() %>" />
	</portlet:renderURL>

	<aui:nav-item href="<%= viewOrganizationsFlatURL %>" label="view-organizations" selected='<%= toolbarItem.equals("view-all-organizations") %>' />

	<portlet:renderURL var="viewUsersFlatURL">
		<portlet:param name="struts_action" value="/users_admin/view" />
		<portlet:param name="toolbarItem" value="view-all-users" />
		<portlet:param name="usersListView" value="<%= UserConstants.LIST_VIEW_FLAT_USERS %>" />
		<portlet:param name="saveUsersListView" value="<%= Boolean.TRUE.toString() %>" />
	</portlet:renderURL>

	<aui:nav-item href="<%= viewUsersFlatURL %>" label="view-users" selected='<%= toolbarItem.equals("view-all-users") %>' />

	<%
	boolean hasAddOrganizationPermission = PortalPermissionUtil.contains(permissionChecker, ActionKeys.ADD_ORGANIZATION);
	boolean hasAddUserPermission = PortalPermissionUtil.contains(permissionChecker, ActionKeys.ADD_USER);
	%>

	<c:if test="<%= hasAddOrganizationPermission || hasAddUserPermission %>">
		<aui:nav-item dropdown="<%= true %>" iconClass="icon-plus" label="add" selected='<%= toolbarItem.equals("add") %>'>
			<portlet:renderURL var="viewUsersURL">
				<portlet:param name="struts_action" value="/users_admin/view" />
				<portlet:param name="sitesListView" value="<%= usersListView %>" />
			</portlet:renderURL>

			<c:if test="<%= hasAddUserPermission %>">
				<portlet:renderURL var="addUserURL">
					<portlet:param name="struts_action" value="/users_admin/edit_user" />
					<portlet:param name="redirect" value="<%= viewUsersURL %>" />
				</portlet:renderURL>

				<aui:nav-item href="<%= addUserURL %>" iconClass="icon-user" label="user" />
			</c:if>

			<aui:nav-item cssClass="divider" />

			<c:if test="<%= hasAddOrganizationPermission %>">

				<%
				for (String organizationType : PropsValues.ORGANIZATIONS_TYPES) {
				%>

					<portlet:renderURL var="addOrganizationURL">
						<portlet:param name="struts_action" value="/users_admin/edit_organization" />
						<portlet:param name="redirect" value="<%= viewUsersURL %>" />
						<portlet:param name="type" value="<%= organizationType %>" />
					</portlet:renderURL>

					<aui:nav-item href="<%= addOrganizationURL %>" iconClass="icon-globe" label="<%= LanguageUtil.get(pageContext, organizationType) %>" />

				<%
				}
				%>

			</c:if>
		</aui:nav-item>
	</c:if>

	<c:choose>
		<c:when test="<%= PortalPermissionUtil.contains(permissionChecker, ActionKeys.EXPORT_USER) %>">
			<aui:nav-item href='<%= "javascript:" + renderResponse.getNamespace() + "exportUsers();" %>' label="export-users" selected='<%= toolbarItem.equals("export-users") %>' />
		</c:when>
		<c:when test="<%= PortletPermissionUtil.contains(permissionChecker, PortletKeys.USERS_ADMIN, ActionKeys.EXPORT_USER) %>">
			<aui:nav-item href='<%= "javascript:" + renderResponse.getNamespace() + "exportUsers();" %>' label="export-organization-users" selected='<%= toolbarItem.equals("export-organization-users") %>' />
		</c:when>
	</c:choose>
</aui:nav>

<aui:script>
	function <portlet:namespace />exportUsers() {
		document.<portlet:namespace />fm.method = "post";

		submitForm(document.<portlet:namespace />fm, "<portlet:actionURL><portlet:param name="struts_action" value="/users_admin/export_users" /></portlet:actionURL>&compress=0&etag=0&strip=0", false);
	}
</aui:script>