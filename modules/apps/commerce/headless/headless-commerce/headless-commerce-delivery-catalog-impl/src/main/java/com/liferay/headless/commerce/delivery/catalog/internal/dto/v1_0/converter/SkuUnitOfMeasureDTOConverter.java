/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.delivery.catalog.internal.dto.v1_0.converter;

import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.model.CommerceMoney;
import com.liferay.commerce.currency.model.CommerceMoneyFactory;
import com.liferay.commerce.currency.service.CommerceCurrencyLocalService;
import com.liferay.commerce.currency.util.CommercePriceFormatter;
import com.liferay.commerce.price.CommerceProductPriceCalculation;
import com.liferay.commerce.price.list.model.CommercePriceEntry;
import com.liferay.commerce.price.list.model.CommerceTierPriceEntry;
import com.liferay.commerce.price.list.service.CommerceTierPriceEntryLocalService;
import com.liferay.commerce.price.list.util.comparator.CommerceTierPriceEntryMinQuantityComparator;
import com.liferay.commerce.product.model.CPInstanceUnitOfMeasure;
import com.liferay.headless.commerce.delivery.catalog.dto.v1_0.Price;
import com.liferay.headless.commerce.delivery.catalog.dto.v1_0.SkuUnitOfMeasure;
import com.liferay.headless.commerce.delivery.catalog.dto.v1_0.TierPrice;
import com.liferay.headless.commerce.delivery.catalog.internal.dto.v1_0.converter.constants.DTOConverterConstants;
import com.liferay.headless.commerce.delivery.catalog.internal.util.v1_0.PriceUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.util.BigDecimalUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.util.TransformUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;

import java.util.List;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian I. Kim
 */
@Component(
	property = "dto.class.name=com.liferay.commerce.product.model.CPInstanceUnitOfMeasure",
	service = DTOConverter.class
)
public class SkuUnitOfMeasureDTOConverter
	implements DTOConverter<CPInstanceUnitOfMeasure, SkuUnitOfMeasure> {

	@Override
	public String getContentType() {
		return SkuUnitOfMeasure.class.getSimpleName();
	}

	@Override
	public SkuUnitOfMeasure toDTO(
			DTOConverterContext dtoConverterContext,
			CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure)
		throws Exception {

		CommerceContext commerceContext =
			(CommerceContext)dtoConverterContext.getAttribute(
				"commerceContext");

		if (commerceContext == null) {
			return null;
		}

		CommerceCurrency commerceCurrency =
			commerceContext.getCommerceCurrency();
		CommercePriceEntry commercePriceEntry =
			_commerceProductPriceCalculation.getUnitCommercePriceEntry(
				commerceContext, cpInstanceUnitOfMeasure.getCPInstanceId(),
				cpInstanceUnitOfMeasure.getKey());
		Locale locale = dtoConverterContext.getLocale();

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
					() -> _toPrice(
						commerceCurrency, commercePriceEntry,
						cpInstanceUnitOfMeasure, locale));
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

						return _toTierPrices(
							commerceContext,
							_commerceTierPriceEntryLocalService.
								getCommerceTierPriceEntries(
									commercePriceEntry.
										getCommercePriceEntryId(),
									QueryUtil.ALL_POS, QueryUtil.ALL_POS,
									CommerceTierPriceEntryMinQuantityComparator.
										getInstance(true)),
							cpInstanceUnitOfMeasure, locale);
					});
			}
		};
	}

	private Price _toPrice(
			CommerceCurrency commerceCurrency,
			CommercePriceEntry commercePriceEntry,
			CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure, Locale locale)
		throws Exception {

		if (commercePriceEntry == null) {
			return null;
		}

		BigDecimal convertedPrice = PriceUtil.getConvertedPrice(
			commerceCurrency, _commerceCurrencyLocalService,
			commercePriceEntry.getCommercePriceList(),
			commercePriceEntry.getPrice());
		CommerceMoney pricingQuantityUnitPriceCommerceMoney =
			PriceUtil.getPricingQuantityUnitPriceCommerceMoney(
				commerceCurrency, _commerceCurrencyLocalService,
				_commerceMoneyFactory, commercePriceEntry,
				commercePriceEntry.getPrice());

		return new Price() {
			{
				setCurrency(() -> commerceCurrency.getName(locale));
				setPrice(convertedPrice::doubleValue);
				setPriceFormatted(
					() -> _commercePriceFormatter.format(
						commerceCurrency, true, locale, convertedPrice));
				setPriceOnApplication(commercePriceEntry::isPriceOnApplication);
				setPricingQuantityPrice(
					() -> {
						if (pricingQuantityUnitPriceCommerceMoney == null) {
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
						if (pricingQuantityUnitPriceCommerceMoney == null) {
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
			}
		};
	}

	private TierPrice[] _toTierPrices(
			CommerceContext commerceContext,
			List<CommerceTierPriceEntry> commerceTierPriceEntries,
			CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure, Locale locale)
		throws Exception {

		DTOConverterContext dtoConverterContext =
			new DefaultDTOConverterContext(null, locale);

		dtoConverterContext.setAttribute("commerceContext", commerceContext);
		dtoConverterContext.setAttribute(
			"cpInstanceUnitOfMeasure", cpInstanceUnitOfMeasure);

		return TransformUtil.transformToArray(
			commerceTierPriceEntries,
			commerceTierPriceEntry -> _tierPriceDTOConverter.toDTO(
				dtoConverterContext, commerceTierPriceEntry),
			TierPrice.class);
	}

	@Reference
	private CommerceCurrencyLocalService _commerceCurrencyLocalService;

	@Reference
	private CommerceMoneyFactory _commerceMoneyFactory;

	@Reference
	private CommercePriceFormatter _commercePriceFormatter;

	@Reference
	private CommerceProductPriceCalculation _commerceProductPriceCalculation;

	@Reference
	private CommerceTierPriceEntryLocalService
		_commerceTierPriceEntryLocalService;

	@Reference(target = DTOConverterConstants.TIER_PRICE_DTO_CONVERTER)
	private DTOConverter<CommerceTierPriceEntry, TierPrice>
		_tierPriceDTOConverter;

}