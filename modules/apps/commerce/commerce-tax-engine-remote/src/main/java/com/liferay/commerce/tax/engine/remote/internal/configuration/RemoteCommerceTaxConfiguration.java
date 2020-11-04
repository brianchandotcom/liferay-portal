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

package com.liferay.commerce.tax.engine.remote.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Ivica Cardic
 */
@ExtendedObjectClassDefinition(
	category = "tax", scope = ExtendedObjectClassDefinition.Scope.GROUP
)
@Meta.OCD(
	id = "com.liferay.commerce.tax.engine.remote.internal.configuration.RemoteCommerceTaxConfiguration",
	localization = "content/Language",
	name = "remote-commerce-tax-configuration-name"
)
public interface RemoteCommerceTaxConfiguration {

	@Meta.AD(name = "tax-value-endpoint-authorization-token", required = false)
	public String taxValueEndpointAuthorizationToken();

	@Meta.AD(name = "tax-value-endpoint-url", required = false)
	public String taxValueEndpointURL();

}