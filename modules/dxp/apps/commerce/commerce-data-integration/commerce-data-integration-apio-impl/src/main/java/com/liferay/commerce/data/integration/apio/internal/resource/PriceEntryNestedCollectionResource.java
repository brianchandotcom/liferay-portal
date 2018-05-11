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

import static com.liferay.portal.apio.idempotent.Idempotent.idempotent;

import static javax.ws.rs.core.Response.Status.CONFLICT;

import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.resource.NestedCollectionResource;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.apio.architect.routes.NestedCollectionRoutes;
import com.liferay.commerce.data.integration.apio.constants.PriceEntryFieldConstants;
import com.liferay.commerce.data.integration.apio.identifiers.PriceEntryIdentifier;
import com.liferay.commerce.data.integration.apio.identifiers.PriceListIdentifier;
import com.liferay.commerce.data.integration.apio.internal.exceptions.ConflictException;
import com.liferay.commerce.data.integration.apio.internal.form.PriceEntryCreatorForm;
import com.liferay.commerce.data.integration.apio.internal.form.PriceEntryUpdaterForm;
import com.liferay.commerce.data.integration.apio.internal.security.permission.PriceEntryPermissionChecker;
import com.liferay.commerce.data.integration.apio.internal.util.PriceEntryHelper;
import com.liferay.commerce.price.list.constants.CommercePriceListConstants;
import com.liferay.commerce.price.list.exception.DuplicateCommercePriceEntryException;
import com.liferay.commerce.price.list.model.CommercePriceEntry;
import com.liferay.commerce.price.list.service.CommercePriceEntryService;
import com.liferay.commerce.product.exception.NoSuchCPInstanceException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.List;

import javax.ws.rs.NotFoundException;
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
		).addCreator(
			this::_addCommercePriceEntry,
			_priceEntryPermissionChecker.forAdding(
				CommercePriceListConstants.RESOURCE_NAME),
			PriceEntryCreatorForm::buildForm
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
		).addUpdater(
			this::_updateCommercePriceEntry,
			_priceEntryPermissionChecker.forUpdating(
				CommercePriceListConstants.RESOURCE_NAME),
			PriceEntryUpdaterForm::buildForm
		).addRemover(
			idempotent(_commercePriceEntryService::deleteCommercePriceEntry),
			_priceEntryPermissionChecker.forDeleting(
				CommercePriceListConstants.RESOURCE_NAME)
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
			PriceEntryFieldConstants.DATE_CREATED,
			CommercePriceEntry::getCreateDate
		).addDate(
			PriceEntryFieldConstants.DATE_MODIFIED,
			CommercePriceEntry::getModifiedDate
		).addNumber(
			PriceEntryFieldConstants.PRICE, CommercePriceEntry::getPrice
		).addNumber(
			PriceEntryFieldConstants.PROMO_PRICE,
			CommercePriceEntry::getPromoPrice
		).addString(
			PriceEntryFieldConstants.NAME, PriceEntryHelper::getProductName
		).addString(
			PriceEntryFieldConstants.SKU, PriceEntryHelper::getSKU
		).build();
	}

	private CommercePriceEntry _addCommercePriceEntry(
		Long commercePriceListId, PriceEntryCreatorForm priceEntryCreatorForm) {

		try {
			return _priceEntryHelper.addCommercePriceEntry(
				priceEntryCreatorForm.getSkuID(), commercePriceListId,
				priceEntryCreatorForm.getPrice(),
				priceEntryCreatorForm.getPromoPrice());
		}
		catch (NoSuchCPInstanceException nscpie) {
			throw new NotFoundException(
				"Unable to find CPInstance with ID " +
					priceEntryCreatorForm.getSkuID(),
				nscpie);
		}
		catch (DuplicateCommercePriceEntryException dcpee) {
			throw new ConflictException(
				"Duplicate Product Instance with ID " +
					priceEntryCreatorForm.getSkuID(),
				CONFLICT.getStatusCode(), dcpee);
		}
		catch (PortalException pe) {
			throw new ServerErrorException(500, pe);
		}
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

	private CommercePriceEntry _updateCommercePriceEntry(
		Long commercePriceEntryId,
		PriceEntryUpdaterForm priceEntryUpdaterForm) {

		try {
			return _priceEntryHelper.updateCommercePriceEntry(
				commercePriceEntryId, priceEntryUpdaterForm.getPrice(),
				priceEntryUpdaterForm.getPromoPrice());
		}
		catch (PortalException pe) {
			throw new ServerErrorException(500, pe);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PriceEntryNestedCollectionResource.class);

	@Reference
	private CommercePriceEntryService _commercePriceEntryService;

	@Reference
	private PriceEntryHelper _priceEntryHelper;

	@Reference
	private PriceEntryPermissionChecker _priceEntryPermissionChecker;

}