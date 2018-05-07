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
EditSiteTeamAssignmentsDisplayContext editSiteTeamAssignmentsDisplayContext = new EditSiteTeamAssignmentsDisplayContext(renderRequest, renderResponse, request);

String redirect = ParamUtil.getString(request, "redirect");

if (Validator.isNull(redirect)) {
	PortletURL portletURL = renderResponse.createRenderURL();

	redirect = portletURL.toString();
}

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle(editSiteTeamAssignmentsDisplayContext.getTeamName());
%>

<c:choose>
	<c:when test='<%= Objects.equals(editSiteTeamAssignmentsDisplayContext.getTabs1(), "users") %>'>
		<liferay-util:include page="/edit_team_assignments_users.jsp" servletContext="<%= application %>" />
	</c:when>
	<c:when test='<%= Objects.equals(editSiteTeamAssignmentsDisplayContext.getTabs1(), "user-groups") %>'>
		<liferay-util:include page="/edit_team_assignments_user_groups.jsp" servletContext="<%= application %>" />
	</c:when>
</c:choose>

<%
Team team = editSiteTeamAssignmentsDisplayContext.getTeam();

Group group = GroupLocalServiceUtil.getGroup(team.getGroupId());

if (group != null) {
	group = StagingUtil.getLiveGroup(group.getGroupId());
}

Organization organization = null;

if (group.isOrganization()) {
	organization = OrganizationLocalServiceUtil.getOrganization(group.getOrganizationId());
}

if (group.isOrganization()) {
	UsersAdminUtil.addPortletBreadcrumbEntries(organization, request, renderResponse);
}
else {
	PortalUtil.addPortletBreadcrumbEntry(request, group.getDescriptiveName(locale), null);
}

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "manage-teams"), redirect);
PortalUtil.addPortletBreadcrumbEntry(request, editSiteTeamAssignmentsDisplayContext.getTeamName(), null);
PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "assign-members"), currentURL);
%>