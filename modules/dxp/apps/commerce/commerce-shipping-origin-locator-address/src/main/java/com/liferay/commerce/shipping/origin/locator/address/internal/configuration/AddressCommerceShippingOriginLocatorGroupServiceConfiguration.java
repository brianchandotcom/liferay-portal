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

package com.liferay.commerce.shipping.origin.locator.address.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Andrea Di Giorgi
 */
@ExtendedObjectClassDefinition(
	category = "commerce", scope = ExtendedObjectClassDefinition.Scope.GROUP
)
@Meta.OCD(
	id = "com.liferay.commerce.shipping.origin.locator.address.internal.configuration.AddressCommerceShippingOriginLocatorGroupServiceConfiguration",
	localization = "content/Language",
	name = "commerce-shipping-origin-locator-address-group-service-configuration-name"
)
public interface AddressCommerceShippingOriginLocatorGroupServiceConfiguration {

	@Meta.AD(name = "name", required = false)
	public String name();

	@Meta.AD(name = "city", required = false)
	public String city();

	@Meta.AD(name = "zip", required = false)
	public String zip();

	@Meta.AD(name = "commerce-country-id", required = false)
	public long commerceCountryId();

	@Meta.AD(name = "commerce-region-id", required = false)
	public long commerceRegionId();

	@Meta.AD(name = "latitude", required = false)
	public double latitude();

	@Meta.AD(name = "longitude", required = false)
	public double longitude();

	@Meta.AD(name = "street1", required = false)
	public String street1();

	@Meta.AD(name = "street2", required = false)
	public String street2();

	@Meta.AD(name = "street3", required = false)
	public String street3();

}