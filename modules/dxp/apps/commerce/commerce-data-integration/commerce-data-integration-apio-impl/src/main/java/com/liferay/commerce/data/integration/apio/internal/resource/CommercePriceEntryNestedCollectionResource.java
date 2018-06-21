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
import com.liferay.commerce.data.integration.apio.identifiers.CommercePriceEntryIdentifier;
import com.liferay.commerce.data.integration.apio.identifiers.CommercePriceListIdentifier;
import com.liferay.commerce.data.integration.apio.internal.exceptions.ConflictException;
import com.liferay.commerce.data.integration.apio.internal.form.CommercePriceEntryUpdaterForm;
import com.liferay.commerce.data.integration.apio.internal.form.CommercePriceEntryUpserterForm;
import com.liferay.commerce.data.integration.apio.internal.util.CommercePriceEntryHelper;
import com.liferay.commerce.price.list.exception.DuplicateCommercePriceEntryException;
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
public class CommercePriceEntryNestedCollectionResource
	implements
		NestedCollectionResource<CommercePriceEntry, Long,
				CommercePriceEntryIdentifier, Long, CommercePriceListIdentifier> {

	@Override
	public NestedCollectionRoutes<CommercePriceEntry, Long, Long>
		collectionRoutes(
			NestedCollectionRoutes.Builder<CommercePriceEntry, Long, Long>
				builder) {

		return builder.addGetter(
			this::_getPageItems
		).addCreator(
			this::_upsertCommercePriceEntry,
			_hasPermission.forAddingIn(CommercePriceEntryIdentifier.class),
			CommercePriceEntryUpserterForm::buildForm
		).build();
	}

	@Override
	public String getName() {
		return "commerce-price-entry";
	}

	@Override
	public ItemRoutes<CommercePriceEntry, Long> itemRoutes(
		ItemRoutes.Builder<CommercePriceEntry, Long> builder) {

		return builder.addGetter(
			_commercePriceEntryHelper::getCommercePriceEntry
		).addUpdater(
			this::_updateCommercePriceEntry, _hasPermission::forUpdating,
			CommercePriceEntryUpdaterForm::buildForm
		).addRemover(
			idempotent(_commercePriceEntryService::deleteCommercePriceEntry),
			_hasPermission::forDeleting
		).build();
	}

	@Override
	public Representor<CommercePriceEntry> representor(
		Representor.Builder<CommercePriceEntry, Long> builder) {

		return builder.types(
			"CommercePriceEntry"
		).identifier(
			CommercePriceEntry::getCommercePriceEntryId
		).addBidirectionalModel(
			"commercePriceList", "commercePriceEntries", CommercePriceListIdentifier.class,
			CommercePriceEntry::getCommercePriceListId
		).addBidirectionalModel(
			"webSite", "commercePriceEntries", WebSiteIdentifier.class,
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
			"sku", CommercePriceEntryHelper::getSKU
		).addString(
			"cpInstanceExternalReferenceCode",
			CommercePriceEntryHelper::getSKUExternalReferenceCode
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
			CommercePriceEntryUpdaterForm commercePriceEntryUpdaterForm)
		throws PortalException {

		return _commercePriceEntryHelper.updateCommercePriceEntry(
			commercePriceEntryId, commercePriceEntryUpdaterForm.getPrice(),
			commercePriceEntryUpdaterForm.getPromoPrice());
	}

	private CommercePriceEntry _upsertCommercePriceEntry(
			Long commercePriceListId,
			CommercePriceEntryUpserterForm commercePriceEntryUpserterForm)
		throws PortalException {

		try {
			return _commercePriceEntryHelper.upsertCommercePriceEntry(
				commercePriceEntryUpserterForm.getCommercePriceEntryId(),
				commercePriceEntryUpserterForm.getCommerceProductInstanceId(), commercePriceListId,
				commercePriceEntryUpserterForm.getExternalReferenceCode(),
				commercePriceEntryUpserterForm.getSkuExternalReferenceCode(),
				commercePriceEntryUpserterForm.getPrice(),
				commercePriceEntryUpserterForm.getPromoPrice());
		}
		catch (NoSuchCPInstanceException nscpie) {
			throw new NotFoundException(
				"Unable to find SKU: " + nscpie.getLocalizedMessage(), nscpie);
		}
		catch (DuplicateCommercePriceEntryException dcpee) {
			throw new ConflictException(
				"Duplicate Product Instance on the Price List ID",
				Response.Status.CONFLICT.getStatusCode(), dcpee);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommercePriceEntryNestedCollectionResource.class);

	@Reference
	private CommercePriceEntryService _commercePriceEntryService;

	@Reference(
		target = "(model.class.name=com.liferay.commerce.price.list.model.CommercePriceEntry)"
	)
	private HasPermission<Long> _hasPermission;

	@Reference
	private CommercePriceEntryHelper _commercePriceEntryHelper;

}