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
%>

<portlet:actionURL name="/commerce_inventory_warehouse/edit_commerce_inventory_warehouse" var="editCommerceInventoryWarehouseActionURL" />

<commerce-ui:modal-content
	title='<%= LanguageUtil.get(request, "add-warehouse") %>'
>
	<aui:form method="post" name="fm">
		<aui:input bean="<%= commerceInventoryWarehousesDisplayContext.getCommerceInventoryWarehouse() %>" label="name" model="<%= CommerceInventoryWarehouse.class %>" name="name" required="<%= true %>" />
	</aui:form>

	<liferay-frontend:component
		context='<%=
			HashMapBuilder.<String, Object>put(
				"defaultLanguageId", themeDisplay.getLanguageId()
			).put(
				"editCommerceInventoryWarehousePortletURL", String.valueOf(commerceInventoryWarehousesDisplayContext.getEditCommerceWarehouseRenderURL())
			).build()
		%>'
		module="js/addCommerceInventoryWarehouse"
	/>
</commerce-ui:modal-content>