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
SearchContainer accountDisplaySearchContainer = AccountDisplaySearchContainerFactory.create(liferayPortletRequest, liferayPortletResponse);

ViewAccountsManagementToolbarDisplayContext viewAccountsManagementToolbarDisplayContext = new ViewAccountsManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, accountDisplaySearchContainer);
%>

<clay:management-toolbar
	displayContext="<%= viewAccountsManagementToolbarDisplayContext %>"
/>

<div class="container-fluid container-fluid-max-xl">
	<aui:form method="post" name="fm">
		<aui:input name="accountEntryIds" type="hidden" />

		<liferay-ui:search-container
			searchContainer="<%= accountDisplaySearchContainer %>"
		>
			<liferay-ui:search-container-row
				className="com.liferay.account.admin.web.internal.display.AccountDisplay"
				keyProperty="accountId"
				modelVar="accountDisplay"
			>

				<%
				Map<String, Object> rowData = new HashMap<>();

				rowData.put("actions", StringUtil.merge(viewAccountsManagementToolbarDisplayContext.getAvailableActions(accountDisplay)));

				row.setData(rowData);
				%>

				<portlet:renderURL var="rowURL">
					<portlet:param name="mvcRenderCommandName" value="/account_admin/edit_account_entry" />
					<portlet:param name="backURL" value="<%= currentURL %>" />
					<portlet:param name="accountEntryId" value="<%= String.valueOf(accountDisplay.getAccountId()) %>" />
				</portlet:renderURL>

				<%
				if (!AccountEntryPermission.contains(permissionChecker, accountDisplay.getAccountId(), ActionKeys.UPDATE)) {
					rowURL = null;
				}
				%>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-expand table-title"
					href="<%= rowURL %>"
					name="name"
					property="name"
				/>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-expand"
					href="<%= rowURL %>"
					name="parent-account"
					property="parentAccountName"
				/>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-expand"
					href="<%= rowURL %>"
					name="account-owner"
					value=""
				/>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-expand"
					name="status"
				>
					<clay:label
						label="<%= StringUtil.toUpperCase(LanguageUtil.get(request, accountDisplay.getStatusLabel()), locale) %>"
						style="<%= accountDisplay.getStatusLabelStyle() %>"
					/>
				</liferay-ui:search-container-column-text>

				<liferay-ui:search-container-column-jsp
					path="/account_entry_action.jsp"
				/>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator
				markupView="lexicon"
			/>
		</liferay-ui:search-container>
	</aui:form>
</div>

<liferay-frontend:component
	componentId="<%= viewAccountsManagementToolbarDisplayContext.getDefaultEventHandler() %>"
	module="js/AccountsManagementToolbarDefaultEventHandler.es"
/>