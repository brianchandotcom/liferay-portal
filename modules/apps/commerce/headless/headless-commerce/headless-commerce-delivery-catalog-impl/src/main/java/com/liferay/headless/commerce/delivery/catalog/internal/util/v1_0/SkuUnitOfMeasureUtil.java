/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.delivery.catalog.internal.util.v1_0;

import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.model.CommerceMoney;
import com.liferay.commerce.currency.model.CommerceMoneyFactory;
import com.liferay.commerce.currency.service.CommerceCurrencyLocalService;
import com.liferay.commerce.currency.util.CommercePriceFormatter;
import com.liferay.commerce.price.CommerceProductPriceCalculation;
import com.liferay.commerce.price.list.model.CommercePriceEntry;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.model.CommerceTierPriceEntry;
import com.liferay.commerce.price.list.service.CommerceTierPriceEntryLocalService;
import com.liferay.commerce.price.list.util.comparator.CommerceTierPriceEntryMinQuantityComparator;
import com.liferay.commerce.product.model.CPInstanceUnitOfMeasure;
import com.liferay.commerce.util.CommerceQuantityFormatter;
import com.liferay.headless.commerce.delivery.catalog.dto.v1_0.Price;
import com.liferay.headless.commerce.delivery.catalog.dto.v1_0.SkuUnitOfMeasure;
import com.liferay.headless.commerce.delivery.catalog.dto.v1_0.TierPrice;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.BigDecimalUtil;
import com.liferay.portal.vulcan.util.TransformUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;

import java.util.Locale;

/**
 * @author Brian I. Kim
 */
public class SkuUnitOfMeasureUtil {

	public static SkuUnitOfMeasure toSkuUnitOfMeasure(
			CommerceContext commerceContext,
			CommerceCurrencyLocalService commerceCurrencyLocalService,
			CommerceMoneyFactory commerceMoneyFactory,
			CommercePriceFormatter commercePriceFormatter,
			CommerceProductPriceCalculation commerceProductPriceCalculation,
			CommerceQuantityFormatter commerceQuantityFormatter,
			CommerceTierPriceEntryLocalService
				commerceTierPriceEntryLocalService,
			CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure, Locale locale)
		throws Exception {

		CommerceCurrency commerceCurrency =
			commerceContext.getCommerceCurrency();

		CommercePriceEntry commercePriceEntry =
			commerceProductPriceCalculation.getUnitCommercePriceEntry(
				commerceContext, cpInstanceUnitOfMeasure.getCPInstanceId(),
				cpInstanceUnitOfMeasure.getKey());

		return new SkuUnitOfMeasure() {
			{
				setIncrementalOrderQuantity(
					() -> {
						BigDecimal incrementalOrderQuantity =
							cpInstanceUnitOfMeasure.
								getIncrementalOrderQuantity();

						if (incrementalOrderQuantity == null) {
							return null;
						}

						return incrementalOrderQuantity.setScale(
							cpInstanceUnitOfMeasure.getPrecision(),
							RoundingMode.HALF_UP);
					});
				setKey(cpInstanceUnitOfMeasure::getKey);
				setName(() -> cpInstanceUnitOfMeasure.getName(locale));
				setPrecision(cpInstanceUnitOfMeasure::getPrecision);
				setPrice(
					() -> {
						if (commercePriceEntry == null) {
							return null;
						}

						CommerceMoney pricingQuantityUnitPriceCommerceMoney =
							_getPricingQuantityUnitPriceCommerceMoney(
								commerceCurrency, commerceCurrencyLocalService,
								commerceMoneyFactory, commercePriceEntry,
								commercePriceEntry.getPrice());

						return new Price() {
							{
								setCurrency(
									() -> commerceCurrency.getName(locale));

								BigDecimal convertedPrice = _getConvertedPrice(
									commerceCurrency,
									commerceCurrencyLocalService,
									commercePriceEntry.getCommercePriceList(),
									commercePriceEntry.getPrice());

								setPrice(convertedPrice::doubleValue);
								setPriceFormatted(
									() -> commercePriceFormatter.format(
										commerceCurrency, true, locale,
										convertedPrice));

								setPriceOnApplication(
									commercePriceEntry::isPriceOnApplication);
								setPricingQuantityPrice(
									() -> {
										if (pricingQuantityUnitPriceCommerceMoney ==
												null) {

											return null;
										}

										BigDecimal pricingQuantityUnitPrice =
											pricingQuantityUnitPriceCommerceMoney.
												getPrice();

										if (pricingQuantityUnitPrice == null) {
											return null;
										}

										return pricingQuantityUnitPrice.
											doubleValue();
									});
								setPricingQuantityPriceFormatted(
									() -> {
										if (pricingQuantityUnitPriceCommerceMoney ==
												null) {

											return null;
										}

										BigDecimal pricingQuantity =
											BigDecimalUtil.get(
												cpInstanceUnitOfMeasure.
													getPricingQuantity(),
												BigDecimal.ZERO);

										if (BigDecimalUtil.lte(
												pricingQuantity,
												BigDecimal.ZERO)) {

											pricingQuantity = BigDecimal.ONE;
										}

										return pricingQuantityUnitPriceCommerceMoney.
											format(
												locale, pricingQuantity,
												cpInstanceUnitOfMeasure.getName(
													locale));
									});
							}
						};
					});
				setPrimary(cpInstanceUnitOfMeasure::isPrimary);
				setPriority(cpInstanceUnitOfMeasure::getPriority);
				setRate(
					() -> {
						BigDecimal rate = cpInstanceUnitOfMeasure.getRate();

						if (rate == null) {
							return null;
						}

						return rate.setScale(
							cpInstanceUnitOfMeasure.getPrecision(),
							RoundingMode.HALF_UP);
					});
				setTierPrices(
					() -> {
						if (commercePriceEntry == null) {
							return null;
						}

						return TransformUtil.transformToArray(
							commerceTierPriceEntryLocalService.
								getCommerceTierPriceEntries(
									commercePriceEntry.
										getCommercePriceEntryId(),
									QueryUtil.ALL_POS, QueryUtil.ALL_POS,
									CommerceTierPriceEntryMinQuantityComparator.
										getInstance(true)),
							commerceTierPriceEntry -> toTierPrice(
								commerceCurrency, commerceCurrencyLocalService,
								commerceMoneyFactory, commercePriceFormatter,
								commerceQuantityFormatter,
								commerceTierPriceEntry, cpInstanceUnitOfMeasure,
								locale),
							TierPrice.class);
					});
			}
		};
	}

