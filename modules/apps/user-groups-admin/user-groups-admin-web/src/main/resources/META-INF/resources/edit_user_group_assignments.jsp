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
String tabs1 = ParamUtil.getString(request, "tabs1");

String redirect = ParamUtil.getString(request, "redirect");

long userGroupId = ParamUtil.getLong(request, "userGroupId");

UserGroup userGroup = UserGroupServiceUtil.fetchUserGroup(userGroupId);

String displayStyle = ParamUtil.getString(request, "displayStyle");

if (Validator.isNull(displayStyle)) {
	displayStyle = portalPreferences.getValue(UserGroupsAdminPortletKeys.USER_GROUPS_ADMIN, "users-display-style", "list");
}
else {
	portalPreferences.setValue(UserGroupsAdminPortletKeys.USER_GROUPS_ADMIN, "users-display-style", displayStyle);

	request.setAttribute(WebKeys.SINGLE_PAGE_APPLICATION_CLEAR_CACHE, Boolean.TRUE);
}

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcPath", "/edit_user_group_assignments.jsp");
portletURL.setParameter("tabs1", tabs1);
portletURL.setParameter("redirect", redirect);
portletURL.setParameter("userGroupId", String.valueOf(userGroup.getUserGroupId()));

PortletURL searchURL = PortletURLUtil.clone(portletURL, renderResponse);

SearchContainer userSearchContainer = new UserSearch(renderRequest, searchURL);

UserSearchTerms searchTerms = (UserSearchTerms)userSearchContainer.getSearchTerms();

LinkedHashMap<String, Object> userParams = new LinkedHashMap<String, Object>();

if (filterManageableOrganizations) {
	userParams.put("usersOrgsTree", user.getOrganizations());
}

userParams.put("usersUserGroups", Long.valueOf(userGroup.getUserGroupId()));

RowChecker rowChecker = new UnsetUserUserGroupChecker(renderResponse, userGroup);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle(userGroup.getName());
%>

<aui:nav-bar cssClass="collapse-basic-search" markupView="lexicon">
	<aui:nav cssClass="navbar-nav">
		<aui:nav-item cssClass="active" label="users" />
	</aui:nav>

	<aui:nav-bar-search>
		<aui:form action="<%= portletURL.toString() %>" name="searchFm">
			<liferay-ui:input-search markupView="lexicon" />
		</aui:form>
	</aui:nav-bar-search>
</aui:nav-bar>

<c:if test="<%= UserLocalServiceUtil.searchCount(company.getCompanyId(), searchTerms.getKeywords(), searchTerms.getStatus(), userParams) > 0 %>">
	<liferay-frontend:management-bar
		includeCheckBox="<%= true %>"
		searchContainerId="users"
	>
		<liferay-frontend:management-bar-buttons>
			<liferay-frontend:management-bar-display-buttons
				displayViews='<%= new String[] {"icon", "descriptive", "list"} %>'
				portletURL="<%= portletURL %>"
				selectedDisplayStyle="<%= displayStyle %>"
			/>
		</liferay-frontend:management-bar-buttons>

		<liferay-frontend:management-bar-filters>
			<liferay-frontend:management-bar-sort
				orderByCol="<%= userSearchContainer.getOrderByCol() %>"
				orderByType="<%= userSearchContainer.getOrderByType() %>"
				orderColumns='<%= new String[] {"first-name", "screen-name"} %>'
				portletURL="<%= portletURL %>"
			/>
		</liferay-frontend:management-bar-filters>

		<liferay-frontend:management-bar-action-buttons>
			<aui:a cssClass="btn" href="javascript:;" iconCssClass="icon-trash" id="removeUsers" />
		</liferay-frontend:management-bar-action-buttons>
	</liferay-frontend:management-bar>
</c:if>

<aui:button-row cssClass="text-center">
	<aui:button cssClass="btn-lg" id="addUsers" primary="<%= true %>" value="add-users" />
</aui:button-row>

<aui:form action="<%= portletURL.toString() %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="tabs1" type="hidden" value="<%= tabs1 %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="assignmentsRedirect" type="hidden" />
	<aui:input name="userGroupId" type="hidden" value="<%= userGroup.getUserGroupId() %>" />
	<aui:input name="addUserIds" type="hidden" />
	<aui:input name="removeUserIds" type="hidden" />

	<liferay-ui:search-container
		id="users"
		rowChecker="<%= rowChecker %>"
		searchContainer="<%= userSearchContainer %>"
	>
		<liferay-ui:user-search-container-results userParams="<%= userParams %>" />

		<liferay-ui:search-container-row
			className="com.liferay.portal.model.User"
			cssClass="selectable"
			escapedModel="<%= true %>"
			keyProperty="userId"
			modelVar="user2"
			rowIdProperty="screenName"
		>
			<%@ include file="/user_search_columns.jspf" %>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator displayStyle="<%= displayStyle %>" markupView="lexicon" />
	</liferay-ui:search-container>
</aui:form>

<aui:script use="liferay-item-selector-dialog">
	var form = AUI.$(document.<portlet:namespace />fm);

	<portlet:renderURL var="selectUsersURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
		<portlet:param name="mvcPath" value="/select_user_group_users.jsp" />
		<portlet:param name="userGroupId" value="<%= String.valueOf(userGroupId) %>" />
	</portlet:renderURL>

	$('#<portlet:namespace />addUsers').on(
		'click',
		function(event) {
			var itemSelectorDialog = new A.LiferayItemSelectorDialog(
				{
					eventName: '<portlet:namespace />selectUsers',
					on: {
						selectedItemChange: function(event) {
							var selectedItem = event.newVal;

							if (selectedItem) {
								form.fm('addUserIds').val(selectedItem.value);

								submitForm(form, '<portlet:actionURL name="editUserGroupAssignments" />');
							}
						}
					},
					title: '<liferay-ui:message arguments="<%= userGroup.getName() %>" key="add-users-to-x" />',
					url: '<%= selectUsersURL %>'
				}
			);

			itemSelectorDialog.open();
		}
	);

	$('#<portlet:namespace />removeUsers').on(
		'click',
		function() {
			form.fm('redirect').val('<%= portletURL.toString() %>');
			form.fm('removeUserIds').val(Liferay.Util.listCheckedExcept(form, '<portlet:namespace />allRowIds'));

			submitForm(form, '<portlet:actionURL name="editUserGroupAssignments" />');
		}
	);
</aui:script>

<%
PortalUtil.addPortletBreadcrumbEntry(request, userGroup.getName(), null);
PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "assign-members"), currentURL);
%>