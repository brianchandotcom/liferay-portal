/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.currency.test.util;

import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.util.ExchangeRateProvider;

import java.math.BigDecimal;

import org.osgi.service.component.annotations.Component;

/**
 * @author Luca Pellizzon
 */
@Component(
	property = "commerce.exchange.provider.key=" + TestExchangeRateProvider.NAME,
	service = ExchangeRateProvider.class
)
public class TestExchangeRateProvider implements ExchangeRateProvider {

	public static final String NAME = "test-exchange";

	@Override
	public BigDecimal getExchangeRate(
			CommerceCurrency primaryCommerceCurrency,
			CommerceCurrency secondaryCommerceCurrency)
		throws Exception {

		String secondaryCommerceCurrencyCode =
			secondaryCommerceCurrency.getCode();

		if (secondaryCommerceCurrencyCode.equals("FAIL")) {
			throw new Exception();
		}

		return BigDecimal.ONE;
	}

}