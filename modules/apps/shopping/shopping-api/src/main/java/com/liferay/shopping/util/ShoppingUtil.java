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

package com.liferay.shopping.util;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MathUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.shopping.configuration.ShoppingGroupServiceOverriddenConfiguration;
import com.liferay.shopping.constants.ShoppingConstants;
import com.liferay.shopping.model.ShoppingCartItem;
import com.liferay.shopping.model.ShoppingCategory;
import com.liferay.shopping.model.ShoppingCoupon;
import com.liferay.shopping.model.ShoppingCouponConstants;
import com.liferay.shopping.model.ShoppingItem;
import com.liferay.shopping.model.ShoppingItemField;
import com.liferay.shopping.model.ShoppingItemPrice;
import com.liferay.shopping.model.ShoppingItemPriceConstants;
import com.liferay.shopping.model.ShoppingOrder;
import com.liferay.shopping.model.ShoppingOrderItem;
import com.liferay.shopping.service.ShoppingCategoryLocalServiceUtil;
import com.liferay.shopping.service.ShoppingOrderItemLocalServiceUtil;
import com.liferay.shopping.service.persistence.ShoppingItemPriceUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 * @author Eduardo Garcia
 */
public class ShoppingUtil {

	public static double calculateActualPrice(ShoppingItem item, int count)
		throws PortalException {

		return _calculatePrice(item, count) -
			_calculateDiscountPrice(item, count);
	}

	public static double calculateActualPrice(ShoppingItemPrice itemPrice) {
		return itemPrice.getPrice() - calculateDiscountPrice(itemPrice);
	}

	public static double calculateActualSubtotal(
		List<ShoppingOrderItem> orderItems) {

		double subtotal = 0.0;

		for (ShoppingOrderItem orderItem : orderItems) {
			subtotal += orderItem.getPrice() * orderItem.getQuantity();
		}

		return subtotal;
	}

	public static double calculateActualSubtotal(
			Map<ShoppingCartItem, Integer> items)
		throws PortalException {

		return calculateSubtotal(items) - calculateDiscountSubtotal(items);
	}

	public static double calculateAlternativeShipping(
			Map<ShoppingCartItem, Integer> items, int altShipping)
		throws PortalException {

		double shipping = calculateShipping(items);

		double alternativeShipping = shipping;

		ShoppingGroupServiceOverriddenConfiguration
			shoppingGroupServiceOverriddenConfiguration = null;

		for (Map.Entry<ShoppingCartItem, Integer> entry : items.entrySet()) {
			ShoppingCartItem cartItem = entry.getKey();

			ShoppingItem item = cartItem.getItem();

			if (shoppingGroupServiceOverriddenConfiguration == null) {
				ShoppingCategory category = item.getCategory();

				shoppingGroupServiceOverriddenConfiguration =
					_getShoppingGroupServiceOverriddenConfiguration(
						category.getGroupId());

				break;
			}
		}

		// Calculate alternative shipping if shopping is configured to use
		// alternative shipping and shipping price is greater than 0

		if ((shoppingGroupServiceOverriddenConfiguration != null) &&
			shoppingGroupServiceOverriddenConfiguration.
				useAlternativeShipping() &&
			(shipping > 0)) {

			double altShippingDelta = 0.0;

			try {
				altShippingDelta = GetterUtil.getDouble(
					shoppingGroupServiceOverriddenConfiguration.
						getAlternativeShipping()[1][altShipping]);
			}
			catch (Exception e) {
				return alternativeShipping;
			}

			if (altShippingDelta > 0) {
				alternativeShipping = shipping * altShippingDelta;
			}
		}

		return alternativeShipping;
	}

