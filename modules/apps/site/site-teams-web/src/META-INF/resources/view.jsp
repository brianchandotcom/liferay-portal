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
Group group = GroupLocalServiceUtil.getGroup(scopeGroupId);

PortletURL portletURL = renderResponse.createRenderURL();

pageContext.setAttribute("portletURL", portletURL);
%>

<aui:form action="<%= portletURL.toString() %>" cssClass="form-search" method="get" name="fm">
	<liferay-portlet:renderURLParams varImpl="portletURL" />

	<liferay-ui:search-container
		searchContainer="<%= new TeamSearch(renderRequest, portletURL) %>"
	>

		<%
		TeamDisplayTerms searchTerms = (TeamDisplayTerms)searchContainer.getSearchTerms();

		portletURL.setParameter(searchContainer.getCurParam(), String.valueOf(searchContainer.getCur()));

		total = TeamLocalServiceUtil.searchCount(scopeGroupId, searchTerms.getName(), searchTerms.getDescription(), new LinkedHashMap<String, Object>());

		searchContainer.setTotal(total);
		%>

		<aui:nav-bar>
			<aui:nav-bar-search>
				<div class="form-search">
					<liferay-ui:input-search autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) %>" name="<%= searchTerms.NAME %>" />
				</div>
			</aui:nav-bar-search>
		</aui:nav-bar>

		<liferay-ui:search-container-results
			results="<%= TeamLocalServiceUtil.search(scopeGroupId, searchTerms.getName(), searchTerms.getDescription(), new LinkedHashMap<String, Object>(), searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator()) %>"
		/>

		<liferay-ui:search-container-row
			className="com.liferay.portal.model.Team"
			escapedModel="<%= true %>"
			modelVar="team"
		>

			<%
			PortletURL rowURL = null;

			if (TeamPermissionUtil.contains(permissionChecker, team, ActionKeys.UPDATE)) {
				rowURL = renderResponse.createRenderURL();

				rowURL.setParameter("mvcPath", "/edit_team.jsp");
				rowURL.setParameter("teamId", String.valueOf(team.getTeamId()));
			}
			%>

			<liferay-ui:search-container-column-text
				href="<%= rowURL %>"
				name="name"
				property="name"
			/>

			<liferay-ui:search-container-column-text
				href="<%= rowURL %>"
				name="description"
				property="description"
			/>

			<liferay-ui:search-container-column-jsp
				cssClass="entry-action"
				path="/team_action.jsp"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />
	</liferay-ui:search-container>
</aui:form>

<c:if test="<%= GroupPermissionUtil.contains(permissionChecker, group, ActionKeys.MANAGE_TEAMS) %>">

	<%
	PortletURL addTeamURL = renderResponse.createRenderURL();

	addTeamURL.setParameter("mvcPath", "/edit_team.jsp");
	%>

	<liferay-frontend:add-menu>
		<liferay-frontend:add-menu-item title='<%= LanguageUtil.get(request, "add-team") %>' url="<%= addTeamURL.toString() %>" />
	</liferay-frontend:add-menu>
</c:if>

<%
if (group.isOrganization()) {
	Organization organization = OrganizationLocalServiceUtil.getOrganization(group.getOrganizationId());

	UsersAdminUtil.addPortletBreadcrumbEntries(organization, request, renderResponse);
}
else {
	PortalUtil.addPortletBreadcrumbEntry(request, group.getDescriptiveName(locale), null);
}

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "manage-teams"), currentURL);
%>