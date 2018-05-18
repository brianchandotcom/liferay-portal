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
			Map<Locale, String> nameMap, String key, boolean active,
			boolean system, double priority, ServiceContext serviceContext)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			CommerceUserSegmentActionKeys.ADD_COMMERCE_USER_SEGMENTATION_ENTRY);

		return commerceUserSegmentEntryLocalService.addCommerceUserSegmentEntry(
			nameMap, key, active, system, priority, serviceContext);
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
			String key, boolean active, double priority,
			ServiceContext serviceContext)
		throws PortalException {

		_commerceUserSegmentEntryResourcePermission.check(
			getPermissionChecker(), commerceUserSegmentEntryId,
			ActionKeys.UPDATE);

		return commerceUserSegmentEntryLocalService.
			updateCommerceUserSegmentEntry(
				commerceUserSegmentEntryId, nameMap, key, active, priority,
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