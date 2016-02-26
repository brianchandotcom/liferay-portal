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
String displayStyle = ParamUtil.getString(request, "displayStyle", "descriptive");

Group group = GroupLocalServiceUtil.getGroup(scopeGroupId);

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("displayStyle", displayStyle);

pageContext.setAttribute("portletURL", portletURL);

SearchContainer teamSearchContainer = new TeamSearch(renderRequest, PortletURLUtil.clone(portletURL, renderResponse));

teamSearchContainer.setRowChecker(new EmptyOnClickRowChecker(renderResponse));

TeamDisplayTerms searchTerms = (TeamDisplayTerms)teamSearchContainer.getSearchTerms();

int teamsCount = TeamServiceUtil.searchCount(scopeGroupId, searchTerms.getKeywords(), searchTerms.getKeywords(), new LinkedHashMap<String, Object>());

teamSearchContainer.setTotal(teamsCount);
%>

<aui:nav-bar cssClass="collapse-basic-search" markupView="lexicon">
	<portlet:renderURL var="mainURL" />

	<aui:nav cssClass="navbar-nav">
		<aui:nav-item href="<%= mainURL.toString() %>" label="teams" selected="<%= true %>" />
	</aui:nav>

	<c:if test="<%= (teamsCount > 0) || searchTerms.isSearch() %>">
		<aui:nav-bar-search>
			<aui:form action="<%= portletURL.toString() %>" name="searchFm">
				<liferay-portlet:renderURLParams varImpl="portletURL" />

				<liferay-ui:input-search autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) %>" markupView="lexicon" />
			</aui:form>
		</aui:nav-bar-search>
	</c:if>
</aui:nav-bar>

<liferay-frontend:management-bar
	disabled="<%= teamsCount <= 0 %>"
	includeCheckBox="<%= true %>"
	searchContainerId="teams"
>

	<%
	PortletURL displayStyleURL = renderResponse.createRenderURL();
	%>

	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"icon", "descriptive", "list"} %>'
			portletURL="<%= displayStyleURL %>"
			selectedDisplayStyle="<%= displayStyle %>"
		/>
	</liferay-frontend:management-bar-buttons>

	<%
	PortletURL iteratorURL = renderResponse.createRenderURL();

	iteratorURL.setParameter("displayStyle", displayStyle);
	%>

	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all"} %>'
			portletURL="<%= renderResponse.createRenderURL() %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= teamSearchContainer.getOrderByCol() %>"
			orderByType="<%= teamSearchContainer.getOrderByType() %>"
			orderColumns='<%= new String[] {"name"} %>'
			portletURL="<%= iteratorURL %>"
		/>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-action-buttons>
		<liferay-frontend:management-bar-button href="javascript:;" icon="trash" id="deleteSelectedTeams" label="delete" />
	</liferay-frontend:management-bar-action-buttons>
</liferay-frontend:management-bar>

<portlet:actionURL name="deleteTeams" var="deleteTeamsURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:actionURL>

<aui:form action="<%= deleteTeamsURL %>" cssClass="container-fluid-1280" name="fm">
	<liferay-ui:search-container
		emptyResultsMessage="there-are-no-site-teams.-you-can-add-a-site-team-by-clicking-the-plus-button-on-the-bottom-right-corner"
		id="teams"
		searchContainer="<%= teamSearchContainer %>"
		total="<%= teamsCount %>"
	>
		<liferay-ui:search-container-results
			results="<%= TeamServiceUtil.search(scopeGroupId, searchTerms.getKeywords(), searchTerms.getKeywords(), new LinkedHashMap<String, Object>(), searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator()) %>"
		/>

		<liferay-ui:search-container-row
			className="com.liferay.portal.kernel.model.Team"
			cssClass="selectable"
			escapedModel="<%= true %>"
			keyProperty="teamId"
			modelVar="team"
		>

			<%
			PortletURL rowURL = null;

			if (TeamPermissionUtil.contains(permissionChecker, team, ActionKeys.ASSIGN_MEMBERS)) {
				rowURL = renderResponse.createRenderURL();

				rowURL.setParameter("mvcPath", "/edit_team_assignments.jsp");
				rowURL.setParameter("teamId", String.valueOf(team.getTeamId()));
			}
			%>

			<c:choose>
				<c:when test='<%= displayStyle.equals("descriptive") %>'>
					<liferay-ui:search-container-column-icon
						icon="users"
						toggleRowChecker="<%= true %>"
					/>

					<liferay-ui:search-container-column-text
						colspan="<%= 2 %>"
					>
						<h5>
							<aui:a href="<%= (rowURL != null) ? rowURL.toString() : null %>"><%= team.getName() %></aui:a>
						</h5>

						<h6 class="text-default">
							<span><%= team.getDescription() %></span>
						</h6>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-jsp
						path="/team_action.jsp"
					/>
				</c:when>
				<c:when test='<%= displayStyle.equals("icon") %>'>

					<%
					row.setCssClass("article-entry col-md-2 col-sm-4 col-xs-6");
					%>

					<liferay-ui:search-container-column-text>
						<liferay-frontend:icon-vertical-card
							actionJsp="/team_action.jsp"
							actionJspServletContext="<%= application %>"
							cssClass="entry-display-style"
							icon="users"
							resultRow="<%= row %>"
							rowChecker="<%= teamSearchContainer.getRowChecker() %>"
							subtitle="<%= team.getDescription() %>"
							title="<%= team.getName() %>"
						/>
					</liferay-ui:search-container-column-text>
				</c:when>
				<c:when test='<%= displayStyle.equals("list") %>'>
					<liferay-ui:search-container-column-text
						cssClass="text-strong"
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
						cssClass="list-group-item-field"
						path="/team_action.jsp"
					/>
				</c:when>
			</c:choose>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator displayStyle="<%= displayStyle %>" markupView="lexicon" searchContainer="<%= searchContainer %>" />
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

<aui:script sandbox="<%= true %>">
	$('#<portlet:namespace />deleteSelectedTeams').on(
		'click',
		function() {
			if (confirm('<liferay-ui:message key="are-you-sure-you-want-to-delete-this" />')) {
				submitForm($(document.<portlet:namespace />fm));
			}
		}
	);
</aui:script>

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