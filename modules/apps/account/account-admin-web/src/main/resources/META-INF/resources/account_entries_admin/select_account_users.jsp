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

SearchContainer userSearchContainer = AssignableAccountUserDisplaySearchContainerFactory.create(accountEntryId, liferayPortletRequest, liferayPortletResponse);

SelectAccountUsersManagementToolbarDisplayContext selectAccountUsersManagementToolbarDisplayContext = new SelectAccountUsersManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, userSearchContainer);
%>

<clay:management-toolbar
	displayContext="<%= selectAccountUsersManagementToolbarDisplayContext %>"
/>

<aui:container cssClass="container-fluid container-fluid-max-xl">
	<c:if test='<%= !Objects.equals(selectAccountUsersManagementToolbarDisplayContext.getNavigation(), "all-users") %>'>
		<clay:alert
			message='<%= LanguageUtil.get(request, "showing-users-with-valid-domains-only") %>'
			title='<%= LanguageUtil.get(request, "info") %>'
		/>
	</c:if>

	<liferay-ui:search-container
		searchContainer="<%= userSearchContainer %>"
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
				name="email-address"
				property="emailAddress"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand-small table-cell-minw-150"
				name="account-roles"
				value="<%= accountUserDisplay.getAccountRoleNames(accountEntryId, locale) %>"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</aui:container>

<aui:script use="liferay-search-container">
	var searchContainer = Liferay.SearchContainer.get(
		'<portlet:namespace />accountUsers'
	);

	searchContainer.on('rowToggled', function(event) {
		var selectedItems = event.elements.allSelectedElements;

		var result = {};

		if (!selectedItems.isEmpty()) {
			result = {
				data: {
					value: selectedItems.get('value').join(','),
				},
			};
		}

		Liferay.Util.getOpener().Liferay.fire(
			'<%= HtmlUtil.escapeJS(renderResponse.getNamespace() + "assignAccountUsers") %>',
			result
		);
	});
</aui:script>