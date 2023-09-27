<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

Team team = (Team)row.getObject();
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>
	<c:if test="<%= TeamPermissionUtil.contains(permissionChecker, team, ActionKeys.UPDATE) %>">
		<portlet:renderURL var="editURL">
			<portlet:param name="mvcPath" value="/edit_team.jsp" />
			<portlet:param name="teamId" value="<%= String.valueOf(team.getTeamId()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			message="edit"
			url="<%= editURL %>"
		/>
	</c:if>

	<c:if test="<%= TeamPermissionUtil.contains(permissionChecker, team, ActionKeys.PERMISSIONS) %>">

		<%
		int[] roleTypes = {RoleConstants.TYPE_REGULAR, RoleConstants.TYPE_SITE};

		Group group = GroupLocalServiceUtil.getGroup(team.getGroupId());

		if (group.isOrganization()) {
			roleTypes = ArrayUtil.append(roleTypes, RoleConstants.TYPE_ORGANIZATION);
		}
		%>

		<liferay-security:permissionsURL
			modelResource="<%= Team.class.getName() %>"
			modelResourceDescription="<%= team.getName() %>"
			resourcePrimKey="<%= String.valueOf(team.getTeamId()) %>"
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