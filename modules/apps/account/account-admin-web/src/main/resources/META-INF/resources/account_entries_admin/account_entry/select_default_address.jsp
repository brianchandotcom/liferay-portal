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
SearchContainer<AddressDisplay> accountEntryAddressDisplaySearchContainer = AccountEntryAddressDisplaySearchContainerFactory.create(liferayPortletRequest, liferayPortletResponse);

accountEntryAddressDisplaySearchContainer.setRowChecker(null);

ViewAccountEntryAddressesManagementToolbarDisplayContext viewAccountEntryAddressesManagementToolbarDisplayContext = new ViewAccountEntryAddressesManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, accountEntryAddressDisplaySearchContainer);
%>

<clay:management-toolbar-v2
	displayContext="<%= viewAccountEntryAddressesManagementToolbarDisplayContext %>"
	selectable="<%= false %>"
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

<script>
	Liferay.Util.selectEntityHandler(
		'#<portlet:namespace />selectDefaultAddress',
		'<%= HtmlUtil.escapeJS(liferayPortletResponse.getNamespace() + "selectDefaultAddress") %>'
	);
</script>

<liferay-frontend:component
	componentId="<%= viewAccountEntryAddressesManagementToolbarDisplayContext.getDefaultEventHandler() %>"
	module="account_entries_admin/js/AccountEntryAddressesManagementToolbarDefaultEventHandler.es"
/>