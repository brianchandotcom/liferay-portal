/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.order;

import com.liferay.commerce.inventory.CPDefinitionInventoryEngine;
import com.liferay.commerce.inventory.CPDefinitionInventoryEngineRegistry;
import com.liferay.commerce.model.CPDefinitionInventory;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.order.CommerceOrderValidator;
import com.liferay.commerce.order.CommerceOrderValidatorResult;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.service.CPDefinitionInventoryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.ArrayUtil;
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
		"commerce.order.validator.key=" + DefaultCommerceOrderValidatorImpl.KEY,
		"commerce.order.validator.priority:Integer=10"
	},
	service = CommerceOrderValidator.class
)
public class DefaultCommerceOrderValidatorImpl
	implements CommerceOrderValidator {

	public static final String KEY = "default";

	@Override
	public String getKey() {
		return KEY;
	}

	@Override
	public CommerceOrderValidatorResult validate(
			Locale locale, CommerceOrder commerceOrder, CPInstance cpInstance,
			BigDecimal quantity)
		throws PortalException {

		if (cpInstance == null) {
			return new CommerceOrderValidatorResult(false);
		}

		CPDefinitionInventory cpDefinitionInventory =
			_cpDefinitionInventoryLocalService.
				fetchCPDefinitionInventoryByCPDefinitionId(
					cpInstance.getCPDefinitionId());

		CPDefinitionInventoryEngine cpDefinitionInventoryEngine =
			_cpDefinitionInventoryEngineRegistry.getCPDefinitionInventoryEngine(
				cpDefinitionInventory);

		BigDecimal minOrderQuantity =
			cpDefinitionInventoryEngine.getMinOrderQuantity(cpInstance);

		if (BigDecimalUtil.lt(quantity, minOrderQuantity)) {
			return new CommerceOrderValidatorResult(
				false,
				_getLocalizedMessage(
					locale, "the-minimum-quantity-is-x",
					new Object[] {minOrderQuantity}));
		}

		BigDecimal maxOrderQuantity =
			cpDefinitionInventoryEngine.getMaxOrderQuantity(cpInstance);

		if (BigDecimalUtil.gt(maxOrderQuantity, BigDecimal.ZERO) &&
			BigDecimalUtil.gt(quantity, maxOrderQuantity)) {

			return new CommerceOrderValidatorResult(
				false,
				_getLocalizedMessage(
					locale, "the-maximum-quantity-is-x",
					new Object[] {maxOrderQuantity}));
		}

		String[] allowedOrderQuantities =
			cpDefinitionInventoryEngine.getAllowedOrderQuantities(cpInstance);

		if ((allowedOrderQuantities.length > 0) &&
			!ArrayUtil.contains(
				allowedOrderQuantities, String.valueOf(quantity.intValue()))) {

			return new CommerceOrderValidatorResult(
				false,
				_getLocalizedMessage(
					locale, "the-specified-quantity-is-not-allowed", null));
		}

		BigDecimal multipleOrderQuantity =
			cpDefinitionInventoryEngine.getMultipleOrderQuantity(cpInstance);

		if (!BigDecimalUtil.eq(
				quantity.remainder(multipleOrderQuantity), BigDecimal.ZERO)) {

			return new CommerceOrderValidatorResult(
				false,
				_getLocalizedMessage(
					locale, "the-specified-quantity-is-not-a-multiple-of-x",
					new Object[] {multipleOrderQuantity}));
		}

		return new CommerceOrderValidatorResult(true);
	}

	@Override
	public CommerceOrderValidatorResult validate(
			Locale locale, CommerceOrderItem commerceOrderItem)
		throws PortalException {

		CPInstance cpInstance = commerceOrderItem.fetchCPInstance();

		if (cpInstance == null) {
			return new CommerceOrderValidatorResult(false);
		}

		CPDefinitionInventory cpDefinitionInventory =
			_cpDefinitionInventoryLocalService.
				fetchCPDefinitionInventoryByCPDefinitionId(
					cpInstance.getCPDefinitionId());

		CPDefinitionInventoryEngine cpDefinitionInventoryEngine =
			_cpDefinitionInventoryEngineRegistry.getCPDefinitionInventoryEngine(
				cpDefinitionInventory);

		BigDecimal minOrderQuantity =
			cpDefinitionInventoryEngine.getMinOrderQuantity(cpInstance);

		BigDecimal quantity = commerceOrderItem.getQuantity();

		if (BigDecimalUtil.lt(quantity, minOrderQuantity)) {
			return new CommerceOrderValidatorResult(
				commerceOrderItem.getCommerceOrderItemId(), false,
				_getLocalizedMessage(
					locale, "the-minimum-quantity-is-x",
					new Object[] {minOrderQuantity}));
		}

		BigDecimal maxOrderQuantity =
			cpDefinitionInventoryEngine.getMaxOrderQuantity(cpInstance);

		if (BigDecimalUtil.gt(maxOrderQuantity, BigDecimal.ZERO) &&
			BigDecimalUtil.gt(quantity, maxOrderQuantity)) {

			return new CommerceOrderValidatorResult(
				commerceOrderItem.getCommerceOrderItemId(), false,
				_getLocalizedMessage(
					locale, "the-maximum-quantity-is-x",
					new Object[] {maxOrderQuantity}));
		}

		String[] allowedOrderQuantities =
			cpDefinitionInventoryEngine.getAllowedOrderQuantities(cpInstance);

		if ((allowedOrderQuantities.length > 0) &&
			!ArrayUtil.contains(
				allowedOrderQuantities, String.valueOf(quantity.intValue()))) {

			return new CommerceOrderValidatorResult(
				commerceOrderItem.getCommerceOrderItemId(), false,
				_getLocalizedMessage(
					locale, "the-specified-quantity-is-not-allowed", null));
		}

		BigDecimal multipleOrderQuantity =
			cpDefinitionInventoryEngine.getMultipleOrderQuantity(cpInstance);

		if (!BigDecimalUtil.eq(
				quantity.remainder(multipleOrderQuantity), BigDecimal.ZERO)) {

			return new CommerceOrderValidatorResult(
				false,
				_getLocalizedMessage(
					locale, "the-specified-quantity-is-not-a-multiple-of-x",
					new Object[] {multipleOrderQuantity}));
		}

		return new CommerceOrderValidatorResult(true);
	}

	private String _getLocalizedMessage(
		Locale locale, String key, Object[] arguments) {

		if (locale == null) {
			return key;
		}

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		if (arguments == null) {
			return _language.get(resourceBundle, key);
		}

		return _language.format(resourceBundle, key, arguments);
	}

	@Reference
	private CPDefinitionInventoryEngineRegistry
		_cpDefinitionInventoryEngineRegistry;

	@Reference
	private CPDefinitionInventoryLocalService
		_cpDefinitionInventoryLocalService;

	@Reference
	private Language _language;

}