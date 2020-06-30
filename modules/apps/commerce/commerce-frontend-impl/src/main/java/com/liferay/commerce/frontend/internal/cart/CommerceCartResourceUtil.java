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

package com.liferay.commerce.frontend.internal.cart;

import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.currency.model.CommerceMoney;
import com.liferay.commerce.discount.CommerceDiscountValue;
import com.liferay.commerce.frontend.internal.cart.model.Cart;
import com.liferay.commerce.frontend.internal.cart.model.Product;
import com.liferay.commerce.frontend.internal.cart.model.Summary;
import com.liferay.commerce.frontend.model.PriceModel;
import com.liferay.commerce.frontend.model.ProductSettingsModel;
import com.liferay.commerce.frontend.util.ProductHelper;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.order.CommerceOrderHttpHelper;
import com.liferay.commerce.order.CommerceOrderValidatorRegistry;
import com.liferay.commerce.order.CommerceOrderValidatorResult;
import com.liferay.commerce.price.CommerceOrderPrice;
import com.liferay.commerce.price.CommerceOrderPriceCalculation;
import com.liferay.commerce.product.util.CPInstanceHelper;
import com.liferay.commerce.service.CommerceOrderItemService;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ArrayUtil;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(service = CommerceCartResourceUtil.class)
public class CommerceCartResourceUtil {

	public Cart getCart(
			long commerceOrderId, String detailsUrl, Locale locale,
			CommerceContext commerceContext, boolean valid)
		throws Exception {

		CommerceOrder commerceOrder = _commerceOrderService.getCommerceOrder(
			commerceOrderId);

		List<Product> product = getProducts(
			commerceOrder, locale, commerceContext);

		if (valid && product.isEmpty()) {
			valid = false;
		}

		return new Cart(
			detailsUrl, commerceOrderId, product,
			getSummary(commerceOrder, locale, commerceContext), valid);
	}

	protected String[] getErrorMessages(
			Locale locale, CommerceOrderItem commerceOrderItem)
		throws PortalException {

		String[] errorMessages = new String[0];

		List<CommerceOrderValidatorResult> commerceOrderValidatorResults =
			_commerceOrderValidatorRegistry.validate(locale, commerceOrderItem);

		for (CommerceOrderValidatorResult commerceOrderValidatorResult :
				commerceOrderValidatorResults) {

			errorMessages = ArrayUtil.append(
				errorMessages,
				commerceOrderValidatorResult.getLocalizedMessage());
		}

		return errorMessages;
	}

	protected List<Product> getProducts(
			CommerceOrder commerceOrder, Locale locale,
			CommerceContext commerceContext)
		throws Exception {

		List<Product> products = new ArrayList<>();

		List<CommerceOrderItem> commerceOrderItems =
			commerceOrder.getCommerceOrderItems();

		for (CommerceOrderItem commerceOrderItem : commerceOrderItems) {
			PriceModel prices = _getCommerceOrderItemPriceModel(
				commerceOrderItem, locale);

			ProductSettingsModel settings =
				_productHelper.getProductSettingsModel(
					commerceOrderItem.getCPInstanceId());

			Product product = new Product(
				commerceOrderItem.getCommerceOrderItemId(),
				commerceOrderItem.getName(locale), commerceOrderItem.getSku(),
				commerceOrderItem.getQuantity(),
				_cpInstanceHelper.getCPInstanceThumbnailSrc(
					commerceOrderItem.getCPInstanceId()),
				prices, settings, getErrorMessages(locale, commerceOrderItem),
				commerceOrderItem.getCPInstanceId());

			product.setOptions(
				_cpInstanceHelper.getKeyValuePairs(
					commerceOrderItem.getCPDefinitionId(),
					commerceOrderItem.getJson(), locale));

			products.add(product);
		}

		return products;
	}

	protected Summary getSummary(
			CommerceOrder commerceOrder, Locale locale,
			CommerceContext commerceContext)
		throws PortalException {

		CommerceOrderPrice commerceOrderPrice =
			_commerceOrderPriceCalculation.getCommerceOrderPrice(
				commerceOrder, commerceContext);

		if (commerceOrderPrice == null) {
			return null;
		}

		CommerceMoney subtotal = commerceOrderPrice.getSubtotal();
		CommerceMoney total = commerceOrderPrice.getTotal();

		int itemsQuantity =
			_commerceOrderItemService.getCommerceOrderItemsQuantity(
				commerceOrder.getCommerceOrderId());

		Summary summary = new Summary(
			subtotal.format(locale), total.format(locale), itemsQuantity);

		CommerceDiscountValue totalDiscountValue =
			commerceOrderPrice.getTotalDiscountValue();

		if (totalDiscountValue != null) {
			CommerceMoney discountAmount =
				totalDiscountValue.getDiscountAmount();

			summary.setDiscount(discountAmount.format(locale));
		}

		return summary;
	}

	private PriceModel _getCommerceOrderItemPriceModel(
			CommerceOrderItem commerceOrderItem, Locale locale)
		throws PortalException {

		CommerceMoney unitPriceMoney = commerceOrderItem.getUnitPriceMoney();
		CommerceMoney promoPriceMoney = commerceOrderItem.getPromoPriceMoney();

		CommerceMoney discountAmountMoney =
			commerceOrderItem.getDiscountAmountMoney();

		BigDecimal level1 = commerceOrderItem.getDiscountPercentageLevel1();
		BigDecimal level2 = commerceOrderItem.getDiscountPercentageLevel2();
		BigDecimal level3 = commerceOrderItem.getDiscountPercentageLevel3();
		BigDecimal level4 = commerceOrderItem.getDiscountPercentageLevel4();

		String[] discountPercentages = {
			level1.toString(), level2.toString(), level3.toString(),
			level4.toString()
		};

		PriceModel prices = new PriceModel(unitPriceMoney.format(locale));

		BigDecimal promoPrice = promoPriceMoney.getPrice();

		if ((promoPriceMoney != null) &&
			(promoPrice.compareTo(BigDecimal.ZERO) > 0)) {

			prices.setPromoPrice(promoPriceMoney.format(locale));
		}

		if (discountAmountMoney != null) {
			prices.setDiscount(discountAmountMoney.format(locale));
		}

		prices.setDiscountPercentages(discountPercentages);

		return prices;
	}

	@Reference
	private CommerceOrderHttpHelper _commerceOrderHttpHelper;

	@Reference
	private CommerceOrderItemService _commerceOrderItemService;

	@Reference
	private CommerceOrderPriceCalculation _commerceOrderPriceCalculation;

	@Reference
	private CommerceOrderService _commerceOrderService;

	@Reference
	private CommerceOrderValidatorRegistry _commerceOrderValidatorRegistry;

	@Reference
	private CPInstanceHelper _cpInstanceHelper;

	@Reference
	private ProductHelper _productHelper;

}