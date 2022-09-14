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
CommerceInventoryDisplayContext commerceInventoryDisplayContext = (CommerceInventoryDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceInventoryWarehouseItem commerceInventoryWarehouseItem = commerceInventoryDisplayContext.getCommerceInventoryWarehouseItem();
%>

<liferay-ui:error exception="<%= MVCCException.class %>" message="this-item-is-no-longer-valid-please-try-again" />

<portlet:actionURL name="/commerce_inventory/edit_commerce_inventory_warehouse" var="editCommerceInventoryWarehouseActionURL" />

<commerce-ui:modal-content
	title='<%= LanguageUtil.get(request, "add-inventory") %>'
>
	<aui:form action="<%= editCommerceInventoryWarehouseActionURL %>" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="sku" type="hidden" value="<%= commerceInventoryDisplayContext.getSku() %>" />
		<aui:input name="mvccVersion" type="hidden" value="<%= (commerceInventoryWarehouseItem == null) ? 0 : commerceInventoryWarehouseItem.getMvccVersion() %>" />

		<aui:model-context bean="<%= commerceInventoryWarehouseItem %>" model="<%= CommerceInventoryWarehouseItem.class %>" />

		<aui:input name="quantity" required="<%= true %>">
			<aui:validator name="min">1</aui:validator>
		</aui:input>

		<aui:select label="warehouse" name="commerceInventoryWarehouseId" required="<%= true %>">

			<%
			for (CommerceInventoryWarehouse commerceInventoryWarehouse : commerceInventoryDisplayContext.getCommerceInventoryWarehouses()) {
			%>

				<aui:option label="<%= commerceInventoryWarehouse.getName(locale) %>" value="<%= commerceInventoryWarehouse.getCommerceInventoryWarehouseId() %>" />

			<%
			}
			%>

		</aui:select>
	</aui:form>
</commerce-ui:modal-content>