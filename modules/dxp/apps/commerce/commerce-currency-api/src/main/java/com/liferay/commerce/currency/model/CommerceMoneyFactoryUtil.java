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