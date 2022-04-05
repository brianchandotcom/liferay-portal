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