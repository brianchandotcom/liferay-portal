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

<%@ include file="/html/portlet/site_teams/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

Team team = (Team)row.getObject();
%>

<liferay-ui:icon-menu direction="down" icon="<%= StringPool.BLANK %>" message="<%= StringPool.BLANK %>" showWhenSingleIcon="<%= true %>">
	<c:if test="<%= TeamPermissionUtil.contains(permissionChecker, team, ActionKeys.UPDATE) %>">
		<portlet:renderURL var="editURL">
			<portlet:param name="mvcPath" value="/html/portlet/site_teams/edit_team.jsp" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="teamId" value="<%= String.valueOf(team.getTeamId()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			iconCssClass="icon-edit"
			message="edit"
			url="<%= editURL %>"
		/>
	</c:if>

	<c:if test="<%= TeamPermissionUtil.contains(permissionChecker, team, ActionKeys.PERMISSIONS) %>">

		<%
		Role role = team.getRole();

		int[] roleTypes = {RoleConstants.TYPE_REGULAR, RoleConstants.TYPE_SITE};

		Group group = GroupLocalServiceUtil.getGroup(team.getGroupId());

		if (group.isOrganization()) {
			roleTypes = ArrayUtil.append(roleTypes, RoleConstants.TYPE_ORGANIZATION);
		}
		%>

		<liferay-security:permissionsURL
			modelResource="<%= Role.class.getName() %>"
			modelResourceDescription="<%= team.getName() %>"
			resourcePrimKey="<%= String.valueOf(role.getRoleId()) %>"
			roleTypes="<%= roleTypes %>"
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

	<c:if test="<%= TeamPermissionUtil.contains(permissionChecker, team, ActionKeys.ASSIGN_MEMBERS) %>">
		<portlet:renderURL var="assignMembersURL">
			<portlet:param name="mvcPath" value="/html/portlet/site_teams/edit_team_assignments.jsp" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="teamId" value="<%= String.valueOf(team.getTeamId()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			iconCssClass="icon-plus"
			message="assign-members"
			url="<%= assignMembersURL %>"
		/>
	</c:if>

	<c:if test="<%= TeamPermissionUtil.contains(permissionChecker, team, ActionKeys.DELETE) %>">
		<portlet:actionURL name="deleteTeam" var="deleteTeamURL">
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="teamId" value="<%= String.valueOf(team.getTeamId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete
			url="<%= deleteTeamURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>