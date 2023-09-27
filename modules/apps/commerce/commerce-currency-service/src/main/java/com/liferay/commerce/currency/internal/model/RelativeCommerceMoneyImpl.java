/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.currency.internal.model;

import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.util.CommercePriceFormatter;

import java.math.BigDecimal;

import java.util.Locale;

/**
 * @author Matija Petanjek
 */
public class RelativeCommerceMoneyImpl extends CommerceMoneyImpl {

	public RelativeCommerceMoneyImpl(
		CommerceCurrency commerceCurrency,
		CommercePriceFormatter commercePriceFormatter, BigDecimal price) {

		super(commerceCurrency, commercePriceFormatter, price);
	}

	@Override
	public String format(Locale locale) {
		BigDecimal price = getPrice();

		if (price == null) {
			price = BigDecimal.ZERO;
		}

		CommercePriceFormatter commercePriceFormatter =
			getCommercePriceFormatter();

		return commercePriceFormatter.formatAsRelative(
			getCommerceCurrency(), price, locale);
	}

}