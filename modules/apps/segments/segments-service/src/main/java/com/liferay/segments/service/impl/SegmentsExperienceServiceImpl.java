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
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.segments.constants.SegmentsActionKeys;
import com.liferay.segments.constants.SegmentsConstants;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.service.base.SegmentsExperienceServiceBaseImpl;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author David Arques
 */
public class SegmentsExperienceServiceImpl
	extends SegmentsExperienceServiceBaseImpl {

	@Override
	public SegmentsExperience addSegmentsExperience(
			long segmentsEntryId, long classNameId, long classPK,
			Map<Locale, String> nameMap, int priority, boolean active,
			ServiceContext serviceContext)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			SegmentsActionKeys.MANAGE_SEGMENTS_ENTRIES);

		return segmentsExperienceLocalService.addSegmentsExperience(
			segmentsEntryId, classNameId, classPK, nameMap, priority, active,
			serviceContext);
	}

	@Override
	public SegmentsExperience deleteSegmentsExperience(
			long segmentsExperienceId)
		throws PortalException {

		_segmentsExperienceResourcePermission.check(
			getPermissionChecker(), segmentsExperienceId, ActionKeys.DELETE);

		return segmentsExperienceLocalService.deleteSegmentsExperience(
			segmentsExperienceId);
	}

	@Override
	public SegmentsExperience getSegmentsExperience(long segmentsExperienceId)
		throws PortalException {

		SegmentsExperience segmentsExperience =
			segmentsExperienceLocalService.getSegmentsExperience(
				segmentsExperienceId);

		_segmentsExperienceResourcePermission.check(
			getPermissionChecker(), segmentsExperience, ActionKeys.VIEW);

		return segmentsExperience;
	}

	@Override
	public List<SegmentsExperience> getSegmentsExperiences(
			long groupId, long classNameId, long classPK, boolean active,
			int start, int end,
			OrderByComparator<SegmentsExperience> orderByComparator)
		throws PortalException {

		segmentsExperienceLocalService.fetchDefaultSegmentsExperience(
			groupId, classNameId, classPK, true);

		return segmentsExperiencePersistence.filterFindByG_C_C_A(
			groupId, classNameId, classPK, active, start, end,
			orderByComparator);
	}

	@Override
	public int getSegmentsExperiencesCount(
			long groupId, long classNameId, long classPK, boolean active)
		throws PortalException {

		segmentsExperienceLocalService.fetchDefaultSegmentsExperience(
			groupId, classNameId, classPK, true);

		return segmentsExperiencePersistence.filterCountByG_C_C_A(
			groupId, classNameId, classPK, active);
	}

	@Override
	public SegmentsExperience updateSegmentsExperience(
			long segmentsExperienceId, long segmentsEntryId,
			Map<Locale, String> nameMap, int priority, boolean active)
		throws PortalException {

		_segmentsExperienceResourcePermission.check(
			getPermissionChecker(), segmentsExperienceId, ActionKeys.UPDATE);

		return segmentsExperienceLocalService.updateSegmentsExperience(
			segmentsExperienceId, segmentsEntryId, nameMap, priority, active);
	}

	private static volatile PortletResourcePermission
		_portletResourcePermission =
			PortletResourcePermissionFactory.getInstance(
				SegmentsExperienceServiceImpl.class,
				"_portletResourcePermission", SegmentsConstants.RESOURCE_NAME);
	private static volatile ModelResourcePermission<SegmentsExperience>
		_segmentsExperienceResourcePermission =
			ModelResourcePermissionFactory.getInstance(
				SegmentsExperienceServiceImpl.class,
				"_segmentsExperienceResourcePermission",
				SegmentsExperience.class);

}