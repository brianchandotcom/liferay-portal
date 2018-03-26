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

CommerceOrderPayment commerceOrderPayment = orderConfirmationCheckoutStepDisplayContext.getCommerceOrderPayment();

int paymentStatus = CommerceOrderPaymentConstants.STATUS_ANY;

if (commerceOrderPayment != null) {
	paymentStatus = commerceOrderPayment.getStatus();
}
%>

<div class="commerce-checkout-confirmation">
	<c:choose>
		<c:when test="<%= (paymentStatus == CommerceOrderPaymentConstants.STATUS_CANCELLED) || (paymentStatus == CommerceOrderPaymentConstants.STATUS_FAILED) %>">
			<div class="alert alert-warning">

				<%
				String taglibMessageKey = "an-error-occurred-while-processing-your-payment";
				String taglibValue = "retry";

				if (paymentStatus == CommerceOrderPaymentConstants.STATUS_CANCELLED) {
					taglibMessageKey = "your-payment-has-been-canceled";
					taglibValue = "pay-now";
				}
				%>

				<liferay-ui:message key="<%= taglibMessageKey %>" />

				<aui:button-row>
					<aui:button cssClass="alert-link btn-link" type="submit" value="<%= taglibValue %>" />
				</aui:button-row>
			</div>
		</c:when>
		<c:otherwise>
			<div class="success-message">
				<liferay-ui:message key="success-your-payment-has-been-processed" />
			</div>

			<liferay-ui:message key="you-will-be-redireted-in-a-few-seconds" />

			<aui:button-row>
				<aui:button primary="<%= true %>" type="submit" value="go-to-order-details" />
			</aui:button-row>
		</c:otherwise>
	</c:choose>
</div>