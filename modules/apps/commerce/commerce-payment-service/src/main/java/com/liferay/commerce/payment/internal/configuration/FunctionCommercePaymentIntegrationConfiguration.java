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
	id = "com.liferay.commerce.payment.internal.configuration.FunctionCommercePaymentIntegrationConfiguration"
)
public interface FunctionCommercePaymentIntegrationConfiguration {

	@Meta.AD(type = Meta.Type.String)
	public String authorizePath();

	@Meta.AD(type = Meta.Type.String)
	public String cancelPath();

	@Meta.AD(type = Meta.Type.String)
	public String capturePath();

	@Meta.AD(name = "key")
	public String key();

	@Meta.AD(type = Meta.Type.String)
	public String oAuth2ApplicationExternalReferenceCode();

	@Meta.AD(type = Meta.Type.String)
	public String refundPath();

	@Meta.AD(type = Meta.Type.Integer)
	public int type();

	@Meta.AD(required = false, type = Meta.Type.String)
	public String typeSettings();

}