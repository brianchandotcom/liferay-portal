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

package com.liferay.commerce.data.integration.apio.internal.util;

import com.liferay.commerce.price.list.model.CommercePriceEntry;
import com.liferay.commerce.price.list.model.CommerceTierPriceEntry;
import com.liferay.commerce.price.list.service.CommerceTierPriceEntryService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;

import java.math.BigDecimal;

import javax.ws.rs.NotFoundException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Zoltán Takács
 */
@Component(immediate = true, service = TierPriceEntryHelper.class)
public class TierPriceEntryHelper {

	public CommerceTierPriceEntry getCommerceTierPriceEntry(
		Long commerceTierPriceEntryId) {

		CommerceTierPriceEntry commerceTierPriceEntry =
			_commerceTierPriceEntryService.fetchCommerceTierPriceEntry(
				commerceTierPriceEntryId);

		if (commerceTierPriceEntry == null) {
			throw new NotFoundException(
				"Unable to find tier price entry with ID " +
					commerceTierPriceEntryId);
		}

		return commerceTierPriceEntry;
	}

	public CommerceTierPriceEntry updateCommerceTierPriceEntry(
			Long commerceTierPriceEntryId, Long minQuantity, Double price,
			Double promoPrice)
		throws PortalException {

		CommerceTierPriceEntry commerceTierPriceEntry =
			getCommerceTierPriceEntry(commerceTierPriceEntryId);

		ServiceContext serviceContext = _serviceContextHelper.getServiceContext(
			commerceTierPriceEntry.getGroupId());

		return _commerceTierPriceEntryService.updateCommerceTierPriceEntry(
			commerceTierPriceEntryId, BigDecimal.valueOf(price),
			BigDecimal.valueOf(promoPrice), minQuantity.intValue(),
			serviceContext);
	}

	public CommerceTierPriceEntry upsertCommerceTierPriceEntry(
			Long commerceTierPriceEntryId, Long commercePriceEntryId,
			Long minQuantity, Double price, Double promoPrice,
			String externalReferenceCode,
			String priceEntryExternalReferenceCode)
		throws PortalException {

		CommercePriceEntry commercePriceEntry =
			_priceEntryHelper.getCommercePriceEntry(commercePriceEntryId);

		ServiceContext serviceContext = _serviceContextHelper.getServiceContext(
			commercePriceEntry.getGroupId());

		return _commerceTierPriceEntryService.upsertCommerceTierPriceEntry(
			commerceTierPriceEntryId, commercePriceEntryId,
			externalReferenceCode, BigDecimal.valueOf(price),
			BigDecimal.valueOf(promoPrice), minQuantity.intValue(),
			priceEntryExternalReferenceCode, serviceContext);
	}

	@Reference
	private CommerceTierPriceEntryService _commerceTierPriceEntryService;

	@Reference
	private PriceEntryHelper _priceEntryHelper;

	@Reference
	private ServiceContextHelper _serviceContextHelper;

}