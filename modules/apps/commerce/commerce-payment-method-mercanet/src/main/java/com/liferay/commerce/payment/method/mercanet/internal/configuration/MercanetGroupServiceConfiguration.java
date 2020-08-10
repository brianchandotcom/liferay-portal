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

package com.liferay.commerce.payment.method.mercanet.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Luca Pellizzon
 */
@ExtendedObjectClassDefinition(
	category = "payment", scope = ExtendedObjectClassDefinition.Scope.GROUP
)
@Meta.OCD(
	id = "com.liferay.commerce.payment.method.mercanet.internal.configuration.MercanetGroupServiceConfiguration",
	localization = "content/Language",
	name = "commerce-payment-method-mercanet-group-service-configuration-name"
)
public interface MercanetGroupServiceConfiguration {

	@Meta.AD(name = "merchant-id", required = false)
	public String merchantId();

	@Meta.AD(name = "environment", required = false)
	public String environment();

	@Meta.AD(name = "secret-key", required = false)
	public String secretKey();

	@Meta.AD(name = "key-version", required = false)
	public String keyVersion();

}