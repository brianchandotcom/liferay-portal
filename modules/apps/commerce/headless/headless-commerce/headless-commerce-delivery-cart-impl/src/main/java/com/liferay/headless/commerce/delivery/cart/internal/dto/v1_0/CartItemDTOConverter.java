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

import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.model.CommerceMoney;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.service.CommerceOrderItemService;
import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.headless.commerce.core.dto.v1_0.converter.DTOConverter;
import com.liferay.headless.commerce.core.dto.v1_0.converter.DTOConverterContext;
import com.liferay.headless.commerce.delivery.cart.dto.v1_0.CartItem;
import com.liferay.headless.commerce.delivery.cart.dto.v1_0.Price;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;

import java.math.BigDecimal;

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
public class CartItemDTOConverter implements DTOConverter {

	@Override
	public String getContentType() {
		return CartItem.class.getSimpleName();
	}

	@Override
	public CartItem toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		CommerceOrderItem commerceOrderItem =
			_commerceOrderItemService.getCommerceOrderItem(
				dtoConverterContext.getResourcePrimKey());

		Locale locale = dtoConverterContext.getLocale();

		ExpandoBridge expandoBridge = commerceOrderItem.getExpandoBridge();

		String languageId = LanguageUtil.getLanguageId(locale);

		return new CartItem() {
			{
				customFields = expandoBridge.getAttributes();
				id = commerceOrderItem.getCommerceOrderItemId();
				name = commerceOrderItem.getName(languageId);
				options = commerceOrderItem.getJson();
				price = _getPrice(commerceOrderItem, locale);
				productId = commerceOrderItem.getCProductId();
				quantity = commerceOrderItem.getQuantity();
				sku = commerceOrderItem.getSku();
				skuId = commerceOrderItem.getCPInstanceId();
				subscription = commerceOrderItem.isSubscription();
			}
		};
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

		CommerceMoney unitPromoPriceMoney =
			commerceOrderItem.getPromoPriceMoney();

		BigDecimal unitPromoPrice = unitPromoPriceMoney.getPrice();

		if (unitPromoPrice != null) {
			price.setPromoPrice(unitPromoPrice.doubleValue());
			price.setPromoPriceFormatted(unitPromoPriceMoney.format(locale));
		}

		CommerceMoney discountAmountMoney =
			commerceOrderItem.getDiscountAmountMoney();

		BigDecimal discountAmount = discountAmountMoney.getPrice();

		if (discountAmount != null) {
			price.setDiscountFormatted(discountAmountMoney.format(locale));
			price.setDiscount(discountAmount.doubleValue());

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
		}

		CommerceMoney finalPriceMoney = commerceOrderItem.getFinalPriceMoney();

		BigDecimal finalPrice = finalPriceMoney.getPrice();

		if (finalPrice != null) {
			price.setFinalPriceFormatted(finalPriceMoney.format(locale));
			price.setFinalPrice(finalPrice.doubleValue());
		}

		return price;
	}

	@Reference
	private CommerceOrderItemService _commerceOrderItemService;

}