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

package com.liferay.commerce.internal.order;

import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.order.CommerceOrderValidator;
import com.liferay.commerce.order.CommerceOrderValidatorResult;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CPSubscriptionInfo;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true,
	property = {
		"commerce.order.validator.key=" + SubscriptionCommerceOrderValidatorImpl.KEY,
		"commerce.order.validator.priority:Integer=30"
	},
	service = CommerceOrderValidator.class
)
public class SubscriptionCommerceOrderValidatorImpl
	implements CommerceOrderValidator {

	public static final String KEY = "subscription";

	@Override
	public String getKey() {
		return KEY;
	}

	@Override
	public CommerceOrderValidatorResult validate(
			Locale locale, CommerceOrder commerceOrder, CPInstance cpInstance,
			int quantity)
		throws PortalException {

		if (cpInstance == null) {
			return new CommerceOrderValidatorResult(false);
		}

		List<CommerceOrderItem> commerceOrderItems =
			commerceOrder.getCommerceOrderItems();

		if (commerceOrderItems.size() <= 0) {
			return new CommerceOrderValidatorResult(true);
		}

		CommerceOrderItem commerceOrderItem = commerceOrderItems.get(0);

		if (commerceOrderItem.getCPInstanceId() ==
				cpInstance.getCPInstanceId()) {

			return new CommerceOrderValidatorResult(true);
		}

		CPSubscriptionInfo cpSubscriptionInfo =
			cpInstance.getCPSubscriptionInfo();

		if (commerceOrderItem.isSubscription() &&
			(cpSubscriptionInfo != null)) {

			return new CommerceOrderValidatorResult(
				commerceOrderItem.getCommerceOrderItemId(), false,
				_getLocalizedMessage(
					locale,
					"your-cart-can-contain-only-one-subscription-product"));
		}
		else if (commerceOrderItem.isSubscription() &&
				 (cpSubscriptionInfo == null)) {

			return new CommerceOrderValidatorResult(
				commerceOrderItem.getCommerceOrderItemId(), false,
				_getLocalizedMessage(
					locale,
					"cart-cannot-contain-both-subscription-and-non-" +
						"subscription-products"));
		}
		else if (!commerceOrderItem.isSubscription() &&
				 (cpSubscriptionInfo != null)) {

			return new CommerceOrderValidatorResult(
				commerceOrderItem.getCommerceOrderItemId(), false,
				_getLocalizedMessage(
					locale,
					"cart-cannot-contain-both-subscription-and-non-" +
						"subscription-products"));
		}

		return new CommerceOrderValidatorResult(true);
	}

	@Override
	public CommerceOrderValidatorResult validate(
			Locale locale, CommerceOrderItem commerceOrderItem)
		throws PortalException {

		CommerceOrder commerceOrder = commerceOrderItem.getCommerceOrder();

		List<CommerceOrderItem> commerceOrderItems =
			commerceOrder.getCommerceOrderItems();

		if (commerceOrderItems.size() <= 1) {
			return new CommerceOrderValidatorResult(true);
		}

		for (CommerceOrderItem curCommerceOrderItem : commerceOrderItems) {
			if (curCommerceOrderItem.equals(commerceOrderItem)) {
				continue;
			}

			if (curCommerceOrderItem.isSubscription() !=
					commerceOrderItem.isSubscription()) {

				return new CommerceOrderValidatorResult(
					commerceOrderItem.getCommerceOrderItemId(), false,
					_getLocalizedMessage(
						locale,
						"cart-cannot-contain-both-subscription-and-non-" +
							"subscription-products"));
			}
		}

		return new CommerceOrderValidatorResult(true);
	}

	private String _getLocalizedMessage(Locale locale, String key) {
		if (locale == null) {
			return key;
		}

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return _language.get(resourceBundle, key);
	}

	@Reference
	private Language _language;

}