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

package com.liferay.commerce.frontend.internal.util;

import com.liferay.commerce.constants.CPDefinitionInventoryConstants;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.currency.model.CommerceMoney;
import com.liferay.commerce.currency.util.CommercePriceFormatter;
import com.liferay.commerce.discount.CommerceDiscountValue;
import com.liferay.commerce.frontend.model.PriceModel;
import com.liferay.commerce.frontend.model.ProductSettingsModel;
import com.liferay.commerce.frontend.util.ProductHelper;
import com.liferay.commerce.inventory.CPDefinitionInventoryEngineRegistry;
import com.liferay.commerce.model.CPDefinitionInventory;
import com.liferay.commerce.price.CommerceProductPrice;
import com.liferay.commerce.price.CommerceProductPriceCalculation;
import com.liferay.commerce.pricing.constants.CommercePricingConstants;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.commerce.product.service.CPInstanceLocalService;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.service.CPDefinitionInventoryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
@Component(enabled = false, service = ProductHelper.class)
public class ProductHelperImpl implements ProductHelper {

	@Override
	public PriceModel getMinPrice(
			long cpDefinitionId, CommerceContext commerceContext, Locale locale)
		throws PortalException {

		CommerceMoney cpDefinitionMinimumPrice =
			_commerceProductPriceCalculation.getCPDefinitionMinimumPrice(
				cpDefinitionId, commerceContext);

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return new PriceModel(
			LanguageUtil.format(
				resourceBundle, "from-x",
				cpDefinitionMinimumPrice.format(locale), false));
	}

	@Override
	public PriceModel getPrice(
			long cpInstanceId, int quantity, CommerceContext commerceContext,
			Locale locale)
		throws PortalException {

		CommerceProductPrice commerceProductPrice =
			_commerceProductPriceCalculation.getCommerceProductPrice(
				cpInstanceId, quantity, true, commerceContext);

		if (commerceProductPrice == null) {
			return null;
		}

		return _getPriceModel(
			commerceContext.getCommerceChannelId(), commerceProductPrice,
			locale);
	}

	@Override
	public ProductSettingsModel getProductSettingsModel(long cpInstanceId)
		throws PortalException {

		ProductSettingsModel productSettingsModel = new ProductSettingsModel();

		int minOrderQuantity =
			CPDefinitionInventoryConstants.DEFAULT_MIN_ORDER_QUANTITY;
		int maxOrderQuantity =
			CPDefinitionInventoryConstants.DEFAULT_MAX_ORDER_QUANTITY;
		int multipleQuantity =
			CPDefinitionInventoryConstants.DEFAULT_MULTIPLE_ORDER_QUANTITY;

		CPDefinitionInventory cpDefinitionInventory = null;

		CPInstance cpInstance = _cpInstanceLocalService.fetchCPInstance(
			cpInstanceId);

		if (cpInstance != null) {
			cpDefinitionInventory =
				_cpDefinitionInventoryLocalService.
					fetchCPDefinitionInventoryByCPDefinitionId(
						cpInstance.getCPDefinitionId());
		}

		if (cpDefinitionInventory != null) {
			minOrderQuantity = cpDefinitionInventory.getMinOrderQuantity();
			maxOrderQuantity = cpDefinitionInventory.getMaxOrderQuantity();
			multipleQuantity = cpDefinitionInventory.getMultipleOrderQuantity();

			int[] allowedOrderQuantitiesArray =
				cpDefinitionInventory.getAllowedOrderQuantitiesArray();

			if ((allowedOrderQuantitiesArray != null) &&
				(allowedOrderQuantitiesArray.length > 0)) {

				productSettingsModel.setAllowedQuantities(
					allowedOrderQuantitiesArray);
			}

			productSettingsModel.setLowStockQuantity(
				cpDefinitionInventory.getMinStockQuantity());
			productSettingsModel.setShowAvailabilityDot(
				cpDefinitionInventory.isDisplayAvailability());
		}

		productSettingsModel.setMinQuantity(minOrderQuantity);
		productSettingsModel.setMaxQuantity(maxOrderQuantity);
		productSettingsModel.setMultipleQuantity(multipleQuantity);

		return productSettingsModel;
	}

