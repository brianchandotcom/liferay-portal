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

import com.liferay.commerce.user.segment.constants.CommerceUserSegmentsActionKeys;
import com.liferay.commerce.user.segment.constants.CommerceUserSegmentsConstants;
import com.liferay.commerce.user.segment.model.CommerceUserSegmentEntry;
import com.liferay.commerce.user.segment.service.base.CommerceUserSegmentEntryServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermissionFactory;
import com.liferay.portal.kernel.service.ServiceContext;

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
			CommerceUserSegmentsActionKeys.
				ADD_COMMERCE_USER_SEGMENTATION_ENTRY);

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
			long groupId, int start, int end)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), groupId, ActionKeys.VIEW);

		return commerceUserSegmentEntryLocalService.
			getCommerceUserSegmentEntries(groupId, start, end);
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
				CommerceUserSegmentsConstants.RESOURCE_NAME);

}