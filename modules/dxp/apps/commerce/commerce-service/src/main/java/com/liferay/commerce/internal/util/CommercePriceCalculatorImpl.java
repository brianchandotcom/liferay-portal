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

import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.util.CommercePriceCalculator;
import com.liferay.portal.kernel.exception.PortalException;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alessio Antonio Rendina
 */
@Component(immediate = true)
public class CommercePriceCalculatorImpl implements CommercePriceCalculator {

	@Override
	public double getPrice(CommerceOrderItem commerceOrderItem)
		throws PortalException {

		return getPrice(
			commerceOrderItem.getCPInstance(), commerceOrderItem.getQuantity());
	}

	@Override
	public double getPrice(CPInstance cpInstance, int quantity) {
		return cpInstance.getPrice() * quantity;
	}

	@Override
	public double getSubtotal(CommerceOrder commerceOrder)
		throws PortalException {

		double subtotal = 0;

		if (commerceOrder == null) {
			return subtotal;
		}

		for (CommerceOrderItem commerceOrderItem :
				commerceOrder.getCommerceOrderItems()) {

			subtotal += getPrice(commerceOrderItem);
		}

		return subtotal;
	}

}