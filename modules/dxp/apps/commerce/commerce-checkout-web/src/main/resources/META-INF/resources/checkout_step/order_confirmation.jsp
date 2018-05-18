<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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
				<liferay-ui:message key="success-your-order-has-been-processed" />
			</div>

			<liferay-ui:message key="you-will-be-redirected-in-a-few-seconds" />

			<aui:button-row>
				<aui:button href="<%= orderConfirmationCheckoutStepDisplayContext.getOrderDetailURL() %>" primary="<%= true %>" type="submit" value="go-to-order-details" />
			</aui:button-row>
		</c:otherwise>
	</c:choose>
</div>