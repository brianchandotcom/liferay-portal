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
AccountEntryDisplay accountEntryDisplay = AccountEntryDisplayFactoryUtil.create(ParamUtil.getLong(request, "accountEntryId"), request);

long defaultAddressId = 0;

String type = ParamUtil.getString(request, "type");

if (Objects.equals("billing", type)) {
	defaultAddressId = accountEntryDisplay.getDefaultBillingAddressId();
}
else if (Objects.equals("shipping", type)) {
	defaultAddressId = accountEntryDisplay.getDefaultShippingAddressId();
}

SearchContainer<AddressDisplay> accountEntryAddressDisplaySearchContainer = AccountEntryAddressDisplaySearchContainerFactory.create(liferayPortletRequest, liferayPortletResponse);

accountEntryAddressDisplaySearchContainer.setRowChecker(null);
%>

<portlet:renderURL var="addAccountEntryDefaultAddressURL">
	<portlet:param name="mvcRenderCommandName" value="/account_admin/edit_account_entry_address" />
	<portlet:param name="backURL" value='<%= ParamUtil.getString(request, "redirect") %>' />
	<portlet:param name="accountEntryId" value='<%= ParamUtil.getString(request, "accountEntryId") %>' />
	<portlet:param name="defaultType" value="<%= type %>" />
</portlet:renderURL>

<clay:management-toolbar
	additionalProps='<%=
		HashMapBuilder.<String, Object>put(
			"addAccountEntryDefaultAddressURL", addAccountEntryDefaultAddressURL.toString()
		).build()
	%>'
	managementToolbarDisplayContext="<%= new SelectAccountEntryAddressManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, accountEntryAddressDisplaySearchContainer) %>"
	propsTransformer="account_entries_admin/js/SelectAccountDefaultAddressManagementToolbarPropsTransformer"
	showCreationMenu="<%= true %>"
/>

<clay:container-fluid
	id='<%= liferayPortletResponse.getNamespace() + "selectDefaultAddress" %>'
>
	<liferay-ui:search-container
		searchContainer="<%= accountEntryAddressDisplaySearchContainer %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.account.admin.web.internal.display.AddressDisplay"
			keyProperty="addressId"
			modelVar="addressDisplay"
		>
			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand-small table-cell-minw-150"
				name="name"
				value="<%= addressDisplay.getName() %>"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand-small table-cell-minw-150"
				name="street"
				value="<%= addressDisplay.getStreet() %>"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand-small table-cell-minw-150"
				name="city"
				value="<%= addressDisplay.getCity() %>"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand-small table-cell-minw-150"
				name="region"
				value="<%= addressDisplay.getRegionName() %>"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand-small table-cell-minw-150"
				name="postal-code"
				value="<%= addressDisplay.getZip() %>"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand-small table-cell-minw-150"
				name="type"
				value="<%= addressDisplay.getType(themeDisplay.getLocale()) %>"
			/>

			<liferay-ui:search-container-column-text>
				<clay:radio
					checked="<%= addressDisplay.getAddressId() == defaultAddressId %>"
					cssClass="selector-button"
					data-entityid="<%= addressDisplay.getAddressId() %>"
					label="<%= addressDisplay.getName() %>"
					name="selectAddress"
					showLabel="<%= false %>"
					value="<%= String.valueOf(addressDisplay.getAddressId()) %>"
				/>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</clay:container-fluid>