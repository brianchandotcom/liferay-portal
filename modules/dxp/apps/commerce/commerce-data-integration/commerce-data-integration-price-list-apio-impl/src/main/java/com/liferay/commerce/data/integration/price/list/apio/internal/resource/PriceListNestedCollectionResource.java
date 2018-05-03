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

package com.liferay.commerce.data.integration.price.list.apio.internal.resource;

import static com.liferay.portal.apio.idempotent.Idempotent.idempotent;

import com.liferay.apio.architect.functional.Try;
import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.resource.NestedCollectionResource;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.apio.architect.routes.NestedCollectionRoutes;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.data.integration.price.list.apio.identifier.PriceListIdentifier;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.service.CommercePriceListService;
import com.liferay.portal.apio.permission.HasPermission;
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
 * Provides the information necessary to expose
 * {@link CommercePriceList} resources
 * through a web API.
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
			this::_getCPDefinition
		).addRemover(
			idempotent(_commercePriceListService::deleteCommercePriceList),
			_hasPermission.forDeleting(CommercePriceList.class)
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
			"dateCreated", CommercePriceList::getCreateDate
		).addDate(
			"dateModified", CommercePriceList::getModifiedDate
		).addString(
			"currency", this::_getCurrencyCode
		).addString(
			"name", CommercePriceList::getName
		).build();
	}

	private CommercePriceList _getCPDefinition(Long commercePriceListId) {
		CommercePriceList commercePriceList =
			_commercePriceListService.fetchCommercePriceList(
				commercePriceListId);

		if (commercePriceList == null) {
			throw new NotFoundException(
				"Unable to find price list with ID " + commercePriceListId);
		}

		return commercePriceList;
	}

	private String _getCurrencyCode(CommercePriceList commercePriceList) {
		CommerceCurrency commerceCurrency = Try.fromFallible(
			() -> commercePriceList.getCommerceCurrency()
		).getUnchecked();

		return commerceCurrency.getCode();
	}

	private PageItems<CommercePriceList> _getPageItems(
		Pagination pagination, Long groupId) {

		try {
			List<CommercePriceList> commercePriceLists =
				_commercePriceListService.getCommercePriceLists(
					groupId, pagination.getStartPosition(),
					pagination.getEndPosition());

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

	private static final Log _log = LogFactoryUtil.getLog(
		PriceListNestedCollectionResource.class);

	@Reference
	private CommercePriceListService _commercePriceListService;

	@Reference
	private HasPermission _hasPermission;

}