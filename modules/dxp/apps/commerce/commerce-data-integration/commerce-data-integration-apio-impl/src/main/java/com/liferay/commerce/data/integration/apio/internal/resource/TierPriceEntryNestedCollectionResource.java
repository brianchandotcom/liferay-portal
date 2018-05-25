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
import com.liferay.commerce.data.integration.apio.internal.form.TierPriceEntryForm;
import com.liferay.commerce.data.integration.apio.internal.util.TierPriceEntryHelper;
import com.liferay.commerce.price.list.exception.DuplicateCommercePriceEntryException;
import com.liferay.commerce.price.list.model.CommerceTierPriceEntry;
import com.liferay.commerce.price.list.service.CommerceTierPriceEntryService;
import com.liferay.portal.apio.permission.HasPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.List;

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
			this::_addCommerceTierPriceEntry,
			_hasPermission.forAddingIn(TierPriceEntryIdentifier.class),
			TierPriceEntryForm::buildForm
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
			TierPriceEntryForm::buildForm
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
		).build();
	}

	private CommerceTierPriceEntry _addCommerceTierPriceEntry(
			Long commercePriceEntryId, TierPriceEntryForm tierPriceEntryForm)
		throws PortalException {

		try {
			return _tierPriceEntryHelper.addCommerceTierPriceEntry(
				commercePriceEntryId, tierPriceEntryForm.getMinQuantity(),
				tierPriceEntryForm.getPrice(),
				tierPriceEntryForm.getPromoPrice());
		}
		catch (DuplicateCommercePriceEntryException dcpee) {
			Response.Status status = Response.Status.CONFLICT;

			// TODO: We will have to check DupCommerceTierPriceEntryException

			throw new ConflictException(
				"Minimum quantity already exists: " +
					tierPriceEntryForm.getMinQuantity(),
				status.getStatusCode(), dcpee);
		}
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
			TierPriceEntryForm tierPriceEntryForm)
		throws PortalException {

		return _tierPriceEntryHelper.updateCommerceTierPriceEntry(
			commerceTierPriceEntryId, tierPriceEntryForm.getMinQuantity(),
			tierPriceEntryForm.getPrice(), tierPriceEntryForm.getPromoPrice());
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