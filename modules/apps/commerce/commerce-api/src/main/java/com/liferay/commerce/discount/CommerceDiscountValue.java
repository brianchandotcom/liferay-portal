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

package com.liferay.commerce.discount;

import com.liferay.commerce.currency.model.CommerceMoney;

import java.math.BigDecimal;

/**
 * @author Marco Leo
 * @author Alec Sloan
 */
public class CommerceDiscountValue {

	public CommerceDiscountValue(
		long id, CommerceMoney discountAmount, BigDecimal discountPercentage,
		BigDecimal[] percentages) {

		_id = id;
		_discountAmount = discountAmount;
		_discountPercentage = discountPercentage;
		_percentages = percentages.clone();
	}

	public CommerceMoney getDiscountAmount() {
		return _discountAmount;
	}

	public BigDecimal getDiscountPercentage() {
		return _discountPercentage;
	}

	public long getId() {
		return _id;
	}

	public BigDecimal[] getPercentages() {
		return _percentages.clone();
	}

	private final CommerceMoney _discountAmount;
	private final BigDecimal _discountPercentage;
	private final long _id;
	private final BigDecimal[] _percentages;

}