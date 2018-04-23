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

package com.liferay.commerce.service.impl;

import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.service.CommerceCurrencyService;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.model.CommercePriceEntry;
import com.liferay.commerce.model.CommercePriceList;
import com.liferay.commerce.model.CommerceTierPriceEntry;
import com.liferay.commerce.price.CommercePriceFormatter;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.service.CPInstanceLocalService;
import com.liferay.commerce.service.base.CommercePriceCalculationLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.math.BigDecimal;

import java.util.List;
import java.util.Optional;

/**
 * @author Marco Leo
 */
public class CommercePriceCalculationLocalServiceImpl
	extends CommercePriceCalculationLocalServiceBaseImpl {

	@Override
	public String formatPrice(long groupId, BigDecimal price)
		throws PortalException {

		CommerceCurrency commerceCurrency =
			_commerceCurrencyService.fetchPrimaryCommerceCurrency(groupId);

		return commercePriceCalculationLocalService.formatPriceWithCurrency(
			commerceCurrency.getCommerceCurrencyId(), price);
	}

	@Override
	public String formatPriceWithCurrency(
			long commerceCurrencyId, BigDecimal price)
		throws PortalException {

		CommerceCurrency commerceCurrency =
			_commerceCurrencyService.getCommerceCurrency(commerceCurrencyId);

		return _commercePriceFormatter.format(commerceCurrency, price);
	}

	@Override
	public BigDecimal getFinalPrice(
			long groupId, long userId, long cpInstanceId, int quantity)
		throws PortalException {

		BigDecimal price = getUnitPrice(
			groupId, userId, cpInstanceId, quantity);

		return price.multiply(new BigDecimal(quantity));
	}

	@Override
	public BigDecimal getFinalPrice(
			long groupId, long commerceCurrencyId, long userId,
			long cpInstanceId, int quantity)
		throws PortalException {

		CommerceCurrency commerceCurrency =
			_commerceCurrencyService.fetchPrimaryCommerceCurrency(groupId);

		BigDecimal price = getUnitPrice(
			groupId, commerceCurrency.getCommerceCurrencyId(), userId,
			cpInstanceId, quantity);

		return price.multiply(new BigDecimal(quantity));
	}

	@Override
	public String getFormattedFinalPrice(
			long groupId, long userId, long cpInstanceId, int quantity)
		throws PortalException {

		CommerceCurrency commerceCurrency =
			_commerceCurrencyService.fetchPrimaryCommerceCurrency(groupId);

		BigDecimal price = getFinalPrice(
			groupId, commerceCurrency.getCommerceCurrencyId(), userId,
			cpInstanceId, quantity);

		return _commercePriceFormatter.format(commerceCurrency, price);
	}

	@Override
	public String getFormattedFinalPrice(
			long groupId, long commerceCurrencyId, long userId,
			long cpInstanceId, int quantity)
		throws PortalException {

		CommerceCurrency commerceCurrency =
			_commerceCurrencyService.getCommerceCurrency(commerceCurrencyId);

		BigDecimal price = getFinalPrice(
			groupId, commerceCurrencyId, userId, cpInstanceId, quantity);

		return _commercePriceFormatter.format(commerceCurrency, price);
	}

	@Override
	public String getFormattedOrderSubtotal(CommerceOrder commerceOrder)
		throws PortalException {

		BigDecimal orderSubtotal = getOrderSubtotal(commerceOrder);

		return _commercePriceFormatter.format(
			commerceOrder.getSiteGroupId(), orderSubtotal);
	}

	@Override
	public BigDecimal getOrderSubtotal(CommerceOrder commerceOrder)
		throws PortalException {

		if (commerceOrder == null) {
			return BigDecimal.ZERO;
		}

		BigDecimal orderSubtotal = BigDecimal.ZERO;

		List<CommerceOrderItem> commerceOrderItems =
			commerceOrder.getCommerceOrderItems();

		for (CommerceOrderItem commerceOrderItem : commerceOrderItems) {
			orderSubtotal = orderSubtotal.add(
				getFinalPrice(
					commerceOrder.getSiteGroupId(),
					commerceOrder.getOrderUserId(),
					commerceOrderItem.getCPInstanceId(),
					commerceOrderItem.getQuantity()));
		}

		return orderSubtotal;
	}

	@Override
	public BigDecimal getUnitPrice(
			long groupId, long userId, long cpInstanceId, int quantity)
		throws PortalException {

		CommerceCurrency commerceCurrency =
			_commerceCurrencyService.fetchPrimaryCommerceCurrency(groupId);

		return getUnitPrice(
			groupId, commerceCurrency.getCommerceCurrencyId(), userId,
			cpInstanceId, quantity);
	}

	@Override
	public BigDecimal getUnitPrice(
			long groupId, long commerceCurrencyId, long userId,
			long cpInstanceId, int quantity)
		throws PortalException {

		CPInstance cpInstance = _cpInstanceService.getCPInstance(cpInstanceId);

		BigDecimal price = cpInstance.getPrice();

		Optional<CommercePriceList> commercePriceListOptional =
			commercePriceListLocalService.getUserCommercePriceList(
				groupId, userId);

		if (commercePriceListOptional.isPresent()) {
			CommercePriceList commercePriceList =
				commercePriceListOptional.get();

			CommercePriceEntry commercePriceEntry =
				commercePriceEntryLocalService.fetchCommercePriceEntry(
					cpInstanceId, commercePriceList.getCommercePriceListId());

			if (commercePriceEntry != null) {
				price = commercePriceEntry.getPrice();

				if (commercePriceEntry.getHasTierPrice()) {
					CommerceTierPriceEntry commerceTierPriceEntry =
						commerceTierPriceEntryLocalService.
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
			_commerceCurrencyService.getCommerceCurrency(commerceCurrencyId);

		if (!commerceCurrency.isPrimary()) {
			price = price.multiply(commerceCurrency.getRate());
		}

		return price;
	}

	@ServiceReference(type = CommerceCurrencyService.class)
	private CommerceCurrencyService _commerceCurrencyService;

	@ServiceReference(type = CommercePriceFormatter.class)
	private CommercePriceFormatter _commercePriceFormatter;

	@ServiceReference(type = CPInstanceLocalService.class)
	private CPInstanceLocalService _cpInstanceService;

}