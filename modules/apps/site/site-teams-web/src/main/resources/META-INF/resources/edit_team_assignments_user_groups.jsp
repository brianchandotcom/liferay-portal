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
String tabs1 = (String)request.getAttribute("edit_team_assignments.jsp-tabs1");

Team team = (Team)request.getAttribute("edit_team_assignments.jsp-team");

String displayStyle = ParamUtil.getString(request, "displayStyle", "list");
String orderByCol = ParamUtil.getString(request, "orderByCol", "name");
String orderByType = ParamUtil.getString(request, "orderByType", "asc");

PortletURL portletURL = (PortletURL)request.getAttribute("edit_team_assignments.jsp-portletURL");

SearchContainer userGroupSearchContainer = new UserGroupSearch(renderRequest, portletURL);

UserGroupDisplayTerms searchTerms = (UserGroupDisplayTerms)userGroupSearchContainer.getSearchTerms();

LinkedHashMap<String, Object> userGroupParams = new LinkedHashMap<String, Object>();

userGroupParams.put("userGroupsTeams", Long.valueOf(team.getTeamId()));

int userGroupsCount = UserGroupLocalServiceUtil.searchCount(company.getCompanyId(), searchTerms.getKeywords(), userGroupParams);

userGroupSearchContainer.setTotal(userGroupsCount);

List<UserGroup> userGroups = UserGroupLocalServiceUtil.search(company.getCompanyId(), searchTerms.getKeywords(), userGroupParams, userGroupSearchContainer.getStart(), userGroupSearchContainer.getEnd(), userGroupSearchContainer.getOrderByComparator());

userGroupSearchContainer.setResults(userGroups);

RowChecker rowChecker = new EmptyOnClickRowChecker(renderResponse);
%>

<c:if test="<%= userGroupsCount > 0 %>">
	<liferay-frontend:management-bar
		checkBoxContainerId="userGroupsSearchContainer"
		includeCheckBox="<%= true %>"
	>
		<liferay-frontend:management-bar-filters>
			<liferay-frontend:management-bar-navigation
				navigationKeys='<%= new String[] {"all"} %>'
				portletURL="<%= portletURL %>"
			/>

			<liferay-frontend:management-bar-sort
				orderByCol="<%= orderByCol %>"
				orderByType="<%= orderByType %>"
				orderColumns='<%= new String[] {"name", "description"} %>'
				portletURL="<%= PortletURLUtil.clone(portletURL, renderResponse) %>"
			/>
		</liferay-frontend:management-bar-filters>

		<liferay-frontend:management-bar-buttons>
			<liferay-frontend:management-bar-display-buttons
				displayViews='<%= new String[] {"icon", "descriptive", "list"} %>'
				portletURL="<%= PortletURLUtil.clone(portletURL, renderResponse) %>"
				selectedDisplayStyle="<%= displayStyle %>"
			/>
		</liferay-frontend:management-bar-buttons>

		<liferay-frontend:management-bar-action-buttons>
			<liferay-frontend:management-bar-button href="javascript:;" iconCssClass="icon-trash" id="deleteUserGroups" />
		</liferay-frontend:management-bar-action-buttons>
	</liferay-frontend:management-bar>
</c:if>

<aui:button-row cssClass="text-center">
	<aui:button cssClass="btn-lg btn-primary" id="addUserGroups" value="add-team-members" />
</aui:button-row>

<portlet:actionURL name="editTeamUserGroups" var="editTeamUserGroupsURL" />

<aui:form action="<%= editTeamUserGroupsURL %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="tabs1" type="hidden" value="<%= tabs1 %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="assignmentsRedirect" type="hidden" />
	<aui:input name="teamId" type="hidden" value="<%= String.valueOf(team.getTeamId()) %>" />
	<aui:input name="addUserGroupIds" type="hidden" />
	<aui:input name="removeUserGroupIds" type="hidden" />

	<liferay-ui:search-container
		emptyResultsMessage="there-are-no-members.-you-can-add-a-member-by-clicking-the-button-on-the-top-of-this-box"
		id="userGroups"
		rowChecker="<%= rowChecker %>"
		searchContainer="<%= userGroupSearchContainer %>"
	>

		<liferay-ui:search-container-row
			className="com.liferay.portal.model.UserGroup"
			cssClass="selectable"
			escapedModel="<%= true %>"
			keyProperty="userGroupId"
			modelVar="userGroup"
		>

			<%
			boolean showActions = true;
			%>

			<%@ include file="/user_group_columns.jspf" %>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator displayStyle="<%= displayStyle %>" markupView="lexicon" />
	</liferay-ui:search-container>
</aui:form>

<aui:script use="liferay-item-selector-dialog">
	var Util = Liferay.Util;

	var form = $(document.<portlet:namespace />fm);

	<portlet:renderURL var="selectUserGroupURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
		<portlet:param name="mvcPath" value="/select_user_groups.jsp" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="teamId" value="<%= String.valueOf(team.getTeamId()) %>" />
	</portlet:renderURL>

	$('#<portlet:namespace />addUserGroups').on(
		'click',
		function(event) {
			event.preventDefault();

			var itemSelectorDialog = new A.LiferayItemSelectorDialog(
				{
					eventName: '<portlet:namespace />selectUserGroup',
					on: {
						selectedItemChange: function(event) {
							var selectedItem = event.newVal;

							if (selectedItem) {
								var form = AUI.$(document.<portlet:namespace />fm);

								form.fm('addUserGroupIds').val(selectedItem.value);

								submitForm(form);
							}
						}
					},
					title: '<liferay-ui:message arguments="<%= team.getName() %>" key="add-new-user-group-to-x" />',
					url: '<%= selectUserGroupURL %>'
				}
			);

			itemSelectorDialog.open();
		}
	);

	$('#<portlet:namespace />deleteUserGroups').on(
		'click',
		function() {
			if (confirm('<liferay-ui:message key="are-you-sure-you-want-to-delete-this" />')) {
				form.fm('removeUserGroupIds').val(Util.listCheckedExcept(form, '<portlet:namespace />allRowIds'));

				submitForm(form);
			}
		}
	);
</aui:script>