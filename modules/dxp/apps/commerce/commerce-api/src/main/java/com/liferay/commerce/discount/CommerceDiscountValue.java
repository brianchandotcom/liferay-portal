/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 * <p>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.commerce.discount;

import java.math.BigDecimal;

/**
 * @author Marco Leo
 */
public class CommerceDiscountValue {

	public CommerceDiscountValue(
		long id, BigDecimal discountAmount, BigDecimal discountPercentage,
		BigDecimal[] percentages) {

		_id = id;
		_discountAmount = discountAmount;
		_discountPercentage = discountPercentage;
		_percentages = percentages;
	}

	public BigDecimal getDiscountAmount() {
		return _discountAmount;
	}

	public BigDecimal getDiscountPercentage() {
		return _discountPercentage;
	}

	public long getId() {
		return _id;
	}

	public BigDecimal[] getPercentages() {
		return _percentages;
	}

	private final BigDecimal _discountAmount;
	private final BigDecimal _discountPercentage;
	private final long _id;
	private final BigDecimal[] _percentages;

}