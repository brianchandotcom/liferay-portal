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

import com.liferay.apio.architect.functional.Try;
import com.liferay.commerce.price.list.model.CommercePriceEntry;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.service.CommercePriceEntryService;
import com.liferay.commerce.price.list.service.CommercePriceListService;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;

import java.math.BigDecimal;

import javax.ws.rs.NotFoundException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Zoltán Takács
 */
@Component(immediate = true, service = PriceEntryHelper.class)
public class PriceEntryHelper {

	public static String getSKU(CommercePriceEntry commercePriceEntry) {
		CPInstance cpInstance = _getCPInstance(commercePriceEntry);

		return Try.fromFallible(
			cpInstance::getSku
		).orElse(
			null
		);
	}

	public static String getSKUExternalReferenceCode(
		CommercePriceEntry commercePriceEntry) {

		CPInstance cpInstance = _getCPInstance(commercePriceEntry);

		return Try.fromFallible(
			cpInstance::getExternalReferenceCode
		).orElse(
			null
		);
	}

	public CommercePriceEntry getCommercePriceEntry(long commercePriceEntryId) {
		CommercePriceEntry commercePriceEntry =
			_commercePriceEntryService.fetchCommercePriceEntry(
				commercePriceEntryId);

		if (commercePriceEntry == null) {
			throw new NotFoundException(
				"Unable to find price entry with ID " + commercePriceEntryId);
		}

		return commercePriceEntry;
	}

	public CommercePriceEntry updateCommercePriceEntry(
			long commercePriceEntryId, double price, double promoPrice)
		throws PortalException {

		CommercePriceEntry commercePriceEntry = getCommercePriceEntry(
			commercePriceEntryId);

		ServiceContext serviceContext = _serviceContextHelper.getServiceContext(
			commercePriceEntry.getGroupId());

		return _commercePriceEntryService.updateCommercePriceEntry(
			commercePriceEntryId, BigDecimal.valueOf(price),
			BigDecimal.valueOf(promoPrice), serviceContext);
	}

	public CommercePriceEntry upsertCommercePriceEntry(
			long commercePriceEntryId, long skuId, long commercePriceListId,
			String externalReferenceCode, String skuExternalReferenceCode,
			double price, double promoPrice)
		throws PortalException {

		CommercePriceList commercePriceList =
			_priceListHelper.getCommercePriceList(commercePriceListId);

		ServiceContext serviceContext = _serviceContextHelper.getServiceContext(
			commercePriceList.getGroupId());

		return _commercePriceEntryService.upsertCommercePriceEntry(
			commercePriceEntryId, skuId, commercePriceListId,
			externalReferenceCode, BigDecimal.valueOf(price),
			BigDecimal.valueOf(promoPrice), skuExternalReferenceCode,
			serviceContext);
	}

	private static CPInstance _getCPInstance(
		CommercePriceEntry commercePriceEntry) {

		CPInstance cpInstance = null;

		try {
			cpInstance = commercePriceEntry.getCPInstance();
		}
		catch (PortalException pe) {
			throw new NotFoundException(
				"Unable to find Product Instance for Price Entry with ID " +
					commercePriceEntry.getCommercePriceEntryId(),
				pe);
		}

		return cpInstance;
	}

	@Reference
	private CommercePriceEntryService _commercePriceEntryService;

	@Reference
	private CommercePriceListService _commercePriceListService;

	@Reference
	private PriceListHelper _priceListHelper;

	@Reference
	private ServiceContextHelper _serviceContextHelper;

}