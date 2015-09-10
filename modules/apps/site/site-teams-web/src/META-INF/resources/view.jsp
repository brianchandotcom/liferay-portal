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

pageContext.setAttribute("portletURL", portletURL);

SearchContainer teamSearchContainer = new TeamSearch(renderRequest, portletURL);

TeamDisplayTerms searchTerms = (TeamDisplayTerms)teamSearchContainer.getSearchTerms();
%>

<aui:nav-bar cssClass="collapse-basic-search" markupView="lexicon">
	<aui:nav cssClass="navbar-nav">
		<aui:nav-item cssClass="active" label="teams" />
	</aui:nav>

	<aui:nav-bar-search>
		<aui:form action="<%= portletURL.toString() %>" method="get" name="searchFm">
			<liferay-portlet:renderURLParams varImpl="portletURL" />

			<liferay-ui:input-search autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) %>" markupView="lexicon" name="<%= searchTerms.NAME %>" />
		</aui:form>
	</aui:nav-bar-search>
</aui:nav-bar>

<div class="management-bar-container">
	<liferay-frontend:management-bar
		includeCheckBox="<%= true %>"
	>
		<liferay-frontend:management-bar-buttons>
			<liferay-frontend:management-bar-display-buttons
				displayStyleURL="<%= portletURL %>"
				displayViews='<%= new String[]{"descriptive", "list"} %>'
				selectedDisplayStyle="<%= displayStyle %>"
			/>
		</liferay-frontend:management-bar-buttons>
	</liferay-frontend:management-bar>

	<liferay-frontend:management-bar
		cssClass="management-bar-no-collapse"
		id="teamsActionsButton"
	>
		<liferay-frontend:management-bar-buttons>
			<aui:a cssClass="btn" href="javascript:;" iconCssClass="icon-trash" id="deleteSelectedTeams" />
		</liferay-frontend:management-bar-buttons>
	</liferay-frontend:management-bar>
</div>

<%
portletURL.setParameter("displayStyle", displayStyle);
%>

<aui:form cssClass="container-fluid-1280" name="fm">
	<aui:input name="teamIds" type="hidden" />

	<liferay-ui:search-container
		id="teams"
		rowChecker="<%= new RowChecker(renderResponse) %>"
		searchContainer="<%= teamSearchContainer %>"
	>

		<%
		portletURL.setParameter(searchContainer.getCurParam(), String.valueOf(searchContainer.getCur()));

		total = TeamLocalServiceUtil.searchCount(scopeGroupId, searchTerms.getName(), searchTerms.getDescription(), new LinkedHashMap<String, Object>());

		searchContainer.setTotal(total);
		%>

		<liferay-ui:search-container-results
			results="<%= TeamLocalServiceUtil.search(scopeGroupId, searchTerms.getName(), searchTerms.getDescription(), new LinkedHashMap<String, Object>(), searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator()) %>"
		/>

		<liferay-ui:search-container-row
			className="com.liferay.portal.model.Team"
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
					<liferay-ui:search-container-column-text>
						<h3 class="icon-group"></h3>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-text
						colspan="<%= 2 %>"
					>
						<h5>
							<a href="<%= rowURL.toString() %>">
								<%= team.getName() %>
							</a>
						</h5>
						<h6 class="text-default">
							<span><%= team.getDescription() %></span>
						</h6>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-jsp
						path="/team_action.jsp"
					/>
				</c:when>
				<c:when test='<%= displayStyle.equals("list") %>'>
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
						cssClass="checkbox-cell entry-action"
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
	var Util = Liferay.Util;

	var form = $(document.<portlet:namespace />fm);

	$('#<portlet:namespace />teamsSearchContainer').on(
		'click',
		'input[type=checkbox]',
		function() {
			var hide = (Util.listCheckedExcept(form, '<portlet:namespace /><%= RowChecker.ALL_ROW_IDS %>').length == 0);

			$('#<portlet:namespace />teamsActionsButton').toggleClass('on', !hide);
		}
	);

	$('#<portlet:namespace />deleteSelectedTeams').on(
		'click',
		function() {
			if (confirm('<liferay-ui:message key="are-you-sure-you-want-to-delete-this" />')) {
				<portlet:actionURL name="deleteTeams" var="deleteTeamsURL">
					<portlet:param name="redirect" value="<%= currentURL %>" />
				</portlet:actionURL>

				form.fm('teamIds').val(Util.listCheckedExcept(form, '<portlet:namespace />allRowIds'));

				submitForm(form, '<%= deleteTeamsURL %>');
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