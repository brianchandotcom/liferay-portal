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