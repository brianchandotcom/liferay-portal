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
%>

<portlet:actionURL name="/commerce_inventory/transfer_quantities" var="transferQuantitiesActionURL" />

<commerce-ui:modal-content
	title='<%= LanguageUtil.get(request, "create-a-transfer") %>'
>
	<aui:form action="<%= transferQuantitiesActionURL %>" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.MOVE %>" />
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="sku" type="hidden" value="<%= commerceInventoryDisplayContext.getSku() %>" />

		<aui:input label="quantity" name="quantity" required="<%= true %>" type="text">
			<aui:validator name="min">1</aui:validator>
		</aui:input>

		<aui:select label="source" name="fromCommerceInventoryWarehouseId" required="<%= true %>">

			<%
			for (CommerceInventoryWarehouse commerceInventoryWarehouse : commerceInventoryDisplayContext.getCommerceInventoryWarehouses()) {
			%>

				<aui:option label="<%= HtmlUtil.escape(commerceInventoryWarehouse.getName()) %>" value="<%= commerceInventoryWarehouse.getCommerceInventoryWarehouseId() %>" />

			<%
			}
			%>

		</aui:select>

		<aui:select label="destination" name="toCommerceInventoryWarehouseId" required="<%= true %>">

			<%
			for (CommerceInventoryWarehouse commerceInventoryWarehouse : commerceInventoryDisplayContext.getCommerceInventoryWarehouses()) {
			%>

				<aui:option label="<%= HtmlUtil.escape(commerceInventoryWarehouse.getName()) %>" value="<%= commerceInventoryWarehouse.getCommerceInventoryWarehouseId() %>" />

			<%
			}
			%>

		</aui:select>
	</aui:form>
</commerce-ui:modal-content>