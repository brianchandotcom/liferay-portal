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