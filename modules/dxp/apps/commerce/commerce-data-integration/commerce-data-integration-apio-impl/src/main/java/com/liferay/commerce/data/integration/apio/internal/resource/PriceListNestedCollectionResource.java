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

import com.liferay.apio.architect.functional.Try;
import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.resource.NestedCollectionResource;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.apio.architect.routes.NestedCollectionRoutes;
import com.liferay.commerce.currency.exception.NoSuchCurrencyException;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.data.integration.apio.constants.PriceListFieldConstants;
import com.liferay.commerce.data.integration.apio.identifiers.PriceListIdentifier;
import com.liferay.commerce.data.integration.apio.internal.form.PriceListForm;
import com.liferay.commerce.data.integration.apio.internal.security.permission.PriceListPermissionChecker;
import com.liferay.commerce.data.integration.apio.internal.util.PriceListHelper;
import com.liferay.commerce.price.list.constants.CommercePriceListConstants;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.service.CommercePriceListService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.site.apio.identifier.WebSiteIdentifier;

import java.util.List;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.ServerErrorException;

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
	public NestedCollectionRoutes<CommercePriceList, Long> collectionRoutes(
		NestedCollectionRoutes.Builder<CommercePriceList, Long> builder) {

		return builder.addGetter(
			this::_getPageItems
		).addCreator(
			this::_addCommercePriceList,
			_priceListPermissionChecker.forAdding(
				CommercePriceListConstants.RESOURCE_NAME),
			PriceListForm::buildForm
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
			_priceListPermissionChecker.forUpdating(
				CommercePriceListConstants.RESOURCE_NAME),
			PriceListForm::buildForm
		).addRemover(
			idempotent(_commercePriceListService::deleteCommercePriceList),
			_priceListPermissionChecker.forDeleting(
				CommercePriceListConstants.RESOURCE_NAME)
		).build();
	}

	@Override
	public Representor<CommercePriceList, Long> representor(
		Representor.Builder<CommercePriceList, Long> builder) {

		return builder.types(
			"PriceList"
		).identifier(
			CommercePriceList::getCommercePriceListId
		).addBidirectionalModel(
			"webSite", "priceLists", WebSiteIdentifier.class,
			CommercePriceList::getGroupId
		).addDate(
			PriceListFieldConstants.DATE_CREATED,
			CommercePriceList::getCreateDate
		).addDate(
			PriceListFieldConstants.DATE_MODIFIED,
			CommercePriceList::getModifiedDate
		).addDate(
			PriceListFieldConstants.DISPLAY_DATE,
			CommercePriceList::getDisplayDate
		).addDate(
			PriceListFieldConstants.EXPIRATION_DATE,
			CommercePriceList::getExpirationDate
		).addNumber(
			PriceListFieldConstants.PRIORITY, CommercePriceList::getPriority
		).addString(
			PriceListFieldConstants.CURRENCY, this::_getCurrencyCode
		).addString(
			PriceListFieldConstants.NAME, CommercePriceList::getName
		).build();
	}

	private CommercePriceList _addCommercePriceList(
		Long groupId, PriceListForm priceListForm) {

		try {
			return _priceListHelper.addCommercePriceList(
				groupId, priceListForm.getCurrency(), priceListForm.getName(),
				priceListForm.getPriority(), priceListForm.isNeverExpire(),
				priceListForm.getDisplayDate(),
				priceListForm.getExpirationDate());
		}
		catch (NoSuchCurrencyException nsce) {
			throw new NotFoundException(nsce.getLocalizedMessage());
		}
		catch (PortalException pe) {
			throw new ServerErrorException(500, pe);
		}
	}

	private String _getCurrencyCode(CommercePriceList commercePriceList) {
		CommerceCurrency commerceCurrency = Try.fromFallible(
			commercePriceList::getCommerceCurrency
		).getUnchecked();

		return commerceCurrency.getCode();
	}

	private PageItems<CommercePriceList> _getPageItems(
		Pagination pagination, Long groupId) {

		try {
			List<CommercePriceList> commercePriceLists =
				_commercePriceListService.getCommercePriceLists(
					groupId, WorkflowConstants.STATUS_APPROVED,
					pagination.getStartPosition(), pagination.getEndPosition(),
					null);

			if (ListUtil.isEmpty(commercePriceLists)) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Unable to find Price Lists on Site with ID " +
							groupId);
				}
			}

			int count = _commercePriceListService.getCommercePriceListsCount(
				groupId, WorkflowConstants.STATUS_APPROVED);

			return new PageItems<>(commercePriceLists, count);
		}
		catch (SystemException se) {
			throw new ServerErrorException(500, se);
		}
	}

	private CommercePriceList _updateCommercePriceList(
		Long commercePriceListId, PriceListForm priceListForm) {

		try {
			return _priceListHelper.updateCommercePriceList(
				commercePriceListId, priceListForm.getCurrency(),
				priceListForm.getName(), priceListForm.getPriority(),
				priceListForm.isNeverExpire(), priceListForm.getDisplayDate(),
				priceListForm.getExpirationDate());
		}
		catch (NoSuchCurrencyException nsce) {
			throw new NotFoundException(nsce.getLocalizedMessage());
		}
		catch (PortalException pe) {
			throw new ServerErrorException(500, pe);
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