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

import com.liferay.commerce.inventory.CPDefinitionInventoryEngineRegistry;
import com.liferay.commerce.inventory.engine.CommerceInventoryEngine;
import com.liferay.commerce.inventory.model.CommerceInventoryBookedQuantity;
import com.liferay.commerce.inventory.service.CommerceInventoryBookedQuantityLocalService;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.order.CommerceOrderValidator;
import com.liferay.commerce.order.CommerceOrderValidatorResult;
import com.liferay.commerce.product.availability.CPAvailabilityChecker;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.service.CPDefinitionInventoryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

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
		"commerce.order.validator.key=" + AvailabilityCommerceOrderValidatorImpl.KEY,
		"commerce.order.validator.priority:Integer=20"
	},
	service = CommerceOrderValidator.class
)
public class AvailabilityCommerceOrderValidatorImpl
	implements CommerceOrderValidator {

	public static final String KEY = "availability";

	@Override
	public String getKey() {
		return KEY;
	}

	@Override
	public CommerceOrderValidatorResult validate(
			Locale locale, CommerceOrder commerceOrder, CPInstance cpInstance,
			int quantity)
		throws PortalException {

		if (!_cpAvailabilityChecker.isPurchasable(cpInstance)) {
			return new CommerceOrderValidatorResult(
				false,
				_getLocalizedMessage(
					locale, "the-product-is-no-longer-available"));
		}

		if (!_cpAvailabilityChecker.isAvailable(
				commerceOrder.getGroupId(), cpInstance, quantity)) {

			return new CommerceOrderValidatorResult(
				false,
				_getLocalizedMessage(locale, "that-quantity-is-unavailable"));
		}

		return new CommerceOrderValidatorResult(true);
	}

	@Override
	public CommerceOrderValidatorResult validate(
			Locale locale, CommerceOrderItem commerceOrderItem)
		throws PortalException {

		CPInstance cpInstance = commerceOrderItem.fetchCPInstance();

		if (!_cpAvailabilityChecker.isPurchasable(cpInstance)) {
			return new CommerceOrderValidatorResult(
				commerceOrderItem.getCommerceOrderItemId(), false,
				_getLocalizedMessage(
					locale, "the-product-is-no-longer-available"));
		}

		CommerceInventoryBookedQuantity commerceInventoryBookedQuantity =
			_commerceInventoryBookedQuantityLocalService.
				fetchCommerceInventoryBookedQuantity(
					commerceOrderItem.getBookedQuantityId());

		if (!_cpAvailabilityChecker.isAvailable(
				commerceOrderItem.getGroupId(), cpInstance,
				commerceOrderItem.getQuantity()) &&
			(commerceInventoryBookedQuantity == null)) {

			return new CommerceOrderValidatorResult(
				commerceOrderItem.getCommerceOrderItemId(), false,
				_getLocalizedMessage(locale, "that-quantity-is-unavailable"));
		}
		else if ((commerceInventoryBookedQuantity != null) &&
				 (commerceOrderItem.getQuantity() !=
					 commerceInventoryBookedQuantity.getQuantity())) {

			return new CommerceOrderValidatorResult(
				commerceOrderItem.getCommerceOrderItemId(), false,
				_getLocalizedMessage(locale, "that-quantity-is-not-allowed"));
		}

		return new CommerceOrderValidatorResult(true);
	}

	private String _getLocalizedMessage(Locale locale, String key) {
		if (locale == null) {
			return key;
		}

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return LanguageUtil.get(resourceBundle, key);
	}

	@Reference
	private CommerceInventoryBookedQuantityLocalService
		_commerceInventoryBookedQuantityLocalService;

	@Reference
	private CommerceInventoryEngine _commerceInventoryEngine;

	@Reference
	private CPAvailabilityChecker _cpAvailabilityChecker;

	@Reference
	private CPDefinitionInventoryEngineRegistry
		_cpDefinitionInventoryEngineRegistry;

	@Reference
	private CPDefinitionInventoryLocalService
		_cpDefinitionInventoryLocalService;

}