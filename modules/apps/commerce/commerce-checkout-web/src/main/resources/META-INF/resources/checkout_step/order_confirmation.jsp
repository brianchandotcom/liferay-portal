<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
OrderConfirmationCheckoutStepDisplayContext orderConfirmationCheckoutStepDisplayContext = (OrderConfirmationCheckoutStepDisplayContext)request.getAttribute(CommerceCheckoutWebKeys.COMMERCE_CHECKOUT_STEP_DISPLAY_CONTEXT);

CommerceOrderPayment commerceOrderPayment = orderConfirmationCheckoutStepDisplayContext.getCommerceOrderPayment();

String commerceOrderPaymentContent = null;

if (commerceOrderPayment != null) {
	commerceOrderPaymentContent = commerceOrderPayment.getContent();
}

int paymentStatus = CommerceOrderPaymentConstants.STATUS_PENDING;

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
					taglibMessageKey = "your-payment-has-been-cancelled";
					taglibValue = "pay-now";
				}
				%>

				<liferay-ui:message key="<%= taglibMessageKey %>" />

				<c:if test="<%= !commerceOrderPaymentContent.isEmpty() %>">
					<div><%= SanitizerUtil.sanitize(themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId(), themeDisplay.getUserId(), CommerceOrderPayment.class.getName(), commerceOrderPayment.getCommerceOrderPaymentId(), "plain/text", commerceOrderPaymentContent) %></div>
				</c:if>

				<aui:button-row>
					<aui:button cssClass="alert-link btn-link" href="<%= orderConfirmationCheckoutStepDisplayContext.getRetryPaymentURL() %>" type="submit" value="<%= taglibValue %>" />
				</aui:button-row>
			</div>
		</c:when>
		<c:when test="<%= paymentStatus == CommerceOrderPaymentConstants.STATUS_COMPLETED %>">
			<div class="success-message">
				<liferay-ui:message key="success-your-order-has-been-processed" />
			</div>

			<c:if test="<%= !user.isGuestUser() %>">
				<aui:button-row>
					<aui:button href="<%= orderConfirmationCheckoutStepDisplayContext.getOrderDetailURL() %>" primary="<%= true %>" type="submit" value="go-to-order-details" />
				</aui:button-row>
			</c:if>
		</c:when>
		<c:otherwise>
			<div class="success-message">
				<liferay-ui:message key="your-order-has-been-processed-but-not-completed-yet" />
			</div>

			<c:if test="<%= !user.isGuestUser() %>">
				<aui:button-row>
					<aui:button href="<%= orderConfirmationCheckoutStepDisplayContext.getOrderDetailURL() %>" primary="<%= true %>" type="submit" value="go-to-order-details" />
				</aui:button-row>
			</c:if>
		</c:otherwise>
	</c:choose>
</div>