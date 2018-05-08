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
EditSiteTeamAssignmentsUserGroupsDisplayContext editSiteTeamAssignmentsUserGroupsDisplayContext = new EditSiteTeamAssignmentsUserGroupsDisplayContext(renderRequest, renderResponse, request);
%>

<clay:navigation-bar
	inverted="<%= true %>"
	navigationItems="<%= editSiteTeamAssignmentsUserGroupsDisplayContext.getNavigationItems() %>"
/>

<clay:management-toolbar
	actionDropdownItems="<%= editSiteTeamAssignmentsUserGroupsDisplayContext.getActionDropdownItems() %>"
	clearResultsURL="<%= editSiteTeamAssignmentsUserGroupsDisplayContext.getClearResultsURL() %>"
	componentId="editTeamAssignemntsUserGroupsWebManagementToolbar"
	disabled="<%= editSiteTeamAssignmentsUserGroupsDisplayContext.isDisabledManagementBar() %>"
	filterDropdownItems="<%= editSiteTeamAssignmentsUserGroupsDisplayContext.getFilterDropdownItems() %>"
	itemsTotal="<%= editSiteTeamAssignmentsUserGroupsDisplayContext.getTotalItems() %>"
	searchActionURL="<%= editSiteTeamAssignmentsUserGroupsDisplayContext.getSearchActionURL() %>"
	searchContainerId="userGroups"
	searchFormName="searchFm"
	showCreationMenu="<%= true %>"
	showSearch="<%= editSiteTeamAssignmentsUserGroupsDisplayContext.isShowSearch() %>"
	sortingOrder="<%= editSiteTeamAssignmentsUserGroupsDisplayContext.getOrderByType() %>"
	sortingURL="<%= editSiteTeamAssignmentsUserGroupsDisplayContext.getSortingURL() %>"
	viewTypeItems="<%= editSiteTeamAssignmentsUserGroupsDisplayContext.getViewTypeItems() %>"
/>

<portlet:actionURL name="deleteTeamUserGroups" var="deleteTeamUserGroupsURL" />

<aui:form action="<%= deleteTeamUserGroupsURL %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="tabs1" type="hidden" value="<%= editSiteTeamAssignmentsUserGroupsDisplayContext.getTabs1() %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="teamId" type="hidden" value="<%= String.valueOf(editSiteTeamAssignmentsUserGroupsDisplayContext.getTeamId()) %>" />

	<liferay-ui:search-container
		id="userGroups"
		searchContainer="<%= editSiteTeamAssignmentsUserGroupsDisplayContext.getUserGroupSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.portal.kernel.model.UserGroup"
			cssClass="selectable"
			escapedModel="<%= true %>"
			keyProperty="userGroupId"
			modelVar="userGroup"
		>

			<%
			LinkedHashMap<String, Object> userParams = new LinkedHashMap<>();

			userParams.put("usersUserGroups", Long.valueOf(userGroup.getUserGroupId()));

			int usersCount = UserLocalServiceUtil.searchCount(company.getCompanyId(), StringPool.BLANK, WorkflowConstants.STATUS_ANY, userParams);
			%>

			<c:choose>
				<c:when test='<%= Objects.equals(editSiteTeamAssignmentsUserGroupsDisplayContext.getDisplayStyle(), "icon") %>'>

					<%
					row.setCssClass("entry-card lfr-asset-item selectable");
					%>

					<liferay-ui:search-container-column-text>
						<liferay-frontend:icon-vertical-card
							actionJsp="/edit_team_assignments_user_groups_action.jsp"
							actionJspServletContext="<%= application %>"
							cssClass="entry-display-style"
							icon="users"
							resultRow="<%= row %>"
							rowChecker="<%= searchContainer.getRowChecker() %>"
							subtitle="<%= userGroup.getDescription() %>"
							title="<%= userGroup.getName() %>"
						>
							<liferay-frontend:vertical-card-footer>
								<liferay-ui:message arguments="<%= usersCount %>" key="x-users" />
							</liferay-frontend:vertical-card-footer>
						</liferay-frontend:icon-vertical-card>
					</liferay-ui:search-container-column-text>
				</c:when>
				<c:when test='<%= Objects.equals(editSiteTeamAssignmentsUserGroupsDisplayContext.getDisplayStyle(), "descriptive") %>'>
					<liferay-ui:search-container-column-icon
						icon="users"
						toggleRowChecker="<%= true %>"
					/>

					<liferay-ui:search-container-column-text
						colspan="<%= 2 %>"
					>
						<h5><%= userGroup.getName() %></h5>

						<h6 class="text-default">
							<span><%= userGroup.getDescription() %></span>
						</h6>

						<h6 class="text-default">
							<span><liferay-ui:message arguments="<%= usersCount %>" key="x-users" /></span>
						</h6>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-jsp
						path="/edit_team_assignments_user_groups_action.jsp"
					/>
				</c:when>
				<c:otherwise>
					<liferay-ui:search-container-column-text
						cssClass="table-cell-content"
						name="name"
						orderable="<%= true %>"
						property="name"
					/>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-content"
						name="description"
						orderable="<%= true %>"
						property="description"
					/>

					<liferay-ui:search-container-column-text
						name="users"
						value="<%= String.valueOf(usersCount) %>"
					/>

					<liferay-ui:search-container-column-jsp
						path="/edit_team_assignments_user_groups_action.jsp"
					/>
				</c:otherwise>
			</c:choose>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="<%= editSiteTeamAssignmentsUserGroupsDisplayContext.getDisplayStyle() %>"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</aui:form>

