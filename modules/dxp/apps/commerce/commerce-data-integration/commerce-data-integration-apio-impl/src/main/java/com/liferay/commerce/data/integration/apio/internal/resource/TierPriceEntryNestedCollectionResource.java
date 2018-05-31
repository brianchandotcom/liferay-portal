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

import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.resource.NestedCollectionResource;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.apio.architect.routes.NestedCollectionRoutes;
import com.liferay.commerce.data.integration.apio.identifiers.PriceEntryIdentifier;
import com.liferay.commerce.data.integration.apio.identifiers.TierPriceEntryIdentifier;
import com.liferay.commerce.data.integration.apio.internal.exceptions.ConflictException;
import com.liferay.commerce.data.integration.apio.internal.form.TierPriceEntryUpdaterForm;
import com.liferay.commerce.data.integration.apio.internal.form.TierPriceEntryUpserterForm;
import com.liferay.commerce.data.integration.apio.internal.util.TierPriceEntryHelper;
import com.liferay.commerce.price.list.exception.DuplicateCommerceTierPriceEntryException;
import com.liferay.commerce.price.list.exception.NoSuchPriceEntryException;
import com.liferay.commerce.price.list.model.CommerceTierPriceEntry;
import com.liferay.commerce.price.list.service.CommerceTierPriceEntryService;
import com.liferay.portal.apio.permission.HasPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.site.apio.architect.identifier.WebSiteIdentifier;

import java.util.List;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the information necessary to expose {@link CommerceTierPriceEntry}
 * resources through a web API.
 *
 * @author Zoltán Takács
 */
@Component(immediate = true)
public class TierPriceEntryNestedCollectionResource
	implements
		NestedCollectionResource<CommerceTierPriceEntry, Long,
			TierPriceEntryIdentifier, Long, PriceEntryIdentifier> {

	@Override
	public NestedCollectionRoutes<CommerceTierPriceEntry, Long, Long>
		collectionRoutes(
			NestedCollectionRoutes.Builder<CommerceTierPriceEntry, Long, Long>
				builder) {

		return builder.addGetter(
			this::_getPageItems
		).addCreator(
			this::_upsertCommerceTierPriceEntry,
			_hasPermission.forAddingIn(TierPriceEntryIdentifier.class),
			TierPriceEntryUpserterForm::buildForm
		).build();
	}

	@Override
	public String getName() {
		return "tier-price-entries";
	}

	@Override
	public ItemRoutes<CommerceTierPriceEntry, Long> itemRoutes(
		ItemRoutes.Builder<CommerceTierPriceEntry, Long> builder) {

		return builder.addGetter(
			_tierPriceEntryHelper::getCommerceTierPriceEntry
		).addUpdater(
			this::_updateCommercePriceEntry, _hasPermission::forUpdating,
			TierPriceEntryUpdaterForm::buildForm
		).addRemover(
			idempotent(
				_commerceTierPriceEntryService::deleteCommerceTierPriceEntry),
			_hasPermission::forDeleting
		).build();
	}

	@Override
	public Representor<CommerceTierPriceEntry> representor(
		Representor.Builder<CommerceTierPriceEntry, Long> builder) {

		return builder.types(
			"TierPriceEntry"
		).identifier(
			CommerceTierPriceEntry::getCommerceTierPriceEntryId
		).addBidirectionalModel(
			"priceEntry", "tierPriceEntries", PriceEntryIdentifier.class,
			CommerceTierPriceEntry::getCommercePriceEntryId
		).addBidirectionalModel(
			"webSite", "tierPriceEntries", WebSiteIdentifier.class,
			CommerceTierPriceEntry::getGroupId
		).addDate(
			"dateCreated", CommerceTierPriceEntry::getCreateDate
		).addDate(
			"dateModified", CommerceTierPriceEntry::getModifiedDate
		).addNumber(
			"id", CommerceTierPriceEntry::getCommerceTierPriceEntryId
		).addNumber(
			"minQuantity", CommerceTierPriceEntry::getMinQuantity
		).addNumber(
			"price", CommerceTierPriceEntry::getPrice
		).addNumber(
			"promoPrice", CommerceTierPriceEntry::getPromoPrice
		).addString(
			"externalReferenceCode",
			CommerceTierPriceEntry::getExternalReferenceCode
		).build();
	}

	private PageItems<CommerceTierPriceEntry> _getPageItems(
		Pagination pagination, Long commercePriceEntryId) {

		List<CommerceTierPriceEntry> commerceTierPriceEntries =
			_commerceTierPriceEntryService.getCommerceTierPriceEntries(
				commercePriceEntryId, pagination.getStartPosition(),
				pagination.getEndPosition());

		if (_log.isDebugEnabled()) {
			if (ListUtil.isEmpty(commerceTierPriceEntries)) {
				_log.debug(
					"Unable to find Tier Price Entries in Price Entry with " +
						"ID " + commercePriceEntryId);
			}
		}

		int count =
			_commerceTierPriceEntryService.getCommerceTierPriceEntriesCount(
				commercePriceEntryId);

		return new PageItems<>(commerceTierPriceEntries, count);
	}

	private CommerceTierPriceEntry _updateCommercePriceEntry(
			Long commerceTierPriceEntryId,
			TierPriceEntryUpdaterForm tierPriceEntryUpdaterForm)
		throws PortalException {

		try {
			return _tierPriceEntryHelper.updateCommerceTierPriceEntry(
				commerceTierPriceEntryId,
				tierPriceEntryUpdaterForm.getMinQuantity(),
				tierPriceEntryUpdaterForm.getPrice(),
				tierPriceEntryUpdaterForm.getPromoPrice());
		}
		catch (DuplicateCommerceTierPriceEntryException dctpee) {
			Response.Status status = Response.Status.CONFLICT;

			throw new ConflictException(
				"Minimum quantity already exists for this price entry: " +
					tierPriceEntryUpdaterForm.getMinQuantity(),
				status.getStatusCode(), dctpee);
		}
	}

	private CommerceTierPriceEntry _upsertCommerceTierPriceEntry(
			Long commercePriceEntryId,
			TierPriceEntryUpserterForm tierPriceEntryUpserterForm)
		throws PortalException {

		try {
			return _tierPriceEntryHelper.upsertCommerceTierPriceEntry(
				tierPriceEntryUpserterForm.getCommerceTierPriceEntryId(),
				commercePriceEntryId,
				tierPriceEntryUpserterForm.getMinQuantity(),
				tierPriceEntryUpserterForm.getPrice(),
				tierPriceEntryUpserterForm.getPromoPrice(),
				tierPriceEntryUpserterForm.getExternalReferenceCode(),
				tierPriceEntryUpserterForm.
					getPriceEntryExternalReferenceCode());
		}
		catch (NoSuchPriceEntryException nspee) {
			throw new NotFoundException(
				"Unable to find price entry: " + nspee.getLocalizedMessage(),
				nspee);
		}
		catch (DuplicateCommerceTierPriceEntryException dctpee) {
			Response.Status status = Response.Status.CONFLICT;

			throw new ConflictException(
				"Minimum quantity already exists for this price entry: " +
					tierPriceEntryUpserterForm.getMinQuantity(),
				status.getStatusCode(), dctpee);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		TierPriceEntryNestedCollectionResource.class);

	@Reference
	private CommerceTierPriceEntryService _commerceTierPriceEntryService;

	@Reference(
		target = "(model.class.name=com.liferay.commerce.price.list.model.CommerceTierPriceEntry)"
	)
	private HasPermission<Long> _hasPermission;

	@Reference
	private TierPriceEntryHelper _tierPriceEntryHelper;

}