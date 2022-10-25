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

<portlet:actionURL name="/account_admin/assign_account_users" var="assignAccountUsersURL" />

<portlet:renderURL var="selectAccountUsersURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
	<portlet:param name="mvcPath" value="/account_entries_admin/select_account_users.jsp" />
	<portlet:param name="redirect" value="<%= currentURL %>" />
	<portlet:param name="accountEntryId" value="<%= String.valueOf(accountEntryDisplay.getAccountEntryId()) %>" />
	<portlet:param name="openModalOnRedirect" value="<%= Boolean.TRUE.toString() %>" />
	<portlet:param name="showCreateButton" value="<%= Boolean.TRUE.toString() %>" />
</portlet:renderURL>

<clay:management-toolbar
	additionalProps='<%=
		HashMapBuilder.<String, Object>put(
			"accountEntryName", accountEntryDisplay.getName()
		).put(
			"assignAccountUsersURL", assignAccountUsersURL
		).put(
			"selectAccountUsersURL", selectAccountUsersURL
		).build()
	%>'
	managementToolbarDisplayContext="<%= viewAccountUsersManagementToolbarDisplayContext %>"
	propsTransformer="account_entries_admin/js/AccountUsersManagementToolbarPropsTransformer"
/>

<clay:container-fluid>
	<aui:form method="post" name="fm">
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="accountEntryId" type="hidden" value="<%= accountEntryDisplay.getAccountEntryId() %>" />
		<aui:input name="accountUserIds" type="hidden" />

		<liferay-ui:error exception="<%= UserEmailAddressException.MustHaveValidDomain.class %>" message="one-or-more-of-the-selected-users-have-invalid-email-address-domains" />

		<liferay-ui:search-container
			searchContainer="<%= accountUserDisplaySearchContainer %>"
		>
			<liferay-ui:search-container-row
				className="com.liferay.account.admin.web.internal.display.AccountUserDisplay"
				keyProperty="userId"
				modelVar="accountUser"
			>

				<%
				String rowURL = null;

				AccountUserActionDropdownItemsProvider accountUserActionDropdownItemsProvider = new AccountUserActionDropdownItemsProvider(accountEntryDisplay, accountUser, permissionChecker, renderRequest, renderResponse);

				if (AccountUserPermission.hasEditUserPermission(permissionChecker, portletName, accountEntryDisplay, accountUser.getUser())) {
					rowURL = accountUserActionDropdownItemsProvider.getEditAccountUserURL();
				}
				%>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-expand-small table-cell-minw-150"
					href="<%= rowURL %>"
					name="name"
					property="name"
				/>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-expand-small table-cell-minw-150"
					href="<%= rowURL %>"
					name="email-address"
					property="emailAddress"
				/>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-expand-small table-cell-minw-150"
					href="<%= rowURL %>"
					name="job-title"
					property="jobTitle"
				/>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-expand-small table-cell-minw-150"
					href="<%= rowURL %>"
					name="account-roles"
					value="<%= accountUser.getAccountRoleNamesString(accountEntryDisplay.getAccountEntryId(), locale) %>"
				/>

				<liferay-ui:search-container-column-text>
					<clay:dropdown-actions
						aria-label='<%= LanguageUtil.get(request, "show-actions") %>'
						dropdownItems="<%= accountUserActionDropdownItemsProvider.getActionDropdownItems() %>"
						propsTransformer="account_entries_admin/js/AccountUserDropdownDefaultPropsTransformer"
					/>
				</liferay-ui:search-container-column-text>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator
				markupView="lexicon"
			/>
		</liferay-ui:search-container>
	</aui:form>
</clay:container-fluid>