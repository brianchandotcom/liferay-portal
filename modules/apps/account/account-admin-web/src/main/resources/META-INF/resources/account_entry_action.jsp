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
String navigation = ParamUtil.getString(request, "navigation", "active");

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

AccountEntryDisplay accountEntryDisplay = (AccountEntryDisplay)row.getObject();

long accountEntryId = accountEntryDisplay.getAccountEntryId();
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>
	<c:if test="<%= AccountEntryPermission.contains(permissionChecker, accountEntryId, ActionKeys.UPDATE) %>">
		<portlet:renderURL var="editAccountURL">
			<portlet:param name="mvcRenderCommandName" value="/account_admin/edit_account_entry" />
			<portlet:param name="backURL" value="<%= currentURL %>" />
			<portlet:param name="accountEntryId" value="<%= String.valueOf(accountEntryId) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			message="edit"
			url="<%= editAccountURL %>"
		/>
	</c:if>

	<c:if test="<%= AccountEntryPermission.contains(permissionChecker, accountEntryId, ActionKeys.MANAGE_USERS) %>">
		<portlet:renderURL var="manageUsersURL">
			<portlet:param name="mvcRenderCommandName" value="/account_admin/edit_account_entry" />
			<portlet:param name="backURL" value="<%= currentURL %>" />
			<portlet:param name="accountEntryId" value="<%= String.valueOf(accountEntryId) %>" />
			<portlet:param name="screenNavigationCategoryKey" value="<%= AccountScreenNavigationEntryConstants.CATEGORY_KEY_USERS %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			message="manage-users"
			url="<%= manageUsersURL %>"
		/>
	</c:if>

	<c:if test="<%= AccountEntryPermission.contains(permissionChecker, accountEntryId, ActionKeys.DELETE) %>">
		<c:if test='<%= Objects.equals(accountEntryDisplay.getStatusLabel(), "active") %>'>
			<portlet:actionURL name="/account_admin/update_account_entry_status" var="deactivateAccountURL">
				<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DEACTIVATE %>" />
				<portlet:param name="navigation" value="<%= navigation %>" />
				<portlet:param name="accountEntryIds" value="<%= String.valueOf(accountEntryId) %>" />
			</portlet:actionURL>

			<liferay-ui:icon-deactivate
				url="<%= deactivateAccountURL %>"
			/>
		</c:if>

		<c:if test='<%= Objects.equals(accountEntryDisplay.getStatusLabel(), "inactive") %>'>
			<portlet:actionURL name="/account_admin/update_account_entry_status" var="activateAccountURL">
				<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.RESTORE %>" />
				<portlet:param name="navigation" value="<%= navigation %>" />
				<portlet:param name="accountEntryIds" value="<%= String.valueOf(accountEntryId) %>" />
			</portlet:actionURL>

			<liferay-ui:icon
				message="activate"
				url="<%= activateAccountURL %>"
			/>
		</c:if>

		<portlet:actionURL name="/account_admin/delete_account_entry" var="deleteAccountURL">
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="accountEntryIds" value="<%= String.valueOf(accountEntryId) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete
			url="<%= deleteAccountURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>