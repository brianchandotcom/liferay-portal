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

<portlet:actionURL name="/commerce_inventory/edit_commerce_inventory_warehouse_item" var="editCommerceInventoryWarehouseItemActionURL" />

<liferay-frontend:side-panel-content
	title='<%= LanguageUtil.get(request, "edit-inventory") %>'
>
	<commerce-ui:panel
		title='<%= LanguageUtil.get(request, "details") %>'
	>
		<aui:form action="<%= editCommerceInventoryWarehouseItemActionURL %>" method="post" name="fm">
			<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
			<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
			<aui:input name="commerceInventoryWarehouseItemId" type="hidden" value="<%= commerceInventoryDisplayContext.getCommerceInventoryWarehouseItemId() %>" />
			<aui:input name="mvccVersion" type="hidden" value="<%= commerceInventoryWarehouseItem.getMvccVersion() %>" />

			<aui:model-context bean="<%= commerceInventoryWarehouseItem %>" model="<%= CommerceInventoryWarehouseItem.class %>" />

			<%
			BigDecimal quantity = commerceInventoryWarehouseItem.getQuantity();
			%>

			<aui:input ignoreRequestValue="<%= true %>" label="quantity-on-hand" name="quantity" type="text" value="<%= quantity.intValue() %>">
				<aui:validator name="min">0</aui:validator>
				<aui:validator name="number" />
			</aui:input>

			<%
			BigDecimal reservedQuantity = commerceInventoryWarehouseItem.getReservedQuantity();
			%>

			<aui:input ignoreRequestValue="<%= true %>" label="safety-stock-quantity" name="reservedQuantity" type="text" value="<%= reservedQuantity.intValue() %>">
				<aui:validator name="min">0</aui:validator>
				<aui:validator name="number" />
			</aui:input>

			<aui:button-row>
				<aui:button cssClass="btn-lg" type="submit" value="save" />

				<aui:button cssClass="btn-lg" type="cancel" />
			</aui:button-row>
		</aui:form>
	</commerce-ui:panel>
</liferay-frontend:side-panel-content>