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
import com.liferay.commerce.currency.exception.NoSuchCurrencyException;
import com.liferay.commerce.data.integration.apio.identifiers.PriceListIdentifier;
import com.liferay.commerce.data.integration.apio.internal.form.PriceListForm;
import com.liferay.commerce.data.integration.apio.internal.security.permission.PriceListPermissionChecker;
import com.liferay.commerce.data.integration.apio.internal.util.PriceListHelper;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.service.CommercePriceListService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.site.apio.architect.identifier.WebSiteIdentifier;

import java.util.List;

import javax.ws.rs.NotFoundException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the information necessary to expose {@link CommercePriceList}
 * resources through a web API.
 *
 * @author Zoltán Takács
 */
@Component(immediate = true)
public class PriceListNestedCollectionResource
	implements
		NestedCollectionResource<CommercePriceList, Long,
			PriceListIdentifier, Long, WebSiteIdentifier> {

	@Override
	public NestedCollectionRoutes<CommercePriceList, Long, Long>
		collectionRoutes(
			NestedCollectionRoutes.Builder<CommercePriceList, Long, Long>
				builder) {

		return builder.addGetter(
			this::_getPageItems
		).addCreator(
			this::_addCommercePriceList,
			_priceListPermissionChecker.forAdding(), PriceListForm::buildForm
		).build();
	}

	@Override
	public String getName() {
		return "price-lists";
	}

	@Override
	public ItemRoutes<CommercePriceList, Long> itemRoutes(
		ItemRoutes.Builder<CommercePriceList, Long> builder) {

		return builder.addGetter(
			_priceListHelper::getCommercePriceList
		).addUpdater(
			this::_updateCommercePriceList,
			_priceListPermissionChecker.forUpdating(), PriceListForm::buildForm
		).addRemover(
			idempotent(_commercePriceListService::deleteCommercePriceList),
			_priceListPermissionChecker.forDeleting()
		).build();
	}

	@Override
	public Representor<CommercePriceList> representor(
		Representor.Builder<CommercePriceList, Long> builder) {

		return builder.types(
			"PriceList"
		).identifier(
			CommercePriceList::getCommercePriceListId
		).addBidirectionalModel(
			"webSite", "priceLists", WebSiteIdentifier.class,
			CommercePriceList::getGroupId
		).addDate(
			"dateCreated", CommercePriceList::getCreateDate
		).addDate(
			"dateModified", CommercePriceList::getModifiedDate
		).addDate(
			"displayDate", CommercePriceList::getDisplayDate
		).addDate(
			"expirationDate", CommercePriceList::getExpirationDate
		).addNumber(
			"priority", CommercePriceList::getPriority
		).addString(
			"currency", PriceListHelper::getCurrencyCode
		).addString(
			"name", CommercePriceList::getName
		).build();
	}

	private CommercePriceList _addCommercePriceList(
			Long groupId, PriceListForm priceListForm)
		throws PortalException {

		try {
			return _priceListHelper.upsertCommercePriceList(
				groupId, priceListForm.getCurrency(), priceListForm.getName(),
				priceListForm.getPriority(), priceListForm.isNeverExpire(),
				priceListForm.getDisplayDate(),
				priceListForm.getExpirationDate(),
				priceListForm.getExternalReferenceCode());
		}
		catch (NoSuchCurrencyException nsce) {
			throw new NotFoundException(
				String.format(
					"Unable to find currency with code: %s. Currency code " +
						"should be expressed with 3-letter ISO 4217 format",
					priceListForm.getCurrency()),
				nsce);
		}
	}

	private PageItems<CommercePriceList> _getPageItems(
			Pagination pagination, Long groupId)
		throws PortalException {

		List<CommercePriceList> commercePriceLists =
			_commercePriceListService.getCommercePriceLists(
				groupId, WorkflowConstants.STATUS_APPROVED,
				pagination.getStartPosition(), pagination.getEndPosition(),
				null);

		if (_log.isDebugEnabled()) {
			if (ListUtil.isEmpty(commercePriceLists)) {
				_log.debug(
					"Unable to find Price Lists on Site with ID " + groupId);
			}
		}

		int count = _commercePriceListService.getCommercePriceListsCount(
			groupId, WorkflowConstants.STATUS_APPROVED);

		return new PageItems<>(commercePriceLists, count);
	}

	private CommercePriceList _updateCommercePriceList(
			Long commercePriceListId, PriceListForm priceListForm)
		throws NotFoundException, PortalException {

		try {
			return _priceListHelper.updateCommercePriceList(
				commercePriceListId, priceListForm.getCurrency(),
				priceListForm.getName(), priceListForm.getPriority(),
				priceListForm.isNeverExpire(), priceListForm.getDisplayDate(),
				priceListForm.getExpirationDate());
		}
		catch (NoSuchCurrencyException nsce) {
			throw new NotFoundException(
				String.format(
					"Unable to find currency with code: %s. Currency code " +
						"should be expressed with 3-letter ISO 4217 format",
					priceListForm.getCurrency()),
				nsce);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PriceListNestedCollectionResource.class);

	@Reference
	private CommercePriceListService _commercePriceListService;

	@Reference
	private PriceListHelper _priceListHelper;

	@Reference
	private PriceListPermissionChecker _priceListPermissionChecker;

}