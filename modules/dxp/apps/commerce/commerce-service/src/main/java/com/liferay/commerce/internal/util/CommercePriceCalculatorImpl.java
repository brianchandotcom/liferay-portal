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

package com.liferay.commerce.internal.util;

import com.liferay.commerce.model.CommerceCart;
import com.liferay.commerce.model.CommerceCartItem;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.util.CommercePriceCalculator;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alessio Antonio Rendina
 */
@Component(immediate = true)
public class CommercePriceCalculatorImpl implements CommercePriceCalculator {

	@Override
	public double getPrice(CommerceCartItem commerceCartItem) {
		return getPrice(
			commerceCartItem.fetchCPInstance(), commerceCartItem.getQuantity());
	}

	@Override
	public double getPrice(CPInstance cpInstance, int quantity) {
		if (cpInstance == null) {
			return 0;
		}

		return cpInstance.getPrice() * quantity;
	}

	@Override
	public double getSubtotal(CommerceCart commerceCart) {
		double subtotal = 0;

		if (commerceCart == null) {
			return subtotal;
		}

		for (CommerceCartItem commerceCartItem :
				commerceCart.getCommerceCartItems()) {

			subtotal += getPrice(commerceCartItem);
		}

		return subtotal;
	}

}