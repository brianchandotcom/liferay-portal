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

<%@ include file="/html/portlet/portal_settings/init.jsp" %>

<%
Group liveGroup = (Group)request.getAttribute("site.liveGroup");

UnicodeProperties groupTypeSettings = null;

long groupId = scopeGroupId;

if (liveGroup != null) {
	groupTypeSettings = liveGroup.getTypeSettingsProperties();
	groupId = liveGroup.getGroupId();
}
else {
	groupTypeSettings = new UnicodeProperties();
}

List<Role> defaultGroupRoles = new ArrayList();

String[] rolesIds = StringUtil.split(groupTypeSettings.getProperty("defaultGroupRoles"), StringPool.COMMA);

for (int i = 0; i < rolesIds.length; i++) {
	Role role = RoleLocalServiceUtil.getRole(Long.valueOf(rolesIds[i]));

	defaultGroupRoles.add(role);
}

List<Team> defaultGroupTeams = new ArrayList();

String[] teamIds = StringUtil.split(groupTypeSettings.getProperty("defaultGroupTeams"), StringPool.COMMA);

for (int i = 0; i < teamIds.length; i++) {
	Team team = TeamLocalServiceUtil.getTeam(Long.valueOf(teamIds[i]));

	defaultGroupTeams.add(team);
}
%>

<liferay-util:buffer var="removeRoleIcon">
	<liferay-ui:icon
		image="unlink"
		label="<%= true %>"
		message="remove"
	/>
</liferay-util:buffer>

<aui:input name="groupRolesRoleIds" type="hidden" value="<%= ListUtil.toString(defaultGroupRoles, Role.ROLE_ID_ACCESSOR) %>" />
<aui:input name="groupTeamsTeamIds" type="hidden" value="<%= ListUtil.toString(defaultGroupTeams, TeamImpl.TEAM_ID_ACCESSOR) %>" />

<h3><liferay-ui:message key="site-roles" /> <liferay-ui:icon-help message="default-roles-assignment-help-message" /></h3>

<liferay-ui:search-container
	headerNames="title,null"
	id="communityRolesSearchContainer"
>

	<liferay-ui:search-container-results
		results="<%= defaultGroupRoles %>"
		total="<%= defaultGroupRoles.size() %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.portal.model.Role"
		keyProperty="roleId"
		modelVar="role"
	>

		<liferay-ui:search-container-column-text
			name="title"
			value="<%= HtmlUtil.escape(role.getTitle(locale)) %>"
		/>

		<liferay-ui:search-container-column-text>
			<a class="modify-link" data-rowId="<%= role.getRoleId() %>" href="javascript:;"><%= removeRoleIcon %></a>
		</liferay-ui:search-container-column-text>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator paginate="<%= false %>" />
</liferay-ui:search-container>

<liferay-ui:icon
	cssClass="modify-link"
	image="add"
	label="<%= true %>"
	message="select"
	url='<%= "javascript:" + renderResponse.getNamespace() + "openCommunityRoleSelector();" %>'
/>

<br /><br />

<h3><liferay-ui:message key="teams" /> <liferay-ui:icon-help message="default-team-assignment-help-message" /></h3>

<liferay-ui:search-container
	headerNames="title,null"
	id="groupTeamsSearchContainer"
>

	<liferay-ui:search-container-results
		results="<%= defaultGroupTeams %>"
		total="<%= defaultGroupTeams.size() %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.portal.model.Team"
		keyProperty="teamId"
		modelVar="team"
	>

		<liferay-ui:search-container-column-text
			name="title"
			value="<%= HtmlUtil.escape(team.getName()) %>"
		/>

		<liferay-ui:search-container-column-text>
			<a class="modify-link" data-rowId="<%= team.getTeamId() %>" href="javascript:;"><%= removeRoleIcon %></a>
		</liferay-ui:search-container-column-text>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator paginate="<%= false %>" />
</liferay-ui:search-container>

<liferay-ui:icon
	cssClass="modify-link"
	image="add"
	label="<%= true %>"
	message="select"
	url='<%= "javascript:" + renderResponse.getNamespace() + "openCommunityTeamSelector();" %>'
/>

<aui:script use="liferay-search-container">
	var searchContainer = Liferay.SearchContainer.get('<portlet:namespace />communityRolesSearchContainer');

	searchContainer.get('contentBox').delegate(
		'click',
		function(event) {
			var link = event.currentTarget;
			var tr = link.ancestor('tr');

			var rowId = link.getAttribute('data-rowId');

			searchContainer.deleteRow(tr, rowId);

			<portlet:namespace />deleteRole(rowId);
		},
		'.modify-link'
	);
</aui:script>

<aui:script use="liferay-search-container">
	var searchContainer = Liferay.SearchContainer.get('<portlet:namespace />groupTeamsSearchContainer');

	searchContainer.get('contentBox').delegate(
		'click',
		function(event) {
			var link = event.currentTarget;
			var tr = link.ancestor('tr');

			var rowId = link.getAttribute('data-rowId');

			searchContainer.deleteRow(tr, rowId);

			<portlet:namespace />deleteTeam(rowId);
		},
		'.modify-link'
	);
