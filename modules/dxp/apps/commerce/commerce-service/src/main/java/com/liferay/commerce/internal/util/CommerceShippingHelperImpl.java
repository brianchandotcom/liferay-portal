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
import com.liferay.commerce.model.Dimensions;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.util.CommercePriceCalculator;
import com.liferay.commerce.util.CommerceShippingHelper;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Di Giorgi
 */
@Component(immediate = true)
public class CommerceShippingHelperImpl implements CommerceShippingHelper {

	@Override
	public Dimensions getDimensions(CPInstance cpInstance) {
		return new Dimensions(
			cpInstance.getWidth(), cpInstance.getHeight(),
			cpInstance.getDepth());
	}

	@Override
	public Dimensions getDimensions(List<CommerceCartItem> commerceCartItems) {
		if (commerceCartItems.size() == 1) {
			CommerceCartItem commerceCartItem = commerceCartItems.get(0);

			if (commerceCartItem.getQuantity() == 1) {
				return getDimensions(commerceCartItem.fetchCPInstance());
			}
		}

		double maxWidth = 0;
		double maxHeight = 0;
		double maxDepth = 0;
		double volume = 0;

		for (CommerceCartItem commerceCartItem : commerceCartItems) {
			Dimensions dimensions = getDimensions(
				commerceCartItem.fetchCPInstance());

			double width = dimensions.getWidth();
			double height = dimensions.getHeight();
			double depth = dimensions.getDepth();

			maxWidth = Math.max(maxWidth, width);
			maxHeight = Math.max(maxHeight, height);
			maxDepth = Math.max(maxDepth, depth);

			volume += width * height * depth * commerceCartItem.getQuantity();
		}

		double width = Math.cbrt(volume);

		double height = width;
		double depth = width;

		width = Math.max(maxWidth, width);

		height = Math.max(maxHeight, height);
		depth = Math.max(maxDepth, depth);

		return new Dimensions(width, height, depth);
	}

	@Override
	public double getPrice(CPInstance cpInstance) {
		return _commercePriceCalculator.getPrice(cpInstance, 1);
	}

	@Override
	public double getPrice(List<CommerceCartItem> commerceCartItems) {
		double price = 0;

		for (CommerceCartItem commerceCartItem : commerceCartItems) {
			price += _commercePriceCalculator.getPrice(commerceCartItem);
		}

		return price;
	}

	@Override
	public double getWeight(CPInstance cpInstance) {
		return cpInstance.getWeight();
	}

	@Override
	public double getWeight(List<CommerceCartItem> commerceCartItems) {
		double weight = 0;

		for (CommerceCartItem commerceCartItem : commerceCartItems) {
			weight +=
				getWeight(commerceCartItem.fetchCPInstance()) *
					commerceCartItem.getQuantity();
		}

		return weight;
	}

	@Override
	public boolean isShippable(CommerceCart commerceCart)
		throws PortalException {

		for (CommerceCartItem commerceCartItem :
				commerceCart.getCommerceCartItems()) {

			CPDefinition cpDefinition = commerceCartItem.getCPDefinition();

			if (cpDefinition.isShippable()) {
				return true;
			}
		}

		return false;
	}

	@Reference
	private CommercePriceCalculator _commercePriceCalculator;

}