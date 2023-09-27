/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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