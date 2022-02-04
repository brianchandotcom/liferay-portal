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
AccountEntryDisplay accountEntryDisplay = (AccountEntryDisplay)request.getAttribute(AccountWebKeys.ACCOUNT_ENTRY_DISPLAY);

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

AccountRoleDisplay accountRoleDisplay = (AccountRoleDisplay)row.getObject();

Role role = accountRoleDisplay.getRole();
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>
	<portlet:renderURL var="editAccountRoleURL">
		<portlet:param name="mvcPath" value="/account_entries_admin/edit_account_role.jsp" />
		<portlet:param name="backURL" value="<%= currentURL %>" />
		<portlet:param name="accountEntryId" value="<%= String.valueOf(accountEntryDisplay.getAccountEntryId()) %>" />
		<portlet:param name="accountRoleId" value="<%= String.valueOf(accountRoleDisplay.getAccountRoleId()) %>" />
	</portlet:renderURL>

	<c:if test="<%= !AccountRoleConstants.isSharedRole(role) && AccountRolePermission.contains(permissionChecker, accountRoleDisplay.getAccountRoleId(), ActionKeys.UPDATE) %>">
		<liferay-ui:icon
			message="edit"
			url="<%= editAccountRoleURL %>"
		/>
	</c:if>

	<c:if test="<%= !AccountRoleConstants.isSharedRole(role) && AccountRolePermission.contains(permissionChecker, accountRoleDisplay.getAccountRoleId(), ActionKeys.DEFINE_PERMISSIONS) %>">
		<liferay-ui:icon
			message="define-permissions"
			url='<%= HttpUtil.setParameter(editAccountRoleURL, liferayPortletResponse.getNamespace() + "screenNavigationCategoryKey", AccountScreenNavigationEntryConstants.CATEGORY_KEY_DEFINE_PERMISSIONS) %>'
		/>
	</c:if>

	<c:if test="<%= AccountRolePermission.contains(permissionChecker, accountRoleDisplay.getAccountRoleId(), AccountActionKeys.ASSIGN_USERS) %>">
		<liferay-ui:icon
			message="assign-users"
			url='<%= HttpUtil.setParameter(editAccountRoleURL, liferayPortletResponse.getNamespace() + "screenNavigationCategoryKey", AccountScreenNavigationEntryConstants.CATEGORY_KEY_ASSIGNEES) %>'
		/>
	</c:if>

	<c:if test="<%= !AccountRoleConstants.isSharedRole(role) && AccountRolePermission.contains(permissionChecker, accountRoleDisplay.getAccountRoleId(), ActionKeys.DELETE) %>">
		<portlet:actionURL name="/account_admin/delete_account_roles" var="deleteAccountRolesURL">
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="accountRoleIds" value="<%= String.valueOf(accountRoleDisplay.getAccountRoleId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete
			confirmation="are-you-sure-you-want-to-delete-this-role"
			message="delete"
			url="<%= deleteAccountRolesURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>