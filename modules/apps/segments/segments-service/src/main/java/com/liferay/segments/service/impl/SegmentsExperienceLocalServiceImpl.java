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

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.ResourceBundleLoaderUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.segments.exception.DefaultSegmentsExperienceException;
import com.liferay.segments.exception.SegmentsExperiencePriorityException;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.service.base.SegmentsExperienceLocalServiceBaseImpl;

import java.util.ArrayList;
import java.util.Collections;
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
	public SegmentsExperience addDefaultSegmentsExperience(
			long groupId, long classNameId, long classPK)
		throws PortalException {

		SegmentsEntry defaultSegmentsEntry =
			segmentsEntryLocalService.getDefaultSegmentsEntry(groupId);

		SegmentsExperience defaultSegmentsExperience =
			segmentsExperiencePersistence.fetchByG_S_C_C_First(
				groupId, defaultSegmentsEntry.getSegmentsEntryId(), classNameId,
				_getPublishedLayoutClassPK(classPK), null);

		if (defaultSegmentsExperience != null) {
			return defaultSegmentsExperience;
		}

		return _addDefaultSegmentsExperience(
			groupId, defaultSegmentsEntry.getSegmentsEntryId(), classNameId,
			classPK);
	}

	@Override
	public SegmentsExperience addSegmentsExperience(
			long segmentsEntryId, long classNameId, long classPK,
			Map<Locale, String> nameMap, boolean active,
			ServiceContext serviceContext)
		throws PortalException {

		return segmentsExperienceLocalService.addSegmentsExperience(
			segmentsEntryId, classNameId, classPK, nameMap,
			getSegmentsExperienceCount(
				serviceContext.getScopeGroupId(), classNameId, classPK),
			active, serviceContext);
	}

	@Override
	public SegmentsExperience addSegmentsExperience(
			long segmentsEntryId, long classNameId, long classPK,
			Map<Locale, String> nameMap, int priority, boolean active,
			ServiceContext serviceContext)
		throws PortalException {

		// Segments experience

		User user = userLocalService.getUser(serviceContext.getUserId());

		long groupId = serviceContext.getScopeGroupId();
		long publishedClassPK = _getPublishedLayoutClassPK(classPK);

		_validate(
			segmentsEntryId, groupId, classNameId, publishedClassPK, priority);

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
		segmentsExperience.setClassPK(publishedClassPK);
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

		// Segments experiences priorities

		List<SegmentsExperience> segmentsExperiences = new ArrayList<>(
			segmentsExperiencePersistence.findByG_C_C_GtP(
				segmentsExperience.getGroupId(),
				segmentsExperience.getClassNameId(),
				segmentsExperience.getClassPK(),
				segmentsExperience.getPriority()));

		Collections.reverse(segmentsExperiences);

		for (SegmentsExperience curSegmentsExperience : segmentsExperiences) {
			TransactionCommitCallbackUtil.registerCallback(
				() -> {
					curSegmentsExperience.setPriority(
						curSegmentsExperience.getPriority() - 1);

					segmentsExperienceLocalService.updateSegmentsExperience(
						curSegmentsExperience);

					return null;
				});
		}

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
				groupId, classNameId, _getPublishedLayoutClassPK(classPK));

		for (SegmentsExperience segmentsExperience : segmentsExperiences) {
			segmentsExperienceLocalService.deleteSegmentsExperience(
				segmentsExperience.getSegmentsExperienceId());
		}
	}

	@Override
	public SegmentsExperience fetchSegmentsExperience(
		long segmentsExperienceId) {

		return segmentsExperiencePersistence.fetchByPrimaryKey(
			segmentsExperienceId);
	}

	@Override
	public SegmentsExperience getDefaultSegmentsExperience(
			long groupId, long classNameId, long classPK)
		throws PortalException {

		SegmentsEntry defaultSegmentsEntry =
			segmentsEntryLocalService.getDefaultSegmentsEntry(groupId);

		SegmentsExperience defaultSegmentsExperience =
			segmentsExperiencePersistence.fetchByG_S_C_C_First(
				groupId, defaultSegmentsEntry.getSegmentsEntryId(), classNameId,
				_getPublishedLayoutClassPK(classPK), null);

		if (defaultSegmentsExperience != null) {
			return defaultSegmentsExperience;
		}

		throw new DefaultSegmentsExperienceException(
			StringBundler.concat(
				"Default segments experience is not available for class ",
				classNameId, " with ID ", classPK));
	}

	@Override
	public SegmentsExperience getSegmentsExperience(long segmentsExperienceId)
		throws PortalException {

		return segmentsExperiencePersistence.findByPrimaryKey(
			segmentsExperienceId);
	}

	@Override
	public int getSegmentsExperienceCount(
		long groupId, long classNameId, long classPK) {

		return segmentsExperiencePersistence.countByG_C_C(
			groupId, classNameId, _getPublishedLayoutClassPK(classPK));
	}

	@Override
	public List<SegmentsExperience> getSegmentsExperiences(
		long groupId, long classNameId, long classPK, boolean active, int start,
		int end, OrderByComparator<SegmentsExperience> orderByComparator) {

		return segmentsExperiencePersistence.findByG_C_C_A(
			groupId, classNameId, _getPublishedLayoutClassPK(classPK), active,
			start, end, orderByComparator);
	}

	@Override
	public List<SegmentsExperience> getSegmentsExperiences(
		long groupId, long[] segmentsEntryIds, long classNameId, long classPK,
		boolean active, int start, int end,
		OrderByComparator<SegmentsExperience> orderByComparator) {

		return segmentsExperiencePersistence.findByG_S_C_C_A(
			groupId, segmentsEntryIds, classNameId,
			_getPublishedLayoutClassPK(classPK), active, start, end,
			orderByComparator);
	}

	@Override
	public int getSegmentsExperiencesCount(
		long groupId, long classNameId, long classPK, boolean active) {

		return segmentsExperiencePersistence.countByG_C_C_A(
			groupId, classNameId, _getPublishedLayoutClassPK(classPK), active);
	}

	@Override
	public SegmentsExperience updateSegmentsExperience(
			long segmentsExperienceId, long segmentsEntryId,
			Map<Locale, String> nameMap, boolean active)
		throws PortalException {

		SegmentsExperience segmentsExperience =
			segmentsExperiencePersistence.findByPrimaryKey(
				segmentsExperienceId);

		segmentsExperience.setSegmentsEntryId(segmentsEntryId);
		segmentsExperience.setNameMap(nameMap);
		segmentsExperience.setActive(active);

		return segmentsExperiencePersistence.update(segmentsExperience);
	}

	@Override
	public SegmentsExperience updateSegmentsExperiencePriority(
			long segmentsExperienceId, int newPriority)
		throws PortalException {

		SegmentsExperience segmentsExperience =
			segmentsExperiencePersistence.findByPrimaryKey(
				segmentsExperienceId);

		int count = segmentsExperiencePersistence.countByG_C_C(
			segmentsExperience.getGroupId(),
			segmentsExperience.getClassNameId(),
			segmentsExperience.getClassPK());

		if ((newPriority < 0) || (newPriority >= count)) {
			return segmentsExperience;
		}

		SegmentsExperience swapSegmentsExperience =
			segmentsExperiencePersistence.fetchByG_C_C_P(
				segmentsExperience.getGroupId(),
				segmentsExperience.getClassNameId(),
				segmentsExperience.getClassPK(), newPriority);

		if (swapSegmentsExperience == null) {
			segmentsExperience.setPriority(newPriority);

			return segmentsExperiencePersistence.update(segmentsExperience);
		}

		final int originalPriority = segmentsExperience.getPriority();

		segmentsExperience.setPriority(-1);

		segmentsExperiencePersistence.update(segmentsExperience);

		swapSegmentsExperience.setPriority(-2);

		segmentsExperiencePersistence.update(swapSegmentsExperience);

		TransactionCommitCallbackUtil.registerCallback(
			() -> {
				segmentsExperience.setPriority(newPriority);

				segmentsExperienceLocalService.updateSegmentsExperience(
					segmentsExperience);

				swapSegmentsExperience.setPriority(originalPriority);

				segmentsExperienceLocalService.updateSegmentsExperience(
					swapSegmentsExperience);

				return null;
			});

		return segmentsExperience;
	}

	private SegmentsExperience _addDefaultSegmentsExperience(
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

	private long _getPublishedLayoutClassPK(long classPK) {
		Layout layout = layoutLocalService.fetchLayout(classPK);

		if ((layout != null) &&
			(layout.getClassNameId() == classNameLocalService.getClassNameId(
				Layout.class)) &&
			(layout.getClassPK() != 0)) {

			return layout.getClassPK();
		}

		return classPK;
	}

	private ResourceBundleLoader _getResourceBundleLoader() {
		return ResourceBundleLoaderUtil.
			getResourceBundleLoaderByBundleSymbolicName(
				"com.liferay.segments.lang");
	}

	private void _validate(
			long segmentsEntryId, long groupId, long classNameId, long classPK,
			int priority)
		throws PortalException {

		SegmentsExperience segmentsExperience =
			segmentsExperiencePersistence.fetchByG_C_C_P(
				groupId, classNameId, classPK, priority);

		if (segmentsExperience != null) {
			throw new SegmentsExperiencePriorityException(
				"A segments experience with the priority " + priority +
					" already exists");
		}

		SegmentsEntry defaultSegmentsEntry =
			segmentsEntryLocalService.getDefaultSegmentsEntry(groupId);

		if ((priority == 0) &&
			(defaultSegmentsEntry.getSegmentsEntryId() != segmentsEntryId)) {

			throw new SegmentsExperiencePriorityException(
				"Priority 0 is reserved for the default segments experience");
		}

		if (defaultSegmentsEntry.getSegmentsEntryId() != segmentsEntryId) {
			return;
		}

		segmentsExperience = segmentsExperiencePersistence.fetchByG_S_C_C_First(
			groupId, defaultSegmentsEntry.getSegmentsEntryId(), classNameId,
			classPK, null);

		if (segmentsExperience == null) {
			return;
		}

		throw new DefaultSegmentsExperienceException(
			StringBundler.concat(
				"A default segments experience with the group ", groupId,
				", class name ID ", classNameId, " and class PK ", classPK,
				" already exists"));
	}

}