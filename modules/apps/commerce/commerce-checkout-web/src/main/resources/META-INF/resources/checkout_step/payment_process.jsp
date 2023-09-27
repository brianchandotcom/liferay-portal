<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
PaymentProcessCheckoutStepDisplayContext paymentProcessCheckoutStepDisplayContext = (PaymentProcessCheckoutStepDisplayContext)request.getAttribute(CommerceCheckoutWebKeys.COMMERCE_CHECKOUT_STEP_DISPLAY_CONTEXT);
%>

<div>
	<c:set var="redirectLink">
		<a href="<%= HtmlUtil.escapeAttribute(paymentProcessCheckoutStepDisplayContext.getPaymentURL()) %>"><%= HtmlUtil.escape(paymentProcessCheckoutStepDisplayContext.getPaymentURL()) %></a>
	</c:set>

	<liferay-ui:message arguments="${redirectLink}" key="the-payment-process-has-been-initiated.-you-should-be-redirected-automatically.-if-the-page-does-not-reload-within-a-few-seconds-please-click-this-link-x" />
</div>

<script>
	window.location.href =
		'<%= HtmlUtil.escapeJS(paymentProcessCheckoutStepDisplayContext.getPaymentURL()) %>';
</script>