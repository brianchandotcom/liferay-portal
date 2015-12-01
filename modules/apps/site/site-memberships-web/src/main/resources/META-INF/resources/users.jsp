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
String displayStyle = ParamUtil.getString(request, "displayStyle", "icon");
String orderByCol = ParamUtil.getString(request, "orderByCol", "first-name");
String orderByType = ParamUtil.getString(request, "orderByType", "asc");

PortletURL viewUsersURL = renderResponse.createRenderURL();

viewUsersURL.setParameter("mvcPath", "/view.jsp");
viewUsersURL.setParameter("tabs1", "users");
viewUsersURL.setParameter("redirect", currentURL);
viewUsersURL.setParameter("groupId", String.valueOf(siteMembershipsDisplayContext.getGroupId()));

UserSearch userSearch = new UserSearch(renderRequest, PortletURLUtil.clone(viewUsersURL, renderResponse));

userSearch.setEmptyResultsMessage("no-user-was-found-that-is-a-direct-member-of-this-site");

RowChecker rowChecker = new EmptyOnClickRowChecker(renderResponse);

UserSearchTerms searchTerms = (UserSearchTerms)userSearch.getSearchTerms();

LinkedHashMap<String, Object> userParams = new LinkedHashMap<String, Object>();

userParams.put("inherit", Boolean.TRUE);
userParams.put("usersGroups", Long.valueOf(siteMembershipsDisplayContext.getGroupId()));

int usersCount = UserLocalServiceUtil.searchCount(company.getCompanyId(), searchTerms.getKeywords(), searchTerms.getStatus(), userParams);

userSearch.setTotal(usersCount);

List<User> users = UserLocalServiceUtil.search(company.getCompanyId(), searchTerms.getKeywords(), searchTerms.getStatus(), userParams, userSearch.getStart(), userSearch.getEnd(), userSearch.getOrderByComparator());

userSearch.setResults(users);
%>

<c:if test="<%= usersCount > 0 %>">
	<liferay-frontend:management-bar
		includeCheckBox="<%= true %>"
		searchContainerId="users"
	>
		<liferay-frontend:management-bar-buttons>
			<liferay-frontend:management-bar-display-buttons
				displayViews='<%= new String[] {"icon", "descriptive", "list"} %>'
				portletURL="<%= PortletURLUtil.clone(viewUsersURL, renderResponse) %>"
				selectedDisplayStyle="<%= displayStyle %>"
			/>
		</liferay-frontend:management-bar-buttons>

		<liferay-frontend:management-bar-filters>
			<liferay-frontend:management-bar-navigation
				navigationKeys='<%= new String[] {"all"} %>'
				portletURL="<%= PortletURLUtil.clone(viewUsersURL, renderResponse) %>"
			/>

			<liferay-frontend:management-bar-sort
				orderByCol="<%= orderByCol %>"
				orderByType="<%= orderByType %>"
				orderColumns='<%= new String[] {"first-name", "screen-name"} %>'
				portletURL="<%= PortletURLUtil.clone(viewUsersURL, renderResponse) %>"
			/>
		</liferay-frontend:management-bar-filters>

		<liferay-frontend:management-bar-action-buttons>
			<liferay-frontend:management-bar-button href="javascript:;" iconCssClass="icon-trash" id="deleteSelectedUsers" />
		</liferay-frontend:management-bar-action-buttons>
	</liferay-frontend:management-bar>
</c:if>

<liferay-util:include page="/info_message.jsp" servletContext="<%= application %>" />

<portlet:actionURL name="deleteGroupUsers" var="deleteGroupUsersURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:actionURL>

<aui:form action="<%= deleteGroupUsersURL %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="tabs1" type="hidden" value="users" />
	<aui:input name="groupId" type="hidden" value="<%= String.valueOf(siteMembershipsDisplayContext.getGroupId()) %>" />
	<aui:input name="p_u_i_d" type="hidden" />
	<aui:input name="addUserIds" type="hidden" />
	<aui:input name="addRoleIds" type="hidden" />
	<aui:input name="removeRoleIds" type="hidden" />

	<liferay-ui:membership-policy-error />

	<liferay-ui:search-container
		id="users"
		rowChecker="<%= rowChecker %>"
		searchContainer="<%= userSearch %>"
	>

		<liferay-ui:search-container-row
			className="com.liferay.portal.model.User"
			escapedModel="<%= true %>"
			keyProperty="userId"
			modelVar="user2"
			rowIdProperty="screenName"
		>

			<%
			boolean selectUsers = false;
			%>

			<%@ include file="/user_columns.jspf" %>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator displayStyle="<%= displayStyle %>" markupView="lexicon" />
	</liferay-ui:search-container>
</aui:form>

<portlet:actionURL name="addGroupUsers" var="addGroupUsersURL" />

<aui:form action="<%= addGroupUsersURL %>" cssClass="hide" method="post" name="addGroupUsersFm">
	<aui:input name="tabs1" type="hidden" value="users" />
</aui:form>

<c:if test="<%= GroupPermissionUtil.contains(permissionChecker, siteMembershipsDisplayContext.getGroupId(), ActionKeys.ASSIGN_MEMBERS) %>">
	<liferay-frontend:add-menu>
		<liferay-frontend:add-menu-item id="selectUsers" title='<%= LanguageUtil.get(request, "assign-users") %>' url="javascript:;" />
	</liferay-frontend:add-menu>
</c:if>

<aui:script use="liferay-item-selector-dialog">
	var Util = Liferay.Util;

	var form = $(document.<portlet:namespace />fm);

	$('#<portlet:namespace />deleteSelectedUsers').on(
		'click',
		function() {
			if (confirm('<liferay-ui:message key="are-you-sure-you-want-to-delete-this" />')) {
				submitForm(form);
			}
		}
	);

	form.on(
		'click',
		'.assign-site-roles a',
		function(event) {
			event.preventDefault();

			var currentTarget = $(event.currentTarget);

			var itemSelectorDialog = new A.LiferayItemSelectorDialog(
				{
					eventName: '<portlet:namespace />selectUsersRoles',
					on: {
						selectedItemChange: function(event) {
							var selectedItem = event.newVal;

							if (selectedItem) {
								form.fm('addRoleIds').val(selectedItem.addRoleIds);
								form.fm('removeRoleIds').val(selectedItem.removeRoleIds);
								form.fm('p_u_i_d').val(selectedItem.selUserId);

								submitForm(form, '<portlet:actionURL name="editUserGroupRole" />');
							}
						}
					},
					title: '<liferay-ui:message key="assign-site-roles" />',
					url: currentTarget.data('href')
				}
			);

			itemSelectorDialog.open();
		}
	);

	$('#<portlet:namespace />selectUsers').on(
		'click',
		function(event) {
			event.preventDefault();

			var itemSelectorDialog = new A.LiferayItemSelectorDialog(
				{
					eventName: '<portlet:namespace />selectUsers',
					on: {
						selectedItemChange: function(event) {
							var selectedItem = event.newVal;

							if (selectedItem) {
								var addGroupUsersFm = $(document.<portlet:namespace />addGroupUsersFm);

								addGroupUsersFm.append(selectedItem);

								submitForm(addGroupUsersFm);
							}
						}
					},
					title: '<liferay-ui:message key="add-users-to-this-site" />',
					url: '<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="mvcPath" value="/select_users.jsp" /></portlet:renderURL>'
				}
			);

			itemSelectorDialog.open();
		}
	);
</aui:script>