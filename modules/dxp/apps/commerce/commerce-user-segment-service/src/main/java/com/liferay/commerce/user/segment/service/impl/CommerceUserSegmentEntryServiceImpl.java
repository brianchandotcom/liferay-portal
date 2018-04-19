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

package com.liferay.commerce.user.segment.service.impl;

import com.liferay.commerce.user.segment.constants.CommerceUserSegmentActionKeys;
import com.liferay.commerce.user.segment.constants.CommerceUserSegmentConstants;
import com.liferay.commerce.user.segment.model.CommerceUserSegmentEntry;
import com.liferay.commerce.user.segment.service.base.CommerceUserSegmentEntryServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermissionFactory;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Marco Leo
 */
public class CommerceUserSegmentEntryServiceImpl
	extends CommerceUserSegmentEntryServiceBaseImpl {

	@Override
	public CommerceUserSegmentEntry addCommerceUserSegmentEntry(
			Map<Locale, String> nameMap, double priority, boolean active,
			ServiceContext serviceContext)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			CommerceUserSegmentActionKeys.ADD_COMMERCE_USER_SEGMENTATION_ENTRY);

		return commerceUserSegmentEntryLocalService.addCommerceUserSegmentEntry(
			nameMap, priority, active, serviceContext);
	}

	@Override
	public CommerceUserSegmentEntry deleteCommerceUserSegmentEntry(
			long commerceUserSegmentEntryId)
		throws PortalException {

		_commerceUserSegmentEntryResourcePermission.check(
			getPermissionChecker(), commerceUserSegmentEntryId,
			ActionKeys.DELETE);

		return commerceUserSegmentEntryLocalService.
			deleteCommerceUserSegmentEntry(commerceUserSegmentEntryId);
	}

	@Override
	public List<CommerceUserSegmentEntry> getCommerceUserSegmentEntries(
			long groupId, int start, int end,
			OrderByComparator<CommerceUserSegmentEntry> orderByComparator)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), groupId, ActionKeys.VIEW);

		return commerceUserSegmentEntryLocalService.
			getCommerceUserSegmentEntries(
				groupId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceUserSegmentEntriesCount(long groupId)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), groupId, ActionKeys.VIEW);

		return commerceUserSegmentEntryLocalService.
			getCommerceUserSegmentEntriesCount(groupId);
	}

	@Override
	public CommerceUserSegmentEntry getCommerceUserSegmentEntry(
			long commerceUserSegmentEntryId)
		throws PortalException {

		_commerceUserSegmentEntryResourcePermission.check(
			getPermissionChecker(), commerceUserSegmentEntryId,
			ActionKeys.VIEW);

		return commerceUserSegmentEntryLocalService.getCommerceUserSegmentEntry(
			commerceUserSegmentEntryId);
	}

	@Override
	public BaseModelSearchResult<CommerceUserSegmentEntry>
			searchCommerceUserSegmentEntries(
				long companyId, long groupId, String keywords, int start,
				int end, Sort sort)
		throws PortalException {

		return commerceUserSegmentEntryLocalService.
			searchCommerceUserSegmentEntries(
				companyId, groupId, keywords, start, end, sort);
	}

	@Override
	public BaseModelSearchResult<CommerceUserSegmentEntry>
			searchCommerceUserSegmentEntries(SearchContext searchContext)
		throws PortalException {

		return commerceUserSegmentEntryLocalService.
			searchCommerceUserSegmentEntries(searchContext);
	}

	@Override
	public CommerceUserSegmentEntry updateCommerceUserSegmentEntry(
			long commerceUserSegmentEntryId, Map<Locale, String> nameMap,
			double priority, boolean active, ServiceContext serviceContext)
		throws PortalException {

		_commerceUserSegmentEntryResourcePermission.check(
			getPermissionChecker(), commerceUserSegmentEntryId,
			ActionKeys.UPDATE);

		return commerceUserSegmentEntryLocalService.
			updateCommerceUserSegmentEntry(
				commerceUserSegmentEntryId, nameMap, priority, active,
				serviceContext);
	}

	private static volatile ModelResourcePermission<CommerceUserSegmentEntry>
		_commerceUserSegmentEntryResourcePermission =
			ModelResourcePermissionFactory.getInstance(
				CommerceUserSegmentEntryServiceImpl.class,
				"_commerceUserSegmentEntryResourcePermission",
				CommerceUserSegmentEntry.class);
	private static volatile PortletResourcePermission
		_portletResourcePermission =
			PortletResourcePermissionFactory.getInstance(
				CommerceUserSegmentEntryServiceImpl.class,
				"_portletResourcePermission",
				CommerceUserSegmentConstants.RESOURCE_NAME);

}