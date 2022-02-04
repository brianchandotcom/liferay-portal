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

SearchContainer<AccountRoleDisplay> accountRoleDisplaySearchContainer = AccountRoleDisplaySearchContainerFactory.create(accountEntryDisplay.getAccountEntryId(), liferayPortletRequest, liferayPortletResponse);

ViewAccountRolesManagementToolbarDisplayContext viewAccountRolesManagementToolbarDisplayContext = new ViewAccountRolesManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, accountRoleDisplaySearchContainer);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(ParamUtil.getString(request, "backURL", String.valueOf(renderResponse.createRenderURL())));

renderResponse.setTitle(accountEntryDisplay.getName());
%>

<clay:management-toolbar
	managementToolbarDisplayContext="<%= viewAccountRolesManagementToolbarDisplayContext %>"
	propsTransformer="account_entries_admin/js/AccountRolesManagementToolbarPropsTransformer"
/>

<clay:container-fluid>
	<aui:form method="post" name="fm">
		<aui:input name="accountRoleIds" type="hidden" />

		<liferay-ui:search-container
			searchContainer="<%= accountRoleDisplaySearchContainer %>"
		>
			<liferay-ui:search-container-row
				className="com.liferay.account.admin.web.internal.display.AccountRoleDisplay"
				keyProperty="accountRoleId"
				modelVar="accountRoleDisplay"
			>

				<%
				row.setData(
					HashMapBuilder.<String, Object>put(
						"actions", StringUtil.merge(viewAccountRolesManagementToolbarDisplayContext.getAvailableActions(accountRoleDisplay))
					).build());
				%>

				<portlet:renderURL var="rowURL">
					<portlet:param name="mvcPath" value="/account_entries_admin/edit_account_role.jsp" />
					<portlet:param name="backURL" value="<%= currentURL %>" />
					<portlet:param name="accountEntryId" value="<%= String.valueOf(accountEntryDisplay.getAccountEntryId()) %>" />
					<portlet:param name="accountRoleId" value="<%= String.valueOf(accountRoleDisplay.getAccountRoleId()) %>" />
				</portlet:renderURL>

				<%
				if (!accountRoleDisplay.isShowRowURL(permissionChecker)) {
					rowURL = null;
				}
				%>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-expand-small table-cell-minw-150"
					href="<%= rowURL %>"
					name="name"
					value="<%= accountRoleDisplay.getName(locale) %>"
				/>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-expand-small table-cell-minw-150"
					href="<%= rowURL %>"
					name="description"
					value="<%= accountRoleDisplay.getDescription(locale) %>"
				/>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-expand-small table-cell-minw-150"
					href="<%= rowURL %>"
					name="type"
					value="<%= accountRoleDisplay.getTypeLabel(locale) %>"
				/>

				<liferay-ui:search-container-column-jsp
					path="/account_entries_admin/account_role_action.jsp"
				/>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator
				markupView="lexicon"
			/>
		</liferay-ui:search-container>
	</aui:form>
</clay:container-fluid>