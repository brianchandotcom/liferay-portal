/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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