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
		icon="times"
		markupView="lexicon"
		message="remove"
	/>
</liferay-util:buffer>

<p class="text-muted">
	<liferay-ui:message key="select-the-default-roles-and-teams-for-new-members" />
</p>

<h4 class="text-default"><liferay-ui:message key="site-roles" /> <liferay-ui:icon-help message="default-site-roles-assignment-help" /></h4>

<p class="text-muted <%= defaultSiteRoles.isEmpty() ? StringPool.BLANK : "hide" %>" id="<portlet:namespace />siteRolesEmptyResultMessage">
	<%= StringUtil.lowerCase(LanguageUtil.get(request, "none")) %>
</p>

<liferay-ui:search-container
	headerNames="title,null"
	id="siteRolesSearchContainer"
	total="<%= defaultSiteRoles.size() %>"
>
	<liferay-ui:search-container-results
		results="<%= defaultSiteRoles %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.portal.kernel.model.Role"
		keyProperty="roleId"
		modelVar="role"
	>
		<liferay-ui:search-container-column-text
			name="title"
			value="<%= HtmlUtil.escape(role.getTitle(locale)) %>"
		/>

		<liferay-ui:search-container-column-text
			cssClass="list-group-item-field"
		>
			<a class="modify-link" data-rowId="<%= role.getRoleId() %>" href="javascript:;"><%= removeRoleIcon %></a>
		</liferay-ui:search-container-column-text>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator markupView="lexicon" paginate="<%= false %>" />
</liferay-ui:search-container>

<div class="button-holder">
	<aui:button cssClass="btn-lg modify-link" id="selectSiteRoleLink" value="select" />
</div>

<h4 class="text-default"><liferay-ui:message key="teams" /> <liferay-ui:icon-help message="default-teams-assignment-help" /></h4>

<p class="text-muted <%= defaultTeams.isEmpty() ? StringPool.BLANK : "hide" %>" id="<portlet:namespace />teamsEmptyResultMessage">
	<%= StringUtil.lowerCase(LanguageUtil.get(request, "none")) %>
</p>

<liferay-ui:search-container
	headerNames="title,null"
	id="teamsSearchContainer"
	total="<%= defaultTeams.size() %>"
>
	<liferay-ui:search-container-results
		results="<%= defaultTeams %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.portal.kernel.model.Team"
		keyProperty="teamId"
		modelVar="team"
	>
		<liferay-ui:search-container-column-text
			name="title"
			value="<%= HtmlUtil.escape(team.getName()) %>"
		/>

		<liferay-ui:search-container-column-text
			cssClass="list-group-item-field"
		>
			<a class="modify-link" data-rowId="<%= team.getTeamId() %>" href="javascript:;"><%= removeRoleIcon %></a>
		</liferay-ui:search-container-column-text>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator markupView="lexicon" paginate="<%= false %>" />
</liferay-ui:search-container>

<aui:button-row>
	<aui:button cssClass="btn-lg modify-link" id="selectTeamLink" value="select" />
</aui:button-row>

<aui:script use="liferay-search-container">
	var Util = Liferay.Util;

	var searchContainer = Liferay.SearchContainer.get('<portlet:namespace />siteRolesSearchContainer');

	var searchContainerContentBox = searchContainer.get('contentBox');

	searchContainerContentBox.delegate(
		'click',
		function(event) {
			var link = event.currentTarget;

			searchContainer.deleteRow(link.ancestor('tr'), link.getAttribute('data-rowId'));

			if (searchContainer.getSize() <= 0) {
				A.one('#<portlet:namespace />siteRolesEmptyResultMessage').show();
			}
		},
		'.modify-link'
	);
</aui:script>

<aui:script use="liferay-search-container">
	var Util = Liferay.Util;

	var searchContainer = Liferay.SearchContainer.get('<portlet:namespace />teamsSearchContainer');

	var searchContainerContentBox = searchContainer.get('contentBox');

	searchContainerContentBox.delegate(
		'click',
		function(event) {
			var link = event.currentTarget;

			searchContainer.deleteRow(link.ancestor('tr'), link.getAttribute('data-rowId'));

			if (searchContainer.getSize() <= 0) {
				A.one('#<portlet:namespace />teamsEmptyResultMessage').show();
			}
		},
		'.modify-link'
	);
</aui:script>

<aui:script use="liferay-search-container,escape">

	<%
	PortletURL selectSiteRoleURL = PortletProviderUtil.getPortletURL(request, Role.class.getName(), PortletProvider.Action.BROWSE);

	selectSiteRoleURL.setParameter("roleType", String.valueOf(RoleConstants.TYPE_SITE));
	selectSiteRoleURL.setParameter("step", "2");
	selectSiteRoleURL.setParameter("groupId", String.valueOf(groupId));
	selectSiteRoleURL.setParameter("eventName", liferayPortletResponse.getNamespace() + "selectSiteRole");
	selectSiteRoleURL.setWindowState(LiferayWindowState.POP_UP);
	%>

	A.one('#<portlet:namespace />selectSiteRoleLink').on(
		'click',
		function(event) {
	        var uri = '<%= selectSiteRoleURL.toString() %>';

			Liferay.Util.selectEntity(
				{
					dialog: {
						constrain: true,
						modal: true
					},
					id: '<portlet:namespace />selectSiteRole',
					title: '<liferay-ui:message arguments="site-role" key="select-x" />',
					uri: uri
				},
				function(event) {
					var searchContainer = Liferay.SearchContainer.get('<portlet:namespace />' + event.searchcontainername + 'SearchContainer');

					var rowColumns = [];

					rowColumns.push(A.Escape.html(event.roletitle));
					rowColumns.push('<a class="modify-link" data-rowId="' + event.roleid + '" href="javascript:;"><%= UnicodeFormatter.toString(removeRoleIcon) %></a>');

					searchContainer.addRow(rowColumns, event.roleid);

					searchContainer.updateDataStore();

					A.one('#<portlet:namespace />siteRolesEmptyResultMessage').hide();
				}
			);
		}
	);

	<%
	PortletURL selectTeamURL = PortletProviderUtil.getPortletURL(request, Team.class.getName(), PortletProvider.Action.BROWSE);

	selectTeamURL.setParameter("groupId", String.valueOf(groupId));
	selectTeamURL.setParameter("eventName", liferayPortletResponse.getNamespace() + "selectTeam");
	selectTeamURL.setWindowState(LiferayWindowState.POP_UP);
	%>

	A.one('#<portlet:namespace />selectTeamLink').on(
		'click',
		function(event) {
			var uri = '<%= selectTeamURL.toString() %>';

			Liferay.Util.selectEntity(
				{
					dialog: {
						constrain: true,
						modal: true
					},
					id: '<portlet:namespace />selectTeam',
					title: '<liferay-ui:message arguments="team" key="select-x" />',
					uri: uri
				},
				function(event) {
					var searchContainer = Liferay.SearchContainer.get('<portlet:namespace />' + event.teamsearchcontainername + 'SearchContainer');

					var rowColumns = [];

					rowColumns.push(event.teamname);
					rowColumns.push('<a class="modify-link" data-rowId="' + event.teamid + '" href="javascript:;"><%= UnicodeFormatter.toString(removeRoleIcon) %></a>');

					searchContainer.addRow(rowColumns, event.teamid);

					searchContainer.updateDataStore();

					A.one('#<portlet:namespace />teamsEmptyResultMessage').hide();
				}
			);
		}
	);
</aui:script>