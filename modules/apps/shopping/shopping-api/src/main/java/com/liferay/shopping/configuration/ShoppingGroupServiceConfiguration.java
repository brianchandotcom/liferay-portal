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

import com.liferay.configuration.admin.ConfigurationAdmin;
import com.liferay.portal.kernel.settings.LocalizedValuesMap;

/**
 * @author Peter Fellwock
 */
@ConfigurationAdmin(category = "other")
@Meta.OCD(
	id = "com.liferay.shopping.configuration.ShoppingGroupServiceConfiguration"
)
public interface ShoppingGroupServiceConfiguration {

	@Meta.AD(deflt = "visa|mastercard|discover|amex", required = false)
	public String[] ccTypes();

	@Meta.AD(deflt = "USD", required = false)
	public String currencyId();

	@Meta.AD(
		deflt = "${server-property://com.liferay.portal/admin.email.from.address}",
		required = false
	)
	public String emailFromAddress();

	@Meta.AD(
		deflt = "${server-property://com.liferay.portal/admin.email.from.name}",
		required = false
	)
	public String emailFromName();

	@Meta.AD(
		deflt = "${resource:com/liferay/shopping/dependencies/email_order_confirmation_body.tmpl}",
		required = false
	)
	public LocalizedValuesMap emailOrderConfirmationBody();

	@Meta.AD(deflt = "true", required = false)
	public boolean emailOrderConfirmationEnabled();

	@Meta.AD(
		deflt = "${resource:com/liferay/shopping/dependencies/email_order_confirmation_subject.tmpl}",
		required = false
	)
	public LocalizedValuesMap emailOrderConfirmationSubject();

	@Meta.AD(
		deflt = "${resource:com/liferay/shopping/dependencies/email_order_shipping_body.tmpl}",
		required = false
	)
	public LocalizedValuesMap emailOrderShippingBody();

	@Meta.AD(deflt = "true", required = false)
	public boolean emailOrderShippingEnabled();

	@Meta.AD(
		deflt = "${resource:com/liferay/shopping/dependencies/email_order_shipping_subject.tmpl}",
		required = false
	)
	public LocalizedValuesMap emailOrderShippingSubject();

	@Meta.AD(required = false)
	public String[] insurance();

	@Meta.AD(deflt = "flat", required = false)
	public String insuranceFormula();

	@Meta.AD(deflt = "0", required = false)
	public double minOrder();

	@Meta.AD(deflt = " ", required = false)
	public String paypalEmailAddress();

	@Meta.AD(required = false)
	public String[] shipping();

	@Meta.AD(deflt = "flat", required = false)
	public String shippingFormula();

	@Meta.AD(deflt = "0", required = false)
	public double taxRate();

	@Meta.AD(deflt = "CA", required = false)
	public String taxState();

}