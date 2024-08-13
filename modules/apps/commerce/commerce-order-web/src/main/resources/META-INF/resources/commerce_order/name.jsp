<%--
/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceOrderEditDisplayContext commerceOrderEditDisplayContext = (CommerceOrderEditDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceOrder commerceOrder = commerceOrderEditDisplayContext.getCommerceOrder();
%>

<portlet:actionURL name="/commerce_order/edit_commerce_order" var="editCommerceOrderNameActionURL" />

<aui:form action="<%= editCommerceOrderNameActionURL %>" cssClass="container-fluid container-fluid-max-xl p-4" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="name" />
	<aui:input name="commerceOrderId" type="hidden" value="<%= commerceOrder.getCommerceOrderId() %>" />

	<liferay-ui:error exception="<%= CommerceOrderPurchaseOrderNumberException.class %>" message="please-enter-a-valid-purchase-order-number" />

	<aui:model-context bean="<%= commerceOrder %>" model="<%= CommerceOrder.class %>" />

	<aui:input name="name" wrapperCssClass="form-group-item" />
</aui:form>