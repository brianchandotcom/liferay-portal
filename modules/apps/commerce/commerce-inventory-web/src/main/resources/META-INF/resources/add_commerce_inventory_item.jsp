<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceInventoryDisplayContext commerceInventoryDisplayContext = (CommerceInventoryDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<portlet:actionURL name="/commerce_inventory/edit_commerce_inventory_warehouse" var="editCommerceInventoryWarehouseActionURL" />

<commerce-ui:modal-content
	title='<%= LanguageUtil.get(request, "add-inventory-item") %>'
>
	<aui:form action="<%= editCommerceInventoryWarehouseActionURL %>" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.ADD %>" />
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

		<liferay-ui:error exception="<%= CPInstanceUnitOfMeasureKeyException.class %>" message="inventory-item-with-this-sku-and-uom-already-exists-in-the-selected-warehouse" />
		<liferay-ui:error exception="<%= DuplicateCommerceInventoryWarehouseItemException.class %>" message="inventory-item-with-this-sku-already-exists-in-the-selected-warehouse" />
		<liferay-ui:error exception="<%= NoSuchCPInstanceUnitOfMeasureException.class %>" message="no-such-uom-exists-with-this-sku" />

		<aui:input name="sku" required="<%= true %>" type="text" />

		<c:if test='<%= FeatureFlagManagerUtil.isEnabled("COMMERCE-11287") %>'>
			<aui:input name="unitOfMeasure" type="text" />
		</c:if>

		<aui:select label="warehouse" name="commerceInventoryWarehouseId" required="<%= true %>">

			<%
			for (CommerceInventoryWarehouse commerceInventoryWarehouse : commerceInventoryDisplayContext.getCommerceInventoryWarehouses()) {
			%>

				<aui:option label="<%= commerceInventoryWarehouse.getName(locale) %>" value="<%= commerceInventoryWarehouse.getCommerceInventoryWarehouseId() %>" />

			<%
			}
			%>

		</aui:select>

		<aui:input name="quantity" required="<%= true %>" type="text">
			<aui:validator name="min">1</aui:validator>
			<aui:validator name="number" />
		</aui:input>
	</aui:form>
</commerce-ui:modal-content>