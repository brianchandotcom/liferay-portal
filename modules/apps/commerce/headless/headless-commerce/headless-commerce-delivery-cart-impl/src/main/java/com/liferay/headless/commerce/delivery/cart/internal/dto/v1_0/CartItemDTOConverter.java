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

package com.liferay.headless.commerce.delivery.cart.internal.dto.v1_0;

import com.liferay.commerce.constants.CPDefinitionInventoryConstants;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.model.CommerceMoney;
import com.liferay.commerce.currency.util.CommercePriceFormatter;
import com.liferay.commerce.model.CPDefinitionInventory;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.service.CPInstanceLocalService;
import com.liferay.commerce.product.util.CPInstanceHelper;
import com.liferay.commerce.service.CPDefinitionInventoryLocalService;
import com.liferay.commerce.service.CommerceOrderItemService;
import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.headless.commerce.delivery.cart.dto.v1_0.CartItem;
import com.liferay.headless.commerce.delivery.cart.dto.v1_0.Price;
import com.liferay.headless.commerce.delivery.cart.dto.v1_0.Settings;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Sbarra
 */
@Component(
	property = "model.class.name=com.liferay.headless.commerce.delivery.cart.dto.v1_0.CartItem",
	service = {CartItemDTOConverter.class, DTOConverter.class}
)
public class CartItemDTOConverter
	implements DTOConverter<CommerceOrderItem, CartItem> {

	@Override
	public String getContentType() {
		return CartItem.class.getSimpleName();
	}

	@Override
	public CartItem toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		CommerceOrderItem commerceOrderItem =
			_commerceOrderItemService.getCommerceOrderItem(
				(Long)dtoConverterContext.getId());

		Locale locale = dtoConverterContext.getLocale();

		ExpandoBridge expandoBridge = commerceOrderItem.getExpandoBridge();

		String languageId = LanguageUtil.getLanguageId(locale);

		return new CartItem() {
			{
				customFields = expandoBridge.getAttributes();
				id = commerceOrderItem.getCommerceOrderItemId();
				name = commerceOrderItem.getName(languageId);
				options = commerceOrderItem.getJson();
				parentCartItemId =
					commerceOrderItem.getParentCommerceOrderItemId();
				price = _getPrice(commerceOrderItem, locale);
				productId = commerceOrderItem.getCProductId();
				quantity = commerceOrderItem.getQuantity();
				settings = _getSettings(commerceOrderItem.getCPInstanceId());
				sku = commerceOrderItem.getSku();
				skuId = commerceOrderItem.getCPInstanceId();
				subscription = commerceOrderItem.isSubscription();
				thumbnail = _cpInstanceHelper.getCPInstanceThumbnailSrc(
					commerceOrderItem.getCPInstanceId());
			}
		};
	}

	private BigDecimal _getDiscountPercentage(
		BigDecimal discountedAmount, BigDecimal amount,
		RoundingMode roundingMode) {

		double actualPrice = discountedAmount.doubleValue();
		double originalPrice = amount.doubleValue();

		double percentage = actualPrice / originalPrice;

		BigDecimal discountPercentage = new BigDecimal(percentage);

		discountPercentage = discountPercentage.multiply(_ONE_HUNDRED);

		MathContext mathContext = new MathContext(
			discountPercentage.precision(), roundingMode);

		return _ONE_HUNDRED.subtract(discountPercentage, mathContext);
	}

	private Price _getPrice(CommerceOrderItem commerceOrderItem, Locale locale)
		throws PortalException {

		CommerceOrder commerceOrder = commerceOrderItem.getCommerceOrder();

		CommerceCurrency commerceCurrency = commerceOrder.getCommerceCurrency();

		CommerceMoney unitPriceMoney = commerceOrderItem.getUnitPriceMoney();

		BigDecimal unitPrice = unitPriceMoney.getPrice();

		Price price = new Price() {
			{
				currency = commerceCurrency.getName(locale);
				price = unitPrice.doubleValue();
				priceFormatted = unitPriceMoney.format(locale);
			}
		};

		BigDecimal activePrice = unitPriceMoney.getPrice();

		CommerceMoney unitPromoPriceMoney =
			commerceOrderItem.getPromoPriceMoney();

		BigDecimal unitPromoPrice = unitPromoPriceMoney.getPrice();

		if (unitPromoPrice != null) {
			price.setPromoPrice(unitPromoPrice.doubleValue());
			price.setPromoPriceFormatted(unitPromoPriceMoney.format(locale));

			if (unitPromoPrice.compareTo(BigDecimal.ZERO) > 0) {
				activePrice = unitPromoPrice;
			}
		}

		CommerceMoney discountAmountMoney =
			commerceOrderItem.getDiscountAmountMoney();

		BigDecimal discountAmount = discountAmountMoney.getPrice();

		if (discountAmount != null) {
			price.setDiscount(discountAmount.doubleValue());
			price.setDiscountFormatted(discountAmountMoney.format(locale));

			BigDecimal discountPercentageLevel1 =
				commerceOrderItem.getDiscountPercentageLevel1();
			BigDecimal discountPercentageLevel2 =
				commerceOrderItem.getDiscountPercentageLevel2();
			BigDecimal discountPercentageLevel3 =
				commerceOrderItem.getDiscountPercentageLevel3();
			BigDecimal discountPercentageLevel4 =
				commerceOrderItem.getDiscountPercentageLevel4();

			price.setDiscountPercentageLevel1(
				discountPercentageLevel1.doubleValue());
			price.setDiscountPercentageLevel2(
				discountPercentageLevel2.doubleValue());
			price.setDiscountPercentageLevel3(
				discountPercentageLevel3.doubleValue());
			price.setDiscountPercentageLevel4(
				discountPercentageLevel4.doubleValue());

			BigDecimal discountPercentage = BigDecimal.ZERO;

			if (activePrice.compareTo(BigDecimal.ZERO) > 0) {
				BigDecimal discountedAmount = activePrice.subtract(
					discountAmount);

				discountPercentage = _getDiscountPercentage(
					discountedAmount, activePrice,
					RoundingMode.valueOf(commerceCurrency.getRoundingMode()));
			}

			price.setDiscountPercentage(
				_commercePriceFormatter.format(discountPercentage, locale));
		}

		CommerceMoney finalPriceMoney = commerceOrderItem.getFinalPriceMoney();

		BigDecimal finalPrice = finalPriceMoney.getPrice();

		if (finalPrice != null) {
			price.setFinalPriceFormatted(finalPriceMoney.format(locale));
			price.setFinalPrice(finalPrice.doubleValue());
		}

		return price;
	}

	private Settings _getSettings(long cpInstanceId) {
		Settings settings = new Settings();

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

				settings.setAllowedQuantities(
					ArrayUtil.toArray(allowedOrderQuantitiesArray));
			}
		}

		settings.setMinQuantity(minOrderQuantity);
		settings.setMaxQuantity(maxOrderQuantity);
		settings.setMultipleQuantity(multipleQuantity);

		return settings;
	}

	private static final BigDecimal _ONE_HUNDRED = BigDecimal.valueOf(100);

	@Reference
	private CommerceOrderItemService _commerceOrderItemService;

	@Reference
	private CommercePriceFormatter _commercePriceFormatter;

	@Reference
	private CPDefinitionInventoryLocalService
		_cpDefinitionInventoryLocalService;

	@Reference
	private CPInstanceHelper _cpInstanceHelper;

	@Reference
	private CPInstanceLocalService _cpInstanceLocalService;

}