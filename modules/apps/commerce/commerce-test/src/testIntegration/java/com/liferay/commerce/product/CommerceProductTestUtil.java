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

package com.liferay.commerce.product;

import com.liferay.commerce.product.option.CommerceOptionValue;

import java.math.BigDecimal;

/**
 * @author Igor Beslic
 */
public class CommerceProductTestUtil {

	public static CommerceOptionValue getCommerceOptionValue(
		long cpInstanceId, String optionKey, String optionValueKey,
		BigDecimal price, String priceType, int quantity) {

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