</aui:script>

<aui:script>
	var <portlet:namespace />groupRolesRoleIds = ['<%= ListUtil.toString(defaultGroupRoles, Role.ROLE_ID_ACCESSOR, "', '") %>'];
	var <portlet:namespace />groupTeamsTeamIds = ['<%= ListUtil.toString(defaultGroupTeams, Team.TEAM_ID_ACCESSOR, "', '") %>'];

	function <portlet:namespace />deleteRole(roleId) {
		for (var i = 0; i < <portlet:namespace />groupRolesRoleIds.length; i++) {
			if (<portlet:namespace />groupRolesRoleIds[i] == roleId) {
				<portlet:namespace />groupRolesRoleIds.splice(i, 1);

				break;
			}
		}

		document.<portlet:namespace />fm.<portlet:namespace />groupRolesRoleIds.value = <portlet:namespace />groupRolesRoleIds.join(',');
	}

	function <portlet:namespace />deleteTeam(teamId) {
		for (var i = 0; i < <portlet:namespace />groupTeamsTeamIds.length; i++) {
			if (<portlet:namespace />groupTeamsTeamIds[i] == teamId) {
				<portlet:namespace />groupTeamsTeamIds.splice(i, 1);

				break;
			}
		}

		document.<portlet:namespace />fm.<portlet:namespace />groupTeamsTeamIds.value = <portlet:namespace />groupTeamsTeamIds.join(',');
	}

	function <portlet:namespace />openCommunityRoleSelector() {
		var url = '<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="struts_action" value="/sites_admin/select_site_role" /><portlet:param name="step" value="2" /><portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" /></portlet:renderURL>';
		var roleWindow = window.open(url, 'role', 'directories=no,height=640,location=no,menubar=no,resizable=yes,scrollbars=yes,status=no,toolbar=no,width=680');

		roleWindow.focus();
	}

	function <portlet:namespace />openCommunityTeamSelector() {
		var url = '<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="struts_action" value="/sites_admin/select_team" /><portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" /></portlet:renderURL>';
		var teamWindow = window.open(url, 'role', 'directories=no,height=640,location=no,menubar=no,resizable=yes,scrollbars=yes,status=no,toolbar=no,width=680');

		teamWindow.focus();
	}

	Liferay.provide(
		window,
		'<portlet:namespace />selectRole',
		function(roleId, name, searchContainer, groupName, groupId) {
			var A = AUI();

			var searchContainerName = '<portlet:namespace />' + searchContainer + 'SearchContainer';

			searchContainer = Liferay.SearchContainer.get(searchContainerName);

			var rowColumns = [];

			rowColumns.push(name);

			if (groupId) {
				rowColumns.push('<a class="modify-link" data-rowId="' + roleId + '" href="javascript:;"><%= UnicodeFormatter.toString(removeRoleIcon) %></a>');

				<portlet:namespace />groupRolesRoleIds.push(roleId);

				document.<portlet:namespace />fm.<portlet:namespace />groupRolesRoleIds.value = <portlet:namespace />groupRolesRoleIds.join(',');
			}
			else {
				rowColumns.push('<a class="modify-link" data-rowId="' + roleId + '" href="javascript:;"><%= UnicodeFormatter.toString(removeRoleIcon) %></a>');
			}

			searchContainer.addRow(rowColumns, roleId);
			searchContainer.updateDataStore();
		},
		['liferay-search-container']
	);

	Liferay.provide(
		window,
		'<portlet:namespace />selectTeam',
		function(teamId, name, searchContainer, description, groupId) {
			var A = AUI();

			var searchContainerName = '<portlet:namespace />' + searchContainer + 'SearchContainer';

			searchContainer = Liferay.SearchContainer.get(searchContainerName);

			var rowColumns = [];

			rowColumns.push(name);

			if (groupId) {
				rowColumns.push('<a class="modify-link" data-rowId="' + teamId + '" href="javascript:;"><%= UnicodeFormatter.toString(removeRoleIcon) %></a>');

				<portlet:namespace />groupTeamsTeamIds.push(teamId);

				document.<portlet:namespace />fm.<portlet:namespace />groupTeamsTeamIds.value = <portlet:namespace />groupTeamsTeamIds.join(',');
			}
			else {
				rowColumns.push('<a class="modify-link" data-rowId="' + teamId + '" href="javascript:;"><%= UnicodeFormatter.toString(removeRoleIcon) %></a>');
			}

			searchContainer.addRow(rowColumns, teamId);
			searchContainer.updateDataStore();
		},
		['liferay-search-container']
	);
</aui:script>

<aui:script use="liferay-search-container">
	var searchContainer = Liferay.SearchContainer.get('<portlet:namespace />rolesSearchContainer');

	searchContainer.get('contentBox').delegate(
	'click',
	function(event) {
		var link = event.currentTarget;
		var tr = link.ancestor('tr');

		searchContainer.deleteRow(tr, link.getAttribute('data-rowId'));
	},
	'.modify-link'
	);
</aui:script>