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
%>

<aui:nav-bar cssClass="collapse-basic-search" markupView="lexicon">
	<aui:nav cssClass="navbar-nav">
		<aui:nav-item href="<%= siteTeamsDisplayContext.getPortletURL().toString() %>" label="teams" selected="<%= true %>" />
	</aui:nav>

	<c:if test="<%= siteTeamsDisplayContext.isSearchEnabled() %>">
		<aui:nav-bar-search>
			<aui:form action="<%= siteTeamsDisplayContext.getPortletURL().toString() %>" name="searchFm">
				<liferay-ui:input-search autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) %>" markupView="lexicon" />
			</aui:form>
		</aui:nav-bar-search>
	</c:if>
</aui:nav-bar>

<liferay-frontend:management-bar
	disabled="<%= siteTeamsDisplayContext.isDisabledManagementBar() %>"
	includeCheckBox="<%= true %>"
	searchContainerId="teams"
>
	<liferay-frontend:management-bar-buttons>
		<liferay-portlet:actionURL name="changeDisplayStyle" varImpl="changeDisplayStyleURL">
			<portlet:param name="redirect" value="<%= currentURL %>" />
		</liferay-portlet:actionURL>

		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"icon", "descriptive", "list"} %>'
			portletURL="<%= changeDisplayStyleURL %>"
			selectedDisplayStyle="<%= siteTeamsDisplayContext.getDisplayStyle() %>"
		/>
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all"} %>'
			portletURL="<%= siteTeamsDisplayContext.getPortletURL() %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= siteTeamsDisplayContext.getOrderByCol() %>"
			orderByType="<%= siteTeamsDisplayContext.getOrderByType() %>"
			orderColumns='<%= new String[] {"name"} %>'
			portletURL="<%= siteTeamsDisplayContext.getPortletURL() %>"
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
				<c:when test="<%= siteTeamsDisplayContext.isIconView() %>">

					<%
					row.setCssClass("entry-card lfr-asset-item");
					%>

					<liferay-ui:search-container-column-text>
						<liferay-frontend:icon-vertical-card
							actionJsp="/team_action.jsp"
							actionJspServletContext="<%= application %>"
							cssClass="entry-display-style"
							icon="users"
							resultRow="<%= row %>"
							rowChecker="<%= searchContainer.getRowChecker() %>"
							subtitle="<%= team.getDescription() %>"
							title="<%= team.getName() %>"
							url="<%= rowURL != null ? rowURL.toString() : null %>"
						/>
					</liferay-ui:search-container-column-text>
				</c:when>
				<c:when test="<%= siteTeamsDisplayContext.isListView() %>">
					<liferay-ui:search-container-column-text
						cssClass="content-column name-column title-column"
						href="<%= rowURL %>"
						name="name"
						property="name"
						truncate="<%= true %>"
					/>

					<liferay-ui:search-container-column-text
						cssClass="content-column description-column"
						href="<%= rowURL %>"
						name="description"
						property="description"
						truncate="<%= true %>"
					/>

					<liferay-ui:search-container-column-jsp
						cssClass="entry-action-column"
						path="/team_action.jsp"
					/>
				</c:when>
			</c:choose>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator displayStyle="<%= siteTeamsDisplayContext.getDisplayStyle() %>" markupView="lexicon" />
	</liferay-ui:search-container>
</aui:form>

<c:if test="<%= siteTeamsDisplayContext.isShowAddButton() %>">

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