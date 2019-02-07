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

package com.liferay.segments.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermissionFactory;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.segments.constants.SegmentsActionKeys;
import com.liferay.segments.constants.SegmentsConstants;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.service.base.SegmentsEntryServiceBaseImpl;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Eduardo García
 */
public class SegmentsEntryServiceImpl extends SegmentsEntryServiceBaseImpl {

	@Override
	public SegmentsEntry addSegmentsEntry(
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			boolean active, String criteria, String key, String source,
			String type, ServiceContext serviceContext)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			SegmentsActionKeys.MANAGE_SEGMENTS_ENTRIES);

		return segmentsEntryLocalService.addSegmentsEntry(
			nameMap, descriptionMap, active, criteria, key, source, type,
			serviceContext);
	}

	@Override
	public SegmentsEntry deleteSegmentsEntry(long segmentsEntryId)
		throws PortalException {

		_segmentsEntryResourcePermission.check(
			getPermissionChecker(), segmentsEntryId, ActionKeys.DELETE);

		return segmentsEntryLocalService.deleteSegmentsEntry(segmentsEntryId);
	}

	@Override
	public List<SegmentsEntry> getSegmentsEntries(
		long groupId, boolean includeAncestorSegmentsEntries, int start,
		int end, OrderByComparator<SegmentsEntry> orderByComparator) {

		if (!includeAncestorSegmentsEntries) {
			return segmentsEntryPersistence.filterFindByGroupId(
				groupId, start, end, orderByComparator);
		}

		return segmentsEntryPersistence.filterFindByGroupId(
			ArrayUtil.append(
				PortalUtil.getAncestorSiteGroupIds(groupId), groupId),
			start, end, orderByComparator);
	}

	@Override
	public int getSegmentsEntriesCount(
		long groupId, boolean includeAncestorSegmentsEntries) {

		if (!includeAncestorSegmentsEntries) {
			return segmentsEntryPersistence.filterCountByGroupId(groupId);
		}

		return segmentsEntryPersistence.filterCountByGroupId(
			ArrayUtil.append(
				PortalUtil.getAncestorSiteGroupIds(groupId), groupId));
	}

	@Override
	public SegmentsEntry getSegmentsEntry(long segmentsEntryId)
		throws PortalException {

		_segmentsEntryResourcePermission.check(
			getPermissionChecker(), segmentsEntryId, ActionKeys.VIEW);

		return segmentsEntryLocalService.getSegmentsEntry(segmentsEntryId);
	}

	@Override
	public BaseModelSearchResult<SegmentsEntry> searchSegmentsEntries(
			long companyId, long groupId, String keywords,
			boolean includeAncestorSegmentsEntries, int start, int end,
			Sort sort)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), groupId, ActionKeys.VIEW);

		return segmentsEntryLocalService.searchSegmentsEntries(
			companyId, groupId, keywords, includeAncestorSegmentsEntries, start,
			end, sort);
	}

	@Override
	public SegmentsEntry updateSegmentsEntry(
			long segmentsEntryId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, boolean active, String criteria,
			String key, ServiceContext serviceContext)
		throws PortalException {

		_segmentsEntryResourcePermission.check(
			getPermissionChecker(), segmentsEntryId, ActionKeys.UPDATE);

		return segmentsEntryLocalService.updateSegmentsEntry(
			segmentsEntryId, nameMap, descriptionMap, active, criteria, key,
			serviceContext);
	}

	private static volatile PortletResourcePermission
		_portletResourcePermission =
			PortletResourcePermissionFactory.getInstance(
				SegmentsEntryServiceImpl.class, "_portletResourcePermission",
				SegmentsConstants.RESOURCE_NAME);
	private static volatile ModelResourcePermission<SegmentsEntry>
		_segmentsEntryResourcePermission =
			ModelResourcePermissionFactory.getInstance(
				SegmentsEntryServiceImpl.class,
				"_segmentsEntryResourcePermission", SegmentsEntry.class);

}