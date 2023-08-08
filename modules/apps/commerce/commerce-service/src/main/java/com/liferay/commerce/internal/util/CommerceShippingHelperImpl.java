/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.util;

import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.model.Dimensions;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.util.CommerceShippingHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.BigDecimalUtil;

import java.math.BigDecimal;

import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Andrea Di Giorgi
 * @author Alessio Antonio Rendina
 */
@Component(service = CommerceShippingHelper.class)
public class CommerceShippingHelperImpl implements CommerceShippingHelper {

	@Override
	public Dimensions getDimensions(CommerceOrderItem commerceOrderItem) {
		if (commerceOrderItem == null) {
			return new Dimensions(0, 0, 0);
		}

		return new Dimensions(
			commerceOrderItem.getWidth(), commerceOrderItem.getHeight(),
			commerceOrderItem.getDepth());
	}

	@Override
	public Dimensions getDimensions(CPInstance cpInstance) {
		if (cpInstance == null) {
			return new Dimensions(0, 0, 0);
		}

		return new Dimensions(
			cpInstance.getWidth(), cpInstance.getHeight(),
			cpInstance.getDepth());
	}

	@Override
	public Dimensions getDimensions(List<CommerceOrderItem> commerceOrderItems)
		throws PortalException {

		if (commerceOrderItems.size() == 1) {
			CommerceOrderItem commerceOrderItem = commerceOrderItems.get(0);

			if (BigDecimalUtil.eq(
					commerceOrderItem.getQuantity(), BigDecimal.ONE)) {

				return getDimensions(commerceOrderItem);
			}
		}

		double maxWidth = 0;
		double maxHeight = 0;
		double maxDepth = 0;
		double volume = 0;

		for (CommerceOrderItem commerceOrderItem : commerceOrderItems) {
			if (!commerceOrderItem.isShippable() ||
				commerceOrderItem.isFreeShipping()) {

				continue;
			}

			Dimensions dimensions = getDimensions(commerceOrderItem);

			double width = dimensions.getWidth();
			double height = dimensions.getHeight();
			double depth = dimensions.getDepth();

			maxWidth = Math.max(maxWidth, width);
			maxHeight = Math.max(maxHeight, height);
			maxDepth = Math.max(maxDepth, depth);

			BigDecimal quantity = commerceOrderItem.getQuantity();

			volume += width * height * depth * quantity.intValue();
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
	public double getWeight(CommerceOrderItem commerceOrderItem) {
		if (commerceOrderItem == null) {
			return 0;
		}

		return commerceOrderItem.getWeight();
	}

	@Override
	public double getWeight(CPInstance cpInstance) {
		if (cpInstance == null) {
			return 0;
		}

		return cpInstance.getWeight();
	}

	@Override
	public double getWeight(List<CommerceOrderItem> commerceOrderItems)
		throws PortalException {

		double weight = 0;

		for (CommerceOrderItem commerceOrderItem : commerceOrderItems) {
			if (!commerceOrderItem.isShippable() ||
				commerceOrderItem.isFreeShipping()) {

				continue;
			}

			BigDecimal quantity = commerceOrderItem.getQuantity();

			weight += getWeight(commerceOrderItem) * quantity.intValue();
		}

		return weight;
	}

	@Override
	public boolean isFreeShipping(CommerceOrder commerceOrder)
		throws PortalException {

		for (CommerceOrderItem commerceOrderItem :
				commerceOrder.getCommerceOrderItems()) {

			if (!commerceOrderItem.isFreeShipping()) {
				return false;
			}
		}

		return true;
	}

	@Override
	public boolean isShippable(CommerceOrder commerceOrder)
		throws PortalException {

		for (CommerceOrderItem commerceOrderItem :
				commerceOrder.getCommerceOrderItems()) {

			if (commerceOrderItem.isShippable()) {
				return true;
			}
		}

		return false;
	}

}