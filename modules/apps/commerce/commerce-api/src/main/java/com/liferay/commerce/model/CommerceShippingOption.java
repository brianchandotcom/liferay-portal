/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.model;

import java.math.BigDecimal;

/**
 * @author Andrea Di Giorgi
 * @author Alessio Antonio Rendina
 */
public class CommerceShippingOption {

	public CommerceShippingOption(
		BigDecimal amount, String commerceShippingMethodKey, String key,
		String name, double priority) {

		_amount = amount;
		_commerceShippingMethodKey = commerceShippingMethodKey;
		_key = key;
		_name = name;
		_priority = priority;
	}

	public BigDecimal getAmount() {
		return _amount;
	}

	public String getCommerceShippingMethodKey() {
		return _commerceShippingMethodKey;
	}

	public String getKey() {
		return _key;
	}

	public String getName() {
		return _name;
	}

	public double getPriority() {
		return _priority;
	}

	private final BigDecimal _amount;
	private final String _commerceShippingMethodKey;
	private final String _key;
	private final String _name;
	private final double _priority;

}