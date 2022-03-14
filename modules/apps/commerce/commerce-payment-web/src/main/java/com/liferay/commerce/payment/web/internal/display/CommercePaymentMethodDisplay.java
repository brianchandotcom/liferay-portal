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

package com.liferay.commerce.payment.web.internal.display;

import com.liferay.commerce.payment.method.CommercePaymentMethod;

import java.util.Locale;

/**
 * @author Andrea Sbarra
 */
public class CommercePaymentMethodDisplay {

	public CommercePaymentMethodDisplay(
		CommercePaymentMethod commercePaymentMethod, Locale locale) {

		_commercePaymentMethodKey = commercePaymentMethod.getKey();
		_name = commercePaymentMethod.getName(locale);
	}

	public String getCommercePaymentMethodKey() {
		return _commercePaymentMethodKey;
	}

	public String getName() {
		return _name;
	}

	private final String _commercePaymentMethodKey;
	private final String _name;

}