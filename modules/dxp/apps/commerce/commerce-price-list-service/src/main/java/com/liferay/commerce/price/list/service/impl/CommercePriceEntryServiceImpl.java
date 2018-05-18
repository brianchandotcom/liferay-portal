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
import com.liferay.commerce.price.list.model.CommercePriceEntry;
import com.liferay.commerce.price.list.service.base.CommercePriceEntryServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermissionFactory;
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

		_portletResourcePermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			CommercePriceListActionKeys.MANAGE_COMMERCE_PRICE_LISTS);

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
			_portletResourcePermission.check(
				getPermissionChecker(), commercePriceEntry.getGroupId(),
				CommercePriceListActionKeys.MANAGE_COMMERCE_PRICE_LISTS);

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

		_portletResourcePermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			CommercePriceListActionKeys.MANAGE_COMMERCE_PRICE_LISTS);

		return commercePriceEntryLocalService.updateCommercePriceEntry(
			commercePriceEntryId, price, promoPrice, serviceContext);
	}

	private static volatile PortletResourcePermission
		_portletResourcePermission =
			PortletResourcePermissionFactory.getInstance(
				CommercePriceEntryServiceImpl.class,
				"_portletResourcePermission",
				CommercePriceListConstants.RESOURCE_NAME);

}