<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceInventoryWarehousesDisplayContext commerceInventoryWarehousesDisplayContext = (CommerceInventoryWarehousesDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceInventoryWarehouse commerceInventoryWarehouse = commerceInventoryWarehousesDisplayContext.getCommerceInventoryWarehouse();

portletDisplay.setShowBackIcon(true);

if (Validator.isNull(redirect)) {
	portletDisplay.setURLBack(String.valueOf(renderResponse.createRenderURL()));
}
else {
	portletDisplay.setURLBack(redirect);
}
%>

<liferay-portlet:renderURL var="editCommerceInventoryWarehouseExternalReferenceCodeURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
	<portlet:param name="mvcRenderCommandName" value="/commerce_inventory_warehouse/edit_commerce_inventory_warehouse_external_reference_code" />
	<portlet:param name="commerceInventoryWarehouseId" value="<%= String.valueOf(commerceInventoryWarehouse.getCommerceInventoryWarehouseId()) %>" />
</liferay-portlet:renderURL>

<commerce-ui:header
	actions="<%= commerceInventoryWarehousesDisplayContext.getHeaderActionModels() %>"
	bean="<%= commerceInventoryWarehouse %>"
	beanIdLabel="id"
	externalReferenceCode="<%= commerceInventoryWarehouse.getExternalReferenceCode() %>"
	externalReferenceCodeEditUrl="<%= editCommerceInventoryWarehouseExternalReferenceCodeURL %>"
	model="<%= CommerceInventoryWarehouse.class %>"
	title="<%= commerceInventoryWarehouse.getName(locale) %>"
/>

<liferay-frontend:screen-navigation
	containerWrapperCssClass="container"
	key="<%= CommerceInventoryWarehouseScreenNavigationConstants.SCREEN_NAVIGATION_KEY_COMMERCE_INVENTORY_WAREHOUSE_GENERAL %>"
	modelBean="<%= commerceInventoryWarehouse %>"
	portletURL="<%= currentURLObj %>"
/>