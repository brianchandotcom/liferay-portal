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

SearchContainer<AddressDisplay> accountEntryAddressDisplaySearchContainer = AccountEntryAddressDisplaySearchContainerFactory.create(liferayPortletRequest, liferayPortletResponse);

ViewAccountEntryAddressesManagementToolbarDisplayContext viewAccountEntryAddressesManagementToolbarDisplayContext = new ViewAccountEntryAddressesManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, accountEntryAddressDisplaySearchContainer);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(ParamUtil.getString(request, "backURL", String.valueOf(renderResponse.createRenderURL())));

renderResponse.setTitle(accountEntryDisplay.getName());
%>

<clay:management-toolbar-v2
	displayContext="<%= viewAccountEntryAddressesManagementToolbarDisplayContext %>"
/>

<clay:container-fluid>
	<aui:form method="post" name="fm">
		<aui:input name="accountEntryAddressIds" type="hidden" />

		<liferay-ui:search-container
			searchContainer="<%= accountEntryAddressDisplaySearchContainer %>"
		>
			<liferay-ui:search-container-row
				className="com.liferay.account.admin.web.internal.display.AddressDisplay"
				keyProperty="addressId"
				modelVar="addressDisplay"
			>
				<portlet:renderURL var="rowURL">
					<portlet:param name="mvcRenderCommandName" value="/account_admin/edit_account_entry_address" />
					<portlet:param name="backURL" value="<%= currentURL %>" />
					<portlet:param name="accountEntryAddressId" value="<%= String.valueOf(addressDisplay.getAddressId()) %>" />
					<portlet:param name="accountEntryId" value="<%= String.valueOf(accountEntryDisplay.getAccountEntryId()) %>" />
				</portlet:renderURL>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-expand-small table-cell-minw-150"
					href="<%= rowURL %>"
					name="name"
					value="<%= addressDisplay.getName() %>"
				/>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-expand-small table-cell-minw-150"
					href="<%= rowURL %>"
					name="street"
					value="<%= addressDisplay.getStreet() %>"
				/>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-expand-small table-cell-minw-150"
					href="<%= rowURL %>"
					name="city"
					value="<%= addressDisplay.getCity() %>"
				/>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-expand-small table-cell-minw-150"
					href="<%= rowURL %>"
					name="region"
					value="<%= addressDisplay.getRegionName() %>"
				/>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-expand-small table-cell-minw-150"
					href="<%= rowURL %>"
					name="postal-code"
					value="<%= addressDisplay.getZip() %>"
				/>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-expand-small table-cell-minw-150"
					href="<%= rowURL %>"
					name="type"
					value="<%= addressDisplay.getType(themeDisplay.getLocale()) %>"
				/>

				<liferay-ui:search-container-column-jsp
					path="/account_entries_admin/account_entry_address_action.jsp"
				/>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator
				markupView="lexicon"
			/>
		</liferay-ui:search-container>
	</aui:form>
</clay:container-fluid>

<liferay-frontend:component
	componentId="<%= viewAccountEntryAddressesManagementToolbarDisplayContext.getDefaultEventHandler() %>"
	module="account_entries_admin/js/AccountEntryAddressesManagementToolbarDefaultEventHandler.es"
/>