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
import com.liferay.commerce.currency.util.CommercePriceFormatter;
import com.liferay.commerce.discount.CommerceDiscountValue;
import com.liferay.commerce.frontend.internal.cart.model.Cart;
import com.liferay.commerce.frontend.internal.cart.model.OrderStatusInfo;
import com.liferay.commerce.frontend.internal.cart.model.Product;
import com.liferay.commerce.frontend.internal.cart.model.Summary;
import com.liferay.commerce.frontend.model.PriceModel;
import com.liferay.commerce.frontend.model.ProductSettingsModel;
import com.liferay.commerce.frontend.util.ProductHelper;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.order.CommerceOrderValidatorRegistry;
import com.liferay.commerce.order.CommerceOrderValidatorResult;
import com.liferay.commerce.price.CommerceOrderItemPrice;
import com.liferay.commerce.price.CommerceOrderPrice;
import com.liferay.commerce.price.CommerceOrderPriceCalculation;
import com.liferay.commerce.pricing.constants.CommercePricingConstants;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.product.util.CPInstanceHelper;
import com.liferay.commerce.service.CommerceOrderItemService;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(enabled = false, service = CommerceCartResourceUtil.class)
public class CommerceCartResourceUtil {

	public Cart getCart(
			long commerceOrderId, String detailsUrl, Locale locale,
			CommerceContext commerceContext, boolean valid)
		throws Exception {

		CommerceOrder commerceOrder = _commerceOrderService.getCommerceOrder(
			commerceOrderId);

		List<Product> product = getProducts(
			commerceOrder, commerceContext, locale);

		if (valid && product.isEmpty()) {
			valid = false;
		}

		String orderStatusInfoLabel = WorkflowConstants.getStatusLabel(
			commerceOrder.getStatus());

		OrderStatusInfo orderStatusInfo = new OrderStatusInfo(
			commerceOrder.getOrderStatus(), orderStatusInfoLabel,
			LanguageUtil.get(locale, orderStatusInfoLabel));

		return new Cart(
			detailsUrl, commerceOrderId, product,
			getSummary(commerceOrder, locale, commerceContext), valid,
			orderStatusInfo);
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
			CommerceOrder commerceOrder, CommerceContext commerceContext,
			Locale locale)
		throws Exception {

		List<Product> products = new ArrayList<>();

		List<CommerceOrderItem> commerceOrderItems =
			commerceOrder.getCommerceOrderItems();

		for (CommerceOrderItem commerceOrderItem : commerceOrderItems) {
			PriceModel prices = _getCommerceOrderItemPriceModel(
				commerceOrderItem, commerceContext, locale);

			ProductSettingsModel settings =
				_productHelper.getProductSettingsModel(
					commerceOrderItem.getCPInstanceId());

			Product product = new Product(
				commerceOrderItem.getCommerceOrderItemId(),
				commerceOrderItem.getParentCommerceOrderItemId(),
				commerceOrderItem.getName(locale), commerceOrderItem.getSku(),
				commerceOrderItem.getQuantity(),
				_cpInstanceHelper.getCPInstanceThumbnailSrc(
					commerceOrderItem.getCPInstanceId()),
				prices, settings, getErrorMessages(locale, commerceOrderItem),
				commerceOrderItem.getCPInstanceId());

			long commerceOptionValueCPDefinitionId =
				commerceOrderItem.getCPDefinitionId();

			if (commerceOrderItem.hasParentCommerceOrderItem()) {
				commerceOptionValueCPDefinitionId =
					commerceOrderItem.
						getParentCommerceOrderItemCPDefinitionId();
			}

			product.setOptions(
				_cpInstanceHelper.getKeyValuePairs(
					commerceOptionValueCPDefinitionId,
					commerceOrderItem.getJson(), locale));

			products.add(product);
		}

		return _groupProductByOrderItemId(products);
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

		CommerceMoney subtotalCommerceMoney = commerceOrderPrice.getSubtotal();
		CommerceMoney totalCommerceMoney = commerceOrderPrice.getTotal();

		int itemsQuantity =
			_commerceOrderItemService.getCommerceOrderItemsQuantity(
				commerceOrder.getCommerceOrderId());

		CommerceDiscountValue totalCommerceDiscountValue =
			commerceOrderPrice.getTotalDiscountValue();

		CommerceChannel commerceChannel =
			_commerceChannelLocalService.getCommerceChannelByOrderGroupId(
				commerceOrder.getGroupId());

		String priceDisplayType = commerceChannel.getPriceDisplayType();

		if (priceDisplayType.equals(
				CommercePricingConstants.TAX_INCLUDED_IN_PRICE)) {

			subtotalCommerceMoney =
				commerceOrderPrice.getSubtotalWithTaxAmount();
			totalCommerceMoney = commerceOrderPrice.getTotalWithTaxAmount();
			totalCommerceDiscountValue =
				commerceOrderPrice.getTotalDiscountValueWithTaxAmount();
		}

		Summary summary = new Summary(
			subtotalCommerceMoney.format(locale),
			totalCommerceMoney.format(locale), itemsQuantity);

		if (totalCommerceDiscountValue != null) {
			CommerceMoney discountAmountCommerceMoney =
				totalCommerceDiscountValue.getDiscountAmount();

			summary.setDiscount(discountAmountCommerceMoney.format(locale));
		}

		return summary;
	}

