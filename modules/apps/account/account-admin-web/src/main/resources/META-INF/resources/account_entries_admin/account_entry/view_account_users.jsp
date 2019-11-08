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

SearchContainer accountUserDisplaySearchContainer = AccountUserDisplaySearchContainerFactory.create(accountEntryDisplay.getAccountEntryId(), liferayPortletRequest, liferayPortletResponse);

ViewAccountUsersManagementToolbarDisplayContext viewAccountUsersManagementToolbarDisplayContext = new ViewAccountUsersManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, accountUserDisplaySearchContainer);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(ParamUtil.getString(request, "backURL", String.valueOf(renderResponse.createRenderURL())));

renderResponse.setTitle((accountEntryDisplay == null) ? "" : accountEntryDisplay.getName());
%>

<clay:management-toolbar
	displayContext="<%= viewAccountUsersManagementToolbarDisplayContext %>"
/>

<aui:container cssClass="container-fluid container-fluid-max-xl">
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
					property="accountRoles"
				/>

				<liferay-ui:search-container-column-jsp
					path="/account_entries_admin/account_user_action.jsp"
				/>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator
				markupView="lexicon"
			/>
		</liferay-ui:search-container>
	</aui:form>
</aui:container>

<liferay-frontend:component
	componentId="<%= viewAccountUsersManagementToolbarDisplayContext.getDefaultEventHandler() %>"
	module="account_entries_admin/js/AccountUsersManagementToolbarDefaultEventHandler.es"
/>