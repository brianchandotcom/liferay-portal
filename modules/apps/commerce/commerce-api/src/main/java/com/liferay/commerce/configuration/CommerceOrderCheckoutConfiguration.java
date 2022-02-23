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
		deflt = "false", name = "view-delivery-term-checkout-step-enabled",
		required = false
	)
	public boolean viewDeliveryTermCheckoutStepEnabled();

	@Meta.AD(
		deflt = "false", name = "view-payment-term-checkout-step-enabled",
		required = false
	)
	public boolean viewPaymentTermCheckoutStepEnabled();

}