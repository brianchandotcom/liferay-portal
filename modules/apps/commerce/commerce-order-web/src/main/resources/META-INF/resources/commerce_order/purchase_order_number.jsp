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
CommerceOrderEditDisplayContext commerceOrderEditDisplayContext = (CommerceOrderEditDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceOrder commerceOrder = commerceOrderEditDisplayContext.getCommerceOrder();
%>

<portlet:actionURL name="/commerce_order/edit_commerce_order" var="editCommerceOrderPurchaseOrderNumberActionURL" />

<commerce-ui:modal-content>
	<aui:form action="<%= editCommerceOrderPurchaseOrderNumberActionURL %>" cssClass="container-fluid container-fluid-max-xl p-0" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="purchaseOrderNumber" />
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="commerceOrderId" type="hidden" value="<%= commerceOrder.getCommerceOrderId() %>" />

		<liferay-ui:error exception="<%= CommerceOrderPurchaseOrderNumberException.class %>" message="please-enter-a-valid-purchase-order-number" />

		<aui:model-context bean="<%= commerceOrder %>" model="<%= CommerceOrder.class %>" />

		<aui:input name="purchaseOrderNumber" wrapperCssClass="form-group-item" />
	</aui:form>
</commerce-ui:modal-content>