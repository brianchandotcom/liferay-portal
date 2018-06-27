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
import com.liferay.commerce.discount.CommerceDiscountCalculation;
import com.liferay.commerce.discount.CommerceDiscountValue;
import com.liferay.commerce.price.CommerceProductPrice;
import com.liferay.commerce.price.CommerceProductPriceCalculation;
import com.liferay.commerce.price.list.model.CommercePriceEntry;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.model.CommerceTierPriceEntry;
import com.liferay.commerce.price.list.service.CommercePriceEntryLocalService;
import com.liferay.commerce.price.list.service.CommercePriceListLocalService;
import com.liferay.commerce.price.list.service.CommerceTierPriceEntryLocalService;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.service.CPInstanceService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.math.BigDecimal;

import java.util.List;
import java.util.Optional;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component
public class CommerceProductPriceCalculationImpl
	implements CommerceProductPriceCalculation {

	@Override
	public CommerceProductPrice getCommerceProductPrice(
			long cpInstanceId, int quantity, CommerceContext commerceContext)
		throws PortalException {

		CommerceMoney unitPrice = getUnitPrice(
			cpInstanceId, quantity, commerceContext.getCommercePriceList(),
			commerceContext.getCommerceCurrency());

		CommerceMoney promoPrice = getPromoPrice(
			cpInstanceId, quantity, commerceContext.getCommercePriceList(),
			commerceContext.getCommerceCurrency());

		CommerceProductPriceImpl commerceProductPrice =
			new CommerceProductPriceImpl();

		commerceProductPrice.setQuantity(quantity);
		commerceProductPrice.setUnitPrice(unitPrice);
		commerceProductPrice.setUnitPromoPrice(promoPrice);

		CommerceDiscountValue commerceDiscountValue =
			_commerceDiscountCalculation.getProductCommerceDiscountValue(
				cpInstanceId, quantity, unitPrice.getPrice(), commerceContext);

		BigDecimal finalPrice = unitPrice.getPrice();

		BigDecimal promo = promoPrice.getPrice();

		if ((promo.compareTo(BigDecimal.ZERO) > 0) &&
			(promo.compareTo(unitPrice.getPrice()) <= 0)) {

			finalPrice = promoPrice.getPrice();
		}

		finalPrice = finalPrice.multiply(BigDecimal.valueOf(quantity));

		if (commerceDiscountValue != null) {
			finalPrice = finalPrice.subtract(
				commerceDiscountValue.getDiscountAmount());
		}

		commerceProductPrice.setCommerceDiscountValue(commerceDiscountValue);
		commerceProductPrice.setFinalPrice(
			_commerceMoneyFactory.create(
				commerceContext.getCommerceCurrency(), finalPrice));

		return commerceProductPrice;
	}

	@Override
	public CommerceMoney getPromoPrice(
			long cpInstanceId, int quantity,
			Optional<CommercePriceList> commercePriceList,
			CommerceCurrency commerceCurrency)
		throws PortalException {

		CPInstance cpInstance = _cpInstanceService.getCPInstance(cpInstanceId);

		BigDecimal price = cpInstance.getPromoPrice();

		if (commercePriceList.isPresent()) {
			price = _getPriceListPrice(
				cpInstanceId, quantity, commercePriceList.get(), true);
		}

		if ((commerceCurrency != null) && !commerceCurrency.isPrimary()) {
			price = price.multiply(commerceCurrency.getRate());
		}

		return _commerceMoneyFactory.create(commerceCurrency, price);
	}

	@Override
	public CommerceMoney getUnitMaxPrice(
			long cpDefinitionId, int quantity, CommerceContext commerceContext)
		throws PortalException {

		CommerceMoney commerceMoney = null;
		BigDecimal maxPrice = BigDecimal.ZERO;

		List<CPInstance> cpInstances =
			_cpInstanceService.getCPDefinitionInstances(
				cpDefinitionId, WorkflowConstants.STATUS_APPROVED,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		for (CPInstance cpInstance : cpInstances) {
			CommerceMoney cpInstanceCommerceMoney = getUnitPrice(
				cpInstance.getCPInstanceId(), quantity,
				commerceContext.getCommercePriceList(),
				commerceContext.getCommerceCurrency());

			if (maxPrice.compareTo(cpInstanceCommerceMoney.getPrice()) < 0) {
				commerceMoney = cpInstanceCommerceMoney;

				maxPrice = commerceMoney.getPrice();
			}
		}

		return commerceMoney;
	}

	@Override
	public CommerceMoney getUnitMinPrice(
			long cpDefinitionId, int quantity, CommerceContext commerceContext)
		throws PortalException {

		CommerceMoney commerceMoney = null;
		BigDecimal minPrice = BigDecimal.ZERO;

		List<CPInstance> cpInstances =
			_cpInstanceService.getCPDefinitionInstances(
				cpDefinitionId, WorkflowConstants.STATUS_APPROVED,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		for (CPInstance cpInstance : cpInstances) {
			CommerceMoney cpInstanceCommerceMoney = getUnitPrice(
				cpInstance.getCPInstanceId(), quantity,
				commerceContext.getCommercePriceList(),
				commerceContext.getCommerceCurrency());

			if ((commerceMoney == null) ||
				(minPrice.compareTo(cpInstanceCommerceMoney.getPrice()) > 0)) {

				commerceMoney = cpInstanceCommerceMoney;

				minPrice = commerceMoney.getPrice();
			}
		}

		return commerceMoney;
	}

	@Override
	public CommerceMoney getUnitPrice(
			long cpInstanceId, int quantity,
			Optional<CommercePriceList> commercePriceList,
			CommerceCurrency commerceCurrency)
		throws PortalException {

		CPInstance cpInstance = _cpInstanceService.getCPInstance(cpInstanceId);

		BigDecimal price = cpInstance.getPrice();

		if (commercePriceList.isPresent()) {
			price = _getPriceListPrice(
				cpInstanceId, quantity, commercePriceList.get(), false);
		}

		if ((commerceCurrency != null) && !commerceCurrency.isPrimary()) {
			price = price.multiply(commerceCurrency.getRate());
		}

		return _commerceMoneyFactory.create(commerceCurrency, price);
	}

	private BigDecimal _getPriceListPrice(
			long cpInstanceId, int quantity,
			CommercePriceList commercePriceList, boolean promo)
		throws PortalException {

		BigDecimal price = BigDecimal.ZERO;

		CommercePriceEntry commercePriceEntry =
			_commercePriceEntryLocalService.fetchCommercePriceEntry(
				cpInstanceId, commercePriceList.getCommercePriceListId());

		if (commercePriceEntry != null) {
			if (promo) {
				price = commercePriceEntry.getPromoPrice();
			}
			else {
				price = commercePriceEntry.getPrice();
			}

			if (commercePriceEntry.getHasTierPrice()) {
				CommerceTierPriceEntry commerceTierPriceEntry =
					_commerceTierPriceEntryLocalService.
						findClosestCommerceTierPriceEntry(
							commercePriceEntry.getCommercePriceEntryId(),
							quantity);

				if (commerceTierPriceEntry != null) {
					if (promo) {
						price = commerceTierPriceEntry.getPromoPrice();
					}
					else {
						price = commerceTierPriceEntry.getPrice();
					}
				}
			}

			CommerceCurrency priceListCurrency =
				_commerceCurrencyService.getCommerceCurrency(
					commercePriceList.getCommerceCurrencyId());

			if (!priceListCurrency.isPrimary()) {
				price = price.divide(priceListCurrency.getRate());
			}
		}

		return price;
	}

	@Reference
	private CommerceCurrencyService _commerceCurrencyService;

	@Reference
	private CommerceDiscountCalculation _commerceDiscountCalculation;

	@Reference
	private CommerceMoneyFactory _commerceMoneyFactory;

	@Reference
	private CommercePriceEntryLocalService _commercePriceEntryLocalService;

	@Reference
	private CommercePriceListLocalService _commercePriceListLocalService;

	@Reference
	private CommerceTierPriceEntryLocalService
		_commerceTierPriceEntryLocalService;

	@Reference
	private CPInstanceService _cpInstanceService;

}