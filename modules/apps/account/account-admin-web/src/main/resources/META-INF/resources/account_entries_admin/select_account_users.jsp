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

SearchContainer<AccountUserDisplay> userSearchContainer = AssignableAccountUserDisplaySearchContainerFactory.create(accountEntryId, liferayPortletRequest, liferayPortletResponse);

SelectAccountUsersManagementToolbarDisplayContext selectAccountUsersManagementToolbarDisplayContext = new SelectAccountUsersManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, userSearchContainer);

if (selectAccountUsersManagementToolbarDisplayContext.isSingleSelect()) {
	userSearchContainer.setRowChecker(null);
}

String eventName = ParamUtil.getString(request, "eventName", liferayPortletResponse.getNamespace() + "assignAccountUsers");
%>

<clay:management-toolbar-v2
	displayContext="<%= selectAccountUsersManagementToolbarDisplayContext %>"
/>

<clay:container-fluid
	id='<%= liferayPortletResponse.getNamespace() + "selectAccountUser" %>'
>
	<c:if test='<%= !Objects.equals(selectAccountUsersManagementToolbarDisplayContext.getNavigation(), "all-users") %>'>
		<clay:alert
			message="showing-users-with-valid-domains-only"
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
				value="<%= accountUserDisplay.getAccountRoleNamesString(accountEntryId, locale) %>"
			/>

			<c:if test="<%= selectAccountUsersManagementToolbarDisplayContext.isSingleSelect() %>">
				<liferay-ui:search-container-column-text>
					<aui:button
						cssClass="choose-user selector-button"
						data='<%=
							HashMapBuilder.<String, Object>put(
								"emailaddress", accountUserDisplay.getEmailAddress()
							).put(
								"entityid", accountUserDisplay.getUserId()
							).put(
								"entityname", accountUserDisplay.getName()
							).put(
								"jobtitle", accountUserDisplay.getJobTitle()
							).build()
						%>'
						value="choose"
					/>
				</liferay-ui:search-container-column-text>
			</c:if>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</clay:container-fluid>

<aui:script use="liferay-search-container">
	var searchContainer = Liferay.SearchContainer.get(
		'<portlet:namespace />accountUsers'
	);

	searchContainer.on('rowToggled', function (event) {
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
			'<%= HtmlUtil.escapeJS(eventName) %>',
			result
		);
	});

	Liferay.Util.selectEntityHandler(
		'#<portlet:namespace />selectAccountUser',
		'<%= HtmlUtil.escapeJS(eventName) %>'
	);
</aui:script>

<c:if test="<%= selectAccountUsersManagementToolbarDisplayContext.isShowCreateButton() %>">
	<liferay-portlet:renderURL portletName="<%= AccountPortletKeys.ACCOUNT_USERS_ADMIN %>" var="addAccountEntryUserURL">
		<portlet:param name="mvcRenderCommandName" value="/account_admin/add_account_user" />
		<portlet:param name="redirect" value='<%= ParamUtil.getString(request, "redirect") %>' />
		<portlet:param name="backURL" value='<%= ParamUtil.getString(request, "redirect") %>' />
		<portlet:param name="accountEntryId" value='<%= ParamUtil.getString(request, "accountEntryId") %>' />
	</liferay-portlet:renderURL>

	<liferay-frontend:component
		componentId="<%= selectAccountUsersManagementToolbarDisplayContext.getDefaultEventHandler() %>"
		context='<%=
			HashMapBuilder.<String, Object>put(
				"addAccountEntryUserURL", addAccountEntryUserURL.toString()
			).put(
				"openModalOnRedirect", selectAccountUsersManagementToolbarDisplayContext.isOpenModalOnRedirect()
			).build()
		%>'
		module="account_entries_admin/js/SelectAccountUsersManagementToolbarDefaultEventHandler.es"
	/>
</c:if>