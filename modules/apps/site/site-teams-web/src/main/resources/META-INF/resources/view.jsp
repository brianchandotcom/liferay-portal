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
SiteTeamsDisplayContext siteTeamsDisplayContext = new SiteTeamsDisplayContext(renderRequest, renderResponse, request);

SiteTeamsManagementToolbarDisplayContext siteTeamsManagementToolbarDisplayContext = new SiteTeamsManagementToolbarDisplayContext(liferayPortletRequest, liferayPortletResponse, request, siteTeamsDisplayContext);
%>

<clay:management-toolbar
	displayContext="<%= siteTeamsManagementToolbarDisplayContext %>"
/>

<portlet:actionURL name="deleteTeams" var="deleteTeamsURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:actionURL>

<aui:form action="<%= deleteTeamsURL %>" cssClass="container-fluid-1280" name="fm">
	<liferay-ui:search-container
		searchContainer="<%= siteTeamsDisplayContext.getSearchContainer() %>"
	>
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

			Map<String, Object> rowData = new HashMap<>();

			rowData.put("actions", siteTeamsManagementToolbarDisplayContext.getAvailableActions(team));

			row.setData(rowData);
			%>

			<c:choose>
				<c:when test="<%= siteTeamsDisplayContext.isDescriptiveView() %>">
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
				<c:when test="<%= siteTeamsDisplayContext.isListView() %>">
					<liferay-ui:search-container-column-text
						cssClass="table-cell-expand-small table-cell-minw-200 table-title"
						href="<%= rowURL %>"
						name="name"
						property="name"
					/>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-expand table-cell-minw-300"
						href="<%= rowURL %>"
						name="description"
						property="description"
					/>

					<liferay-ui:search-container-column-jsp
						path="/team_action.jsp"
					/>
				</c:when>
			</c:choose>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="<%= siteTeamsDisplayContext.getDisplayStyle() %>"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</aui:form>

<liferay-frontend:component
	componentId="<%= siteTeamsManagementToolbarDisplayContext.getDefaultEventHandler() %>"
	module="js/SiteTeamsManagementToolbarDefaultEventHandler.es"
/>