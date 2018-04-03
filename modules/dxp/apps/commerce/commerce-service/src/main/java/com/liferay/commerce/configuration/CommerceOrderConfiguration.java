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

package com.liferay.commerce.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Marco Leo
 */
@ExtendedObjectClassDefinition(category = "commerce")
@Meta.OCD(
	id = "com.liferay.commerce.configuration.CommerceOrderConfiguration",
	localization = "content/Language",
	name = "commerce-order-group-service-configuration-name"
)
public interface CommerceOrderConfiguration {

	@Meta.AD(
		deflt = "10000", name = "commerce-order-guest-cart-max-allowed",
		required = false
	)
	public int guestCartMaxAllowed();

	@Meta.AD(
		deflt = "1000", name = "commerce-order-guest-cart-item-max-allowed",
		required = false
	)
	public int guestCartItemMaxAllowed();

}