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
	id = "com.liferay.commerce.shipping.engine.internal.configuration.FunctionCommerceShippingEngineConfiguration"
)
public interface FunctionCommerceShippingEngineConfiguration {

	@Meta.AD(type = Meta.Type.String)
	public String shippingEngineOptionLabelPath();

	@Meta.AD(type = Meta.Type.String)
	public String shippingEngineLocalizedNamePath();

	@Meta.AD(type = Meta.Type.String)
	public String shippingEngineLocalizedDescriptionPath();

	@Meta.AD(type = Meta.Type.String)
	public String shippingEngineOptionsPath();

	@Meta.AD(type = Meta.Type.String)
	public String enabledShippingEngineOptionsPath();

	@Meta.AD(name = "key")
	public String key();

	@Meta.AD(type = Meta.Type.String)
	public String oAuth2ApplicationExternalReferenceCode();

	@Meta.AD(required = false, type = Meta.Type.String)
	public String shippingEngineTypeSettings();

}