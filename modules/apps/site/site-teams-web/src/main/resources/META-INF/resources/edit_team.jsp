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
String redirect = ParamUtil.getString(request, "redirect");

if (Validator.isNull(redirect)) {
	PortletURL portletURL = renderResponse.createRenderURL();

	redirect = portletURL.toString();
}

long teamId = ParamUtil.getLong(request, "teamId");

Team team = TeamLocalServiceUtil.fetchTeam(teamId);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle(((team == null) ? LanguageUtil.get(request, "new-team") : team.getName()));
%>

<portlet:actionURL name="editTeam" var="editTeamURL">
	<portlet:param name="mvcPath" value="/edit_team.jsp" />
</portlet:actionURL>

<aui:form action="<%= editTeamURL %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="teamId" type="hidden" value="<%= teamId %>" />

	<liferay-ui:error exception="<%= DuplicateTeamException.class %>" message="please-enter-a-unique-name" />
	<liferay-ui:error exception="<%= TeamNameException.class %>" message="please-enter-a-valid-name" />

	<aui:model-context bean="<%= team %>" model="<%= Team.class %>" />

	<aui:fieldset-group markupView="lexicon">
		<aui:fieldset>
			<c:if test="<%= team != null %>">
				<aui:input name="teamId" type="resource" value="<%= String.valueOf(team.getTeamId()) %>" />
			</c:if>

			<aui:input autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) %>" name="name" />

			<aui:input name="description" />
		</aui:fieldset>
	</aui:fieldset-group>

	<aui:button-row>
		<aui:button cssClass="btn-lg" type="submit" />

		<aui:button cssClass="btn-lg" href="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>

<%
Group group = GroupLocalServiceUtil.getGroup(scopeGroupId);

if (group.isOrganization()) {
	Organization organization = OrganizationLocalServiceUtil.getOrganization(group.getOrganizationId());

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