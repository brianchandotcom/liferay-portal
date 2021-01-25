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

package com.liferay.commerce.payment.method.remote.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Ivica Cardic
 */
@ExtendedObjectClassDefinition(
	category = "payment", scope = ExtendedObjectClassDefinition.Scope.GROUP
)
@Meta.OCD(
	id = "com.liferay.commerce.payment.method.remote.internal.configuration.RemoteCommercePaymentMethodConfiguration",
	localization = "content/Language",
	name = "remote-commerce-payment-method-configuration-name"
)
public interface RemoteCommercePaymentMethodConfiguration {

	@Meta.AD(name = "activate-recurring-payment-endpoint-url", required = false)
	public String activateRecurringPaymentEndpointURL();

	@Meta.AD(name = "authorize-payment-endpoint-url", required = false)
	public String authorizePaymentEndpointURL();

	@Meta.AD(name = "cancel-payment-endpoint-url", required = false)
	public String cancelPaymentEndpointURL();

	@Meta.AD(name = "cancel-recurring-payment-endpoint-url", required = false)
	public String cancelRecurringPaymentEndpointURL();

	@Meta.AD(name = "capture-payment-endpoint-url", required = false)
	public String capturePaymentEndpointURL();

	@Meta.AD(name = "complete-payment-endpoint-url", required = false)
	public String completePaymentEndpointURL();

	@Meta.AD(name = "complete-recurring-payment-endpoint-url", required = false)
	public String completeRecurringPaymentEndpointURL();

	@Meta.AD(name = "subscription-validity-endpoint-url", required = false)
	public String subscriptionValidityEndpointURL();

	@Meta.AD(name = "partially-refund-payment-endpoint-url", required = false)
	public String partiallyRefundPaymentEndpointURL();

	@Meta.AD(name = "process-payment-endpoint-url", required = false)
	public String processPaymentEndpointURL();

	@Meta.AD(name = "process-recurring-payment-endpoint-url", required = false)
	public String processRecurringPaymentEndpointURL();

	@Meta.AD(name = "refund-payment-endpoint-url", required = false)
	public String refundPaymentEndpointURL();

	@Meta.AD(name = "suspend-recurring-payment-endpoint-url", required = false)
	public String suspendRecurringPaymentEndpointURL();

	@Meta.AD(name = "void-transaction-endpoint-url", required = false)
	public String voidTransactionEndpointURL();

	@Meta.AD(name = "endpoint-authorization-token", required = false)
	public String endpointAuthorizationToken();

}