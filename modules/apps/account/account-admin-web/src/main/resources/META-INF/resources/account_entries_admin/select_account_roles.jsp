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
SearchContainer<AccountRoleDisplay> accountRoleDisplaySearchContainer = AccountRoleDisplaySearchContainerFactory.create(ParamUtil.getLong(request, "accountEntryId"), liferayPortletRequest, liferayPortletResponse);

accountRoleDisplaySearchContainer.setRowChecker(new SelectAccountUserAccountRoleRowChecker(liferayPortletResponse, ParamUtil.getLong(liferayPortletRequest, "accountEntryId"), ParamUtil.getLong(liferayPortletRequest, "accountUserIds")));
%>

<clay:management-toolbar
	managementToolbarDisplayContext="<%= new ViewAccountUserRolesManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, accountRoleDisplaySearchContainer) %>"
	showCreationMenu="<%= false %>"
/>

<clay:container-fluid>
	<liferay-ui:search-container
		searchContainer="<%= accountRoleDisplaySearchContainer %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.account.admin.web.internal.display.AccountRoleDisplay"
			keyProperty="accountRoleId"
			modelVar="accountRole"
		>
			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand-small table-cell-minw-150"
				name="name"
				value="<%= accountRole.getName(locale) %>"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand-small table-cell-minw-150"
				name="description"
				value="<%= accountRole.getDescription(locale) %>"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</clay:container-fluid>