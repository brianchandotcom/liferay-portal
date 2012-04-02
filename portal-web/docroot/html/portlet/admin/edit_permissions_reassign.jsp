<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/admin/init.jsp" %>

<%
String tabs2 = (String)request.getAttribute("edit_permissions.jsp-tabs2");

PortletURL portletURL = (PortletURL)request.getAttribute("edit_permissions.jsp-portletURL");

String systemRoleName = RoleConstants.OWNER;

if (tabs2.equals("organizations")) {
	systemRoleName = RoleConstants.ORGANIZATION_USER;
}
else if (tabs2.equals("sites")) {
	systemRoleName = RoleConstants.SITE_MEMBER;
}
%>

<style type="text/css">
	.model-details {
		background: none;
		border: 0px;
		color: none;
	}
</style>

<div class="portlet-msg-info">
	<%= LanguageUtil.format(pageContext, "by-choosing-to-reassign-the-resource,-all-actions-will-be-removed-from-the-role-and-the-default-actions-will-be-enabled-for-the-system-role-x", systemRoleName) %>
</div>

<%
List<ResourcePermission> resourcePermissions = new UniqueList<ResourcePermission>();

int roleType = RoleConstants.TYPE_REGULAR;

if (tabs2.equals("organizations")) {
	roleType = RoleConstants.TYPE_ORGANIZATION;
}
else if (tabs2.equals("sites")) {
	roleType = RoleConstants.TYPE_SITE;
}

List<Role> roles = RoleLocalServiceUtil.getRoles(roleType, "lfr-permission-algorithm-5");

for (Role role : roles) {
	if (tabs2.equals("users")) {
		List<User> users = UserLocalServiceUtil.getRoleUsers(role.getRoleId());

		if (users.size() != 1) {
			continue;
		}
	}
	else {
		List<Group> groups = GroupLocalServiceUtil.getRoleGroups(role.getRoleId());

		if (groups.size() != 1) {
			continue;
		}

		Group group = groups.get(0);

		if (tabs2.equals("sites") && !group.isRegularSite()) {
			continue;
		}
		else if (tabs2.equals("organizations") && !group.isOrganization()) {
			continue;
		}
	}

	List<ResourcePermission> roleResourcePermissions = ResourcePermissionLocalServiceUtil.getRoleResourcePermissions(role.getRoleId());

	for (ResourcePermission resourcePermission : roleResourcePermissions) {
		if (resourcePermission.getScope() != ResourceConstants.SCOPE_INDIVIDUAL) {
			continue;
		}

		BaseModel model = null;

		try {
			model = PortalUtil.getBaseModel(resourcePermission);

			if (model == null) {
				continue;
			}
		}
		catch (Exception e) {
			continue;
		}

		if (tabs2.equals("users")) {
			List<User> users = UserLocalServiceUtil.getRoleUsers(role.getRoleId());

			long userId = BeanPropertiesUtil.getLong(model, "userId");

			if (users.get(0).getUserId() == userId) {
				resourcePermissions.add(resourcePermission);
			}
		}
		else {
			resourcePermissions.add(resourcePermission);
		}
	}
}
%>

<liferay-ui:search-container
	searchContainer='<%= new SearchContainer(renderRequest, portletURL, null, "there-are-no-generated-roles-to-reassign") %>'
>
	<liferay-ui:search-container-results
		results="<%= ListUtil.subList(resourcePermissions, searchContainer.getStart(), searchContainer.getEnd()) %>"
		total="<%= resourcePermissions.size() %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.portal.model.ResourcePermission"
		escapedModel="<%= true %>"
		keyProperty="resourcePermissionId"
		modelVar="resourcePermission"
	>

		<%
		Role role = RoleLocalServiceUtil.getRole(resourcePermission.getRoleId());

		Role systemRole = RoleLocalServiceUtil.getRole(role.getCompanyId(), systemRoleName);

		Group group = null;

		List<String> systemActionLabels = null;

		if (tabs2.equals("users")) {
			group = UserLocalServiceUtil.getRoleUsers(role.getRoleId()).get(0).getGroup();

			systemActionLabels = ResourceActionsUtil.getActionsNames(pageContext, ResourceActionsUtil.getModelResourceActions(resourcePermission.getName()));
		}
		else {
			group = GroupLocalServiceUtil.getRoleGroups(role.getRoleId()).get(0);

			systemActionLabels = ResourceActionsUtil.getActionsNames(pageContext, ResourceActionsUtil.getModelResourceGroupDefaultActions(resourcePermission.getName()));
		}

		ListUtil.sort(systemActionLabels);

		List<String> actionLabels = ResourceActionsUtil.getActionsNames(pageContext, resourcePermission.getName(), resourcePermission.getActionIds());

		ListUtil.sort(actionLabels);
		%>

		<%
		PortletURL editGroupURL = renderResponse.createRenderURL();

		editGroupURL.setParameter("redirect", currentURL);

		if (group.isOrganization()) {
			editGroupURL.setParameter("struts_action", "/admin_server/edit_organization");
			editGroupURL.setParameter("tabs1Names", "organizations");
			editGroupURL.setParameter("organizationId", String.valueOf(group.getOrganizationId()));
		}
		else if (group.isRegularSite()) {
			editGroupURL.setParameter("struts_action", "/admin_server/edit_site");
			editGroupURL.setParameter("groupId", String.valueOf(group.getGroupId()));
		}
		else if (group.isUser()) {
			editGroupURL.setParameter("struts_action", "/admin_server/edit_user");
			editGroupURL.setParameter("tabs1Names", "users");
			editGroupURL.setParameter("p_u_i_d", String.valueOf(group.getClassPK()));
		}
		%>

		<liferay-ui:search-container-column-text
			href="<%= editGroupURL %>"
			name="name"
			value="<%= HtmlUtil.escape(group.getDescriptiveName(locale)) %>"
		/>

		<portlet:renderURL var="editRoleURL">
			<portlet:param name="struts_action" value="/admin_server/edit_role_permissions" />
			<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.VIEW %>" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="roleId" value="<%= String.valueOf(role.getRoleId()) %>" />
		</portlet:renderURL>

		<liferay-ui:search-container-column-text
			href="<%= editRoleURL %>"
			name="role"
			value="<%= role.getName() %>"
		/>

		<liferay-ui:search-container-column-jsp
			name="resource"
			path="/html/portlet/admin/view_model.jsp"
		/>

		<liferay-ui:search-container-column-text
			name="actions"
			value='<%= StringUtil.merge(actionLabels, "<br />") %>'
		/>

		<liferay-ui:search-container-column-text
			name="potential-actions"
			value='<%= StringUtil.merge(systemActionLabels, "<br />") %>'
		/>

		<portlet:actionURL var="reassignURL">
			<portlet:param name="struts_action" value="/admin_server/edit_permissions" />
			<portlet:param name="<%= Constants.CMD %>" value="reassign" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="resourcePermissionId" value="<%= String.valueOf(resourcePermission.getResourcePermissionId()) %>" />
			<portlet:param name="toRoleId" value="<%= String.valueOf(systemRole.getRoleId()) %>" />
		</portlet:actionURL>

		<%
		String taglibReassignURL = renderResponse.getNamespace() + "invoke('" + reassignURL + "');";
		%>

		<liferay-ui:search-container-column-button
			align="right"
			href="<%= taglibReassignURL %>"
			name="reassign"
		/>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator />
</liferay-ui:search-container>