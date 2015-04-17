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
String redirect = ParamUtil.getString(request, "redirect");

long teamId = ParamUtil.getLong(request, "teamId");

Team team = TeamLocalServiceUtil.fetchTeam(teamId);

long groupId = BeanParamUtil.getLong(team, request, "groupId");

Group group = GroupLocalServiceUtil.getGroup(groupId);

Organization organization = null;

if (group.isOrganization()) {
	organization = OrganizationLocalServiceUtil.getOrganization(group.getOrganizationId());
}
%>

<liferay-ui:header
	backURL="<%= redirect %>"
	escapeXml="<%= false %>"
	localizeTitle="<%= false %>"
	title='<%= ((team == null) ? LanguageUtil.get(request, "new-team") : HtmlUtil.escape(team.getName())) %>'
/>

<portlet:actionURL name="editTeam" var="editTeamURL" />

<aui:form action="<%= editTeamURL %>" method="post" name="fm">
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="teamId" type="hidden" value="<%= teamId %>" />
	<aui:input name="groupId" type="hidden" value="<%= groupId %>" />

	<liferay-ui:error exception="<%= DuplicateTeamException.class %>" message="please-enter-a-unique-name" />
	<liferay-ui:error exception="<%= TeamNameException.class %>" message="please-enter-a-valid-name" />

	<aui:model-context bean="<%= team %>" model="<%= Team.class %>" />

	<aui:fieldset>
		<c:if test="<%= team != null %>">
			<aui:input name="teamId" type="resource" value="<%= String.valueOf(team.getTeamId()) %>" />
		</c:if>

		<aui:input autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) %>" name="name" />

		<aui:input name="description" />
	</aui:fieldset>

	<aui:button-row>
		<aui:button type="submit" />

		<aui:button href="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>

<%
if (group.isOrganization()) {
	UsersAdminUtil.addPortletBreadcrumbEntries(organization, request, renderResponse);
}
else {
	PortalUtil.addPortletBreadcrumbEntry(request, group.getDescriptiveName(locale), null);
}

if (team != null) {
	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "manage-teams"), redirect);
	PortalUtil.addPortletBreadcrumbEntry(request, team.getName(), null);
	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "edit"), currentURL);
}
else {
	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "manage-teams"), redirect);
	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "add-team"), currentURL);
}
%>