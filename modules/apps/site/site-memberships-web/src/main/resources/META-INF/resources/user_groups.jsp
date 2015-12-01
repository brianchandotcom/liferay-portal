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
String orderByCol = ParamUtil.getString(request, "orderByCol", "name");
String orderByType = ParamUtil.getString(request, "orderByType", "asc");

PortletURL viewUserGroupsURL = renderResponse.createRenderURL();

viewUserGroupsURL.setParameter("mvcPath", "/view.jsp");
viewUserGroupsURL.setParameter("tabs1", "user-groups");
viewUserGroupsURL.setParameter("redirect", currentURL);
viewUserGroupsURL.setParameter("groupId", String.valueOf(siteMembershipsDisplayContext.getGroupId()));

UserGroupSearch userGroupSearch = new UserGroupSearch(renderRequest, PortletURLUtil.clone(viewUserGroupsURL, renderResponse));

userGroupSearch.setEmptyResultsMessage("no-user-group-was-found-that-is-a-member-of-this-site");

RowChecker rowChecker = new EmptyOnClickRowChecker(renderResponse);

UserGroupDisplayTerms searchTerms = (UserGroupDisplayTerms)userGroupSearch.getSearchTerms();

LinkedHashMap<String, Object> userGroupParams = new LinkedHashMap<String, Object>();

userGroupParams.put("userGroupsGroups", Long.valueOf(siteMembershipsDisplayContext.getGroupId()));

int userGroupsCount = UserGroupLocalServiceUtil.searchCount(company.getCompanyId(), searchTerms.getKeywords(), userGroupParams);

userGroupSearch.setTotal(userGroupsCount);

List<UserGroup> userGroups = UserGroupLocalServiceUtil.search(company.getCompanyId(), searchTerms.getKeywords(), userGroupParams, userGroupSearch.getStart(), userGroupSearch.getEnd(), userGroupSearch.getOrderByComparator());

userGroupSearch.setResults(userGroups);
%>

<c:if test="<%= userGroupsCount > 0 %>">
	<liferay-frontend:management-bar
		includeCheckBox="<%= true %>"
		searchContainerId="userGroups"
	>
		<liferay-frontend:management-bar-buttons>
			<liferay-frontend:management-bar-display-buttons
				displayViews='<%= new String[] {"icon", "descriptive", "list"} %>'
				portletURL="<%= PortletURLUtil.clone(viewUserGroupsURL, renderResponse) %>"
				selectedDisplayStyle="<%= displayStyle %>"
			/>
		</liferay-frontend:management-bar-buttons>

		<liferay-frontend:management-bar-filters>
			<liferay-frontend:management-bar-navigation
				navigationKeys='<%= new String[] {"all"} %>'
				portletURL="<%= PortletURLUtil.clone(viewUserGroupsURL, renderResponse) %>"
			/>

			<liferay-frontend:management-bar-sort
				orderByCol="<%= orderByCol %>"
				orderByType="<%= orderByType %>"
				orderColumns='<%= new String[] {"name", "description"} %>'
				portletURL="<%= PortletURLUtil.clone(viewUserGroupsURL, renderResponse) %>"
			/>
		</liferay-frontend:management-bar-filters>

		<liferay-frontend:management-bar-action-buttons>
			<liferay-frontend:management-bar-button href="javascript:;" iconCssClass="icon-trash" id="deleteSelectedUserGroups" />
		</liferay-frontend:management-bar-action-buttons>
	</liferay-frontend:management-bar>
</c:if>

<liferay-util:include page="/info_message.jsp" servletContext="<%= application %>" />

<portlet:actionURL name="deleteGroupUserGroups" var="deleteGroupUserGroupsURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:actionURL>

<aui:form action="<%= deleteGroupUserGroupsURL %>" cssClass="container-fluid-1280" name="fm">
	<aui:input name="tabs1" type="hidden" value="user-groups" />
	<aui:input name="groupId" type="hidden" value="<%= String.valueOf(siteMembershipsDisplayContext.getGroupId()) %>" />
	<aui:input name="userGroupId" type="hidden" />
	<aui:input name="addRoleIds" type="hidden" />
	<aui:input name="removeRoleIds" type="hidden" />

	<liferay-ui:search-container
		id="userGroups"
		rowChecker="<%= rowChecker %>"
		searchContainer="<%= userGroupSearch %>"
	>

		<liferay-ui:search-container-row
			className="com.liferay.portal.model.UserGroup"
			escapedModel="<%= true %>"
			keyProperty="userGroupId"
			modelVar="userGroup"
		>

			<%
			boolean selectUserGroup = false;
			%>

			<%@ include file="/user_group_columns.jspf" %>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator displayStyle="<%= displayStyle %>" markupView="lexicon" />
	</liferay-ui:search-container>
</aui:form>

<portlet:actionURL name="addGroupUserGroups" var="addGroupUserGroupsURL" />

<aui:form action="<%= addGroupUserGroupsURL %>" cssClass="hide" name="addGroupUserGroupsFm">
	<aui:input name="tabs1" type="hidden" value="user-groups" />
</aui:form>

<c:if test="<%= GroupPermissionUtil.contains(permissionChecker, siteMembershipsDisplayContext.getGroupId(), ActionKeys.ASSIGN_MEMBERS) %>">
	<liferay-frontend:add-menu>
		<liferay-frontend:add-menu-item id="selectUserGroups" title='<%= LanguageUtil.get(request, "assign-user-groups") %>' url="javascript:;" />
	</liferay-frontend:add-menu>
</c:if>

<aui:script use="liferay-item-selector-dialog">
	var Util = Liferay.Util;

	var form = $(document.<portlet:namespace />fm);

	$('#<portlet:namespace />deleteSelectedUserGroups').on(
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
					eventName: '<portlet:namespace />selectUserGroupsRoles',
					on: {
						selectedItemChange: function(event) {
							var selectedItem = event.newVal;

							if (selectedItem) {
								form.fm('addRoleIds').val(selectedItem.addRoleIds);
								form.fm('removeRoleIds').val(selectedItem.removeRoleIds);
								form.fm('userGroupId').val(selectedItem.userGroupId);

								submitForm(form, '<portlet:actionURL name="editUserGroupGroupRole" />');
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

	$('#<portlet:namespace />selectUserGroups').on(
		'click',
		function(event) {
			event.preventDefault();

			var itemSelectorDialog = new A.LiferayItemSelectorDialog(
				{
					eventName: '<portlet:namespace />selectUserGroups',
					on: {
						selectedItemChange: function(event) {
							var selectedItem = event.newVal;

							if (selectedItem) {
								var addGroupUserGroupsFm = $(document.<portlet:namespace />addGroupUserGroupsFm);

								addGroupUserGroupsFm.append(selectedItem);

								submitForm(addGroupUserGroupsFm);
							}
						}
					},
					title: '<liferay-ui:message key="add-user-groups-to-this-site" />',
					url: '<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="mvcPath" value="/select_user_groups.jsp" /></portlet:renderURL>'
				}
			);

			itemSelectorDialog.open();
		}
	);
</aui:script>