	public static double calculateCouponDiscount(
			Map<ShoppingCartItem, Integer> items, String stateId,
			ShoppingCoupon coupon)
		throws PortalException {

		double discount = 0.0;

		if ((coupon == null) || !coupon.isActive() ||
			!coupon.hasValidDateRange()) {

			return discount;
		}

		String[] categoryNames = StringUtil.split(coupon.getLimitCategories());

		Set<Long> categoryIds = new HashSet<>();

		for (String categoryName : categoryNames) {
			ShoppingCategory category =
				ShoppingCategoryLocalServiceUtil.getCategory(
					coupon.getGroupId(), categoryName);

			List<Long> subcategoryIds = new ArrayList<>();

			ShoppingCategoryLocalServiceUtil.getSubcategoryIds(
				subcategoryIds, category.getGroupId(),
				category.getCategoryId());

			categoryIds.add(category.getCategoryId());
			categoryIds.addAll(subcategoryIds);
		}

		String[] skus = StringUtil.split(coupon.getLimitSkus());

		if (!categoryIds.isEmpty() || (skus.length > 0)) {
			Set<String> skusSet = new HashSet<>();

			for (String sku : skus) {
				skusSet.add(sku);
			}

			Map<ShoppingCartItem, Integer> newItems = new HashMap<>();

			for (Map.Entry<ShoppingCartItem, Integer> entry :
					items.entrySet()) {

				ShoppingCartItem cartItem = entry.getKey();
				Integer count = entry.getValue();

				ShoppingItem item = cartItem.getItem();

				if ((!categoryIds.isEmpty() &&
					 categoryIds.contains(item.getCategoryId())) ||
					(!skusSet.isEmpty() && skusSet.contains(item.getSku()))) {

					newItems.put(cartItem, count);
				}
			}

			items = newItems;
		}

		double actualSubtotal = calculateActualSubtotal(items);

		if ((coupon.getMinOrder() > 0) &&
			(coupon.getMinOrder() > actualSubtotal)) {

			return discount;
		}

		String type = coupon.getDiscountType();

		if (type.equals(ShoppingCouponConstants.DISCOUNT_TYPE_PERCENTAGE)) {
			discount = actualSubtotal * coupon.getDiscount();
		}
		else if (type.equals(ShoppingCouponConstants.DISCOUNT_TYPE_ACTUAL)) {
			discount = coupon.getDiscount();
		}
		else if (type.equals(
					ShoppingCouponConstants.DISCOUNT_TYPE_FREE_SHIPPING)) {

			discount = calculateShipping(items);
		}
		else if (type.equals(ShoppingCouponConstants.DISCOUNT_TYPE_TAX_FREE)) {
			if (stateId != null) {
				discount = calculateTax(items, stateId);
			}
		}

		return discount;
	}

	public static double calculateDiscountPrice(ShoppingItemPrice itemPrice) {
		return itemPrice.getPrice() * itemPrice.getDiscount();
	}

	public static double calculateDiscountSubtotal(
			Map<ShoppingCartItem, Integer> items)
		throws PortalException {

		double subtotal = 0.0;

		for (Map.Entry<ShoppingCartItem, Integer> entry : items.entrySet()) {
			ShoppingCartItem cartItem = entry.getKey();
			Integer count = entry.getValue();

			ShoppingItem item = cartItem.getItem();

			subtotal += _calculateDiscountPrice(item, count.intValue());
		}

		return subtotal;
	}

	public static double calculateInsurance(
			Map<ShoppingCartItem, Integer> items)
		throws PortalException {

		double insurance = 0.0;
		double subtotal = 0.0;

		ShoppingGroupServiceOverriddenConfiguration
			shoppingGroupServiceOverriddenConfiguration = null;

		for (Map.Entry<ShoppingCartItem, Integer> entry : items.entrySet()) {
			ShoppingCartItem cartItem = entry.getKey();
			Integer count = entry.getValue();

			ShoppingItem item = cartItem.getItem();

			if (shoppingGroupServiceOverriddenConfiguration == null) {
				ShoppingCategory category = item.getCategory();

				shoppingGroupServiceOverriddenConfiguration =
					_getShoppingGroupServiceOverriddenConfiguration(
						category.getGroupId());
			}

			ShoppingItemPrice itemPrice = _getItemPrice(item, count.intValue());

			subtotal += calculateActualPrice(itemPrice) * count.intValue();
		}

		if ((shoppingGroupServiceOverriddenConfiguration == null) ||
			(subtotal == 0)) {

			return insurance;
		}

		double insuranceRate = 0.0;

		double[] range =
			ShoppingGroupServiceOverriddenConfiguration.INSURANCE_RANGE;

		for (int i = 0; i < range.length - 1; i++) {
			if ((subtotal > range[i]) && (subtotal <= range[i + 1])) {
				int rangeId = i / 2;

				if (MathUtil.isOdd(i)) {
					rangeId = (i + 1) / 2;
				}

				String[] insurances =
					shoppingGroupServiceOverriddenConfiguration.getInsurance();

				if (insurances.length < rangeId) {
					continue;
				}

				insuranceRate = GetterUtil.getDouble(insurances[rangeId]);
			}
		}

		String formula =
			shoppingGroupServiceOverriddenConfiguration.getInsuranceFormula();

		if (formula.equals("flat")) {
			insurance += insuranceRate;
		}
		else if (formula.equals("percentage")) {
			insurance += subtotal * insuranceRate;
		}

		return insurance;
	}