<portlet:actionURL name="addTeamUserGroups" var="addTeamUserGroupsURL" />

<aui:form action="<%= addTeamUserGroupsURL %>" cssClass="hide" name="addTeamUserGroupsFm">
	<aui:input name="tabs1" type="hidden" value="<%= editSiteTeamAssignmentsUserGroupsDisplayContext.getTabs1() %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="teamId" type="hidden" value="<%= String.valueOf(editSiteTeamAssignmentsUserGroupsDisplayContext.getTeamId()) %>" />
</aui:form>

<aui:script use="liferay-item-selector-dialog">
	<portlet:renderURL var="selectUserGroupURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
		<portlet:param name="mvcPath" value="/select_user_groups.jsp" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="teamId" value="<%= String.valueOf(editSiteTeamAssignmentsUserGroupsDisplayContext.getTeamId()) %>" />
	</portlet:renderURL>

	function handleAddClick(event) {
		var itemSelectorDialog = new A.LiferayItemSelectorDialog(
			{
				eventName: '<portlet:namespace />selectUserGroup',
				on: {
					selectedItemChange: function(event) {
						var selectedItem = event.newVal;

						if (selectedItem) {
							var addTeamUserGroupsFm = $(document.<portlet:namespace />addTeamUserGroupsFm);

							addTeamUserGroupsFm.append(selectedItem);

							submitForm(addTeamUserGroupsFm);
						}
					}
				},
				title: '<liferay-ui:message arguments="<%= editSiteTeamAssignmentsUserGroupsDisplayContext.getTeamName() %>" key="add-new-user-group-to-x" />',
				url: '<%= selectUserGroupURL %>'
			}
		);

		itemSelectorDialog.open();
	}

	Liferay.componentReady('editTeamAssignemntsUserGroupsWebManagementToolbar').then(
		(managementToolbar) => {
			managementToolbar.on('creationButtonClicked', handleAddClick);
		}
	);

	window.<portlet:namespace />deleteUserGroups = function() {
		if (confirm('<liferay-ui:message key="are-you-sure-you-want-to-delete-this" />')) {
			submitForm($(document.<portlet:namespace />fm));
		}
	}
</aui:script>