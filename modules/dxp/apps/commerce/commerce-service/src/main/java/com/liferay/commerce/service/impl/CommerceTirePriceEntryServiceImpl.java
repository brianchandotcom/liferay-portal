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
import com.liferay.commerce.model.CommerceTirePriceEntry;
import com.liferay.commerce.service.base.CommerceTirePriceEntryServiceBaseImpl;
import com.liferay.commerce.service.permission.CommercePriceListPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceTirePriceEntryServiceImpl
	extends CommerceTirePriceEntryServiceBaseImpl {

	@Override
	public CommerceTirePriceEntry addCommerceTirePriceEntry(
			long commercePriceEntryId, double price, int minQuantity,
			ServiceContext serviceContext)
		throws PortalException {

		CommercePriceListPermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			CommerceActionKeys.MANAGE_COMMERCE_PRICE_LISTS);

		return commerceTirePriceEntryLocalService.addCommerceTirePriceEntry(
			commercePriceEntryId, price, minQuantity, serviceContext);
	}

	@Override
	public void deleteCommerceTirePriceEntry(long commerceTirePriceEntryId)
		throws PortalException {

		CommerceTirePriceEntry commerceTirePriceEntry =
			commerceTirePriceEntryLocalService.fetchCommerceTirePriceEntry(
				commerceTirePriceEntryId);

		if (commerceTirePriceEntry != null) {
			CommercePriceListPermission.check(
				getPermissionChecker(), commerceTirePriceEntry.getGroupId(),
				CommerceActionKeys.MANAGE_COMMERCE_PRICE_LISTS);

			commerceTirePriceEntryLocalService.deleteCommerceTirePriceEntry(
				commerceTirePriceEntryId);
		}
	}

	@Override
	public CommerceTirePriceEntry fetchCommerceTirePriceEntry(
		long commerceTirePriceEntryId) {

		return commerceTirePriceEntryLocalService.fetchCommerceTirePriceEntry(
			commerceTirePriceEntryId);
	}

	@Override
	public List<CommerceTirePriceEntry> getCommerceTirePriceEntries(
		long commercePriceEntryId, int start, int end) {

		return commerceTirePriceEntryLocalService.getCommerceTirePriceEntries(
			commercePriceEntryId, start, end);
	}

	@Override
	public List<CommerceTirePriceEntry> getCommerceTirePriceEntries(
		long commercePriceEntryId, int start, int end,
		OrderByComparator<CommerceTirePriceEntry> orderByComparator) {

		return commerceTirePriceEntryLocalService.getCommerceTirePriceEntries(
			commercePriceEntryId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceTirePriceEntriesCount(long commercePriceEntryId) {
		return
			commerceTirePriceEntryLocalService.getCommerceTirePriceEntriesCount(
				commercePriceEntryId);
	}

	@Override
	public Hits search(SearchContext searchContext) {
		return commerceTirePriceEntryLocalService.search(searchContext);
	}

	@Override
	public BaseModelSearchResult<CommerceTirePriceEntry>
			searchCommerceTirePriceEntries(
				long companyId, long groupId, long commercePriceEntryId,
				String keywords, int start, int end, Sort sort)
		throws PortalException {

		return
			commerceTirePriceEntryLocalService.searchCommerceTirePriceEntries(
				companyId, groupId, commercePriceEntryId, keywords, start, end,
				sort);
	}

	@Override
	public CommerceTirePriceEntry updateCommerceTirePriceEntry(
			long commerceTirePriceEntryId, double price, int minQuantity,
			ServiceContext serviceContext)
		throws PortalException {

		CommercePriceListPermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			CommerceActionKeys.MANAGE_COMMERCE_PRICE_LISTS);

		return commerceTirePriceEntryLocalService.updateCommerceTirePriceEntry(
			commerceTirePriceEntryId, price, minQuantity, serviceContext);
	}

}