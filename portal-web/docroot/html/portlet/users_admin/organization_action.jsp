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
SearchContainer searchContainer = (SearchContainer)request.getAttribute("liferay-ui:search:searchContainer");

String redirect = currentURL;

if ((searchContainer != null) && (searchContainer instanceof OrganizationSearch)) {
	redirect = searchContainer.getIteratorURL().toString();
}

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

Organization organization = null;

if (row != null) {
	organization = (Organization)row.getObject();
}
else {
	organization = (Organization)request.getAttribute("view_organizations_tree.jsp-organization");
}

long organizationId = organization.getOrganizationId();

Group organizationGroup = organization.getGroup();

long organizationGroupId = organizationGroup.getGroupId();

String cssClass = StringPool.BLANK;

boolean view = false;

if (row == null) {
	cssClass = "nav nav-list unstyled well";

	view = true;
}
%>

<liferay-ui:icon-menu cssClass="<%= cssClass %>" showExpanded="<%= view %>" showWhenSingleIcon="<%= view %>">

	<%
	boolean hasUpdatePermission = OrganizationPermissionUtil.contains(permissionChecker, organizationId, ActionKeys.UPDATE);
	%>

	<c:if test="<%= hasUpdatePermission %>">
		<portlet:renderURL var="editOrganizationURL">
			<portlet:param name="struts_action" value="/users_admin/edit_organization" />
			<portlet:param name="redirect" value="<%= redirect %>" />
			<portlet:param name="organizationId" value="<%= String.valueOf(organizationId) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			image="edit"
			url="<%= editOrganizationURL %>"
		/>
	</c:if>

	<%--<c:if test="<%= OrganizationPermissionUtil.contains(permissionChecker, organizationId, ActionKeys.PERMISSIONS) %>">
		<liferay-security:permissionsURL
			modelResource="<%= Organization.class.getName() %>"
			modelResourceDescription="<%= HtmlUtil.escape(organization.getName()) %>"
			resourcePrimKey="<%= String.valueOf(organization.getOrganizationId()) %>"
			var="editOrganizationPermissionsURL"
		/>

		<liferay-ui:icon
			image="permissions"
			url="<%= editOrganizationPermissionsURL %>"
		/>
	</c:if>--%>

	<c:if test="<%= organizationGroup.isSite() && (GroupPermissionUtil.contains(permissionChecker, organizationGroup, ActionKeys.MANAGE_STAGING) || hasUpdatePermission) %>">
		<liferay-portlet:renderURL doAsGroupId="<%= organizationGroupId %>" portletName="<%= PortletKeys.SITE_SETTINGS %>" var="editSettingsURL">
			<portlet:param name="struts_action" value="/sites_admin/edit_site" />
			<portlet:param name="viewOrganizationsRedirect" value="<%= currentURL %>" />
		</liferay-portlet:renderURL>

		<liferay-ui:icon
			image="configuration"
			message="manage-site"
			url="<%= editSettingsURL %>"
		/>
	</c:if>

	<c:if test="<%= permissionChecker.isGroupOwner(organizationGroupId) || OrganizationPermissionUtil.contains(permissionChecker, organizationId, ActionKeys.ASSIGN_USER_ROLES) %>">
		<portlet:renderURL var="assignUserRolesURL">
			<portlet:param name="struts_action" value="/users_admin/edit_user_roles" />
			<portlet:param name="redirect" value="<%= redirect %>" />
			<portlet:param name="groupId" value="<%= String.valueOf(organizationGroupId) %>" />
			<portlet:param name="roleType" value="<%= String.valueOf(RoleConstants.TYPE_ORGANIZATION) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			image="assign_user_roles"
			message="assign-organization-roles"
			url="<%= assignUserRolesURL %>"
		/>
	</c:if>

	<c:if test="<%= OrganizationPermissionUtil.contains(permissionChecker, organizationId, ActionKeys.ASSIGN_MEMBERS) %>">
		<portlet:renderURL var="assignMembersURL">
			<portlet:param name="struts_action" value="/users_admin/edit_organization_assignments" />
			<portlet:param name="redirect" value="<%= redirect %>" />
			<portlet:param name="organizationId" value="<%= String.valueOf(organizationId) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			image="assign"
			message="assign-users"
			url="<%= assignMembersURL %>"
		/>
	</c:if>

	<c:if test="<%= OrganizationPermissionUtil.contains(permissionChecker, organizationId, ActionKeys.MANAGE_USERS) %>">
		<portlet:renderURL var="addUserURL">
			<portlet:param name="struts_action" value="/users_admin/edit_user" />
			<portlet:param name="redirect" value="<%= redirect %>" />
			<portlet:param name="organizationsSearchContainerPrimaryKeys" value="<%= String.valueOf(organizationId) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			image="add_user"
			message="add-user"
			url="<%= addUserURL %>"
		/>
	</c:if>

	<c:if test="<%= organization.isParentable() %>">

		<%
		String[] childrenTypes = organization.getChildrenTypes();

		for (String childrenType : childrenTypes) {
		%>

			<c:if test="<%= OrganizationPermissionUtil.contains(permissionChecker, organizationId, ActionKeys.ADD_ORGANIZATION) %>">
				<portlet:renderURL var="addSuborganizationURL">
					<portlet:param name="struts_action" value="/users_admin/edit_organization" />
					<portlet:param name="redirect" value="<%= redirect %>" />
					<portlet:param name="parentOrganizationSearchContainerPrimaryKeys" value="<%= String.valueOf(organizationId) %>" />
					<portlet:param name="type" value="<%= childrenType %>" />
				</portlet:renderURL>

				<liferay-ui:icon
					image="add_location"
					message='<%= LanguageUtil.format(pageContext, "add-x", new String [] {LanguageUtil.get(pageContext, childrenType)}) %>'
					url="<%= addSuborganizationURL %>"
				/>
			</c:if>

		<%
		}
		%>

	</c:if>

	<c:if test="<%= OrganizationPermissionUtil.contains(permissionChecker, organizationId, ActionKeys.DELETE) %>">

		<%
		String taglibDeleteURL = "javascript:" + renderResponse.getNamespace() + "deleteOrganization('" + organizationId + "');";
		%>

		<liferay-ui:icon
			image="delete"
			url="<%= taglibDeleteURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>