/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.currency.util;

import com.liferay.commerce.currency.model.CommerceCurrency;

import java.math.BigDecimal;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
public interface ExchangeRateProvider {

	public BigDecimal getExchangeRate(
			CommerceCurrency primaryCommerceCurrency,
			CommerceCurrency secondaryCommerceCurrency)
		throws Exception;

}