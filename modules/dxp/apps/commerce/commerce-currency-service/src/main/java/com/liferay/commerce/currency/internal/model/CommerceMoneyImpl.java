/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.currency.internal.model;

import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.model.CommerceMoney;
import com.liferay.commerce.currency.util.CommercePriceFormatter;

import java.math.BigDecimal;

/**
 * @author Marco Leo
 */
public class CommerceMoneyImpl implements CommerceMoney {

	public CommerceMoneyImpl(CommercePriceFormatter commercePriceFormatter) {
		_commercePriceFormatter = commercePriceFormatter;
	}

	public CommerceCurrency getCommerceCurrency() {
		return _commerceCurrency;
	}

	public BigDecimal getPrice() {
		return _price;
	}

	public void setCommerceCurrency(CommerceCurrency commerceCurrency) {
		_commerceCurrency = commerceCurrency;
	}

	public void setPrice(BigDecimal price) {
		_price = price;
	}

	@Override
	public String toString() {
		return _commercePriceFormatter.format(
			getCommerceCurrency(), getPrice());
	}

	private CommerceCurrency _commerceCurrency;
	private final CommercePriceFormatter _commercePriceFormatter;
	private BigDecimal _price;

}