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

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

CommerceInventoryWarehouse commerceInventoryWarehouse = (CommerceInventoryWarehouse)row.getObject();
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>
	<c:if test="<%= commerceInventoryWarehousesDisplayContext.hasManageCommerceInventoryWarehousePermission() %>">
		<portlet:renderURL var="editURL">
			<portlet:param name="mvcRenderCommandName" value="/commerce_inventory_warehouse/edit_commerce_inventory_warehouse" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="commerceInventoryWarehouseId" value="<%= String.valueOf(commerceInventoryWarehouse.getCommerceInventoryWarehouseId()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			message="edit"
			url="<%= editURL %>"
		/>

		<portlet:actionURL name="/commerce_inventory_warehouse/edit_commerce_inventory_warehouse" var="geolocateURL">
			<portlet:param name="<%= Constants.CMD %>" value="geolocate" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="commerceInventoryWarehouseId" value="<%= String.valueOf(commerceInventoryWarehouse.getCommerceInventoryWarehouseId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon
			message="geolocate"
			url="<%= geolocateURL %>"
		/>

		<liferay-security:permissionsURL
			modelResource="<%= CommerceInventoryWarehouse.class.getName() %>"
			modelResourceDescription="<%= commerceInventoryWarehouse.getName() %>"
			resourcePrimKey="<%= String.valueOf(commerceInventoryWarehouse.getCommerceInventoryWarehouseId()) %>"
			var="permissionsCommerceInventoryWarehouseURL"
			windowState="<%= LiferayWindowState.POP_UP.toString() %>"
		/>

		<liferay-ui:icon
			message="permissions"
			method="get"
			url="<%= permissionsCommerceInventoryWarehouseURL %>"
			useDialog="<%= true %>"
		/>

		<portlet:actionURL name="/commerce_inventory_warehouse/edit_commerce_inventory_warehouse" var="setActiveURL">
			<portlet:param name="<%= Constants.CMD %>" value="setActive" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="commerceInventoryWarehouseId" value="<%= String.valueOf(commerceInventoryWarehouse.getCommerceInventoryWarehouseId()) %>" />
			<portlet:param name="active" value="<%= String.valueOf(!commerceInventoryWarehouse.isActive()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon
			message='<%= commerceInventoryWarehouse.isActive() ? "deactivate" : "activate" %>'
			url="<%= setActiveURL %>"
		/>

		<portlet:actionURL name="/commerce_inventory_warehouse/edit_commerce_inventory_warehouse" var="deleteURL">
			<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="commerceInventoryWarehouseId" value="<%= String.valueOf(commerceInventoryWarehouse.getCommerceInventoryWarehouseId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete
			url="<%= deleteURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>