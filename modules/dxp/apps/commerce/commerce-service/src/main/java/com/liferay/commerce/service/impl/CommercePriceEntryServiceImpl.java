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
import com.liferay.commerce.model.CommercePriceEntry;
import com.liferay.commerce.service.base.CommercePriceEntryServiceBaseImpl;
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
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
public class CommercePriceEntryServiceImpl
	extends CommercePriceEntryServiceBaseImpl {

	@Override
	public CommercePriceEntry addCommercePriceEntry(
			long cpInstanceId, long commercePriceListId, BigDecimal price,
			BigDecimal promoPrice, ServiceContext serviceContext)
		throws PortalException {

		CommercePermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			CommerceActionKeys.MANAGE_COMMERCE_PRICE_LISTS);

		return commercePriceEntryLocalService.addCommercePriceEntry(
			cpInstanceId, commercePriceListId, price, promoPrice,
			serviceContext);
	}

	@Override
	public void deleteCommercePriceEntry(long commercePriceEntryId)
		throws PortalException {

		CommercePriceEntry commercePriceEntry =
			commercePriceEntryLocalService.fetchCommercePriceEntry(
				commercePriceEntryId);

		if (commercePriceEntry != null) {
			CommercePermission.check(
				getPermissionChecker(), commercePriceEntry.getGroupId(),
				CommerceActionKeys.MANAGE_COMMERCE_PRICE_LISTS);

			commercePriceEntryLocalService.deleteCommercePriceEntry(
				commercePriceEntryId);
		}
	}

	@Override
	public CommercePriceEntry fetchCommercePriceEntry(
		long commercePriceEntryId) {

		return commercePriceEntryLocalService.fetchCommercePriceEntry(
			commercePriceEntryId);
	}

	@Override
	public List<CommercePriceEntry> getCommercePriceEntries(
		long commercePriceListId, int start, int end) {

		return commercePriceEntryLocalService.getCommercePriceEntries(
			commercePriceListId, start, end);
	}

	@Override
	public List<CommercePriceEntry> getCommercePriceEntries(
		long commercePriceListId, int start, int end,
		OrderByComparator<CommercePriceEntry> orderByComparator) {

		return commercePriceEntryLocalService.getCommercePriceEntries(
			commercePriceListId, start, end, orderByComparator);
	}

	@Override
	public int getCommercePriceEntriesCount(long commercePriceListId) {
		return commercePriceEntryLocalService.getCommercePriceEntriesCount(
			commercePriceListId);
	}

	@Override
	public List<CommercePriceEntry> getInstanceCommercePriceEntries(
		long cpInstanceId, int start, int end) {

		return commercePriceEntryLocalService.getInstanceCommercePriceEntries(
			cpInstanceId, start, end);
	}

	@Override
	public List<CommercePriceEntry> getInstanceCommercePriceEntries(
		long cpInstanceId, int start, int end,
		OrderByComparator<CommercePriceEntry> orderByComparator) {

		return commercePriceEntryLocalService.getInstanceCommercePriceEntries(
			cpInstanceId, start, end, orderByComparator);
	}

	@Override
	public int getInstanceCommercePriceEntriesCount(long cpInstanceId) {
		return
			commercePriceEntryLocalService.getInstanceCommercePriceEntriesCount(
				cpInstanceId);
	}

	@Override
	public Hits search(SearchContext searchContext) {
		return commercePriceEntryLocalService.search(searchContext);
	}

	@Override
	public BaseModelSearchResult<CommercePriceEntry> searchCommercePriceEntries(
			long companyId, long groupId, long commercePriceListId,
			String keywords, int start, int end, Sort sort)
		throws PortalException {

		return commercePriceEntryLocalService.searchCommercePriceEntries(
			companyId, groupId, commercePriceListId, keywords, start, end,
			sort);
	}

	@Override
	public CommercePriceEntry updateCommercePriceEntry(
			long commercePriceEntryId, BigDecimal price, BigDecimal promoPrice,
			ServiceContext serviceContext)
		throws PortalException {

		CommercePermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			CommerceActionKeys.MANAGE_COMMERCE_PRICE_LISTS);

		return commercePriceEntryLocalService.updateCommercePriceEntry(
			commercePriceEntryId, price, promoPrice, serviceContext);
	}

}