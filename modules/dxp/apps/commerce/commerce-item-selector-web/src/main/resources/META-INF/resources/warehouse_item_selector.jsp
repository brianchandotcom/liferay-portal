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
CommerceWarehouseItemSelectorViewDisplayContext commerceWarehouseItemSelectorViewDisplayContext = (CommerceWarehouseItemSelectorViewDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

long commerceCountryId = commerceWarehouseItemSelectorViewDisplayContext.getCommerceCountryId();
String itemSelectedEventName = commerceWarehouseItemSelectorViewDisplayContext.getItemSelectedEventName();
List<ManagementBarFilterItem> managementBarFilterItems = commerceWarehouseItemSelectorViewDisplayContext.getManagementBarFilterItems();
PortletURL portletURL = commerceWarehouseItemSelectorViewDisplayContext.getPortletURL();

String managementBarFilterValue = null;

for (ManagementBarFilterItem managementBarFilterItem : managementBarFilterItems) {
	if (commerceCountryId == Long.valueOf(managementBarFilterItem.getId())) {
		managementBarFilterValue = managementBarFilterItem.getLabel();

		break;
	}
}
%>

<liferay-frontend:management-bar
	includeCheckBox="<%= true %>"
	searchContainerId="commerceWarehouses"
>
	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-filter
			label="country"
			managementBarFilterItems="<%= managementBarFilterItems %>"
			value="<%= managementBarFilterValue %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= commerceWarehouseItemSelectorViewDisplayContext.getOrderByCol() %>"
			orderByType="<%= commerceWarehouseItemSelectorViewDisplayContext.getOrderByType() %>"
			orderColumns='<%= new String[] {"city", "name"} %>'
			portletURL="<%= commerceWarehouseItemSelectorViewDisplayContext.getPortletURL() %>"
		/>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"list"} %>'
			portletURL="<%= renderResponse.createRenderURL() %>"
			selectedDisplayStyle="list"
		/>
	</liferay-frontend:management-bar-buttons>
</liferay-frontend:management-bar>

<div class="container-fluid-1280" id="<portlet:namespace />commerceWarehouseSelectorWrapper">
	<liferay-ui:search-container
		id="commerceWarehouses"
		searchContainer="<%= commerceWarehouseItemSelectorViewDisplayContext.getSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.commerce.model.CommerceWarehouse"
			keyProperty="commerceWarehouseId"
			modelVar="commerceWarehouse"
		>
			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				property="name"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				property="city"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</div>

<aui:script use="liferay-search-container">
	var commerceWarehouseSelectorWrapper = A.one("#<portlet:namespace />commerceWarehouseSelectorWrapper");

	var searchContainer = Liferay.SearchContainer.get('<portlet:namespace />commerceWarehouses');

	searchContainer.on(
		'rowToggled',
		function(event) {
			Liferay.Util.getOpener().Liferay.fire(
				'<%= HtmlUtil.escapeJS(itemSelectedEventName) %>',
				{
					data: Liferay.Util.listCheckedExcept(commerceWarehouseSelectorWrapper, '<portlet:namespace />allRowIds')
				}
			);
		}
	);
</aui:script>