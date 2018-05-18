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

package com.liferay.commerce.internal.price;

import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.model.CommerceMoney;
import com.liferay.commerce.currency.model.CommerceMoneyFactory;
import com.liferay.commerce.currency.service.CommerceCurrencyService;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.price.CommercePriceCalculation;
import com.liferay.commerce.price.list.model.CommercePriceEntry;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.model.CommerceTierPriceEntry;
import com.liferay.commerce.price.list.service.CommercePriceEntryLocalService;
import com.liferay.commerce.price.list.service.CommerceTierPriceEntryLocalService;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.service.CPInstanceService;
import com.liferay.portal.kernel.exception.PortalException;

import java.math.BigDecimal;

import java.util.List;
import java.util.Optional;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component
public class CommercePriceCalculationImpl implements CommercePriceCalculation {

	@Override
	public CommerceMoney getFinalPrice(
			long cpInstanceId, int quantity, boolean includeDiscounts,
			boolean includeTaxes, CommerceContext commerceContext)
		throws PortalException {

		CommerceMoney commerceMoney = getUnitPrice(
			cpInstanceId, quantity, commerceContext);

		BigDecimal price = commerceMoney.getPrice();

		return _commerceMoneyFactory.create(
			commerceMoney.getCommerceCurrency(),
			price.multiply(BigDecimal.valueOf(quantity)));
	}

	@Override
	public CommerceMoney getOrderSubtotal(
			CommerceOrder commerceOrder, CommerceContext commerceContext)
		throws PortalException {

		if (commerceOrder == null) {
			return _commerceMoneyFactory.create(
				commerceContext.getCommerceCurrency(), BigDecimal.ZERO);
		}

		BigDecimal orderSubtotal = BigDecimal.ZERO;

		List<CommerceOrderItem> commerceOrderItems =
			commerceOrder.getCommerceOrderItems();

		for (CommerceOrderItem commerceOrderItem : commerceOrderItems) {
			CommerceMoney commerceMoney = getFinalPrice(
				commerceOrderItem.getCPInstanceId(),
				commerceOrderItem.getQuantity(), false, false, commerceContext);

			orderSubtotal = orderSubtotal.add(commerceMoney.getPrice());
		}

		return _commerceMoneyFactory.create(
			commerceContext.getCommerceCurrency(), orderSubtotal);
	}

	@Override
	public CommerceMoney getUnitPrice(
			long cpInstanceId, int quantity, CommerceContext commerceContext)
		throws PortalException {

		CPInstance cpInstance = _cpInstanceService.getCPInstance(cpInstanceId);

		BigDecimal price = cpInstance.getPrice();

		Optional<CommercePriceList> commercePriceListOptional =
			commerceContext.getCommercePriceList();

		if (commercePriceListOptional.isPresent()) {
			CommercePriceList commercePriceList =
				commercePriceListOptional.get();

			CommercePriceEntry commercePriceEntry =
				_commercePriceEntryLocalService.fetchCommercePriceEntry(
					cpInstanceId, commercePriceList.getCommercePriceListId());

			if (commercePriceEntry != null) {
				price = commercePriceEntry.getPrice();

				if (commercePriceEntry.getHasTierPrice()) {
					CommerceTierPriceEntry commerceTierPriceEntry =
						_commerceTierPriceEntryLocalService.
							findClosestCommerceTierPriceEntry(
								commercePriceEntry.getCommercePriceEntryId(),
								quantity);

					if (commerceTierPriceEntry != null) {
						price = commerceTierPriceEntry.getPrice();
					}
				}

				CommerceCurrency priceListCurrency =
					_commerceCurrencyService.getCommerceCurrency(
						commercePriceList.getCommerceCurrencyId());

				if (!priceListCurrency.isPrimary()) {
					price = price.divide(priceListCurrency.getRate());
				}
			}
		}

		CommerceCurrency commerceCurrency =
			commerceContext.getCommerceCurrency();

		if (!commerceCurrency.isPrimary()) {
			price = price.multiply(commerceCurrency.getRate());
		}

		return _commerceMoneyFactory.create(commerceCurrency, price);
	}

	@Reference
	private CommerceCurrencyService _commerceCurrencyService;

	@Reference
	private CommerceMoneyFactory _commerceMoneyFactory;

	@Reference
	private CommercePriceEntryLocalService _commercePriceEntryLocalService;

	@Reference
	private CommerceTierPriceEntryLocalService
		_commerceTierPriceEntryLocalService;

	@Reference
	private CPInstanceService _cpInstanceService;

}