	public static double calculateShipping(Map<ShoppingCartItem, Integer> items)
		throws PortalException {

		double shipping = 0.0;
		double subtotal = 0.0;

		ShoppingGroupServiceOverriddenConfiguration
			shoppingGroupServiceOverriddenConfiguration = null;

		for (Map.Entry<ShoppingCartItem, Integer> entry : items.entrySet()) {
			ShoppingCartItem cartItem = entry.getKey();
			Integer count = entry.getValue();

			ShoppingItem item = cartItem.getItem();

			if (shoppingGroupServiceOverriddenConfiguration == null) {
				ShoppingCategory category = item.getCategory();

				shoppingGroupServiceOverriddenConfiguration =
					_getShoppingGroupServiceOverriddenConfiguration(
						category.getGroupId());
			}

			if (item.isRequiresShipping()) {
				ShoppingItemPrice itemPrice = _getItemPrice(
					item, count.intValue());

				if (itemPrice.isUseShippingFormula()) {
					subtotal +=
						calculateActualPrice(itemPrice) * count.intValue();
				}
				else {
					shipping += itemPrice.getShipping() * count.intValue();
				}
			}
		}

		if ((shoppingGroupServiceOverriddenConfiguration == null) ||
			(subtotal == 0)) {

			return shipping;
		}

		double shippingRate = 0.0;

		double[] range =
			ShoppingGroupServiceOverriddenConfiguration.SHIPPING_RANGE;

		for (int i = 0; i < range.length - 1; i++) {
			if ((subtotal > range[i]) && (subtotal <= range[i + 1])) {
				int rangeId = i / 2;

				if (MathUtil.isOdd(i)) {
					rangeId = (i + 1) / 2;
				}

				shippingRate = GetterUtil.getDouble(
					shoppingGroupServiceOverriddenConfiguration.
						getShipping()[rangeId]);
			}
		}

		String formula =
			shoppingGroupServiceOverriddenConfiguration.getShippingFormula();

		if (formula.equals("flat")) {
			shipping += shippingRate;
		}
		else if (formula.equals("percentage")) {
			shipping += subtotal * shippingRate;
		}

		return shipping;
	}

	public static double calculateSubtotal(Map<ShoppingCartItem, Integer> items)
		throws PortalException {

		double subtotal = 0.0;

		for (Map.Entry<ShoppingCartItem, Integer> entry : items.entrySet()) {
			ShoppingCartItem cartItem = entry.getKey();
			Integer count = entry.getValue();

			ShoppingItem item = cartItem.getItem();

			subtotal += _calculatePrice(item, count.intValue());
		}

		return subtotal;
	}

	public static double calculateTax(
			Map<ShoppingCartItem, Integer> items, String stateId)
		throws PortalException {

		double tax = 0.0;

		ShoppingGroupServiceOverriddenConfiguration
			shoppingGroupServiceOverriddenConfiguration = null;

		for (Map.Entry<ShoppingCartItem, Integer> entry : items.entrySet()) {
			ShoppingCartItem cartItem = entry.getKey();

			ShoppingItem item = cartItem.getItem();

			if (shoppingGroupServiceOverriddenConfiguration == null) {
				ShoppingCategory category = item.getCategory();

				shoppingGroupServiceOverriddenConfiguration =
					_getShoppingGroupServiceOverriddenConfiguration(
						category.getGroupId());

				break;
			}
		}

		if (shoppingGroupServiceOverriddenConfiguration == null) {
			return tax;
		}

		String taxState =
			shoppingGroupServiceOverriddenConfiguration.getTaxState();

		if (!taxState.equals(stateId)) {
			return tax;
		}

		double subtotal = 0.0;

		for (Map.Entry<ShoppingCartItem, Integer> entry : items.entrySet()) {
			ShoppingCartItem cartItem = entry.getKey();
			Integer count = entry.getValue();

			ShoppingItem item = cartItem.getItem();

			if (item.isTaxable()) {
				subtotal += _calculatePrice(item, count.intValue());
			}
		}

		return shoppingGroupServiceOverriddenConfiguration.getTaxRate() *
			subtotal;
	}

