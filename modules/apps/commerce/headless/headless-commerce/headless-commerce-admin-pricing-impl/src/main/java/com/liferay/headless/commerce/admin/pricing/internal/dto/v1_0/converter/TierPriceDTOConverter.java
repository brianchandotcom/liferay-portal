/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.pricing.internal.dto.v1_0.converter;

import com.liferay.commerce.price.list.model.CommercePriceEntry;
import com.liferay.commerce.price.list.model.CommerceTierPriceEntry;
import com.liferay.commerce.price.list.service.CommerceTierPriceEntryService;
import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.headless.commerce.admin.pricing.dto.v1_0.TierPrice;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import java.math.BigDecimal;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
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
	public TierPrice toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		CommerceTierPriceEntry commerceTierPriceEntry =
			_commerceTierPriceEntryService.getCommerceTierPriceEntry(
				(Long)dtoConverterContext.getId());

		CommercePriceEntry commercePriceEntry =
			commerceTierPriceEntry.getCommercePriceEntry();

		ExpandoBridge expandoBridge = commerceTierPriceEntry.getExpandoBridge();

		return new TierPrice() {
			{
				customFields = expandoBridge.getAttributes();
				externalReferenceCode =
					commerceTierPriceEntry.getExternalReferenceCode();
				id = commerceTierPriceEntry.getCommerceTierPriceEntryId();
				price = commerceTierPriceEntry.getPrice();
				priceEntryExternalReferenceCode =
					commercePriceEntry.getExternalReferenceCode();
				priceEntryId = commercePriceEntry.getCommercePriceEntryId();
				promoPrice = commerceTierPriceEntry.getPromoPrice();

				setMinimumQuantity(
					() -> {
						BigDecimal minQuantity =
							commerceTierPriceEntry.getMinQuantity();

						if (minQuantity == null) {
							return 0;
						}

						return minQuantity.intValue();
					});
			}
		};
	}

	@Reference
	private CommerceTierPriceEntryService _commerceTierPriceEntryService;

}