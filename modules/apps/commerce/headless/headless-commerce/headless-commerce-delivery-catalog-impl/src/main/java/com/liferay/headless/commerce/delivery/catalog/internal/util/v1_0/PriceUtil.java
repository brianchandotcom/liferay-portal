/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.delivery.catalog.internal.util.v1_0;

import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.model.CommerceMoney;
import com.liferay.commerce.currency.model.CommerceMoneyFactory;
import com.liferay.commerce.currency.service.CommerceCurrencyLocalService;
import com.liferay.commerce.price.list.model.CommercePriceEntry;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.BigDecimalUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author Danny Situ
 */
public class PriceUtil {

	public static BigDecimal getConvertedPrice(
			CommerceCurrency commerceCurrency,
			CommerceCurrencyLocalService commerceCurrencyLocalService,
			CommercePriceList commercePriceList, BigDecimal price)
		throws PortalException {

		CommerceCurrency priceListCommerceCurrency =
			commerceCurrencyLocalService.getCommerceCurrency(
				commercePriceList.getCompanyId(),
				commercePriceList.getCommerceCurrencyCode());

		if (priceListCommerceCurrency.getCommerceCurrencyId() !=
				commerceCurrency.getCommerceCurrencyId()) {

			price = price.divide(
				priceListCommerceCurrency.getRate(),
				RoundingMode.valueOf(
					priceListCommerceCurrency.getRoundingMode()));

			price = price.multiply(commerceCurrency.getRate());
		}

		return price;
	}

	public static CommerceMoney getPricingQuantityUnitPriceCommerceMoney(
			CommerceCurrency commerceCurrency,
			CommerceCurrencyLocalService commerceCurrencyLocalService,
			CommerceMoneyFactory commerceMoneyFactory,
			CommercePriceEntry commercePriceEntry, BigDecimal price)
		throws PortalException {

		BigDecimal pricingQuantity = commercePriceEntry.getPricingQuantity();

		if ((pricingQuantity == null) ||
			BigDecimalUtil.lte(pricingQuantity, BigDecimal.ZERO)) {

			return commerceMoneyFactory.emptyCommerceMoney();
		}

		BigDecimal pricingQuantityUnitPrice = pricingQuantity.multiply(price);

		pricingQuantityUnitPrice = pricingQuantityUnitPrice.divide(
			commercePriceEntry.getQuantity(),
			commerceCurrency.getMaxFractionDigits(),
			RoundingMode.valueOf(commerceCurrency.getRoundingMode()));

		return commerceMoneyFactory.create(
			commerceCurrency,
			getConvertedPrice(
				commerceCurrency, commerceCurrencyLocalService,
				commercePriceEntry.getCommercePriceList(),
				pricingQuantityUnitPrice));
	}

}