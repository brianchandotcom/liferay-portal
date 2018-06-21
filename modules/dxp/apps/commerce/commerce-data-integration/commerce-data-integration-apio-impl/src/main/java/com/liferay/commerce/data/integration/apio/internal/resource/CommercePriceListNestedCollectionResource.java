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
import com.liferay.commerce.data.integration.apio.identifiers.CommercePriceListIdentifier;
import com.liferay.commerce.data.integration.apio.internal.exceptions.ConflictException;
import com.liferay.commerce.data.integration.apio.internal.form.CommercePriceListUpdaterForm;
import com.liferay.commerce.data.integration.apio.internal.form.CommercePriceListUpserterForm;
import com.liferay.commerce.data.integration.apio.internal.util.CommercePriceListHelper;
import com.liferay.commerce.price.list.exception.CommercePriceListDisplayDateException;
import com.liferay.commerce.price.list.exception.CommercePriceListExpirationDateException;
import com.liferay.commerce.price.list.exception.NoSuchPriceListException;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.service.CommercePriceListService;
import com.liferay.portal.apio.permission.HasPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.site.apio.architect.identifier.WebSiteIdentifier;

import java.util.List;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the information necessary to expose {@link CommercePriceList}
 * resources through a web API.
 *
 * @author Zoltán Takács
 */
@Component(immediate = true)
public class CommercePriceListNestedCollectionResource
	implements
		NestedCollectionResource<CommercePriceList, Long,
                CommercePriceListIdentifier, Long, WebSiteIdentifier> {

	@Override
	public NestedCollectionRoutes<CommercePriceList, Long, Long>
		collectionRoutes(
			NestedCollectionRoutes.Builder<CommercePriceList, Long, Long>
				builder) {

		return builder.addGetter(
			this::_getPageItems
		).addCreator(
			this::_upsertCommercePriceList,
			_hasPermission.forAddingIn(CommercePriceListIdentifier.class),
			CommercePriceListUpserterForm::buildForm
		).build();
	}

	@Override
	public String getName() {
		return "commerce-price-list";
	}

	@Override
	public ItemRoutes<CommercePriceList, Long> itemRoutes(
		ItemRoutes.Builder<CommercePriceList, Long> builder) {

		return builder.addGetter(
			_commercePriceListHelper::getCommercePriceList
		).addUpdater(
			this::_updateCommercePriceList, _hasPermission::forUpdating,
			CommercePriceListUpdaterForm::buildForm
		).addRemover(
			idempotent(_commercePriceListService::deleteCommercePriceList),
			_hasPermission::forDeleting
		).build();
	}

	@Override
	public Representor<CommercePriceList> representor(
		Representor.Builder<CommercePriceList, Long> builder) {

		return builder.types(
			"CommercePriceList"
		).identifier(
			CommercePriceList::getCommercePriceListId
		).addBidirectionalModel(
			"webSite", "commercePriceLists", WebSiteIdentifier.class,
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
			"id", CommercePriceList::getCommercePriceListId
		).addNumber(
			"priority", CommercePriceList::getPriority
		).addString(
			"currency", CommercePriceListHelper::getCurrencyCode
		).addString(
			"externalReferenceCode", CommercePriceList::getExternalReferenceCode
		).addString(
			"name", CommercePriceList::getName
		).build();
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
			Long commercePriceListId, CommercePriceListUpdaterForm commercePriceListUpdaterForm)
		throws NotFoundException, PortalException {

		try {
			return _commercePriceListHelper.updateCommercePriceList(
				commercePriceListId, commercePriceListUpdaterForm.getCurrency(),
				commercePriceListUpdaterForm.getName(),
				commercePriceListUpdaterForm.getPriority(),
				commercePriceListUpdaterForm.isNeverExpire(),
				commercePriceListUpdaterForm.getDisplayDate(),
				commercePriceListUpdaterForm.getExpirationDate());
		}
		catch (NoSuchCurrencyException nsce) {
			throw new NotFoundException(
				String.format(
					"Unable to find currency with code: %s. Currency code " +
						"should be expressed with 3-letter ISO 4217 format",
					commercePriceListUpdaterForm.getCurrency()),
				nsce);
		}
		catch (CommercePriceListDisplayDateException cpldde) {
			throw new ConflictException(
				"Invalid display date:  " +
					commercePriceListUpdaterForm.getDisplayDate(),
				Response.Status.CONFLICT.getStatusCode(), cpldde);
		}
		catch (CommercePriceListExpirationDateException cplede) {
			throw new ConflictException(
				"Invalid expiration date:  " +
					commercePriceListUpdaterForm.getExpirationDate(),
				Response.Status.CONFLICT.getStatusCode(), cplede);
		}
	}

	private CommercePriceList _upsertCommercePriceList(
			Long groupId, CommercePriceListUpserterForm commercePriceListUpserterForm)
		throws PortalException {

		try {
			return _commercePriceListHelper.upsertCommercePriceList(
				groupId, commercePriceListUpserterForm.getCommercePriceListId(),
				commercePriceListUpserterForm.getCurrency(),
				commercePriceListUpserterForm.getName(),
				commercePriceListUpserterForm.getPriority(),
				commercePriceListUpserterForm.isNeverExpire(),
				commercePriceListUpserterForm.getDisplayDate(),
				commercePriceListUpserterForm.getExpirationDate(),
				commercePriceListUpserterForm.getExternalReferenceCode());
		}
		catch (NoSuchPriceListException nsple) {
			throw new NotFoundException(
				"Unable to update price list: " + nsple.getLocalizedMessage(),
				nsple);
		}
		catch (NoSuchCurrencyException nsce) {
			throw new NotFoundException(
				String.format(
					"Unable to find currency with code: %s. Currency code " +
						"should be expressed with 3-letter ISO 4217 format",
					commercePriceListUpserterForm.getCurrency()),
				nsce);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommercePriceListNestedCollectionResource.class);

	@Reference
	private CommercePriceListService _commercePriceListService;

	@Reference(
		target = "(model.class.name=com.liferay.commerce.price.list.model.CommercePriceList)"
	)
	private HasPermission<Long> _hasPermission;

	@Reference
	private CommercePriceListHelper _commercePriceListHelper;

}