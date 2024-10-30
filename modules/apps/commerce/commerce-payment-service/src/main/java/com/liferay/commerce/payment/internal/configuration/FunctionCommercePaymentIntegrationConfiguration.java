/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.payment.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Luca Pellizzon
 */
@ExtendedObjectClassDefinition(
	category = "payment", factoryInstanceLabelAttribute = "key",
	scope = ExtendedObjectClassDefinition.Scope.SYSTEM
)
@Meta.OCD(
	factory = true,
	id = "com.liferay.commerce.payment.internal.configuration.FunctionCommercePaymentIntegrationConfiguration",
	name = "commerce-payment-integration-configuration-name"
)
public interface FunctionCommercePaymentIntegrationConfiguration {

	@Meta.AD(name = "key", required = false)
	public String key();

	@Meta.AD(name = "name", required = false)
	public String name();

	@Meta.AD(
		name = "oauth2-application-external-reference-code", required = false,
		type = Meta.Type.String
	)
	public String oAuth2ApplicationExternalReferenceCode();

	@Meta.AD(
		name = "payment-integration-type", required = false,
		type = Meta.Type.Integer
	)
	public int paymentIntegrationType();

	@Meta.AD(
		name = "payment-integration-type-settings", required = false,
		type = Meta.Type.String
	)
	public String paymentIntegrationTypeSettings();

}