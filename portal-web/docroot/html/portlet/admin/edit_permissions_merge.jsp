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

MultiValueMap mvp = new MultiValueMap();

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

		mvp.put(users.get(0).getGroup(), role);
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

		mvp.put(group, role);
	}
}

Iterator<Group> groupsItr = mvp.keySet().iterator();

while (groupsItr.hasNext()) {
	Group group = groupsItr.next();

	if (mvp.size(group) == 1) {
		groupsItr.remove();
	}
}

List<Group> groups = new ArrayList<Group>(mvp.keySet());
%>

<liferay-ui:search-container
	searchContainer='<%= new SearchContainer(renderRequest, portletURL, null, "there-are-no-generated-roles-to-merge") %>'
>
	<liferay-ui:search-container-results
		results="<%= ListUtil.subList(groups, searchContainer.getStart(), searchContainer.getEnd()) %>"
		total="<%= groups.size() %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.portal.model.Group"
		escapedModel="<%= true %>"
		keyProperty="groupId"
		modelVar="group"
		rowIdProperty="friendlyURL"
	>

		<%
		Collection<Role> groupRoles = mvp.getCollection(group);

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

		<liferay-ui:search-container-column-text
			buffer="buffer"
			name="role"
		>

			<%
			PortletURL editRoleURL = renderResponse.createRenderURL();

			editRoleURL.setParameter("struts_action", "/admin_server/edit_role_permissions");
			editRoleURL.setParameter(Constants.CMD, Constants.VIEW);
			editRoleURL.setParameter("redirect", currentURL);

			for (Role role : groupRoles) {
				editRoleURL.setParameter("roleId", String.valueOf(role.getRoleId()));

				buffer.append("<a href=\"");
				buffer.append(editRoleURL);
				buffer.append("\">");
				buffer.append(role.getName());
				buffer.append("</a><br />");
			}
			%>

		</liferay-ui:search-container-column-text>

		<portlet:actionURL var="mergeURL">
			<portlet:param name="struts_action" value="/admin_server/edit_permissions" />
			<portlet:param name="<%= Constants.CMD %>" value="merge" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="roleIds" value='<%= ListUtil.toString(ListUtil.fromCollection(groupRoles), Role.ROLE_ID_ACCESSOR) %>' />
		</portlet:actionURL>

		<%
		String taglibMergeURL = renderResponse.getNamespace() + "invoke('" + mergeURL + "');";
		%>

		<liferay-ui:search-container-column-button
			align="right"
			href="<%= taglibMergeURL %>"
			name="merge"
		/>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator />
</liferay-ui:search-container>