	public static double calculateTotal(ShoppingOrder order) {
		List<ShoppingOrderItem> orderItems =
			ShoppingOrderItemLocalServiceUtil.getOrderItems(order.getOrderId());

		double total =
			calculateActualSubtotal(orderItems) + order.getTax() +
				order.getShipping() + order.getInsurance() -
					order.getCouponDiscount();

		if (total < 0) {
			total = 0.0;
		}

		return total;
	}

	public static int getFieldsQuantitiesPos(
		ShoppingItem item, ShoppingItemField[] itemFields,
		String[] fieldsArray) {

		Set<String> fieldsValues = new HashSet<>();

		for (String fields : fieldsArray) {
			int pos = fields.indexOf("=");

			String fieldValue = fields.substring(pos + 1);

			fieldsValues.add(fieldValue.trim());
		}

		List<String> names = new ArrayList<>();
		List<String[]> values = new ArrayList<>();

		for (int i = 0; i < itemFields.length; i++) {
			names.add(itemFields[i].getName());
			values.add(StringUtil.split(itemFields[i].getValues()));
		}

		int numOfRows = 1;

		for (String[] vArray : values) {
			numOfRows = numOfRows * vArray.length;
		}

		int rowPos = 0;

		for (int i = 0; i < numOfRows; i++) {
			boolean match = true;

			for (int j = 0; j < names.size(); j++) {
				int numOfRepeats = 1;

				for (int k = j + 1; k < values.size(); k++) {
					String[] vArray = values.get(k);

					numOfRepeats = numOfRepeats * vArray.length;
				}

				String[] vArray = values.get(j);

				int arrayPos = 0;

				for (arrayPos = i / numOfRepeats; arrayPos >= vArray.length;
					arrayPos = arrayPos - vArray.length) {
				}

				if (!fieldsValues.contains(vArray[arrayPos].trim())) {
					match = false;

					break;
				}
			}

			if (match) {
				rowPos = i;

				break;
			}
		}

		return rowPos;
	}

	public static String getItemFields(String itemId) {
		int pos = itemId.indexOf(CharPool.PIPE);

		if (pos == -1) {
			return StringPool.BLANK;
		}
		else {
			return itemId.substring(pos + 1);
		}
	}

	public static long getItemId(String itemId) {
		int pos = itemId.indexOf(CharPool.PIPE);

		if (pos != -1) {
			itemId = itemId.substring(0, pos);
		}

		return GetterUtil.getLong(itemId);
	}

	private static double _calculateDiscountPrice(ShoppingItem item, int count)
		throws PortalException {

		ShoppingItemPrice itemPrice = _getItemPrice(item, count);

		return itemPrice.getPrice() * itemPrice.getDiscount() * count;
	}

	private static double _calculatePrice(ShoppingItem item, int count)
		throws PortalException {

		ShoppingItemPrice itemPrice = _getItemPrice(item, count);

		return itemPrice.getPrice() * count;
	}

	private static ShoppingItemPrice _getItemPrice(ShoppingItem item, int count)
		throws PortalException {

		ShoppingItemPrice itemPrice = null;

		List<ShoppingItemPrice> itemPrices = item.getItemPrices();

		for (ShoppingItemPrice temp : itemPrices) {
			int minQty = temp.getMinQuantity();
			int maxQty = temp.getMaxQuantity();

			if (temp.getStatus() !=
					ShoppingItemPriceConstants.STATUS_INACTIVE) {

				if ((count >= minQty) && ((count <= maxQty) || (maxQty == 0))) {
					return temp;
				}

				if ((count > maxQty) &&
					((itemPrice == null) ||
					 (itemPrice.getMaxQuantity() < maxQty))) {

					itemPrice = temp;
				}
			}
		}

		if (itemPrice == null) {
			return ShoppingItemPriceUtil.create(0);
		}

		return itemPrice;
	}

	private static ShoppingGroupServiceOverriddenConfiguration
			_getShoppingGroupServiceOverriddenConfiguration(long groupId)
		throws ConfigurationException {

		return ConfigurationProviderUtil.getConfiguration(
			ShoppingGroupServiceOverriddenConfiguration.class,
			new GroupServiceSettingsLocator(
				groupId, ShoppingConstants.SERVICE_NAME));
	}

}