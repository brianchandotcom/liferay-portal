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

<%@ include file="/html/portlet/portal_settings/init.jsp" %>

<%
Group liveGroup = (Group)request.getAttribute("site.liveGroup");

long groupId = scopeGroupId;

UnicodeProperties groupTypeSettings = null;

if (liveGroup != null) {
	groupId = liveGroup.getGroupId();

	groupTypeSettings = liveGroup.getTypeSettingsProperties();
}
else {
	groupTypeSettings = new UnicodeProperties();
}

List<Role> defaultSiteRoles = new ArrayList();

long[] defaultSiteRoleIds = StringUtil.split(groupTypeSettings.getProperty("defaultSiteRoleIds"), 0L);

for (long defaultSiteRoleId : defaultSiteRoleIds) {
	Role role = RoleLocalServiceUtil.getRole(defaultSiteRoleId);

	defaultSiteRoles.add(role);
}

List<Team> defaultTeams = new ArrayList();

long[] defaultTeamIds = StringUtil.split(groupTypeSettings.getProperty("defaultTeamIds"), 0L);

for (long defaultTeamId : defaultTeamIds) {
	Team team = TeamLocalServiceUtil.getTeam(defaultTeamId);

	defaultTeams.add(team);
}
%>

<liferay-util:buffer var="removeRoleIcon">
	<liferay-ui:icon
		image="unlink"
		label="<%= true %>"
		message="remove"
	/>
</liferay-util:buffer>

<aui:input name="siteRolesRoleIds" type="hidden" value="<%= ListUtil.toString(defaultSiteRoles, Role.ROLE_ID_ACCESSOR) %>" />
<aui:input name="teamsTeamIds" type="hidden" value="<%= ListUtil.toString(defaultTeams, TeamImpl.TEAM_ID_ACCESSOR) %>" />

<h3><liferay-ui:message key="site-roles" /> <liferay-ui:icon-help message="default-site-roles-assignment-help" /></h3>

<liferay-ui:search-container
	headerNames="title,null"
	id="siteRolesSearchContainer"
>
	<liferay-ui:search-container-results
		results="<%= defaultSiteRoles %>"
		total="<%= defaultSiteRoles.size() %>"
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
	url='<%= "javascript:" + renderResponse.getNamespace() + "openSiteRoleSelector();" %>'
/>

<br /><br />

<h3><liferay-ui:message key="teams" /> <liferay-ui:icon-help message="default-teams-assignment-help" /></h3>

<liferay-ui:search-container
	headerNames="title,null"
	id="teamsSearchContainer"
>
	<liferay-ui:search-container-results
		results="<%= defaultTeams %>"
		total="<%= defaultTeams.size() %>"
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
	url='<%= "javascript:" + renderResponse.getNamespace() + "openTeamSelector();" %>'
/>

<aui:script use="liferay-search-container">
	var searchContainer = Liferay.SearchContainer.get('<portlet:namespace />siteRolesSearchContainer');

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
	var searchContainer = Liferay.SearchContainer.get('<portlet:namespace />teamsSearchContainer');

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
	var <portlet:namespace />siteRolesRoleIds = ['<%= ListUtil.toString(defaultSiteRoles, Role.ROLE_ID_ACCESSOR, "', '") %>'];
	var <portlet:namespace />teamsTeamIds = ['<%= ListUtil.toString(defaultTeams, Team.TEAM_ID_ACCESSOR, "', '") %>'];

	function <portlet:namespace />deleteRole(roleId) {
		for (var i = 0; i < <portlet:namespace />siteRolesRoleIds.length; i++) {
			if (<portlet:namespace />siteRolesRoleIds[i] == roleId) {
				<portlet:namespace />siteRolesRoleIds.splice(i, 1);

				break;
			}
		}

		document.<portlet:namespace />fm.<portlet:namespace />siteRolesRoleIds.value = <portlet:namespace />siteRolesRoleIds.join(',');
	}

	function <portlet:namespace />deleteTeam(teamId) {
		for (var i = 0; i < <portlet:namespace />teamsTeamIds.length; i++) {
			if (<portlet:namespace />teamsTeamIds[i] == teamId) {
				<portlet:namespace />teamsTeamIds.splice(i, 1);

				break;
			}
		}

		document.<portlet:namespace />fm.<portlet:namespace />teamsTeamIds.value = <portlet:namespace />teamsTeamIds.join(',');
	}

	function <portlet:namespace />openSiteRoleSelector() {
		var url = '<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="struts_action" value="/sites_admin/select_site_role" /><portlet:param name="step" value="2" /><portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" /></portlet:renderURL>';

		var roleWindow = window.open(url, 'role', 'directories=no,height=640,location=no,menubar=no,resizable=yes,scrollbars=yes,status=no,toolbar=no,width=680');

		roleWindow.focus();
	}

	function <portlet:namespace />openTeamSelector() {
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

			rowColumns.push(A.Escape.html(name));

			if (groupId) {
				rowColumns.push('<a class="modify-link" data-rowId="' + roleId + '" href="javascript:;"><%= UnicodeFormatter.toString(removeRoleIcon) %></a>');

				<portlet:namespace />siteRolesRoleIds.push(roleId);

				document.<portlet:namespace />fm.<portlet:namespace />siteRolesRoleIds.value = <portlet:namespace />siteRolesRoleIds.join(',');
			}
			else {
				rowColumns.push('<a class="modify-link" data-rowId="' + roleId + '" href="javascript:;"><%= UnicodeFormatter.toString(removeRoleIcon) %></a>');
			}

			searchContainer.addRow(rowColumns, roleId);
			searchContainer.updateDataStore();
		},
		['liferay-search-container', 'escape']
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

				<portlet:namespace />teamsTeamIds.push(teamId);

				document.<portlet:namespace />fm.<portlet:namespace />teamsTeamIds.value = <portlet:namespace />teamsTeamIds.join(',');
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