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

SearchContainer<AccountUserDisplay> accountUserDisplaySearchContainer = AccountUserDisplaySearchContainerFactory.create(accountEntryDisplay.getAccountEntryId(), liferayPortletRequest, liferayPortletResponse);

ViewAccountUsersManagementToolbarDisplayContext viewAccountUsersManagementToolbarDisplayContext = new ViewAccountUsersManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, accountUserDisplaySearchContainer);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(ParamUtil.getString(request, "backURL", String.valueOf(renderResponse.createRenderURL())));

renderResponse.setTitle(accountEntryDisplay.getName());
%>

<clay:management-toolbar-v2
	displayContext="<%= viewAccountUsersManagementToolbarDisplayContext %>"
/>

<clay:container-fluid>
	<aui:form method="post" name="fm">
		<aui:input name="accountEntryId" type="hidden" value="<%= accountEntryDisplay.getAccountEntryId() %>" />
		<aui:input name="accountUserIds" type="hidden" />

		<liferay-ui:search-container
			searchContainer="<%= accountUserDisplaySearchContainer %>"
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

				<liferay-ui:search-container-column-text
					cssClass="table-cell-expand-small table-cell-minw-150"
					name="account-roles"
					value="<%= accountUser.getAccountRoleNamesString(accountEntryDisplay.getAccountEntryId(), locale) %>"
				/>

				<c:if test="<%= AccountEntryPermission.contains(permissionChecker, accountEntryDisplay.getAccountEntryId(), ActionKeys.MANAGE_USERS) %>">
					<liferay-ui:search-container-column-jsp
						path="/account_entries_admin/account_user_action.jsp"
					/>
				</c:if>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator
				markupView="lexicon"
			/>
		</liferay-ui:search-container>
	</aui:form>
</clay:container-fluid>

<portlet:actionURL name="/account_admin/assign_account_users" var="assignAccountUsersURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:actionURL>

<portlet:renderURL var="selectAccountUsersURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
	<portlet:param name="mvcPath" value="/account_entries_admin/select_account_users.jsp" />
	<portlet:param name="redirect" value="<%= currentURL %>" />
	<portlet:param name="accountEntryId" value="<%= String.valueOf(accountEntryDisplay.getAccountEntryId()) %>" />
	<portlet:param name="openModalOnRedirect" value="<%= Boolean.TRUE.toString() %>" />
	<portlet:param name="showCreateButton" value="<%= Boolean.TRUE.toString() %>" />
</portlet:renderURL>

<liferay-frontend:component
	componentId="<%= viewAccountUsersManagementToolbarDisplayContext.getDefaultEventHandler() %>"
	context='<%=
		HashMapBuilder.<String, Object>put(
			"accountEntryName", accountEntryDisplay.getName()
		).put(
			"assignAccountUsersURL", assignAccountUsersURL
		).put(
			"selectAccountUsersURL", selectAccountUsersURL
		).build()
	%>'
	module="account_entries_admin/js/AccountUsersManagementToolbarDefaultEventHandler.es"
/>