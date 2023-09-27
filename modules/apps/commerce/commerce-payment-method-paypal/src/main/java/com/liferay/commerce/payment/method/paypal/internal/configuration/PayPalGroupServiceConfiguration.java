/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.payment.method.paypal.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Luca Pellizzon
 */
@ExtendedObjectClassDefinition(
	category = "payment", scope = ExtendedObjectClassDefinition.Scope.GROUP
)
@Meta.OCD(
	id = "com.liferay.commerce.payment.method.paypal.internal.configuration.PayPalGroupServiceConfiguration",
	localization = "content/Language",
	name = "commerce-payment-method-paypal-group-service-configuration-name"
)
public interface PayPalGroupServiceConfiguration {

	@Meta.AD(name = "client-id", required = false)
	public String clientId();

	@Meta.AD(
		name = "client-secret", required = false, type = Meta.Type.Password
	)
	public String clientSecret();

	@Meta.AD(name = "mode", required = false)
	public String mode();

	@Meta.AD(deflt = "0", name = "payment-attempts-max-count", required = false)
	public String paymentAttemptsMaxCount();

	@Meta.AD(name = "request-details", required = false)
	public String requestDetails();

}