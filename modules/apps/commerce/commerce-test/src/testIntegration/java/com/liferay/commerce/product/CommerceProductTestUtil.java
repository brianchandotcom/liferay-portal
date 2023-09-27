/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product;

import com.liferay.commerce.product.option.CommerceOptionValue;

import java.math.BigDecimal;

/**
 * @author Igor Beslic
 */
public class CommerceProductTestUtil {

	public static CommerceOptionValue getCommerceOptionValue(
		long cpInstanceId, String optionKey, String optionValueKey,
		BigDecimal price, String priceType, BigDecimal quantity) {

		CommerceOptionValue.Builder builder = new CommerceOptionValue.Builder();

		builder.cpInstanceId(cpInstanceId);
		builder.optionKey(optionKey);
		builder.optionValueKey(optionValueKey);
		builder.price(price);
		builder.priceType(priceType);
		builder.quantity(quantity);

		return builder.build();
	}

}