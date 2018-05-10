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

package com.liferay.commerce.data.integration.apio.internal.resource;

import static com.liferay.commerce.data.integration.apio.constants.PriceEntryFieldConstants.DATE_CREATED;
import static com.liferay.commerce.data.integration.apio.constants.PriceEntryFieldConstants.DATE_MODIFIED;
import static com.liferay.commerce.data.integration.apio.constants.PriceEntryFieldConstants.NAME;
import static com.liferay.commerce.data.integration.apio.constants.PriceEntryFieldConstants.PRICE;
import static com.liferay.commerce.data.integration.apio.constants.PriceEntryFieldConstants.PROMO_PRICE;
import static com.liferay.commerce.data.integration.apio.constants.PriceEntryFieldConstants.SKU;

import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.resource.NestedCollectionResource;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.apio.architect.routes.NestedCollectionRoutes;
import com.liferay.commerce.data.integration.apio.identifiers.PriceEntryIdentifier;
import com.liferay.commerce.data.integration.apio.identifiers.PriceListIdentifier;
import com.liferay.commerce.data.integration.apio.internal.security.permission.PriceListPermissionChecker;
import com.liferay.commerce.data.integration.apio.internal.util.PriceEntryHelper;
import com.liferay.commerce.price.list.model.CommercePriceEntry;
import com.liferay.commerce.price.list.service.CommercePriceEntryService;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.List;

import javax.ws.rs.ServerErrorException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the information necessary to expose {@link CommercePriceEntry}
 * resources through a web API.
 *
 * @author Zoltán Takács
 */
@Component(immediate = true)
public class PriceEntryNestedCollectionResource
	implements
		NestedCollectionResource<CommercePriceEntry, Long,
			PriceEntryIdentifier, Long, PriceListIdentifier> {

	@Override
	public NestedCollectionRoutes<CommercePriceEntry, Long> collectionRoutes(
		NestedCollectionRoutes.Builder<CommercePriceEntry, Long> builder) {

		return builder.addGetter(
			this::_getPageItems
		).build();
	}

	@Override
	public String getName() {
		return "price-entries";
	}

	@Override
	public ItemRoutes<CommercePriceEntry, Long> itemRoutes(
		ItemRoutes.Builder<CommercePriceEntry, Long> builder) {

		return builder.addGetter(
			_priceEntryHelper::getCommercePriceEntry
		).build();
	}

	@Override
	public Representor<CommercePriceEntry, Long> representor(
		Representor.Builder<CommercePriceEntry, Long> builder) {

		return builder.types(
			"PriceEntry"
		).identifier(
			CommercePriceEntry::getCommercePriceEntryId
		).addBidirectionalModel(
			"priceList", "priceEntries", PriceListIdentifier.class,
			CommercePriceEntry::getCommercePriceListId
		).addDate(
			DATE_CREATED, CommercePriceEntry::getCreateDate
		).addDate(
			DATE_MODIFIED, CommercePriceEntry::getModifiedDate
		).addNumber(
			PRICE, CommercePriceEntry::getPrice
		).addNumber(
			PROMO_PRICE, CommercePriceEntry::getPromoPrice
		).addString(
			NAME, PriceEntryHelper::getProductName
		).addString(
			SKU, PriceEntryHelper::getSKU
		).build();
	}

	private PageItems<CommercePriceEntry> _getPageItems(
		Pagination pagination, Long commercePriceListId) {

		try {
			List<CommercePriceEntry> commercePriceEntries =
				_commercePriceEntryService.getCommercePriceEntries(
					commercePriceListId, pagination.getStartPosition(),
					pagination.getEndPosition());

			if (ListUtil.isEmpty(commercePriceEntries)) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Unable to find Price Entries in Price List with ID " +
							commercePriceListId);
				}
			}

			int count = _commercePriceEntryService.getCommercePriceEntriesCount(
				commercePriceListId);

			return new PageItems<>(commercePriceEntries, count);
		}
		catch (SystemException se) {
			throw new ServerErrorException(500, se);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PriceEntryNestedCollectionResource.class);

	@Reference
	private CommercePriceEntryService _commercePriceEntryService;

	@Reference
	private PriceEntryHelper _priceEntryHelper;

	@Reference
	private PriceListPermissionChecker _priceListPermissionChecker;

}