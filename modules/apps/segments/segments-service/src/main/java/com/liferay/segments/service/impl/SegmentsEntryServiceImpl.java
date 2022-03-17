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

import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.segments.constants.SegmentsActionKeys;
import com.liferay.segments.constants.SegmentsConstants;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.service.base.SegmentsEntryServiceBaseImpl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo García
 */
@Component(
	property = {
		"json.web.service.context.name=segments",
		"json.web.service.context.path=SegmentsEntry"
	},
	service = AopService.class
)
public class SegmentsEntryServiceImpl extends SegmentsEntryServiceBaseImpl {

	@Override
	public SegmentsEntry addSegmentsEntry(
			String segmentsEntryKey, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, boolean active, String criteria,
			String type, ServiceContext serviceContext)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			SegmentsActionKeys.MANAGE_SEGMENTS_ENTRIES);

		return segmentsEntryLocalService.addSegmentsEntry(
			segmentsEntryKey, nameMap, descriptionMap, active, criteria, type,
			serviceContext);
	}

	@Override
	public SegmentsEntry addSegmentsEntry(
			String segmentsEntryKey, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, boolean active, String criteria,
			String source, String type, ServiceContext serviceContext)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			SegmentsActionKeys.MANAGE_SEGMENTS_ENTRIES);

		return segmentsEntryLocalService.addSegmentsEntry(
			segmentsEntryKey, nameMap, descriptionMap, active, criteria, source,
			type, serviceContext);
	}

	@Override
	public void addSegmentsEntryClassPKs(
			long segmentsEntryId, long[] classPKs,
			ServiceContext serviceContext)
		throws PortalException {

		_segmentsEntryResourcePermission.check(
			getPermissionChecker(), segmentsEntryId, ActionKeys.UPDATE);

		segmentsEntryLocalService.addSegmentsEntryClassPKs(
			segmentsEntryId, classPKs, serviceContext);
	}

	@Override
	public SegmentsEntry deleteSegmentsEntry(long segmentsEntryId)
		throws PortalException {

		_segmentsEntryResourcePermission.check(
			getPermissionChecker(), segmentsEntryId, ActionKeys.DELETE);

		return segmentsEntryLocalService.deleteSegmentsEntry(segmentsEntryId);
	}

	@Override
	public void deleteSegmentsEntryClassPKs(
			long segmentsEntryId, long[] classPKs)
		throws PortalException {

		_segmentsEntryResourcePermission.check(
			getPermissionChecker(), segmentsEntryId, ActionKeys.UPDATE);

		segmentsEntryLocalService.deleteSegmentsEntryClassPKs(
			segmentsEntryId, classPKs);
	}

	@Override
	public List<SegmentsEntry> getSegmentsEntries(
		long groupId, boolean includeAncestorSegmentsEntries) {

		if (!includeAncestorSegmentsEntries) {
			return segmentsEntryPersistence.filterFindByGroupId(groupId);
		}

		return segmentsEntryPersistence.filterFindByGroupId(
			ArrayUtil.append(
				_portal.getAncestorSiteGroupIds(groupId), groupId));
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
			ArrayUtil.append(_portal.getAncestorSiteGroupIds(groupId), groupId),
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
				_portal.getAncestorSiteGroupIds(groupId), groupId));
	}

	@Override
	public SegmentsEntry getSegmentsEntry(long segmentsEntryId)
		throws PortalException {

		SegmentsEntry segmentsEntry =
			segmentsEntryLocalService.getSegmentsEntry(segmentsEntryId);

		_segmentsEntryResourcePermission.check(
			getPermissionChecker(), segmentsEntryId, ActionKeys.VIEW);

		return segmentsEntry;
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
			companyId, groupId, keywords, includeAncestorSegmentsEntries,
			new LinkedHashMap<>(), start, end, sort);
	}

	@Override
	public SegmentsEntry updateSegmentsEntry(
			long segmentsEntryId, String segmentsEntryKey,
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			boolean active, String criteria, ServiceContext serviceContext)
		throws PortalException {

		_segmentsEntryResourcePermission.check(
			getPermissionChecker(), segmentsEntryId, ActionKeys.UPDATE);

		return segmentsEntryLocalService.updateSegmentsEntry(
			segmentsEntryId, segmentsEntryKey, nameMap, descriptionMap, active,
			criteria, serviceContext);
	}

	@Reference
	private Portal _portal;

	@Reference(
		target = "(resource.name=" + SegmentsConstants.RESOURCE_NAME + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

	@Reference(
		target = "(model.class.name=com.liferay.segments.model.SegmentsEntry)"
	)
	private ModelResourcePermission<SegmentsEntry>
		_segmentsEntryResourcePermission;

}