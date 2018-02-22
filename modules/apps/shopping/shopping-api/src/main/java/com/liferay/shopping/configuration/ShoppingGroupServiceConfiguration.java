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

package com.liferay.shopping.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.kernel.settings.LocalizedValuesMap;

/**
 * @author Peter Fellwock
 */
@ExtendedObjectClassDefinition(
	category = "shopping", scope = ExtendedObjectClassDefinition.Scope.GROUP
)
@Meta.OCD(
	id = "com.liferay.shopping.configuration.ShoppingGroupServiceConfiguration",
	localization = "content/Language",
	name = "shopping-group-service-configuration-name"
)
public interface ShoppingGroupServiceConfiguration {

	@Meta.AD(
		deflt = "visa|mastercard|discover|amex", name = "cc-types",
		required = false
	)
	public String[] ccTypes();

	@Meta.AD(deflt = "USD", name = "currency-id", required = false)
	public String currencyId();

	@Meta.AD(
		deflt = "${server-property://com.liferay.portal/admin.email.from.address}",
		name = "email-from-address", required = false
	)
	public String emailFromAddress();

	@Meta.AD(
		deflt = "${server-property://com.liferay.portal/admin.email.from.name}",
		name = "email-from-name", required = false
	)
	public String emailFromName();

	@Meta.AD(
		deflt = "${resource:com/liferay/shopping/dependencies/email_order_confirmation_body.tmpl}",
		name = "email-order-confirmation-body", required = false
	)
	public LocalizedValuesMap emailOrderConfirmationBody();

	@Meta.AD(
		deflt = "true", name = "email-order-confirmation-enabled",
		required = false
	)
	public boolean emailOrderConfirmationEnabled();

	@Meta.AD(
		deflt = "${resource:com/liferay/shopping/dependencies/email_order_confirmation_subject.tmpl}",
		name = "email-order-confirmation-subject", required = false
	)
	public LocalizedValuesMap emailOrderConfirmationSubject();

	@Meta.AD(
		deflt = "${resource:com/liferay/shopping/dependencies/email_order_shipping_body.tmpl}",
		name = "email-order-shipping-body", required = false
	)
	public LocalizedValuesMap emailOrderShippingBody();

	@Meta.AD(
		deflt = "true", name = "email-order-shipping-enabled", required = false
	)
	public boolean emailOrderShippingEnabled();

	@Meta.AD(
		deflt = "${resource:com/liferay/shopping/dependencies/email_order_shipping_subject.tmpl}",
		name = "email-order-shipping-subject", required = false
	)
	public LocalizedValuesMap emailOrderShippingSubject();

	@Meta.AD(name = "insurance", required = false)
	public String[] insurance();

	@Meta.AD(deflt = "flat", name = "insurance-formula", required = false)
	public String insuranceFormula();

	@Meta.AD(deflt = "0", name = "min-order", required = false)
	public double minOrder();

	@Meta.AD(deflt = " ", name = "paypal-email-address", required = false)
	public String paypalEmailAddress();

	@Meta.AD(name = "shipping", required = false)
	public String[] shipping();

	@Meta.AD(deflt = "flat", name = "shipping-formula", required = false)
	public String shippingFormula();

	@Meta.AD(deflt = "0", name = "tax-rate", required = false)
	public double taxRate();

	@Meta.AD(deflt = "CA", name = "tax-state", required = false)
	public String taxState();

}