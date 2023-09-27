/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.currency.util;

/**
 * @author Marco Leo
 */
public interface ExchangeRateProviderRegistry {

	public ExchangeRateProvider getExchangeRateProvider(String key);

	public Iterable<String> getExchangeRateProviderKeys();

}