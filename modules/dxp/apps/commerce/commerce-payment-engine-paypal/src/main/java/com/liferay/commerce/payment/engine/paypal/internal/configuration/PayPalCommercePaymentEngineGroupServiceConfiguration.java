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

package com.liferay.commerce.payment.engine.paypal.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Andrea Di Giorgi
 */
@ExtendedObjectClassDefinition(
	category = "commerce", scope = ExtendedObjectClassDefinition.Scope.GROUP
)
@Meta.OCD(
	id = "com.liferay.commerce.payment.engine.paypal.internal.configuration.PayPalCommercePaymentEngineGroupServiceConfiguration",
	localization = "content/Language",
	name = "commerce-payment-engine-paypal-group-service-configuration-name"
)
public interface PayPalCommercePaymentEngineGroupServiceConfiguration {

	@Meta.AD(name = "client-id", required = false)
	public String clientId();

	@Meta.AD(name = "mode", required = false)
	public String mode();

	@Meta.AD(name = "client-secret", required = false)
	public String clientSecret();

}