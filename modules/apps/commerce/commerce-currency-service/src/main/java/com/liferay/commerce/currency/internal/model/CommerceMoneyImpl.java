/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

		_priceOnApplication = false;
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

	@Override
	public boolean isEmpty() {
		if (_price == null) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isPriceOnApplication() {
		return _priceOnApplication;
	}

	protected CommercePriceFormatter getCommercePriceFormatter() {
		return _commercePriceFormatter;
	}

	private final CommerceCurrency _commerceCurrency;
	private final CommercePriceFormatter _commercePriceFormatter;
	private final BigDecimal _price;
	private final boolean _priceOnApplication;

}