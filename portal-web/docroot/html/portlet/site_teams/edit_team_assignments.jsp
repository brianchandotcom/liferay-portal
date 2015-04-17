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
String tabs1 = ParamUtil.getString(request, "tabs1", "users");
String tabs2 = ParamUtil.getString(request, "tabs2", "current");

int cur = ParamUtil.getInteger(request, SearchContainer.DEFAULT_CUR_PARAM);

String redirect = ParamUtil.getString(request, "redirect");

long teamId = ParamUtil.getLong(request, "teamId");

Team team = TeamLocalServiceUtil.fetchTeam(teamId);

Group group = GroupLocalServiceUtil.getGroup(team.getGroupId());

Organization organization = null;

if (group.isOrganization()) {
	organization = OrganizationLocalServiceUtil.getOrganization(group.getOrganizationId());
}

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcPath", "/html/portlet/site_teams/edit_team_assignments.jsp");
portletURL.setParameter("tabs1", tabs1);
portletURL.setParameter("tabs2", tabs2);
portletURL.setParameter("redirect", redirect);
portletURL.setParameter("teamId", String.valueOf(team.getTeamId()));

request.setAttribute("edit_team_assignments.jsp-tabs1", tabs1);
request.setAttribute("edit_team_assignments.jsp-tabs2", tabs2);

request.setAttribute("edit_team_assignments.jsp-cur", cur);

request.setAttribute("edit_team_assignments.jsp-redirect", redirect);

request.setAttribute("edit_team_assignments.jsp-team", team);

request.setAttribute("edit_team_assignments.jsp-group", group);

request.setAttribute("edit_team_assignments.jsp-organization", organization);

request.setAttribute("edit_team_assignments.jsp-portletURL", portletURL);
%>

<liferay-ui:header
	backURL="<%= redirect %>"
	localizeTitle="<%= false %>"
	title="<%= team.getName() %>"
/>

<liferay-ui:tabs
	names="users,user-groups"
	param="tabs1"
	url="<%= portletURL.toString() %>"
/>

<portlet:actionURL name="editTeamAssignment" var="editAssignmentsURL" />

<aui:form action="<%= editAssignmentsURL %>" method="post" name="fm">
	<aui:input name="tabs1" type="hidden" value="<%= tabs1 %>" />
	<aui:input name="tabs2" type="hidden" value="<%= tabs2 %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="assignmentsRedirect" type="hidden" />
	<aui:input name="teamId" type="hidden" value="<%= String.valueOf(team.getTeamId()) %>" />

	<c:choose>
		<c:when test='<%= tabs1.equals("users") %>'>
			<liferay-util:include page="/html/portlet/site_teams/edit_team_assignments_users.jsp" />
		</c:when>
		<c:when test='<%= tabs1.equals("user-groups") %>'>
			<liferay-util:include page="/html/portlet/site_teams/edit_team_assignments_user_groups.jsp" />
		</c:when>
	</c:choose>
</aui:form>

<aui:script>
	function <portlet:namespace />updateTeamUserGroups(assignmentsRedirect) {
		var Util = Liferay.Util;

		var form = AUI.$(document.<portlet:namespace />fm);

		form.fm('assignmentsRedirect').val(assignmentsRedirect);
		form.fm('addUserGroupIds').val(Util.listCheckedExcept(form, '<portlet:namespace />allRowIds'));
		form.fm('removeUserGroupIds').val(Util.listUncheckedExcept(form, '<portlet:namespace />allRowIds'));

		submitForm(form);
	}

	function <portlet:namespace />updateTeamUsers(assignmentsRedirect) {
		var Util = Liferay.Util;

		var form = AUI.$(document.<portlet:namespace />fm);

		form.fm('assignmentsRedirect').val(assignmentsRedirect);
		form.fm('addUserIds').val(Util.listCheckedExcept(form, '<portlet:namespace />allRowIds'));
		form.fm('removeUserIds').val(Util.listUncheckedExcept(form, '<portlet:namespace />allRowIds'));

		submitForm(form);
	}
</aui:script>

<%
if (group.isOrganization()) {
	UsersAdminUtil.addPortletBreadcrumbEntries(organization, request, renderResponse);
}
else {
	PortalUtil.addPortletBreadcrumbEntry(request, group.getDescriptiveName(locale), null);
}

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "manage-teams"), redirect);
PortalUtil.addPortletBreadcrumbEntry(request, team.getName(), null);
PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "assign-members"), currentURL);
%>