<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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