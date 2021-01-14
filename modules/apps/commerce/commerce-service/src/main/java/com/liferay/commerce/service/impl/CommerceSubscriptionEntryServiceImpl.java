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
import com.liferay.commerce.constants.CommerceConstants;
import com.liferay.commerce.model.CommerceSubscriptionEntry;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.service.base.CommerceSubscriptionEntryServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermissionFactory;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.util.List;
import java.util.stream.Stream;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceSubscriptionEntryServiceImpl
	extends CommerceSubscriptionEntryServiceBaseImpl {

	@Override
	public void deleteCommerceSubscriptionEntry(
			long commerceSubscriptionEntryId)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), null,
			CommerceActionKeys.MANAGE_COMMERCE_SUBSCRIPTIONS);

		commerceSubscriptionEntryLocalService.deleteCommerceSubscriptionEntry(
			commerceSubscriptionEntryId);
	}

	@Override
	public CommerceSubscriptionEntry fetchCommerceSubscriptionEntry(
			long commerceSubscriptionEntryId)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), null,
			CommerceActionKeys.MANAGE_COMMERCE_SUBSCRIPTIONS);

		return commerceSubscriptionEntryLocalService.
			fetchCommerceSubscriptionEntry(commerceSubscriptionEntryId);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	@Override
	public List<CommerceSubscriptionEntry> getCommerceSubscriptionEntries(
			long companyId, long userId, int start, int end,
			OrderByComparator<CommerceSubscriptionEntry> orderByComparator)
		throws PortalException {

		if (userId != getUserId()) {
			_portletResourcePermission.check(
				getPermissionChecker(), null,
				CommerceActionKeys.MANAGE_COMMERCE_SUBSCRIPTIONS);
		}

		return commerceSubscriptionEntryLocalService.
			getCommerceSubscriptionEntries(
				companyId, userId, start, end, orderByComparator);
	}

	@Override
	public List<CommerceSubscriptionEntry> getCommerceSubscriptionEntries(
			long companyId, long groupId, long userId, int start, int end,
			OrderByComparator<CommerceSubscriptionEntry> orderByComparator)
		throws PortalException {

		if (userId != getUserId()) {
			_portletResourcePermission.check(
				getPermissionChecker(), null,
				CommerceActionKeys.MANAGE_COMMERCE_SUBSCRIPTIONS);
		}

		return commerceSubscriptionEntryLocalService.
			getCommerceSubscriptionEntries(
				companyId, groupId, userId, start, end, orderByComparator);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	@Override
	public int getCommerceSubscriptionEntriesCount(long companyId, long userId)
		throws PortalException {

		if (userId != getUserId()) {
			_portletResourcePermission.check(
				getPermissionChecker(), null,
				CommerceActionKeys.MANAGE_COMMERCE_SUBSCRIPTIONS);
		}

		return commerceSubscriptionEntryLocalService.
			getCommerceSubscriptionEntriesCount(companyId, userId);
	}

	@Override
	public int getCommerceSubscriptionEntriesCount(
			long companyId, long groupId, long userId)
		throws PortalException {

		if (userId != getUserId()) {
			_portletResourcePermission.check(
				getPermissionChecker(), null,
				CommerceActionKeys.MANAGE_COMMERCE_SUBSCRIPTIONS);
		}

		return commerceSubscriptionEntryLocalService.
			getCommerceSubscriptionEntriesCount(companyId, groupId, userId);
	}

	@Override
	public BaseModelSearchResult<CommerceSubscriptionEntry>
			searchCommerceSubscriptionEntries(
				long companyId, Long maxSubscriptionCycles,
				Integer subscriptionStatus, String keywords, int start, int end,
				Sort sort)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), null,
			CommerceActionKeys.MANAGE_COMMERCE_SUBSCRIPTIONS);

		List<CommerceChannel> commerceChannels =
			_commerceChannelLocalService.searchCommerceChannels(companyId);

		Stream<CommerceChannel> stream = commerceChannels.stream();

		long[] commerceChannelGroupIds = stream.mapToLong(
			CommerceChannel::getGroupId
		).toArray();

		return commerceSubscriptionEntryLocalService.
			searchCommerceSubscriptionEntries(
				companyId, commerceChannelGroupIds, maxSubscriptionCycles,
				subscriptionStatus, keywords, start, end, sort);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	@Override
	public BaseModelSearchResult<CommerceSubscriptionEntry>
			searchCommerceSubscriptionEntries(
				long companyId, long[] groupIds, Long maxSubscriptionCycles,
				Integer subscriptionStatus, String keywords, int start, int end,
				Sort sort)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), null,
			CommerceActionKeys.MANAGE_COMMERCE_SUBSCRIPTIONS);

		return commerceSubscriptionEntryLocalService.
			searchCommerceSubscriptionEntries(
				companyId, groupIds, maxSubscriptionCycles, subscriptionStatus,
				keywords, start, end, sort);
	}

	@Override
	public CommerceSubscriptionEntry updateCommerceSubscriptionEntry(
			long commerceSubscriptionEntryId, int subscriptionLength,
			String subscriptionType,
			UnicodeProperties subscriptionTypeSettingsUnicodeProperties,
			long maxSubscriptionCycles, int subscriptionStatus,
			int nextIterationDateMonth, int nextIterationDateDay,
			int nextIterationDateYear, int nextIterationDateHour,
			int nextIterationDateMinute, int deliverySubscriptionLength,
			String deliverySubscriptionType,
			UnicodeProperties deliverySubscriptionTypeSettingsUnicodeProperties,
			long deliveryMaxSubscriptionCycles, int deliverySubscriptionStatus,
			int deliveryNextIterationDateMonth,
			int deliveryNextIterationDateDay, int deliveryNextIterationDateYear,
			int deliveryNextIterationDateHour,
			int deliveryNextIterationDateMinute)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), null,
			CommerceActionKeys.MANAGE_COMMERCE_SUBSCRIPTIONS);

		return commerceSubscriptionEntryLocalService.
			updateCommerceSubscriptionEntry(
				commerceSubscriptionEntryId, subscriptionLength,
				subscriptionType, subscriptionTypeSettingsUnicodeProperties,
				maxSubscriptionCycles, subscriptionStatus,
				nextIterationDateMonth, nextIterationDateDay,
				nextIterationDateYear, nextIterationDateHour,
				nextIterationDateMinute, deliverySubscriptionLength,
				deliverySubscriptionType,
				deliverySubscriptionTypeSettingsUnicodeProperties,
				deliveryMaxSubscriptionCycles, deliverySubscriptionStatus,
				deliveryNextIterationDateMonth, deliveryNextIterationDateDay,
				deliveryNextIterationDateYear, deliveryNextIterationDateHour,
				deliveryNextIterationDateMinute);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	@Override
	public CommerceSubscriptionEntry updateSubscriptionStatus(
			long commerceSubscriptionEntryId, int subscriptionStatus)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), null,
			CommerceActionKeys.MANAGE_COMMERCE_SUBSCRIPTIONS);

		return commerceSubscriptionEntryLocalService.updateSubscriptionStatus(
			commerceSubscriptionEntryId, subscriptionStatus);
	}

	private static volatile PortletResourcePermission
		_portletResourcePermission =
			PortletResourcePermissionFactory.getInstance(
				CommerceSubscriptionEntryServiceImpl.class,
				"_portletResourcePermission",
				CommerceConstants.RESOURCE_NAME_SUBSCRIPTION);

	@ServiceReference(type = CommerceChannelLocalService.class)
	private CommerceChannelLocalService _commerceChannelLocalService;

}