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
import com.liferay.commerce.price.list.model.CommerceTierPriceEntry;
import com.liferay.commerce.product.model.CPInstanceUnitOfMeasure;
import com.liferay.commerce.util.CommerceQuantityFormatter;
import com.liferay.headless.commerce.delivery.catalog.dto.v1_0.TierPrice;
import com.liferay.headless.commerce.delivery.catalog.internal.util.v1_0.PriceUtil;
import com.liferay.portal.kernel.util.BigDecimalUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import java.math.BigDecimal;

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

		CommercePriceEntry commercePriceEntry =
			commerceTierPriceEntry.getCommercePriceEntry();

		BigDecimal convertedPrice = PriceUtil.getConvertedPrice(
			commerceCurrency, _commerceCurrencyLocalService,
			commercePriceEntry.getCommercePriceList(),
			commerceTierPriceEntry.getPrice());
		CommerceMoney pricingQuantityUnitPriceCommerceMoney =
			PriceUtil.getPricingQuantityUnitPriceCommerceMoney(
				commerceCurrency, _commerceCurrencyLocalService,
				_commerceMoneyFactory, commercePriceEntry,
				commerceTierPriceEntry.getPrice());

		CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure =
			(CPInstanceUnitOfMeasure)dtoConverterContext.getAttribute(
				"cpInstanceUnitOfMeasure");

		Locale locale = dtoConverterContext.getLocale();

		return new TierPrice() {
			{
				setCurrency(() -> commerceCurrency.getName(locale));
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

	@Reference
	private CommerceCurrencyLocalService _commerceCurrencyLocalService;

	@Reference
	private CommerceMoneyFactory _commerceMoneyFactory;

	@Reference
	private CommercePriceFormatter _commercePriceFormatter;

	@Reference
	private CommerceQuantityFormatter _commerceQuantityFormatter;

}