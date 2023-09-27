/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Alec Sloan
 */
@ExtendedObjectClassDefinition(
	category = "orders", scope = ExtendedObjectClassDefinition.Scope.GROUP
)
@Meta.OCD(
	id = "com.liferay.commerce.configuration.CommerceOrderCheckoutConfiguration",
	localization = "content/Language",
	name = "order-checkout-configuration-name"
)
public interface CommerceOrderCheckoutConfiguration {

	@Meta.AD(
		deflt = "false", name = "checkout-requested-delivery-date-enabled",
		required = false
	)
	public boolean checkoutRequestedDeliveryDateEnabled();

	@Meta.AD(deflt = "false", name = "guest-checkout-enabled", required = false)
	public boolean guestCheckoutEnabled();

	@Meta.AD(
		deflt = "false", name = "hide-shipping-price-zero", required = false
	)
	public boolean hideShippingPriceZero();

}