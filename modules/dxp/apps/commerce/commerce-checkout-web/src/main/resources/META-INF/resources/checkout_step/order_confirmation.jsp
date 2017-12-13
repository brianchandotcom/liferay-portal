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
OrderConfirmationCheckoutStepDisplayContext orderConfirmationCheckoutStepDisplayContext = (OrderConfirmationCheckoutStepDisplayContext)request.getAttribute(CommerceCheckoutWebKeys.COMMERCE_CHECKOUT_STEP_DISPLAY_CONTEXT);

CommerceOrder commerceOrder = orderConfirmationCheckoutStepDisplayContext.getCommerceOrder();
CommerceOrderPayment commerceOrderPayment = orderConfirmationCheckoutStepDisplayContext.getCommerceOrderPayment();

int paymentStatus = commerceOrderPayment.getStatus();
%>

Order <%= commerceOrder.getCommerceOrderId() %>

<c:if test="<%= (paymentStatus == CommerceOrderPaymentConstants.STATUS_CANCELLED) || (paymentStatus == CommerceOrderPaymentConstants.STATUS_FAILED) %>">
	<aui:input name="commerceOrderId" type="hidden" value="<%= commerceOrder.getCommerceOrderId() %>" />

	<div class="alert alert-warning">

		<%
		String taglibMessageKey = "an-error-occurred-while-processing-your-payment";
		String taglibValue = "retry";

		if (paymentStatus == CommerceOrderPaymentConstants.STATUS_CANCELLED) {
			taglibMessageKey = "your-payment-has-been-cancelled";
			taglibValue = "pay-now";
		}
		%>

		<liferay-ui:message key="<%= taglibMessageKey %>" />

		<aui:button cssClass="alert-link btn-link" type="submit" value="<%= taglibValue %>" />
	</div>
</c:if>