/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.price.list.service.impl;

import com.liferay.commerce.price.list.constants.CommercePriceListActionKeys;
import com.liferay.commerce.price.list.constants.CommercePriceListConstants;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.service.base.CommercePriceListServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermissionFactory;
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

		_portletResourcePermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			CommercePriceListActionKeys.MANAGE_COMMERCE_PRICE_LISTS);

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
			_portletResourcePermission.check(
				getPermissionChecker(), commercePriceList.getGroupId(),
				CommercePriceListActionKeys.MANAGE_COMMERCE_PRICE_LISTS);

			commercePriceListLocalService.deleteCommercePriceList(
				commercePriceListId);
		}
	}

	@Override
	public CommercePriceList fetchCommercePriceList(long commercePriceListId)
		throws PortalException {

		CommercePriceList commercePriceList =
			commercePriceListLocalService.fetchCommercePriceList(
				commercePriceListId);

		if (commercePriceList != null) {
			_portletResourcePermission.check(
				getPermissionChecker(), commercePriceList.getGroupId(),
				CommercePriceListActionKeys.MANAGE_COMMERCE_PRICE_LISTS);
		}

		return commercePriceList;
	}

	@Override
	public List<CommercePriceList> getCommercePriceLists(
			long groupId, int start, int end)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), groupId,
			CommercePriceListActionKeys.MANAGE_COMMERCE_PRICE_LISTS);

		return commercePriceListLocalService.getCommercePriceLists(
			groupId, start, end);
	}

	@Override
	public List<CommercePriceList> getCommercePriceLists(
			long groupId, int status, int start, int end,
			OrderByComparator<CommercePriceList> orderByComparator)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), groupId,
			CommercePriceListActionKeys.MANAGE_COMMERCE_PRICE_LISTS);

		return commercePriceListLocalService.getCommercePriceLists(
			groupId, status, start, end, orderByComparator);
	}

	@Override
	public int getCommercePriceListsCount(long groupId, int status)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), groupId,
			CommercePriceListActionKeys.MANAGE_COMMERCE_PRICE_LISTS);

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

		_portletResourcePermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			CommercePriceListActionKeys.MANAGE_COMMERCE_PRICE_LISTS);

		return commercePriceListLocalService.updateCommercePriceList(
			commercePriceListId, commerceCurrencyId, name, priority,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, serviceContext);
	}

	private static volatile PortletResourcePermission
		_portletResourcePermission =
			PortletResourcePermissionFactory.getInstance(
				CommercePriceListServiceImpl.class,
				"_portletResourcePermission",
				CommercePriceListConstants.RESOURCE_NAME);

}