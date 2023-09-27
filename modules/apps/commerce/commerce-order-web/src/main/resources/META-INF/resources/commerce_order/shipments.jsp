<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceOrderEditDisplayContext commerceOrderEditDisplayContext = (CommerceOrderEditDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<liferay-portlet:renderURL var="editCommerceShipmentURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
	<portlet:param name="mvcRenderCommandName" value="/commerce_order/edit_commerce_shipment" />
	<portlet:param name="commerceOrderId" value="<%= String.valueOf(commerceOrderEditDisplayContext.getCommerceOrderId()) %>" />
</liferay-portlet:renderURL>

<%
CommerceOrder commerceOrder = commerceOrderEditDisplayContext.getCommerceOrder();
%>

<frontend-data-set:classic-display
	contextParams='<%=
		HashMapBuilder.<String, String>put(
			"commerceOrderId", String.valueOf(commerceOrder.getCommerceOrderId())
		).build()
	%>'
	dataProviderKey="<%= CommerceShipmentFDSNames.SHIPMENTS %>"
	id="<%= CommerceShipmentFDSNames.ORDER_SHIPMENTS %>"
	itemsPerPage="<%= 10 %>"
	showManagementBar="<%= false %>"
	style="stacked"
/>