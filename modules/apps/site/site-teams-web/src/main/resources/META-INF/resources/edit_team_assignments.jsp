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
String tabs1 = ParamUtil.getString(request, "tabs1", "users");
String tabs2 = ParamUtil.getString(request, "tabs2", "current");

int cur = ParamUtil.getInteger(request, SearchContainer.DEFAULT_CUR_PARAM);

String redirect = ParamUtil.getString(request, "redirect");

if (Validator.isNull(redirect)) {
	PortletURL portletURL = renderResponse.createRenderURL();

	redirect = portletURL.toString();
}

long teamId = ParamUtil.getLong(request, "teamId");

Team team = TeamLocalServiceUtil.fetchTeam(teamId);

Group group = GroupLocalServiceUtil.getGroup(team.getGroupId());

if (group != null) {
	group = StagingUtil.getLiveGroup(group.getGroupId());
}

Organization organization = null;

if (group.isOrganization()) {
	organization = OrganizationLocalServiceUtil.getOrganization(group.getOrganizationId());
}

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcPath", "/edit_team_assignments.jsp");
portletURL.setParameter("tabs1", tabs1);
portletURL.setParameter("tabs2", tabs2);
portletURL.setParameter("teamId", String.valueOf(team.getTeamId()));

request.setAttribute("edit_team_assignments.jsp-tabs1", tabs1);
request.setAttribute("edit_team_assignments.jsp-tabs2", tabs2);

request.setAttribute("edit_team_assignments.jsp-cur", cur);

request.setAttribute("edit_team_assignments.jsp-redirect", redirect);

request.setAttribute("edit_team_assignments.jsp-team", team);

request.setAttribute("edit_team_assignments.jsp-group", group);

request.setAttribute("edit_team_assignments.jsp-organization", organization);

request.setAttribute("edit_team_assignments.jsp-portletURL", portletURL);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle(team.getName());
%>

<liferay-ui:tabs
	names="users,user-groups"
	param="tabs1"
	portletURL="<%= portletURL %>"
/>

<c:choose>
	<c:when test='<%= tabs1.equals("users") %>'>
		<liferay-util:include page="/edit_team_assignments_users.jsp" servletContext="<%= application %>" />
	</c:when>
	<c:when test='<%= tabs1.equals("user-groups") %>'>
		<liferay-util:include page="/edit_team_assignments_user_groups.jsp" servletContext="<%= application %>" />
	</c:when>
</c:choose>

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