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
import com.liferay.commerce.price.list.model.CommercePriceEntry;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.model.CommerceTierPriceEntry;
import com.liferay.commerce.product.model.CPInstanceUnitOfMeasure;
import com.liferay.commerce.util.CommerceQuantityFormatter;
import com.liferay.headless.commerce.delivery.catalog.dto.v1_0.TierPrice;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.BigDecimalUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import java.math.BigDecimal;
import java.math.RoundingMode;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Sbarra
 */
@Component(
	property = "dto.class.name=com.liferay.commerce.price.list.model.CommerceTierPriceEntry",
	service = DTOConverter.class
)
public class TierPriceDTOConverter
	implements DTOConverter<CommerceTierPriceEntry, TierPrice> {

	@Override
	public String getContentType() {
		return TierPrice.class.getSimpleName();
	}

	@Override
	public TierPrice toDTO(
			DTOConverterContext dtoConverterContext,
			CommerceTierPriceEntry commerceTierPriceEntry)
		throws Exception {

		CommerceContext commerceContext =
			(CommerceContext)dtoConverterContext.getAttribute(
				"commerceContext");

		CommerceCurrency commerceCurrency =
			commerceContext.getCommerceCurrency();

		CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure =
			(CPInstanceUnitOfMeasure)dtoConverterContext.getAttribute(
				"cpInstanceUnitOfMeasure");

		Locale locale = dtoConverterContext.getLocale();

		CommercePriceEntry commercePriceEntry =
			commerceTierPriceEntry.getCommercePriceEntry();

		CommerceMoney pricingQuantityUnitPriceCommerceMoney =
			_getPricingQuantityUnitPriceCommerceMoney(
				commerceCurrency, commercePriceEntry,
				commerceTierPriceEntry.getPrice());

		return new TierPrice() {
			{
				setCurrency(() -> commerceCurrency.getName(locale));

				BigDecimal convertedPrice = _getConvertedPrice(
					commerceCurrency, commercePriceEntry.getCommercePriceList(),
					commerceTierPriceEntry.getPrice());

				setPrice(convertedPrice::doubleValue);
				setPriceFormatted(
					() -> _commercePriceFormatter.format(
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
					() -> _commerceQuantityFormatter.format(
						cpInstanceUnitOfMeasure,
						commerceTierPriceEntry.getMinQuantity()));
			}
		};
	}

	private BigDecimal _getConvertedPrice(
			CommerceCurrency commerceCurrency,
			CommercePriceList commercePriceList, BigDecimal price)
		throws PortalException {

		CommerceCurrency priceListCommerceCurrency =
			_commerceCurrencyLocalService.getCommerceCurrency(
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

	private CommerceMoney _getPricingQuantityUnitPriceCommerceMoney(
			CommerceCurrency commerceCurrency,
			CommercePriceEntry commercePriceEntry, BigDecimal price)
		throws Exception {

		BigDecimal pricingQuantity = commercePriceEntry.getPricingQuantity();

		if ((pricingQuantity == null) ||
			BigDecimalUtil.lte(pricingQuantity, BigDecimal.ZERO)) {

			return _commerceMoneyFactory.emptyCommerceMoney();
		}

		BigDecimal pricingQuantityUnitPrice = pricingQuantity.multiply(
			price
		).divide(
			commercePriceEntry.getQuantity(),
			commerceCurrency.getMaxFractionDigits(),
			RoundingMode.valueOf(commerceCurrency.getRoundingMode())
		);

		return _commerceMoneyFactory.create(
			commerceCurrency,
			_getConvertedPrice(
				commerceCurrency, commercePriceEntry.getCommercePriceList(),
				pricingQuantityUnitPrice));
	}

	@Reference
	private CommerceCurrencyLocalService _commerceCurrencyLocalService;

	@Reference
	private CommerceMoneyFactory _commerceMoneyFactory;

	@Reference
	private CommercePriceFormatter _commercePriceFormatter;

	@Reference
	private CommerceQuantityFormatter _commerceQuantityFormatter;

}