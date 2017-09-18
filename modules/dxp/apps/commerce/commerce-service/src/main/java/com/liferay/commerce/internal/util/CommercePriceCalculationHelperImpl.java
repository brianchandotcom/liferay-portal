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
import com.liferay.commerce.product.service.CPDefinitionService;
import com.liferay.commerce.product.service.CPInstanceService;
import com.liferay.commerce.util.CommercePriceCalculationHelper;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(immediate = true, service = CommercePriceCalculationHelper.class)
public class CommercePriceCalculationHelperImpl
	implements CommercePriceCalculationHelper {

	@Override
	public double getPrice(CommerceCartItem commerceCartItem)
		throws PortalException {

		return getPrice(
			commerceCartItem.getCPInstanceId(), commerceCartItem.getQuantity());
	}

	@Override
	public double getPrice(long cpInstanceId, int quantity)
		throws PortalException {

		double price = 0;

		if (cpInstanceId > 0) {
			CPInstance cpInstance = _cpInstanceService.fetchCPInstance(
				cpInstanceId);

			if (cpInstance != null) {
				price = cpInstance.getPrice();
			}
		}

		return price * quantity;
	}

	@Override
	public double getTotal(CommerceCart commerceCart) throws PortalException {
		double total = 0;

		if (commerceCart == null) {
			return total;
		}

		List<CommerceCartItem> commerceCartItems =
			commerceCart.getCommerceCartItems();

		for (CommerceCartItem commerceCartItem : commerceCartItems) {
			double price = getPrice(commerceCartItem);

			total += price;
		}

		return total;
	}

	@Reference
	private CPDefinitionService _cpDefinitionService;

	@Reference
	private CPInstanceService _cpInstanceService;

}