	private String[] _getFormattedDiscountPercentages(
			BigDecimal[] discountPercentages, Locale locale)
		throws PortalException {

		List<String> formattedDiscountPercentages = new ArrayList<>();

		for (BigDecimal percentage : discountPercentages) {
			if (percentage == null) {
				percentage = BigDecimal.ZERO;
			}

			formattedDiscountPercentages.add(
				_commercePriceFormatter.format(percentage, locale));
		}

		return formattedDiscountPercentages.toArray(new String[0]);
	}

	private PriceModel _getPriceModel(
			CommerceMoney finalPriceCommerceMoney,
			CommerceMoney unitPriceCommerceMoney,
			CommerceMoney unitPromoPriceCommerceMoney,
			CommerceDiscountValue commerceDiscountValue, Locale locale)
		throws PortalException {

		PriceModel priceModel = new PriceModel(
			unitPriceCommerceMoney.format(locale));

		if (!unitPromoPriceCommerceMoney.isEmpty()) {
			BigDecimal unitPromoPrice = unitPromoPriceCommerceMoney.getPrice();

			if ((unitPromoPrice.compareTo(BigDecimal.ZERO) > 0) &&
				(unitPromoPrice.compareTo(unitPriceCommerceMoney.getPrice()) <
					0)) {

				priceModel.setPromoPrice(
					unitPromoPriceCommerceMoney.format(locale));
			}
		}

		return _updatePriceModelDiscount(
			priceModel, commerceDiscountValue, finalPriceCommerceMoney, locale);
	}

	private PriceModel _getPriceModel(
			long commerceChannelId, CommerceProductPrice commerceProductPrice,
			Locale locale)
		throws PortalException {

		CommerceChannel commerceChannel =
			_commerceChannelLocalService.getCommerceChannel(commerceChannelId);

		String priceDisplayType = commerceChannel.getPriceDisplayType();

		if (priceDisplayType.equals(
				CommercePricingConstants.TAX_EXCLUDED_FROM_PRICE)) {

			return _getPriceModel(
				commerceProductPrice.getFinalPrice(),
				commerceProductPrice.getUnitPrice(),
				commerceProductPrice.getUnitPromoPrice(),
				commerceProductPrice.getDiscountValue(), locale);
		}

		return _getPriceModel(
			commerceProductPrice.getFinalPriceWithTaxAmount(),
			commerceProductPrice.getUnitPriceWithTaxAmount(),
			commerceProductPrice.getUnitPromoPriceWithTaxAmount(),
			commerceProductPrice.getDiscountValueWithTaxAmount(), locale);
	}

	private PriceModel _updatePriceModelDiscount(
			PriceModel priceModel, CommerceDiscountValue commerceDiscountValue,
			CommerceMoney finalPriceCommerceMoney, Locale locale)
		throws PortalException {

		if (commerceDiscountValue == null) {
			return priceModel;
		}

		CommerceMoney discountAmount =
			commerceDiscountValue.getDiscountAmount();

		priceModel.setDiscount(discountAmount.format(locale));

		priceModel.setDiscountPercentage(
			_commercePriceFormatter.format(
				commerceDiscountValue.getDiscountPercentage(), locale));

		priceModel.setDiscountPercentages(
			_getFormattedDiscountPercentages(
				commerceDiscountValue.getPercentages(), locale));

		priceModel.setFinalPrice(finalPriceCommerceMoney.format(locale));

		return priceModel;
	}

	@Reference
	private CommerceChannelLocalService _commerceChannelLocalService;

	@Reference
	private CommercePriceFormatter _commercePriceFormatter;

	@Reference
	private CommerceProductPriceCalculation _commerceProductPriceCalculation;

	@Reference
	private CPDefinitionInventoryEngineRegistry
		_cpDefinitionInventoryEngineRegistry;

	@Reference
	private CPDefinitionInventoryLocalService
		_cpDefinitionInventoryLocalService;

	@Reference
	private CPDefinitionLocalService _cpDefinitionLocalService;

	@Reference
	private CPInstanceLocalService _cpInstanceLocalService;

}