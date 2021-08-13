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
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.model.CommerceMoney;
import com.liferay.commerce.currency.util.CommercePriceFormatter;
import com.liferay.commerce.discount.CommerceDiscountValue;
import com.liferay.commerce.frontend.model.PriceModel;
import com.liferay.commerce.frontend.model.ProductSettingsModel;
import com.liferay.commerce.frontend.util.ProductHelper;
import com.liferay.commerce.inventory.CPDefinitionInventoryEngineRegistry;
import com.liferay.commerce.model.CPDefinitionInventory;
import com.liferay.commerce.percentage.PercentageFormatter;
import com.liferay.commerce.price.CommerceProductPrice;
import com.liferay.commerce.price.CommerceProductPriceCalculation;
import com.liferay.commerce.price.CommerceProductPriceRequest;
import com.liferay.commerce.pricing.constants.CommercePricingConstants;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.option.CommerceOptionValue;
import com.liferay.commerce.product.option.CommerceOptionValueHelper;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.commerce.product.service.CPInstanceLocalService;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.service.CPDefinitionInventoryLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.Validator;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Collections;
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

		CommerceMoney cpDefinitionMinimumPriceCommerceMoney =
			_commerceProductPriceCalculation.getCPDefinitionMinimumPrice(
				cpDefinitionId, commerceContext);

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return new PriceModel(
			LanguageUtil.format(
				resourceBundle, "from-x",
				cpDefinitionMinimumPriceCommerceMoney.format(locale), false));
	}

	/**
	 * @param      cpInstanceId
	 * @param      quantity
	 * @param      commerceContext
	 * @param      locale
	 * @return
	 *
	 * @throws     PortalException
	 * @deprecated As of Athanasius (7.3.x), use {@link
	 *             #getPriceModel(long, int, CommerceContext, String, Locale)}
	 */
	@Deprecated
	@Override
	public PriceModel getPrice(
			long cpInstanceId, int quantity, CommerceContext commerceContext,
			Locale locale)
		throws PortalException {

		return getPriceModel(
			cpInstanceId, quantity, commerceContext, StringPool.BLANK, locale);
	}

	@Override
	public PriceModel getPriceModel(
			long cpInstanceId, int quantity, CommerceContext commerceContext,
			String commerceOptionValuesJSON, Locale locale)
		throws PortalException {

		CommerceProductPriceRequest commerceProductPriceRequest =
			new CommerceProductPriceRequest();

		commerceProductPriceRequest.setCpInstanceId(cpInstanceId);
		commerceProductPriceRequest.setQuantity(quantity);
		commerceProductPriceRequest.setCommerceContext(commerceContext);
		commerceProductPriceRequest.setCommerceOptionValues(
			_getCommerceOptionValues(cpInstanceId, commerceOptionValuesJSON));
		commerceProductPriceRequest.setSecure(true);

		boolean taxIncludedInPrice = _isTaxIncludedInPrice(
			commerceContext.getCommerceChannelId());

		commerceProductPriceRequest.setCalculateTax(taxIncludedInPrice);

		CommerceProductPrice commerceProductPrice =
			_commerceProductPriceCalculation.getCommerceProductPrice(
				commerceProductPriceRequest);

		if (commerceProductPrice == null) {
			return null;
		}

		if (taxIncludedInPrice) {
			return _getPriceModel(
				commerceProductPrice.getFinalPriceWithTaxAmount(),
				commerceProductPrice.getUnitPriceWithTaxAmount(),
				commerceProductPrice.getUnitPromoPriceWithTaxAmount(),
				commerceProductPrice.getDiscountValueWithTaxAmount(), locale);
		}

		return _getPriceModel(
			commerceProductPrice.getFinalPrice(),
			commerceProductPrice.getUnitPrice(),
			commerceProductPrice.getUnitPromoPrice(),
			commerceProductPrice.getDiscountValue(), locale);
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

			productSettingsModel.setBackOrders(
				cpDefinitionInventory.isBackOrders());
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

	private List<CommerceOptionValue> _getCommerceOptionValues(
			long cpInstanceId, String ddmFormValues)
		throws PortalException {

		if (Validator.isNull(ddmFormValues)) {
			return Collections.emptyList();
		}

		CPInstance cpInstance = _cpInstanceLocalService.getCPInstance(
			cpInstanceId);

		return _commerceOptionValueHelper.getCPDefinitionCommerceOptionValues(
			cpInstance.getCPDefinitionId(), ddmFormValues);
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

	private boolean _isTaxIncludedInPrice(long commerceChannelId)
		throws PortalException {

		CommerceChannel commerceChannel =
			_commerceChannelLocalService.getCommerceChannel(commerceChannelId);

		String priceDisplayType = commerceChannel.getPriceDisplayType();

		if (priceDisplayType.equals(
				CommercePricingConstants.TAX_INCLUDED_IN_PRICE)) {

			return true;
		}

		return false;
	}

	private PriceModel _updatePriceModelDiscount(
			PriceModel priceModel, CommerceDiscountValue commerceDiscountValue,
			CommerceMoney finalPriceCommerceMoney, Locale locale)
		throws PortalException {

		if (commerceDiscountValue == null) {
			return priceModel;
		}

		CommerceMoney discountAmountCommerceMoney =
			commerceDiscountValue.getDiscountAmount();

		priceModel.setDiscount(discountAmountCommerceMoney.format(locale));

		CommerceCurrency commerceCurrency =
			discountAmountCommerceMoney.getCommerceCurrency();

		priceModel.setDiscountPercentage(
			_percentageFormatter.getLocalizedPercentage(
				locale, commerceCurrency.getMaxFractionDigits(),
				commerceCurrency.getMinFractionDigits(),
				commerceDiscountValue.getDiscountPercentage()));

		priceModel.setDiscountPercentages(
			_getFormattedDiscountPercentages(
				commerceDiscountValue.getPercentages(), locale));

		priceModel.setFinalPrice(finalPriceCommerceMoney.format(locale));

		return priceModel;
	}

	@Reference
	private CommerceChannelLocalService _commerceChannelLocalService;

	@Reference
	private CommerceOptionValueHelper _commerceOptionValueHelper;

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

	@Reference
	private PercentageFormatter _percentageFormatter;

}