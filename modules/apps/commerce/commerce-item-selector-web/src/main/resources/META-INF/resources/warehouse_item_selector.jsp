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
CommerceInventoryWarehouseItemSelectorViewDisplayContext commerceInventoryWarehouseItemSelectorViewDisplayContext = (CommerceInventoryWarehouseItemSelectorViewDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

String itemSelectedEventName = commerceInventoryWarehouseItemSelectorViewDisplayContext.getItemSelectedEventName();
%>

<clay:management-toolbar
	managementToolbarDisplayContext="<%= new CommerceInventoryWarehouseManagementToolbarDisplayContext(commerceInventoryWarehouseItemSelectorViewDisplayContext, request, liferayPortletRequest, liferayPortletResponse) %>"
/>

<div class="container-fluid container-fluid-max-xl" id="<portlet:namespace />commerceInventoryWarehouseSelectorWrapper">
	<liferay-ui:search-container
		id="commerceInventoryWarehouses"
		searchContainer="<%= commerceInventoryWarehouseItemSelectorViewDisplayContext.getSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.commerce.inventory.model.CommerceInventoryWarehouse"
			keyProperty="commerceInventoryWarehouseId"
			modelVar="commerceInventoryWarehouse"
		>
			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand"
				property="name"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand"
				property="city"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</div>

<aui:script use="liferay-search-container">
	var commerceInventoryWarehouseSelectorWrapper = A.one(
		'#<portlet:namespace />commerceInventoryWarehouseSelectorWrapper'
	);

	var searchContainer = Liferay.SearchContainer.get(
		'<portlet:namespace />commerceInventoryWarehouses'
	);

	searchContainer.on('rowToggled', (event) => {
		Liferay.Util.getOpener().Liferay.fire(
			'<%= HtmlUtil.escapeJS(itemSelectedEventName) %>',
			{
				data: Liferay.Util.getCheckedCheckboxes(
					commerceInventoryWarehouseSelectorWrapper,
					'<portlet:namespace />allRowIds'
				),
			}
		);
	});
</aui:script>