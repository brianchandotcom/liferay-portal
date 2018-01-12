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
import com.liferay.commerce.model.CommercePriceList;
import com.liferay.commerce.service.base.CommercePriceListServiceBaseImpl;
import com.liferay.commerce.service.permission.CommercePermission;
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
public class CommercePriceListServiceImpl
	extends CommercePriceListServiceBaseImpl {

	@Override
	public CommercePriceList addCommercePriceList(
			long commerceCurrencyId, String name, double priority,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, ServiceContext serviceContext)
		throws PortalException {

		CommercePermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			CommerceActionKeys.MANAGE_COMMERCE_PRICE_LISTS);

		return commercePriceListLocalService.addCommercePriceList(
			commerceCurrencyId, name, priority, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire,
			serviceContext);
	}

	@Override
	public void deleteCommercePriceList(long commercePriceListId)
		throws PortalException {

		CommercePriceList commercePriceList =
			commercePriceListLocalService.fetchCommercePriceList(
				commercePriceListId);

		if (commercePriceList != null) {
			CommercePermission.check(
				getPermissionChecker(), commercePriceList.getGroupId(),
				CommerceActionKeys.MANAGE_COMMERCE_PRICE_LISTS);

			commercePriceListLocalService.deleteCommercePriceList(
				commercePriceListId);
		}
	}

	@Override
	public CommercePriceList fetchCommercePriceList(long commercePriceListId) {
		return commercePriceListLocalService.fetchCommercePriceList(
			commercePriceListId);
	}

	@Override
	public List<CommercePriceList> getCommercePriceLists(
		long groupId, int start, int end) {

		return commercePriceListLocalService.getCommercePriceLists(
			groupId, start, end);
	}

	@Override
	public List<CommercePriceList> getCommercePriceLists(
		long groupId, int status, int start, int end,
		OrderByComparator<CommercePriceList> orderByComparator) {

		return commercePriceListLocalService.getCommercePriceLists(
			groupId, status, start, end, orderByComparator);
	}

	@Override
	public int getCommercePriceListsCount(long groupId, int status) {
		return commercePriceListLocalService.getCommercePriceListsCount(
			groupId, status);
	}

	@Override
	public Hits search(SearchContext searchContext) {
		return commercePriceListLocalService.search(searchContext);
	}

	@Override
	public BaseModelSearchResult<CommercePriceList> searchCommercePriceLists(
			long companyId, long groupId, String keywords, int status,
			int start, int end, Sort sort)
		throws PortalException {

		return commercePriceListLocalService.searchCommercePriceLists(
			companyId, groupId, keywords, status, start, end, sort);
	}

	@Override
	public CommercePriceList updateCommercePriceList(
			long commercePriceListId, long commerceCurrencyId, String name,
			double priority, int displayDateMonth, int displayDateDay,
			int displayDateYear, int displayDateHour, int displayDateMinute,
			int expirationDateMonth, int expirationDateDay,
			int expirationDateYear, int expirationDateHour,
			int expirationDateMinute, boolean neverExpire,
			ServiceContext serviceContext)
		throws PortalException {

		CommercePermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			CommerceActionKeys.MANAGE_COMMERCE_PRICE_LISTS);

		return commercePriceListLocalService.updateCommercePriceList(
			commercePriceListId, commerceCurrencyId, name, priority,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, serviceContext);
	}

}