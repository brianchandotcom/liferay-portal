<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

		<aui:input name="quantity" required="<%= true %>" type="text">
			<aui:validator name="min">1</aui:validator>
			<aui:validator name="number" />
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