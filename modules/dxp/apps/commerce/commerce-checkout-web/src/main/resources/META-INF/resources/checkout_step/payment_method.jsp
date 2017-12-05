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
PaymentMethodCheckoutStepDisplayContext paymentMethodCheckoutStepDisplayContext = (PaymentMethodCheckoutStepDisplayContext)request.getAttribute(CommerceCheckoutWebKeys.COMMERCE_CHECKOUT_STEP_DISPLAY_CONTEXT);

CommerceCart commerceCart = paymentMethodCheckoutStepDisplayContext.getCommerceCart();

long commercePaymentMethodId = BeanParamUtil.getLong(commerceCart, request, "commercePaymentMethodId");
%>

<h3><liferay-ui:message key="payment-method" /></h3>

<aui:fieldset>
	<liferay-ui:error exception="<%= CommerceCartPaymentMethodException.class %>" message="please-select-a-valid-payment-method" />

	<%
	for (CommercePaymentMethod commercePaymentMethod : paymentMethodCheckoutStepDisplayContext.getCommercePaymentMethods()) {
	%>

		<aui:input
			checked="<%= commercePaymentMethod.getCommercePaymentMethodId() == commercePaymentMethodId %>"
			label="<%= commercePaymentMethod.getName(locale) %>"
			name="commercePaymentMethodId"
			type="radio"
			value="<%= commercePaymentMethod.getCommercePaymentMethodId() %>"
		/>

	<%
	}
	%>

</aui:fieldset>