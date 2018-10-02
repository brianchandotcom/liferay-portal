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
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermissionFactory;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.segments.constants.SegmentsActionKeys;
import com.liferay.segments.constants.SegmentsConstants;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.model.SegmentsEntryRel;
import com.liferay.segments.service.base.SegmentsEntryRelServiceBaseImpl;

import java.util.List;

/**
 * The implementation of the segments entry rel remote service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.segments.service.SegmentsEntryRelService} interface.
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have security checks based on the propagated JAAS credentials because this service can be accessed remotely.
 * </p>
 *
 * @author Eduardo Garcia
 * @see SegmentsEntryRelServiceBaseImpl
 * @see com.liferay.segments.service.SegmentsEntryRelServiceUtil
 */
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
	public void deleteCommerceDiscountRel(long segmentsEntryRelId)
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
	public List<SegmentsEntryRel> getSegmentsEntryRels(long segmentsEntryId)
		throws PortalException {

		_segmentsEntryResourcePermission.check(
			getPermissionChecker(), segmentsEntryId, ActionKeys.VIEW);

		return segmentsEntryRelLocalService.getSegmentsEntryRels(
			segmentsEntryId);
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