/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.currency.model;

import com.liferay.portal.kernel.exception.PortalException;

import java.math.BigDecimal;

/**
 * @author Marco Leo
 */
public class CommerceMoneyFactoryUtil {

	public static CommerceMoney create(
		CommerceCurrency commerceCurrency, BigDecimal price) {

		return _commerceMoneyFactory.create(commerceCurrency, price);
	}

	public static CommerceMoney create(
			long commerceCurrencyId, BigDecimal price)
		throws PortalException {

		return _commerceMoneyFactory.create(commerceCurrencyId, price);
	}

	public static void setCommerceMoneyFactory(
		CommerceMoneyFactory commerceMoneyFactory) {

		_commerceMoneyFactory = commerceMoneyFactory;
	}

	private static CommerceMoneyFactory _commerceMoneyFactory;

}