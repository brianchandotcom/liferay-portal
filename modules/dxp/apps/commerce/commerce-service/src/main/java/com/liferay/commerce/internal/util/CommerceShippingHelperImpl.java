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
	public Dimensions getDimensions(List<CommerceOrderItem> commerceOrderItems)
		throws PortalException {

		if (commerceOrderItems.size() == 1) {
			CommerceOrderItem commerceOrderItem = commerceOrderItems.get(0);

			if (commerceOrderItem.getQuantity() == 1) {
				return getDimensions(commerceOrderItem.getCPInstance());
			}
		}

		double maxWidth = 0;
		double maxHeight = 0;
		double maxDepth = 0;
		double volume = 0;

		for (CommerceOrderItem commerceOrderItem : commerceOrderItems) {
			Dimensions dimensions = getDimensions(
				commerceOrderItem.getCPInstance());

			double width = dimensions.getWidth();
			double height = dimensions.getHeight();
			double depth = dimensions.getDepth();

			maxWidth = Math.max(maxWidth, width);
			maxHeight = Math.max(maxHeight, height);
			maxDepth = Math.max(maxDepth, depth);

			volume += width * height * depth * commerceOrderItem.getQuantity();
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
	public double getPrice(List<CommerceOrderItem> commerceOrderItems)
		throws PortalException {

		double price = 0;

		for (CommerceOrderItem commerceOrderItem : commerceOrderItems) {
			price += _commercePriceCalculator.getPrice(commerceOrderItem);
		}

		return price;
	}

	@Override
	public double getWeight(CPInstance cpInstance) {
		return cpInstance.getWeight();
	}

	@Override
	public double getWeight(List<CommerceOrderItem> commerceOrderItems)
		throws PortalException {

		double weight = 0;

		for (CommerceOrderItem commerceOrderItem : commerceOrderItems) {
			weight +=
				getWeight(commerceOrderItem.getCPInstance()) *
					commerceOrderItem.getQuantity();
		}

		return weight;
	}

	@Override
	public boolean isShippable(CommerceOrder commerceOrder)
		throws PortalException {

		for (CommerceOrderItem commerceOrderItem :
				commerceOrder.getCommerceOrderItems()) {

			CPDefinition cpDefinition = commerceOrderItem.getCPDefinition();

			if (cpDefinition.isShippable()) {
				return true;
			}
		}

		return false;
	}

	@Reference
	private CommercePriceCalculator _commercePriceCalculator;

}