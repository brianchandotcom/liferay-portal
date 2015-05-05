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

long organizationGroupId = organization.getGroupId();

String cssClass = StringPool.BLANK;

boolean view = false;

if (row == null) {
	cssClass = "list-group nav";

	view = true;
}
%>

<liferay-ui:icon-menu cssClass="<%= cssClass %>" icon="<%= StringPool.BLANK %>" message="<%= StringPool.BLANK %>" showExpanded="<%= view %>" showWhenSingleIcon="<%= view %>">

	<%
	boolean hasUpdatePermission = OrganizationPermissionUtil.contains(permissionChecker, organization, ActionKeys.UPDATE);
	%>

	<c:if test="<%= hasUpdatePermission %>">
		<portlet:renderURL var="editOrganizationURL">
			<portlet:param name="struts_action" value="/users_admin/edit_organization" />
			<portlet:param name="redirect" value="<%= redirect %>" />
			<portlet:param name="organizationId" value="<%= String.valueOf(organizationId) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			iconCssClass="icon-edit"
			message="edit"
			url="<%= editOrganizationURL %>"
		/>
	</c:if>

	<%--<c:if test="<%= OrganizationPermissionUtil.contains(permissionChecker, organization, ActionKeys.PERMISSIONS) %>">
		<liferay-security:permissionsURL
			modelResource="<%= Organization.class.getName() %>"
			modelResourceDescription="<%= HtmlUtil.escape(organization.getName()) %>"
			resourcePrimKey="<%= String.valueOf(organization.getOrganizationId()) %>"
			var="editOrganizationPermissionsURL"
			windowState="<%= LiferayWindowState.POP_UP.toString() %>"
		/>

		<liferay-ui:icon
			iconCssClass="icon-lock"
			message="permissions"
			method="get"
			url="<%= editOrganizationPermissionsURL %>"
			useDialog="<%= true %>"
		/>
	</c:if>--%>

	<c:if test="<%= organizationGroup.isSite() && (GroupPermissionUtil.contains(permissionChecker, organizationGroup, ActionKeys.MANAGE_STAGING) || hasUpdatePermission) %>">

		<%
		LiferayPortletURL editSettingsURL = (LiferayPortletURL)PortletProviderUtil.getPortletURL(request, Group.class.getName(), PortletProvider.Action.EDIT);

		editSettingsURL.setDoAsGroupId(organizationGroupId);
		editSettingsURL.setParameter("viewOrganizationsRedirect", currentURL);
		%>

		<liferay-ui:icon
			iconCssClass="icon-cog"
			message="manage-site"
			url="<%= editSettingsURL.toString() %>"
		/>
	</c:if>

	<c:if test="<%= permissionChecker.isGroupOwner(organizationGroupId) || OrganizationPermissionUtil.contains(permissionChecker, organization, ActionKeys.ASSIGN_USER_ROLES) %>">

		<%
		PortletURL assignUserRolesURL = PortletProviderUtil.getPortletURL(request, UserGroupRole.class.getName(), PortletProvider.Action.EDIT);

		assignUserRolesURL.setParameter("className", User.class.getName());
		assignUserRolesURL.setParameter("groupId", String.valueOf(organizationGroupId));
		assignUserRolesURL.setWindowState(LiferayWindowState.POP_UP);
		%>

		<liferay-ui:icon
			iconCssClass="icon-signin"
			message="assign-organization-roles"
			url="<%= assignUserRolesURL.toString() %>"
			useDialog="<%= true %>"
		/>
	</c:if>

	<c:if test="<%= OrganizationPermissionUtil.contains(permissionChecker, organization, ActionKeys.ASSIGN_MEMBERS) %>">
		<portlet:renderURL var="assignMembersURL">
			<portlet:param name="struts_action" value="/users_admin/edit_organization_assignments" />
			<portlet:param name="redirect" value="<%= redirect %>" />
			<portlet:param name="organizationId" value="<%= String.valueOf(organizationId) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			iconCssClass="icon-signin"
			message="assign-users"
			url="<%= assignMembersURL %>"
		/>
	</c:if>

	<c:if test="<%= OrganizationPermissionUtil.contains(permissionChecker, organization, ActionKeys.MANAGE_USERS) %>">
		<portlet:renderURL var="addUserURL">
			<portlet:param name="struts_action" value="/users_admin/edit_user" />
			<portlet:param name="redirect" value="<%= redirect %>" />
			<portlet:param name="organizationsSearchContainerPrimaryKeys" value="<%= String.valueOf(organizationId) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			iconCssClass="icon-plus"
			message="add-user"
			url="<%= addUserURL %>"
		/>
	</c:if>

	<c:if test="<%= organization.isParentable() %>">

		<%
		String[] childrenTypes = organization.getChildrenTypes();

		for (String childrenType : childrenTypes) {
		%>

			<c:if test="<%= OrganizationPermissionUtil.contains(permissionChecker, organization, ActionKeys.ADD_ORGANIZATION) %>">
				<portlet:renderURL var="addSuborganizationURL">
					<portlet:param name="struts_action" value="/users_admin/edit_organization" />
					<portlet:param name="redirect" value="<%= redirect %>" />
					<portlet:param name="parentOrganizationSearchContainerPrimaryKeys" value="<%= String.valueOf(organizationId) %>" />
					<portlet:param name="type" value="<%= childrenType %>" />
				</portlet:renderURL>

				<liferay-ui:icon
					iconCssClass="icon-plus"
					message='<%= LanguageUtil.format(request, "add-x", childrenType) %>'
					url="<%= addSuborganizationURL %>"
				/>
			</c:if>

		<%
		}
		%>

	</c:if>

	<c:if test="<%= OrganizationPermissionUtil.contains(permissionChecker, organization, ActionKeys.DELETE) %>">

		<%
		String taglibDeleteURL = "javascript:" + renderResponse.getNamespace() + "deleteOrganization('" + organizationId + "');";
		%>

		<liferay-ui:icon
			cssClass="item-remove"
			iconCssClass="icon-remove"
			message="delete"
			url="<%= taglibDeleteURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>