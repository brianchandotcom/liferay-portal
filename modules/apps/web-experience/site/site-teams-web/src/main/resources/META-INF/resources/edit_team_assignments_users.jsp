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
EditSiteTeamAssignmentsUsersDisplayContext editSiteTeamAssignmentsUsersDisplayContext = new EditSiteTeamAssignmentsUsersDisplayContext(renderRequest, renderResponse, request);
%>

<clay:navigation-bar
	inverted="<%= true %>"
	items="<%= editSiteTeamAssignmentsUsersDisplayContext.getNavigationItems() %>"
/>

<clay:management-toolbar
	actionItems="<%= editSiteTeamAssignmentsUsersDisplayContext.getActionDropdownItems() %>"
	clearResultsURL="<%= editSiteTeamAssignmentsUsersDisplayContext.getClearResultsURL() %>"
	componentId="editTeamAssignemntsUsersWebManagementToolbar"
	disabled="<%= editSiteTeamAssignmentsUsersDisplayContext.isDisabledManagementBar() %>"
	filterItems="<%= editSiteTeamAssignmentsUsersDisplayContext.getFilterDropdownItems() %>"
	searchActionURL="<%= editSiteTeamAssignmentsUsersDisplayContext.getSearchActionURL() %>"
	searchContainerId="users"
	searchFormName="searchFm"
	showCreationMenu="<%= true %>"
	showSearch="<%= editSiteTeamAssignmentsUsersDisplayContext.isShowSearch() %>"
	sortingOrder="<%= editSiteTeamAssignmentsUsersDisplayContext.getOrderByType() %>"
	sortingURL="<%= editSiteTeamAssignmentsUsersDisplayContext.getSortingURL() %>"
	totalItems="<%= editSiteTeamAssignmentsUsersDisplayContext.getTotalItems() %>"
	viewTypes="<%= editSiteTeamAssignmentsUsersDisplayContext.getViewTypeItems() %>"
/>

<portlet:actionURL name="deleteTeamUsers" var="deleteTeamUsersURL" />

<aui:form action="<%= deleteTeamUsersURL %>" cssClass="container-fluid-1280 portlet-site-teams-users" method="post" name="fm">
	<aui:input name="tabs1" type="hidden" value="<%= editSiteTeamAssignmentsUsersDisplayContext.getTabs1() %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="teamId" type="hidden" value="<%= String.valueOf(editSiteTeamAssignmentsUsersDisplayContext.getTeamId()) %>" />

	<liferay-ui:search-container
		id="users"
		searchContainer="<%= editSiteTeamAssignmentsUsersDisplayContext.getUserSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.portal.kernel.model.User"
			cssClass="selectable"
			escapedModel="<%= true %>"
			keyProperty="userId"
			modelVar="user2"
			rowIdProperty="screenName"
		>
			<c:choose>
				<c:when test='<%= Objects.equals(editSiteTeamAssignmentsUsersDisplayContext.getDisplayStyle(), "icon") %>'>

					<%
					row.setCssClass("entry-card lfr-asset-item selectable");
					%>

					<liferay-ui:search-container-column-text>
						<liferay-frontend:user-vertical-card
							actionJsp="/edit_team_assignments_users_action.jsp"
							actionJspServletContext="<%= application %>"
							cssClass="entry-display-style"
							resultRow="<%= row %>"
							rowChecker="<%= searchContainer.getRowChecker() %>"
							subtitle="<%= user2.getScreenName() %>"
							title="<%= user2.getFullName() %>"
							userId="<%= user2.getUserId() %>"
						/>
					</liferay-ui:search-container-column-text>
				</c:when>
				<c:when test='<%= Objects.equals(editSiteTeamAssignmentsUsersDisplayContext.getDisplayStyle(), "descriptive") %>'>
					<liferay-ui:search-container-column-text>
						<liferay-ui:user-portrait
							userId="<%= user2.getUserId() %>"
						/>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-text
						colspan="<%= 2 %>"
					>
						<h5><%= user2.getFullName() %></h5>

						<h6 class="text-default">
							<span><%= user2.getScreenName() %></span>
						</h6>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-jsp
						path="/edit_team_assignments_users_action.jsp"
					/>
				</c:when>
				<c:otherwise>
					<liferay-ui:search-container-column-text
						cssClass="table-cell-content"
						name="name"
						property="fullName"
					/>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-content"
						name="screen-name"
						property="screenName"
					/>

					<liferay-ui:search-container-column-jsp
						path="/edit_team_assignments_users_action.jsp"
					/>
				</c:otherwise>
			</c:choose>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="<%= editSiteTeamAssignmentsUsersDisplayContext.getDisplayStyle() %>"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</aui:form>

<portlet:actionURL name="addTeamUsers" var="addTeamUsersURL" />

<aui:form action="<%= addTeamUsersURL %>" cssClass="hide" name="addTeamUsersFm">
	<aui:input name="tabs1" type="hidden" value="<%= editSiteTeamAssignmentsUsersDisplayContext.getTabs1() %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="teamId" type="hidden" value="<%= String.valueOf(editSiteTeamAssignmentsUsersDisplayContext.getTeamId()) %>" />
</aui:form>

<aui:script use="liferay-item-selector-dialog">
	<portlet:renderURL var="selectUserURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
		<portlet:param name="mvcPath" value="/select_users.jsp" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="teamId" value="<%= String.valueOf(editSiteTeamAssignmentsUsersDisplayContext.getTeamId()) %>" />
	</portlet:renderURL>

	function handleAddClick(event) {
		var itemSelectorDialog = new A.LiferayItemSelectorDialog(
			{
				eventName: '<portlet:namespace />selectUser',
				on: {
					selectedItemChange: function(event) {
						var selectedItem = event.newVal;

						if (selectedItem) {
							var addTeamUsersFm = $(document.<portlet:namespace />addTeamUsersFm);

							addTeamUsersFm.append(selectedItem);

							submitForm(addTeamUsersFm);
						}
					}
				},
				title: '<liferay-ui:message arguments="<%= editSiteTeamAssignmentsUsersDisplayContext.getTeamName() %>" key="add-new-user-to-x" />',
				url: '<%= selectUserURL %>'
			}
		);

		itemSelectorDialog.open();
	}

	Liferay.componentReady('editTeamAssignemntsUsersWebManagementToolbar').then(
		(managementToolbar) => {
			managementToolbar.on('creationButtonClicked', handleAddClick);
		}
	);

	window.<portlet:namespace />deleteUsers = function() {
		if (confirm('<liferay-ui:message key="are-you-sure-you-want-to-delete-this" />')) {
			submitForm(document.<portlet:namespace />fm);
		}
	}
</aui:script>