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
SearchContainer accountDisplaySearchContainer = AccountDisplaySearchContainerBuilder.getAccountDisplaySearchContainer(liferayPortletRequest, liferayPortletResponse);

ViewAccountsManagementToolbarDisplayContext viewAccountsManagementToolbarDisplayContext = new ViewAccountsManagementToolbarDisplayContext(liferayPortletRequest, liferayPortletResponse, request, accountDisplaySearchContainer);
%>

<clay:management-toolbar
	displayContext="<%= viewAccountsManagementToolbarDisplayContext %>"
/>

<div class="container-fluid container-fluid-max-xl">
	<liferay-ui:search-container
		searchContainer="<%= accountDisplaySearchContainer %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.account.admin.web.internal.display.AccountDisplay"
			keyProperty="accountId"
			modelVar="accountDisplay"
		>
			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand table-title"
				name="name"
				property="name"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand"
				name="parent-account"
				property="parentAccountName"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand"
				name="website"
				property="website"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand"
				name="account-owner"
				property="accountOwner"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand"
				name="status"
			>
				<clay:label
					label="<%= StringUtil.toUpperCase(LanguageUtil.get(request, accountDisplay.getStatusLabel()), locale) %>"
					style="success"
				/>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</div>