	public static TierPrice toTierPrice(
			CommerceCurrency commerceCurrency,
			CommerceCurrencyLocalService commerceCurrencyLocalService,
			CommerceMoneyFactory commerceMoneyFactory,
			CommercePriceFormatter commercePriceFormatter,
			CommerceQuantityFormatter commerceQuantityFormatter,
			CommerceTierPriceEntry commerceTierPriceEntry,
			CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure, Locale locale)
		throws PortalException {

		CommercePriceEntry commercePriceEntry =
			commerceTierPriceEntry.getCommercePriceEntry();

		CommerceMoney pricingQuantityUnitPriceCommerceMoney =
			_getPricingQuantityUnitPriceCommerceMoney(
				commerceCurrency, commerceCurrencyLocalService,
				commerceMoneyFactory, commercePriceEntry,
				commerceTierPriceEntry.getPrice());

		return new TierPrice() {
			{
				setCurrency(() -> commerceCurrency.getName(locale));

				BigDecimal convertedPrice = _getConvertedPrice(
					commerceCurrency, commerceCurrencyLocalService,
					commercePriceEntry.getCommercePriceList(),
					commerceTierPriceEntry.getPrice());

				setPrice(convertedPrice::doubleValue);
				setPriceFormatted(
					() -> commercePriceFormatter.format(
						commerceCurrency, true, locale, convertedPrice));

				setPricingQuantityPrice(
					() -> {
						if ((cpInstanceUnitOfMeasure == null) ||
							(pricingQuantityUnitPriceCommerceMoney == null)) {

							return null;
						}

						BigDecimal pricingQuantityUnitPrice =
							pricingQuantityUnitPriceCommerceMoney.getPrice();

						if (pricingQuantityUnitPrice == null) {
							return null;
						}

						return pricingQuantityUnitPrice.doubleValue();
					});
				setPricingQuantityPriceFormatted(
					() -> {
						if ((cpInstanceUnitOfMeasure == null) ||
							(pricingQuantityUnitPriceCommerceMoney == null)) {

							return null;
						}

						BigDecimal pricingQuantity = BigDecimalUtil.get(
							cpInstanceUnitOfMeasure.getPricingQuantity(),
							BigDecimal.ZERO);

						if (BigDecimalUtil.lte(
								pricingQuantity, BigDecimal.ZERO)) {

							pricingQuantity = BigDecimal.ONE;
						}

						return pricingQuantityUnitPriceCommerceMoney.format(
							locale, pricingQuantity,
							cpInstanceUnitOfMeasure.getName(locale));
					});
				setQuantity(
					() -> commerceQuantityFormatter.format(
						cpInstanceUnitOfMeasure,
						commerceTierPriceEntry.getMinQuantity()));
			}
		};
	}

	private static BigDecimal _getConvertedPrice(
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

	private static CommerceMoney _getPricingQuantityUnitPriceCommerceMoney(
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

		BigDecimal pricingQuantityUnitPrice = pricingQuantity.multiply(
			price
		).divide(
			commercePriceEntry.getQuantity(),
			commerceCurrency.getMaxFractionDigits(),
			RoundingMode.valueOf(commerceCurrency.getRoundingMode())
		);

		return commerceMoneyFactory.create(
			commerceCurrency,
			_getConvertedPrice(
				commerceCurrency, commerceCurrencyLocalService,
				commercePriceEntry.getCommercePriceList(),
				pricingQuantityUnitPrice));
	}

}