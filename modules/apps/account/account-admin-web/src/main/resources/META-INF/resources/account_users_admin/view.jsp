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
SearchContainer accountUsersDisplaySearchContainer = AccountUserDisplaySearchContainerFactory.create(liferayPortletRequest, liferayPortletResponse);

AccountUsersAdminManagementToolbarDisplayContext accountUsersAdminManagementToolbarDisplayContext = new AccountUsersAdminManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, accountUsersDisplaySearchContainer);
%>

<clay:management-toolbar
	displayContext="<%= accountUsersAdminManagementToolbarDisplayContext %>"
/>

<aui:container cssClass="container-fluid container-fluid-max-xl">
	<aui:form method="post" name="fm">
		<aui:input name="accountUserIds" type="hidden" />

		<liferay-ui:search-container
			searchContainer="<%= accountUsersDisplaySearchContainer %>"
		>
			<liferay-ui:search-container-row
				className="com.liferay.account.admin.web.internal.display.AccountUserDisplay"
				keyProperty="userId"
				modelVar="accountUserDisplay"
			>
				<liferay-ui:search-container-column-text
					cssClass="table-cell-expand-small table-cell-minw-150"
					name="name"
					property="name"
				/>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-expand-small table-cell-minw-150"
					name="email"
					property="emailAddress"
				/>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-expand-small table-cell-minw-150"
					name="job-title"
					property="jobTitle"
				/>

				<liferay-ui:search-container-column-text
					cssClass='<%= "table-cell-expand-small table-cell-minw-150 " + accountUserDisplay.getAccountNamesStyle() %>'
					name="accounts"
					value="<%= accountUserDisplay.getAccountNames(request) %>"
				/>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-expand"
					name="status"
				>
					<clay:label
						label="<%= StringUtil.toUpperCase(LanguageUtil.get(request, accountUserDisplay.getStatusLabel()), locale) %>"
						style="<%= accountUserDisplay.getStatusLabelStyle() %>"
					/>
				</liferay-ui:search-container-column-text>

				<liferay-ui:search-container-column-jsp
					path="/account_users_admin/account_user_action.jsp"
				/>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator
				markupView="lexicon"
			/>
		</liferay-ui:search-container>
	</aui:form>
</aui:container>

<liferay-frontend:component
	componentId="<%= accountUsersAdminManagementToolbarDisplayContext.getDefaultEventHandler() %>"
	module="account_users_admin/js/ManagementToolbarDefaultEventHandler.es"
/>