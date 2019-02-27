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
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.ResourceBundleLoaderUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.segments.constants.SegmentsConstants;
import com.liferay.segments.exception.SegmentsExperienceSegmentsEntryException;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.service.base.SegmentsExperienceLocalServiceBaseImpl;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author David Arques
 */
public class SegmentsExperienceLocalServiceImpl
	extends SegmentsExperienceLocalServiceBaseImpl {

	@Override
	public SegmentsExperience addSegmentsExperience(
			long segmentsEntryId, long classNameId, long classPK,
			Map<Locale, String> nameMap, int priority, boolean active,
			ServiceContext serviceContext)
		throws PortalException {

		// Segments experience

		User user = userLocalService.getUser(serviceContext.getUserId());

		long groupId = serviceContext.getScopeGroupId();

		long segmentsExperienceId = counterLocalService.increment();

		SegmentsExperience segmentsExperience =
			segmentsExperiencePersistence.create(segmentsExperienceId);

		segmentsExperience.setGroupId(groupId);
		segmentsExperience.setCompanyId(user.getCompanyId());
		segmentsExperience.setUserId(user.getUserId());
		segmentsExperience.setUserName(user.getFullName());
		segmentsExperience.setCreateDate(
			serviceContext.getCreateDate(new Date()));
		segmentsExperience.setModifiedDate(
			serviceContext.getModifiedDate(new Date()));
		segmentsExperience.setSegmentsEntryId(segmentsEntryId);
		segmentsExperience.setClassNameId(classNameId);
		segmentsExperience.setClassPK(classPK);
		segmentsExperience.setNameMap(nameMap);
		segmentsExperience.setPriority(priority);
		segmentsExperience.setActive(active);

		segmentsExperiencePersistence.update(segmentsExperience);

		// Resources

		resourceLocalService.addModelResources(
			segmentsExperience, serviceContext);

		return segmentsExperience;
	}

	@Override
	public void deleteSegmentsEntrySegmentsExperiences(long segmentsEntryId)
		throws PortalException {

		List<SegmentsExperience> segmentsExperiences =
			segmentsExperiencePersistence.findBySegmentsEntryId(
				segmentsEntryId);

		for (SegmentsExperience segmentsExperience : segmentsExperiences) {
			segmentsExperienceLocalService.deleteSegmentsExperience(
				segmentsExperience.getSegmentsExperienceId());
		}
	}

	@Override
	public SegmentsExperience deleteSegmentsExperience(
			long segmentsExperienceId)
		throws PortalException {

		SegmentsExperience segmentsExperience =
			segmentsExperiencePersistence.findByPrimaryKey(
				segmentsExperienceId);

		return segmentsExperienceLocalService.deleteSegmentsExperience(
			segmentsExperience);
	}

	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public SegmentsExperience deleteSegmentsExperience(
			SegmentsExperience segmentsExperience)
		throws PortalException {

		// Segments experience

		segmentsExperiencePersistence.remove(segmentsExperience);

		// Resources

		resourceLocalService.deleteResource(
			segmentsExperience, ResourceConstants.SCOPE_INDIVIDUAL);

		return segmentsExperience;
	}

	@Override
	public void deleteSegmentsExperiences(
			long groupId, long classNameId, long classPK)
		throws PortalException {

		List<SegmentsExperience> segmentsExperiences =
			segmentsExperiencePersistence.findByG_C_C(
				groupId, classNameId, classPK);

		for (SegmentsExperience segmentsExperience : segmentsExperiences) {
			segmentsExperienceLocalService.deleteSegmentsExperience(
				segmentsExperience.getSegmentsExperienceId());
		}
	}

	@Override
	public SegmentsExperience fetchDefaultSegmentsExperience(
			long groupId, long classNameId, long classPK,
			boolean addDefaultExperience)
		throws PortalException {

		SegmentsEntry segmentsEntry = _getDefaultSegment(groupId);

		SegmentsExperience segmentsExperience =
			segmentsExperiencePersistence.fetchByG_S_C_C(
				groupId, segmentsEntry.getSegmentsEntryId(), classNameId,
				classPK);

		if (segmentsExperience != null) {
			return segmentsExperience;
		}

		if (!addDefaultExperience) {
			return null;
		}

		return _addDefaultExperience(
			groupId, segmentsEntry.getSegmentsEntryId(), classNameId, classPK);
	}

	@Override
	public SegmentsExperience fetchSegmentsExperience(
		long segmentsExperienceId) {

		return segmentsExperiencePersistence.fetchByPrimaryKey(
			segmentsExperienceId);
	}

	@Override
	public SegmentsExperience getSegmentsExperience(long segmentsExperienceId)
		throws PortalException {

		return segmentsExperiencePersistence.findByPrimaryKey(
			segmentsExperienceId);
	}

	@Override
	public List<SegmentsExperience> getSegmentsExperiences(
			long groupId, long classNameId, long classPK, boolean active,
			boolean addDefaultExperience, int start, int end,
			OrderByComparator<SegmentsExperience> orderByComparator)

		throws PortalException {

		if (addDefaultExperience) {
			segmentsExperienceLocalService.fetchDefaultSegmentsExperience(
				groupId, classNameId, classPK, true);
		}

		return segmentsExperiencePersistence.findByG_C_C_A(
			groupId, classNameId, classPK, active, start, end,
			orderByComparator);
	}

	@Override
	public List<SegmentsExperience> getSegmentsExperiences(
			long groupId, long classNameId, long classPK, boolean active,
			int start, int end,
			OrderByComparator<SegmentsExperience> orderByComparator)
		throws PortalException {

		return segmentsExperienceLocalService.getSegmentsExperiences(
			groupId, classNameId, classPK, active, true, start, end,
			orderByComparator);
	}

	@Override
	public List<SegmentsExperience> getSegmentsExperiences(
			long groupId, long[] segmentsEntryIds, long classNameId,
			long classPK, boolean active, boolean addDefaultExperience,
			int start, int end,
			OrderByComparator<SegmentsExperience> orderByComparator)

		throws PortalException {

		if (addDefaultExperience) {
			segmentsExperienceLocalService.fetchDefaultSegmentsExperience(
				groupId, classNameId, classPK, true);
		}

		return segmentsExperiencePersistence.findByG_S_C_C_A(
			groupId, segmentsEntryIds, classNameId, classPK, active, start, end,
			orderByComparator);
	}

	@Override
	public List<SegmentsExperience> getSegmentsExperiences(
			long groupId, long[] segmentsEntryIds, long classNameId,
			long classPK, boolean active, int start, int end,
			OrderByComparator<SegmentsExperience> orderByComparator)

		throws PortalException {

		return segmentsExperienceLocalService.getSegmentsExperiences(
			groupId, segmentsEntryIds, classNameId, classPK, active, true,
			start, end, orderByComparator);
	}

	@Override
	public int getSegmentsExperiencesCount(
			long groupId, long classNameId, long classPK, boolean active)

		throws PortalException {

		return segmentsExperienceLocalService.getSegmentsExperiencesCount(
			groupId, classNameId, classPK, active, true);
	}

	@Override
	public int getSegmentsExperiencesCount(
			long groupId, long classNameId, long classPK, boolean active,
			boolean addDefaultExperience)
		throws PortalException {

		if (addDefaultExperience) {
			segmentsExperienceLocalService.fetchDefaultSegmentsExperience(
				groupId, classNameId, classPK, true);
		}

		return segmentsExperiencePersistence.countByG_C_C_A(
			groupId, classNameId, classPK, active);
	}

	@Override
	public SegmentsExperience updateSegmentsExperience(
			long segmentsExperienceId, long segmentsEntryId,
			Map<Locale, String> nameMap, int priority, boolean active)
		throws PortalException {

		SegmentsExperience segmentsExperience =
			segmentsExperiencePersistence.findByPrimaryKey(
				segmentsExperienceId);

		segmentsExperience.setSegmentsEntryId(segmentsEntryId);
		segmentsExperience.setNameMap(nameMap);
		segmentsExperience.setPriority(priority);
		segmentsExperience.setActive(active);

		return segmentsExperiencePersistence.update(segmentsExperience);
	}

	private SegmentsExperience _addDefaultExperience(
			long groupId, long segmentsEntryId, long classNameId, long classPK)
		throws PortalException {

		Map<Locale, String> nameMap = ResourceBundleUtil.getLocalizationMap(
			_getResourceBundleLoader(), "default-experience-name");

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGuestPermissions(true);
		serviceContext.setAddGroupPermissions(true);
		serviceContext.setScopeGroupId(groupId);

		Group group = groupLocalService.getGroup(groupId);

		long defaultUserId = userLocalService.getDefaultUserId(
			group.getCompanyId());

		serviceContext.setUserId(defaultUserId);

		return segmentsExperienceLocalService.addSegmentsExperience(
			segmentsEntryId, classNameId, classPK, nameMap, 0, true,
			serviceContext);
	}

	private SegmentsEntry _getDefaultSegment(long groupId)
		throws PortalException {

		SegmentsEntry segmentsEntry =
			segmentsEntryLocalService.fetchSegmentsEntry(
				groupId, SegmentsConstants.KEY_DEFAULT, true);

		if (segmentsEntry == null) {
			throw new SegmentsExperienceSegmentsEntryException(
				"Unable to find default segment");
		}

		return segmentsEntry;
	}

	private ResourceBundleLoader _getResourceBundleLoader() {
		return ResourceBundleLoaderUtil.
			getResourceBundleLoaderByBundleSymbolicName(
				"com.liferay.segments.lang");
	}

}