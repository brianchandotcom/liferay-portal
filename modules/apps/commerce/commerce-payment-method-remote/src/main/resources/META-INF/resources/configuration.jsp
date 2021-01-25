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
String redirect = ParamUtil.getString(request, "redirect");

RemoteCommercePaymentMethodConfiguration remoteCommercePaymentMethodConfiguration = (RemoteCommercePaymentMethodConfiguration)request.getAttribute(RemoteCommercePaymentMethodConfiguration.class.getName());
%>

<portlet:actionURL name="editRemoteCommercePaymentMethodConfiguration" var="editRemoteCommercePaymentMethodConfigurationURL" />

<aui:form action="<%= editRemoteCommercePaymentMethodConfigurationURL %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

	<div class="lfr-form-content">
		<aui:fieldset-group markupView="lexicon">
			<aui:fieldset>
				<aui:input id="activate-recurring-payment-endpoint-url" label="activate-recurring-payment-endpoint-url" name="settings--activateRecurringPaymentEndpointURL--" type="url" value="<%= remoteCommercePaymentMethodConfiguration.activateRecurringPaymentEndpointURL() %>" />

				<aui:input id="authorize-payment-endpoint-url" label="authorize-payment-endpoint-url" name="settings--authorizePaymentEndpointURL--" type="url" value="<%= remoteCommercePaymentMethodConfiguration.authorizePaymentEndpointURL() %>" />

				<aui:input id="cancel-payment-endpoint-url" label="cancel-payment-endpoint-url" name="settings--cancelPaymentEndpointURL--" type="url" value="<%= remoteCommercePaymentMethodConfiguration.cancelPaymentEndpointURL() %>" />

				<aui:input id="cancel-recurring-payment-endpoint-url" label="cancel-recurring-payment-endpoint-url" name="settings--cancelRecurringPaymentEndpointURL--" type="url" value="<%= remoteCommercePaymentMethodConfiguration.cancelRecurringPaymentEndpointURL() %>" />

				<aui:input id="capture-payment-endpoint-url" label="capture-payment-endpoint-url" name="settings--capturePaymentEndpointURL--" type="url" value="<%= remoteCommercePaymentMethodConfiguration.capturePaymentEndpointURL() %>" />

				<aui:input id="complete-payment-endpoint-url" label="complete-payment-endpoint-url" name="settings--completePaymentEndpointURL--" type="url" value="<%= remoteCommercePaymentMethodConfiguration.completePaymentEndpointURL() %>" />

				<aui:input id="complete-recurring-payment-endpoint-url" label="complete-recurring-payment-endpoint-url" name="settings--completeRecurringPaymentEndpointURL--" type="url" value="<%= remoteCommercePaymentMethodConfiguration.completeRecurringPaymentEndpointURL() %>" />

				<aui:input id="subscription-validity-endpoint-url" label="subscription-validity-endpoint-url" name="settings--subscriptionValidityEndpointURL--" type="url" value="<%= remoteCommercePaymentMethodConfiguration.subscriptionValidityEndpointURL() %>" />

				<aui:input id="partially-refund-payment-endpoint-url" label="partially-refund-payment-endpoint-url" name="settings--partiallyRefundPaymentEndpointURL--" type="url" value="<%= remoteCommercePaymentMethodConfiguration.partiallyRefundPaymentEndpointURL() %>" />

				<aui:input id="process-payment-endpoint-url" label="process-payment-endpoint-url" name="settings--processPaymentEndpointURL--" type="url" value="<%= remoteCommercePaymentMethodConfiguration.processPaymentEndpointURL() %>" />

				<aui:input id="process-recurring-payment-endpoint-url" label="process-recurring-payment-endpoint-url" name="settings--processRecurringPaymentEndpointURL--" type="url" value="<%= remoteCommercePaymentMethodConfiguration.processRecurringPaymentEndpointURL() %>" />

				<aui:input id="refund-payment-endpoint-url" label="refund-payment-endpoint-url" name="settings--refundPaymentEndpointURL--" type="url" value="<%= remoteCommercePaymentMethodConfiguration.refundPaymentEndpointURL() %>" />

				<aui:input id="suspend-recurring-payment-endpoint-url" label="suspend-recurring-payment-endpoint-url" name="settings--suspendRecurringPaymentEndpointURL--" type="url" value="<%= remoteCommercePaymentMethodConfiguration.suspendRecurringPaymentEndpointURL() %>" />

				<aui:input id="void-transaction-endpoint-url" label="void-transaction-endpoint-url" name="settings--voidTransactionEndpointURL--" type="url" value="<%= remoteCommercePaymentMethodConfiguration.voidTransactionEndpointURL() %>" />

				<aui:input id="endpoint-authorization-token" label="endpoint-authorization-token" name="settings--endpointAuthorizationToken--" type="input" value="<%= remoteCommercePaymentMethodConfiguration.endpointAuthorizationToken() %>" />
			</aui:fieldset>
		</aui:fieldset-group>
	</div>

	<aui:button-row>
		<aui:button cssClass="btn-lg" type="submit" />

		<aui:button cssClass="btn-lg" href="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>