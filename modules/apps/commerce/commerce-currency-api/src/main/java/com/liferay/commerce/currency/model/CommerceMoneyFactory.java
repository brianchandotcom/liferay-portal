/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.currency.model;

import com.liferay.commerce.currency.util.PriceFormat;
import com.liferay.portal.kernel.exception.PortalException;

import java.math.BigDecimal;

/**
 * @author Marco Leo
 */
public interface CommerceMoneyFactory {

	public CommerceMoney create(
		CommerceCurrency commerceCurrency, BigDecimal price);

	public CommerceMoney create(
		CommerceCurrency commerceCurrency, BigDecimal price,
		PriceFormat priceFormat);

	public CommerceMoney create(long commerceCurrencyId, BigDecimal price)
		throws PortalException;

	public CommerceMoney emptyCommerceMoney();

	public CommerceMoney priceOnApplicationCommerceMoney();

}