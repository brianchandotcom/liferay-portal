<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceOrder commerceOrder = commerceOrderContentDisplayContext.getCommerceOrder();
%>

<portlet:actionURL name="/commerce_open_order_content/edit_commerce_order" var="editCommerceOrderPurchaseOrderNumberActionURL" />

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