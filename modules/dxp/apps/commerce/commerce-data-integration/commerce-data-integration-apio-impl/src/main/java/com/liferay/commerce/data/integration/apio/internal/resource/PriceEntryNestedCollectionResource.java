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
import com.liferay.commerce.data.integration.apio.identifiers.PriceListIdentifier;
import com.liferay.commerce.data.integration.apio.internal.exceptions.ConflictException;
import com.liferay.commerce.data.integration.apio.internal.form.PriceEntryUpdaterForm;
import com.liferay.commerce.data.integration.apio.internal.form.PriceEntryUpserterForm;
import com.liferay.commerce.data.integration.apio.internal.util.PriceEntryHelper;
import com.liferay.commerce.price.list.exception.DuplicateCommercePriceEntryException;
import com.liferay.commerce.price.list.exception.NoSuchPriceEntryException;
import com.liferay.commerce.price.list.model.CommercePriceEntry;
import com.liferay.commerce.price.list.service.CommercePriceEntryService;
import com.liferay.commerce.product.exception.NoSuchCPInstanceException;
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
	public NestedCollectionRoutes<CommercePriceEntry, Long, Long>
		collectionRoutes(
			NestedCollectionRoutes.Builder<CommercePriceEntry, Long, Long>
				builder) {

		return builder.addGetter(
			this::_getPageItems
		).addCreator(
			this::_upsertCommercePriceEntry,
			_hasPermission.forAddingIn(PriceEntryIdentifier.class),
			PriceEntryUpserterForm::buildForm
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
			this::_updateCommercePriceEntry, _hasPermission::forUpdating,
			PriceEntryUpdaterForm::buildForm
		).addRemover(
			idempotent(_commercePriceEntryService::deleteCommercePriceEntry),
			_hasPermission::forDeleting
		).build();
	}

	@Override
	public Representor<CommercePriceEntry> representor(
		Representor.Builder<CommercePriceEntry, Long> builder) {

		return builder.types(
			"PriceEntry"
		).identifier(
			CommercePriceEntry::getCommercePriceEntryId
		).addBidirectionalModel(
			"priceList", "priceEntries", PriceListIdentifier.class,
			CommercePriceEntry::getCommercePriceListId
		).addBidirectionalModel(
			"webSite", "priceEntries", WebSiteIdentifier.class,
			CommercePriceEntry::getGroupId
		).addBoolean(
			"hasTierPrice", CommercePriceEntry::isHasTierPrice
		).addDate(
			"dateCreated", CommercePriceEntry::getCreateDate
		).addDate(
			"dateModified", CommercePriceEntry::getModifiedDate
		).addNumber(
			"id", CommercePriceEntry::getCommercePriceEntryId
		).addNumber(
			"price", CommercePriceEntry::getPrice
		).addNumber(
			"promoPrice", CommercePriceEntry::getPromoPrice
		).addString(
			"externalReferenceCode",
			CommercePriceEntry::getExternalReferenceCode
		).addString(
			"sku", PriceEntryHelper::getSKU
		).addString(
			"skuExternalReferenceCode",
			PriceEntryHelper::getSKUExternalReferenceCode
		).build();
	}

	private PageItems<CommercePriceEntry> _getPageItems(
		Pagination pagination, Long commercePriceListId) {

		List<CommercePriceEntry> commercePriceEntries =
			_commercePriceEntryService.getCommercePriceEntries(
				commercePriceListId, pagination.getStartPosition(),
				pagination.getEndPosition());

		if (_log.isDebugEnabled()) {
			if (ListUtil.isEmpty(commercePriceEntries)) {
				_log.debug(
					"Unable to find Price Entries in Price List with ID " +
						commercePriceListId);
			}
		}

		int count = _commercePriceEntryService.getCommercePriceEntriesCount(
			commercePriceListId);

		return new PageItems<>(commercePriceEntries, count);
	}

	private CommercePriceEntry _updateCommercePriceEntry(
			Long commercePriceEntryId,
			PriceEntryUpdaterForm priceEntryUpdaterForm)
		throws PortalException {

		return _priceEntryHelper.updateCommercePriceEntry(
			commercePriceEntryId, priceEntryUpdaterForm.getPrice(),
			priceEntryUpdaterForm.getPromoPrice());
	}

	private CommercePriceEntry _upsertCommercePriceEntry(
			Long commercePriceListId,
			PriceEntryUpserterForm priceEntryUpserterForm)
		throws PortalException {

		try {
			return _priceEntryHelper.upsertCommercePriceEntry(
				priceEntryUpserterForm.getCommercePriceEntryId(),
				priceEntryUpserterForm.getSkuId(), commercePriceListId,
				priceEntryUpserterForm.getExternalReferenceCode(),
				priceEntryUpserterForm.getSkuExternalReferenceCode(),
				priceEntryUpserterForm.getPrice(),
				priceEntryUpserterForm.getPromoPrice());
		}
		catch (NoSuchPriceEntryException nspee) {
			throw new NotFoundException(
				String.format(
					"Unable to update price entry: " +
						nspee.getLocalizedMessage()),
				nspee);
		}
		catch (NoSuchCPInstanceException nscpie) {
			throw new NotFoundException(
				String.format(
					"Unable to find SKU: " +
						nscpie.getLocalizedMessage()),
				nscpie);
		}
		catch (DuplicateCommercePriceEntryException dcpee) {
			Response.Status status = Response.Status.CONFLICT;

			throw new ConflictException(
				"Duplicate Product Instance on the Price List ID",
				status.getStatusCode(), dcpee);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PriceEntryNestedCollectionResource.class);

	@Reference
	private CommercePriceEntryService _commercePriceEntryService;

	@Reference(
		target = "(model.class.name=com.liferay.commerce.price.list.model.CommercePriceEntry)"
	)
	private HasPermission<Long> _hasPermission;

	@Reference
	private PriceEntryHelper _priceEntryHelper;

}