	private PriceModel _getCommerceOrderItemPriceModel(
			CommerceOrderItem commerceOrderItem,
			CommerceContext commerceContext, Locale locale)
		throws Exception {

		CommerceOrderItemPrice commerceOrderItemPrice =
			_commerceOrderPriceCalculation.getCommerceOrderItemPricePerUnit(
				commerceContext.getCommerceCurrency(), commerceOrderItem);

		return _getPriceModel(
			commerceOrderItemPrice.getUnitPrice(),
			commerceOrderItemPrice.getPromoPrice(),
			commerceOrderItemPrice.getDiscountAmount(),
			commerceOrderItemPrice.getDiscountPercentage(),
			commerceOrderItemPrice.getDiscountPercentageLevel1(),
			commerceOrderItemPrice.getDiscountPercentageLevel2(),
			commerceOrderItemPrice.getDiscountPercentageLevel3(),
			commerceOrderItemPrice.getDiscountPercentageLevel4(),
			commerceOrderItemPrice.getFinalPrice(), locale);
	}

	private PriceModel _getPriceModel(
			CommerceMoney unitPriceCommerceMoney,
			CommerceMoney promoPriceCommerceMoney,
			CommerceMoney discountAmountCommerceMoney,
			BigDecimal discountPercentage, BigDecimal discountPercentageLevel1,
			BigDecimal discountPercentageLevel2,
			BigDecimal discountPercentageLevel3,
			BigDecimal discountPercentageLevel4,
			CommerceMoney finalPriceCommerceMoney, Locale locale)
		throws Exception {

		PriceModel priceModel = new PriceModel(
			unitPriceCommerceMoney.format(locale));

		if (promoPriceCommerceMoney != null) {
			priceModel.setPromoPrice(promoPriceCommerceMoney.format(locale));
		}

		if (discountAmountCommerceMoney == null) {
			return priceModel;
		}

		BigDecimal discountAmount = discountAmountCommerceMoney.getPrice();

		if ((discountAmount == null) ||
			(discountAmount.compareTo(BigDecimal.ZERO) == 0)) {

			return priceModel;
		}

		priceModel.setDiscount(discountAmountCommerceMoney.format(locale));

		priceModel.setDiscountPercentage(
			_commercePriceFormatter.format(discountPercentage, locale));

		BigDecimal level1 = BigDecimal.ZERO;
		BigDecimal level2 = BigDecimal.ZERO;
		BigDecimal level3 = BigDecimal.ZERO;
		BigDecimal level4 = BigDecimal.ZERO;

		if (discountPercentageLevel1 != null) {
			level1 = discountPercentageLevel1;
		}

		if (discountPercentageLevel2 != null) {
			level2 = discountPercentageLevel2;
		}

		if (discountPercentageLevel3 != null) {
			level3 = discountPercentageLevel3;
		}

		if (discountPercentageLevel4 != null) {
			level4 = discountPercentageLevel4;
		}

		String[] discountPercentages = {
			level1.toString(), level2.toString(), level3.toString(),
			level4.toString()
		};

		priceModel.setDiscountPercentages(discountPercentages);

		priceModel.setFinalPrice(finalPriceCommerceMoney.format(locale));

		return priceModel;
	}

	private List<Product> _groupProductByOrderItemId(List<Product> products) {
		Map<Long, Product> productMap = new HashMap<>();

		for (Product product : products) {
			productMap.put(product.getId(), product);
		}

		for (Product product : products) {
			long parentProductId = product.getParentProductId();

			if (parentProductId == 0) {
				continue;
			}

			Product parent = productMap.get(parentProductId);

			if (parent != null) {
				if (parent.getChildItems() == null) {
					parent.setChildItems(new ArrayList<>());
				}

				List<Product> childItems = parent.getChildItems();

				childItems.add(product);

				productMap.remove(product.getId());
			}
		}

		return new ArrayList(productMap.values());
	}

	@Reference
	private CommerceChannelLocalService _commerceChannelLocalService;

	@Reference
	private CommerceOrderItemService _commerceOrderItemService;

	@Reference
	private CommerceOrderPriceCalculation _commerceOrderPriceCalculation;

	@Reference
	private CommerceOrderService _commerceOrderService;

	@Reference
	private CommerceOrderValidatorRegistry _commerceOrderValidatorRegistry;

	@Reference
	private CommercePriceFormatter _commercePriceFormatter;

	@Reference
	private CPInstanceHelper _cpInstanceHelper;

	@Reference
	private ProductHelper _productHelper;

}