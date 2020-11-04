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
long accountEntryId = ParamUtil.getLong(request, "accountEntryId");

long accountRoleId = ParamUtil.getLong(request, "accountRoleId");

AccountRole accountRole = AccountRoleLocalServiceUtil.fetchAccountRole(accountRoleId);

Role role = null;

if (accountRole != null) {
	role = accountRole.getRole();
}

SearchContainer<AccountUserDisplay> accountRoleUserDisplaySearchContainer = AccountUserDisplaySearchContainerFactory.create(accountEntryId, role.getRoleId(), liferayPortletRequest, liferayPortletResponse);

ViewAccountRoleAssigneesManagementToolbarDisplayContext viewAccountRoleAssigneesManagementToolbarDisplayContext = new ViewAccountRoleAssigneesManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, accountRoleUserDisplaySearchContainer);
%>

<clay:management-toolbar-v2
	displayContext="<%= viewAccountRoleAssigneesManagementToolbarDisplayContext %>"
/>

<clay:container-fluid>
	<aui:form method="post" name="fm">
		<aui:input name="accountEntryId" type="hidden" value="<%= accountEntryId %>" />
		<aui:input name="accountRoleId" type="hidden" value="<%= accountRoleId %>" />
		<aui:input name="accountUserIds" type="hidden" />

		<liferay-ui:search-container
			searchContainer="<%= accountRoleUserDisplaySearchContainer %>"
		>
			<liferay-ui:search-container-row
				className="com.liferay.account.admin.web.internal.display.AccountUserDisplay"
				keyProperty="userId"
				modelVar="accountUser"
			>
				<liferay-ui:search-container-column-text
					cssClass="table-cell-expand-small table-cell-minw-150"
					name="name"
					property="name"
				/>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-expand-small table-cell-minw-150"
					name="email-address"
					property="emailAddress"
				/>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-expand-small table-cell-minw-150"
					name="job-title"
					property="jobTitle"
				/>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator
				markupView="lexicon"
			/>
		</liferay-ui:search-container>
	</aui:form>
</clay:container-fluid>

<portlet:actionURL name="/account_admin/assign_account_role_users" var="assignAccountUsersURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:actionURL>

<portlet:renderURL var="selectAccountUsersURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
	<portlet:param name="mvcPath" value="/account_entries_admin/select_account_users.jsp" />
	<portlet:param name="accountEntryId" value="<%= String.valueOf(accountEntryId) %>" />
	<portlet:param name="accountRoleId" value="<%= String.valueOf(accountRoleId) %>" />
</portlet:renderURL>

<liferay-frontend:component
	componentId="<%= viewAccountRoleAssigneesManagementToolbarDisplayContext.getDefaultEventHandler() %>"
	context='<%=
		HashMapBuilder.<String, Object>put(
			"accountEntryName", role.getTitle(locale)
		).put(
			"assignAccountUsersURL", assignAccountUsersURL
		).put(
			"selectAccountUsersURL", selectAccountUsersURL
		).build()
	%>'
	module="account_entries_admin/js/AccountUsersManagementToolbarDefaultEventHandler.es"
/>