/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.shipping.engine.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Luca Pellizzon
 */
@ExtendedObjectClassDefinition(
	category = "shipping", factoryInstanceLabelAttribute = "key",
	scope = ExtendedObjectClassDefinition.Scope.SYSTEM
)
@Meta.OCD(
	factory = true,
	id = "com.liferay.commerce.shipping.engine.internal.configuration.FunctionCommerceShippingEngineConfiguration",
	name = "commerce-shipping-engine-configuration-name"
)
public interface FunctionCommerceShippingEngineConfiguration {

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
		name = "shipping-engine-type-settings", required = false,
		type = Meta.Type.String
	)
	public String shippingEngineTypeSettings();

}