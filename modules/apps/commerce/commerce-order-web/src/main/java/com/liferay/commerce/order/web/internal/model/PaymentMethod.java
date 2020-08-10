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

package com.liferay.commerce.order.web.internal.model;

import com.liferay.commerce.frontend.model.ImageField;

/**
 * @author Alessio Antonio Rendina
 */
public class PaymentMethod {

	public PaymentMethod(
		String description, String paymentMethodKey, ImageField thumbnail,
		String title) {

		_description = description;
		_paymentMethodKey = paymentMethodKey;
		_thumbnail = thumbnail;
		_title = title;
	}

	public String getDescription() {
		return _description;
	}

	public String getPaymentMethodKey() {
		return _paymentMethodKey;
	}

	public ImageField getThumbnail() {
		return _thumbnail;
	}

	public String getTitle() {
		return _title;
	}

	private final String _description;
	private final String _paymentMethodKey;
	private final ImageField _thumbnail;
	private final String _title;

}