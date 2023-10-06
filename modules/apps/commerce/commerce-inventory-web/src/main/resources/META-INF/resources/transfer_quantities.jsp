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

<portlet:actionURL name="/commerce_inventory/transfer_quantities" var="transferQuantitiesActionURL" />

<commerce-ui:modal-content
	title='<%= LanguageUtil.get(request, "create-a-transfer") %>'
>
	<aui:form action="<%= transferQuantitiesActionURL %>" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.MOVE %>" />
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="sku" type="hidden" value="<%= commerceInventoryDisplayContext.getSku() %>" />
		<aui:input name="unitOfMeasureKey" type="hidden" value="<%= commerceInventoryDisplayContext.getUnitOfMeasureKey() %>" />

		<liferay-ui:error exception="<%= PrincipalException.MustHavePermission.class %>" message="you-do-not-have-the-required-permissions" />

		<aui:input label="quantity" name="quantity" required="<%= true %>" type="text">
			<aui:validator name="min">1</aui:validator>
		</aui:input>

		<aui:select label="source" name="fromCommerceInventoryWarehouseId" required="<%= true %>">

			<%
			for (CommerceInventoryWarehouse commerceInventoryWarehouse : commerceInventoryDisplayContext.getCommerceInventoryWarehouses()) {
			%>

				<aui:option label="<%= HtmlUtil.escape(commerceInventoryWarehouse.getName(locale)) %>" value="<%= commerceInventoryWarehouse.getCommerceInventoryWarehouseId() %>" />

			<%
			}
			%>

		</aui:select>

		<aui:select label="destination" name="toCommerceInventoryWarehouseId" required="<%= true %>">

			<%
			for (CommerceInventoryWarehouse commerceInventoryWarehouse : commerceInventoryDisplayContext.getCommerceInventoryWarehouses()) {
			%>

				<aui:option label="<%= HtmlUtil.escape(commerceInventoryWarehouse.getName(locale)) %>" value="<%= commerceInventoryWarehouse.getCommerceInventoryWarehouseId() %>" />

			<%
			}
			%>

		</aui:select>
	</aui:form>
</commerce-ui:modal-content>