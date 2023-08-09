/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.pricing.internal.dto.v2_0.converter;

import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.service.CommerceCurrencyService;
import com.liferay.commerce.currency.util.CommercePriceFormatter;
import com.liferay.commerce.price.list.constants.CommercePriceListConstants;
import com.liferay.commerce.price.list.model.CommercePriceEntry;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.service.CommercePriceEntryLocalService;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.service.CPInstanceService;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.Sku;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import java.math.BigDecimal;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	property = "dto.class.name=com.liferay.commerce.product.model.CPInstance",
	service = DTOConverter.class
)
public class SkuDTOConverter implements DTOConverter<CPInstance, Sku> {

	@Override
	public String getContentType() {
		return Sku.class.getSimpleName();
	}

	@Override
	public Sku toDTO(DTOConverterContext dtoConverterContext) throws Exception {
		CPInstance cpInstance = _cpInstanceService.fetchCPInstance(
			(Long)dtoConverterContext.getId());

		CommercePriceEntry commerceBasePriceListPriceEntry =
			_commercePriceEntryLocalService.getInstanceBaseCommercePriceEntry(
				cpInstance.getCPInstanceUuid(),
				CommercePriceListConstants.TYPE_PRICE_LIST, StringPool.BLANK);

		CommercePriceEntry commerceBasePromotionPriceEntry =
			_commercePriceEntryLocalService.getInstanceBaseCommercePriceEntry(
				cpInstance.getCPInstanceUuid(),
				CommercePriceListConstants.TYPE_PROMOTION, StringPool.BLANK);

		Locale locale = dtoConverterContext.getLocale();

		return new Sku() {
			{
				basePrice = _getPrice(commerceBasePriceListPriceEntry);
				basePriceFormatted = _formatPrice(
					cpInstance.getCompanyId(), commerceBasePriceListPriceEntry,
					locale);
				basePromoPrice = _getPrice(commerceBasePromotionPriceEntry);
				basePromoPriceFormatted = _formatPrice(
					cpInstance.getCompanyId(), commerceBasePromotionPriceEntry,
					locale);
				id = cpInstance.getCPInstanceId();
				name = cpInstance.getSku();
			}
		};
	}

	private String _formatPrice(
			long companyId, CommercePriceEntry priceEntry, Locale locale)
		throws Exception {

		if (priceEntry == null) {
			CommerceCurrency commerceCurrency =
				_commerceCurrencyService.fetchPrimaryCommerceCurrency(
					companyId);

			return _commercePriceFormatter.format(
				commerceCurrency, BigDecimal.ZERO, locale);
		}

		CommercePriceList commercePriceList = priceEntry.getCommercePriceList();

		return _commercePriceFormatter.format(
			commercePriceList.getCommerceCurrency(), priceEntry.getPrice(),
			locale);
	}

	private double _getPrice(CommercePriceEntry commercePriceEntry) {
		if (commercePriceEntry == null) {
			return 0D;
		}

		BigDecimal price = commercePriceEntry.getPrice();

		return price.doubleValue();
	}

	@Reference
	private CommerceCurrencyService _commerceCurrencyService;

	@Reference
	private CommercePriceEntryLocalService _commercePriceEntryLocalService;

	@Reference
	private CommercePriceFormatter _commercePriceFormatter;

	@Reference
	private CPInstanceService _cpInstanceService;

}