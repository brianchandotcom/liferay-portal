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
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.segments.constants.SegmentsActionKeys;
import com.liferay.segments.constants.SegmentsConstants;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.model.SegmentsEntryRel;
import com.liferay.segments.service.base.SegmentsEntryRelServiceBaseImpl;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo García
 */
@Component(
	property = {
		"json.web.service.context.name=segments",
		"json.web.service.context.path=SegmentsEntryRel"
	},
	service = AopService.class
)
public class SegmentsEntryRelServiceImpl
	extends SegmentsEntryRelServiceBaseImpl {

	@Override
	public SegmentsEntryRel addSegmentsEntryRel(
			long segmentsEntryId, long classNameId, long classPK,
			ServiceContext serviceContext)
		throws PortalException {

		_segmentsEntryResourcePermission.check(
			getPermissionChecker(), segmentsEntryId, ActionKeys.UPDATE);

		return segmentsEntryRelLocalService.addSegmentsEntryRel(
			segmentsEntryId, classNameId, classPK, serviceContext);
	}

	@Override
	public void deleteSegmentsEntryRel(long segmentsEntryRelId)
		throws PortalException {

		SegmentsEntryRel segmentsEntryRel =
			segmentsEntryRelLocalService.getSegmentsEntryRel(
				segmentsEntryRelId);

		_segmentsEntryResourcePermission.check(
			getPermissionChecker(), segmentsEntryRel.getSegmentsEntryId(),
			ActionKeys.UPDATE);

		segmentsEntryRelLocalService.deleteSegmentsEntryRel(segmentsEntryRel);
	}

	@Override
	public void deleteSegmentsEntryRel(
			long segmentsEntryId, long classNameId, long classPK)
		throws PortalException {

		_segmentsEntryResourcePermission.check(
			getPermissionChecker(), segmentsEntryId, ActionKeys.UPDATE);

		segmentsEntryRelLocalService.deleteSegmentsEntryRel(
			segmentsEntryId, classNameId, classPK);
	}

	@Override
	public List<SegmentsEntryRel> getSegmentsEntryRels(long segmentsEntryId)
		throws PortalException {

		_segmentsEntryResourcePermission.check(
			getPermissionChecker(), segmentsEntryId, ActionKeys.VIEW);

		return segmentsEntryRelLocalService.getSegmentsEntryRels(
			segmentsEntryId);
	}

	@Override
	public List<SegmentsEntryRel> getSegmentsEntryRels(
			long segmentsEntryId, int start, int end,
			OrderByComparator<SegmentsEntryRel> orderByComparator)
		throws PortalException {

		_segmentsEntryResourcePermission.check(
			getPermissionChecker(), segmentsEntryId, ActionKeys.VIEW);

		return segmentsEntryRelLocalService.getSegmentsEntryRels(
			segmentsEntryId, start, end, orderByComparator);
	}

	@Override
	public List<SegmentsEntryRel> getSegmentsEntryRels(
			long groupId, long classNameId, long classPK)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), groupId,
			SegmentsActionKeys.MANAGE_SEGMENTS_ENTRIES);

		return segmentsEntryRelLocalService.getSegmentsEntryRels(
			groupId, classNameId, classPK);
	}

	@Override
	public int getSegmentsEntryRelsCount(long segmentsEntryId)
		throws PortalException {

		_segmentsEntryResourcePermission.check(
			getPermissionChecker(), segmentsEntryId, ActionKeys.VIEW);

		return segmentsEntryRelLocalService.getSegmentsEntryRelsCount(
			segmentsEntryId);
	}

	@Override
	public int getSegmentsEntryRelsCount(
			long groupId, long classNameId, long classPK)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), groupId,
			SegmentsActionKeys.MANAGE_SEGMENTS_ENTRIES);

		return segmentsEntryRelLocalService.getSegmentsEntryRelsCount(
			groupId, classNameId, classPK);
	}

	@Override
	public boolean hasSegmentsEntryRel(
		long segmentsEntryId, long classNameId, long classPK) {

		return segmentsEntryRelLocalService.hasSegmentsEntryRel(
			segmentsEntryId, classNameId, classPK);
	}

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