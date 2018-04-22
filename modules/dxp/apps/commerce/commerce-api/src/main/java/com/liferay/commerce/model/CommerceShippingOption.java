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
 */
public class CommerceShippingOption {

	public CommerceShippingOption(
		String name, String label, BigDecimal amount) {

		_name = name;
		_label = label;
		_amount = amount;
	}

	public BigDecimal getAmount() {
		return _amount;
	}

	public String getLabel() {
		return _label;
	}

	public String getName() {
		return _name;
	}

	private final BigDecimal _amount;
	private final String _label;
	private final String _name;

}