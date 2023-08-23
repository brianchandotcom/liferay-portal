/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.order;

import com.liferay.commerce.inventory.model.CommerceInventoryBookedQuantity;
import com.liferay.commerce.inventory.service.CommerceInventoryBookedQuantityLocalService;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.order.CommerceOrderValidator;
import com.liferay.commerce.order.CommerceOrderValidatorResult;
import com.liferay.commerce.product.availability.CPAvailabilityChecker;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.BigDecimalUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.math.BigDecimal;

import java.util.Locale;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
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
			BigDecimal quantity)
		throws PortalException {

		if (!_cpAvailabilityChecker.isPurchasable(cpInstance)) {
			return new CommerceOrderValidatorResult(
				false,
				_getLocalizedMessage(
					locale, "the-product-is-no-longer-available"));
		}

		if (!_cpAvailabilityChecker.isAvailable(
				commerceOrder.getGroupId(), cpInstance, StringPool.BLANK,
				quantity)) {

			return new CommerceOrderValidatorResult(
				false,
				_getLocalizedMessage(
					locale, "the-specified-quantity-is-unavailable"));
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
					commerceOrderItem.getCommerceInventoryBookedQuantityId());

		BigDecimal quantity = commerceOrderItem.getQuantity();

		if (!_cpAvailabilityChecker.isAvailable(
				commerceOrderItem.getGroupId(), cpInstance, StringPool.BLANK,
				quantity) &&
			(commerceInventoryBookedQuantity == null)) {

			return new CommerceOrderValidatorResult(
				commerceOrderItem.getCommerceOrderItemId(), false,
				_getLocalizedMessage(
					locale, "the-specified-quantity-is-unavailable"));
		}

		if ((commerceInventoryBookedQuantity != null) &&
			!BigDecimalUtil.eq(
				quantity, commerceInventoryBookedQuantity.getQuantity())) {

			BigDecimal bookedQuantity = BigDecimal.ZERO;

			BigDecimal commerceInventoryWarehouseItemQuantity =
				commerceInventoryBookedQuantity.getQuantity();

			if (commerceInventoryWarehouseItemQuantity != null) {
				bookedQuantity = commerceInventoryWarehouseItemQuantity;
			}

			if (!BigDecimalUtil.eq(
					commerceOrderItem.getQuantity(), bookedQuantity)) {

				return new CommerceOrderValidatorResult(
					commerceOrderItem.getCommerceOrderItemId(), false,
					_getLocalizedMessage(
						locale, "the-specified-quantity-is-not-allowed"));
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
	private CommerceInventoryBookedQuantityLocalService
		_commerceInventoryBookedQuantityLocalService;

	@Reference
	private CPAvailabilityChecker _cpAvailabilityChecker;

	@Reference
	private Language _language;

}