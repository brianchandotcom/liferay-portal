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
SearchContainer<AccountGroupDisplay> accountGroupDisplaySearchContainer = AccountGroupDisplaySearchContainerFactory.create(liferayPortletRequest, liferayPortletResponse);

ViewAccountGroupsManagementToolbarDisplayContext viewAccountGroupsManagementToolbarDisplayContext = new ViewAccountGroupsManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, accountGroupDisplaySearchContainer);
%>

<clay:management-toolbar
	managementToolbarDisplayContext="<%= viewAccountGroupsManagementToolbarDisplayContext %>"
	propsTransformer="account_groups_admin/js/AccountGroupsManagementToolbarPropsTransformer"
/>

<clay:container-fluid>
	<aui:form method="post" name="fm">
		<aui:input name="accountGroupIds" type="hidden" />

		<liferay-ui:search-container
			searchContainer="<%= accountGroupDisplaySearchContainer %>"
		>
			<liferay-ui:search-container-row
				className="com.liferay.account.admin.web.internal.display.AccountGroupDisplay"
				keyProperty="accountGroupId"
				modelVar="accountGroupDisplay"
			>

				<%
				row.setData(
					HashMapBuilder.<String, Object>put(
						"actions", StringUtil.merge(viewAccountGroupsManagementToolbarDisplayContext.getAvailableActions(accountGroupDisplay))
					).build());
				%>

				<portlet:renderURL var="rowURL">
					<portlet:param name="mvcRenderCommandName" value="/account_admin/edit_account_group" />
					<portlet:param name="backURL" value="<%= currentURL %>" />
					<portlet:param name="accountGroupId" value="<%= String.valueOf(accountGroupDisplay.getAccountGroupId()) %>" />
					<portlet:param name="screenNavigationCategoryKey" value="<%= AccountScreenNavigationEntryConstants.CATEGORY_KEY_ACCOUNTS %>" />
				</portlet:renderURL>

				<%
				if (!AccountGroupPermission.contains(permissionChecker, accountGroupDisplay.getAccountGroupId(), AccountActionKeys.VIEW_ACCOUNTS)) {
					rowURL = null;
				}
				%>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-expand table-title"
					href="<%= rowURL %>"
					name="name"
					value="<%= HtmlUtil.escape(accountGroupDisplay.getName()) %>"
				/>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-expand"
					href="<%= rowURL %>"
					name="description"
					value="<%= HtmlUtil.escape(accountGroupDisplay.getDescription()) %>"
				/>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-expand"
					href="<%= rowURL %>"
					name="accounts"
					value="<%= String.valueOf(accountGroupDisplay.getAccountEntriesCount()) %>"
				/>

				<liferay-ui:search-container-column-jsp
					path="/account_groups_admin/account_group_action.jsp"
				/>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator
				markupView="lexicon"
			/>
		</liferay-ui:search-container>
	</aui:form>
</clay:container-fluid>