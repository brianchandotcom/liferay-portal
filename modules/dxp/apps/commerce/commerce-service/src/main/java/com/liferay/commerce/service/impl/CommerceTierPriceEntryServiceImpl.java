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

package com.liferay.commerce.service.impl;

import com.liferay.commerce.constants.CommerceActionKeys;
import com.liferay.commerce.model.CommerceTierPriceEntry;
import com.liferay.commerce.service.base.CommerceTierPriceEntryServiceBaseImpl;
import com.liferay.commerce.service.permission.CommercePermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.math.BigDecimal;

import java.util.List;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceTierPriceEntryServiceImpl
	extends CommerceTierPriceEntryServiceBaseImpl {

	@Override
	public CommerceTierPriceEntry addCommerceTierPriceEntry(
			long commercePriceEntryId, BigDecimal price, int minQuantity,
			ServiceContext serviceContext)
		throws PortalException {

		CommercePermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			CommerceActionKeys.MANAGE_COMMERCE_PRICE_LISTS);

		return commerceTierPriceEntryLocalService.addCommerceTierPriceEntry(
			commercePriceEntryId, price, minQuantity, serviceContext);
	}

	@Override
	public void deleteCommerceTierPriceEntry(long commerceTierPriceEntryId)
		throws PortalException {

		CommerceTierPriceEntry commerceTierPriceEntry =
			commerceTierPriceEntryLocalService.fetchCommerceTierPriceEntry(
				commerceTierPriceEntryId);

		if (commerceTierPriceEntry != null) {
			CommercePermission.check(
				getPermissionChecker(), commerceTierPriceEntry.getGroupId(),
				CommerceActionKeys.MANAGE_COMMERCE_PRICE_LISTS);

			commerceTierPriceEntryLocalService.deleteCommerceTierPriceEntry(
				commerceTierPriceEntryId);
		}
	}

	@Override
	public CommerceTierPriceEntry fetchCommerceTierPriceEntry(
		long commerceTierPriceEntryId) {

		return commerceTierPriceEntryLocalService.fetchCommerceTierPriceEntry(
			commerceTierPriceEntryId);
	}

	@Override
	public List<CommerceTierPriceEntry> getCommerceTierPriceEntries(
		long commercePriceEntryId, int start, int end) {

		return commerceTierPriceEntryLocalService.getCommerceTierPriceEntries(
			commercePriceEntryId, start, end);
	}

	@Override
	public List<CommerceTierPriceEntry> getCommerceTierPriceEntries(
		long commercePriceEntryId, int start, int end,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator) {

		return commerceTierPriceEntryLocalService.getCommerceTierPriceEntries(
			commercePriceEntryId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceTierPriceEntriesCount(long commercePriceEntryId) {
		return
			commerceTierPriceEntryLocalService.getCommerceTierPriceEntriesCount(
				commercePriceEntryId);
	}

	@Override
	public Hits search(SearchContext searchContext) {
		return commerceTierPriceEntryLocalService.search(searchContext);
	}

	@Override
	public BaseModelSearchResult<CommerceTierPriceEntry>
			searchCommerceTierPriceEntries(
				long companyId, long groupId, long commercePriceEntryId,
				String keywords, int start, int end, Sort sort)
		throws PortalException {

		return
			commerceTierPriceEntryLocalService.searchCommerceTierPriceEntries(
				companyId, groupId, commercePriceEntryId, keywords, start, end,
				sort);
	}

	@Override
	public CommerceTierPriceEntry updateCommerceTierPriceEntry(
			long commerceTierPriceEntryId, BigDecimal price, int minQuantity,
			ServiceContext serviceContext)
		throws PortalException {

		CommercePermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			CommerceActionKeys.MANAGE_COMMERCE_PRICE_LISTS);

		return commerceTierPriceEntryLocalService.updateCommerceTierPriceEntry(
			commerceTierPriceEntryId, price, minQuantity, serviceContext);
	}

}