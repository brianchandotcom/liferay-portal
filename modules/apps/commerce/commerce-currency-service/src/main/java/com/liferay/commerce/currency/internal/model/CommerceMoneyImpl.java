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

package com.liferay.commerce.currency.internal.model;

import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.model.CommerceMoney;
import com.liferay.commerce.currency.util.CommercePriceFormatter;
import com.liferay.portal.kernel.exception.PortalException;

import java.math.BigDecimal;

import java.util.Locale;

/**
 * @author Marco Leo
 */
public class CommerceMoneyImpl implements CommerceMoney {

	public CommerceMoneyImpl(
		CommerceCurrency commerceCurrency,
		CommercePriceFormatter commercePriceFormatter, BigDecimal price) {

		_commerceCurrency = commerceCurrency;
		_commercePriceFormatter = commercePriceFormatter;
		_price = price;
	}

	@Override
	public String format(Locale locale) throws PortalException {
		BigDecimal price = getPrice();

		if (price == null) {
			price = BigDecimal.ZERO;
		}

		return _commercePriceFormatter.format(
			getCommerceCurrency(), price, locale);
	}

	@Override
	public CommerceCurrency getCommerceCurrency() {
		return _commerceCurrency;
	}

	@Override
	public BigDecimal getPrice() {
		return _price;
	}

	protected CommercePriceFormatter getCommercePriceFormatter() {
		return _commercePriceFormatter;
	}

	private final CommerceCurrency _commerceCurrency;
	private final CommercePriceFormatter _commercePriceFormatter;
	private final BigDecimal _price;

}