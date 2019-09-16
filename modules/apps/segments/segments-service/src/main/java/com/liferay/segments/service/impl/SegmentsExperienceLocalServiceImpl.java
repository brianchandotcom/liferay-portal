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
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackUtil;
import com.liferay.portal.kernel.util.GroupThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.segments.constants.SegmentsExperienceConstants;
import com.liferay.segments.constants.SegmentsExperimentConstants;
import com.liferay.segments.exception.RequiredSegmentsExperienceException;
import com.liferay.segments.exception.SegmentsExperienceNameException;
import com.liferay.segments.exception.SegmentsExperiencePriorityException;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.model.SegmentsExperiment;
import com.liferay.segments.service.base.SegmentsExperienceLocalServiceBaseImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author David Arques
 */
@Component(
	property = "model.class.name=com.liferay.segments.model.SegmentsExperience",
	service = AopService.class
)
public class SegmentsExperienceLocalServiceImpl
	extends SegmentsExperienceLocalServiceBaseImpl {

	@Override
	public SegmentsExperience addSegmentsExperience(
			long segmentsEntryId, long classNameId, long classPK,
			Map<Locale, String> nameMap, boolean active,
			ServiceContext serviceContext)
		throws PortalException {

		return addSegmentsExperience(
			segmentsEntryId, classNameId, classPK, nameMap,
			getSegmentsExperiencesCount(
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

		_validateName(nameMap);
		_validatePriority(groupId, classNameId, publishedClassPK, priority);

		long segmentsExperienceId = counterLocalService.increment();

		SegmentsExperience segmentsExperience =
			segmentsExperiencePersistence.create(segmentsExperienceId);

		segmentsExperience.setUuid(serviceContext.getUuid());
		segmentsExperience.setGroupId(groupId);
		segmentsExperience.setCompanyId(user.getCompanyId());
		segmentsExperience.setUserId(user.getUserId());
		segmentsExperience.setUserName(user.getFullName());
		segmentsExperience.setCreateDate(
			serviceContext.getCreateDate(new Date()));
		segmentsExperience.setModifiedDate(
			serviceContext.getModifiedDate(new Date()));
		segmentsExperience.setSegmentsEntryId(segmentsEntryId);
		segmentsExperience.setSegmentsExperienceKey(
			String.valueOf(counterLocalService.increment()));
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
				segmentsExperience);
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

		if (!GroupThreadLocal.isDeleteInProcess()) {
			int count = segmentsExperimentFinder.countByS_C_C_S(
				segmentsExperience.getSegmentsExperienceId(),
				segmentsExperience.getClassNameId(),
				segmentsExperience.getClassPK(),
				SegmentsExperimentConstants.Status.getLockedStatusValues());

			if (count > 0) {
				throw new RequiredSegmentsExperienceException.
					MustNotDeleteSegmentsExperienceReferencedBySegmentsExperiments(
						segmentsExperience.getSegmentsExperienceId());
			}
		}

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

		// Segments experiments

		for (SegmentsExperiment segmentsExperiment :
				segmentsExperimentPersistence.findByS_C_C(
					segmentsExperience.getSegmentsExperienceId(),
					segmentsExperience.getClassNameId(),
					_getPublishedLayoutClassPK(
						segmentsExperience.getClassPK()))) {

			_deleteSegmentsExperiment(segmentsExperiment);
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

		// Segments experiences

		List<SegmentsExperience> segmentsExperiences =
			segmentsExperiencePersistence.findByG_C_C(
				groupId, classNameId, _getPublishedLayoutClassPK(classPK));

		for (SegmentsExperience segmentsExperience : segmentsExperiences) {
			segmentsExperienceLocalService.deleteSegmentsExperience(
				segmentsExperience);
		}

		// Segments experiments

		for (SegmentsExperiment segmentsExperiment :
				segmentsExperimentPersistence.findByS_C_C(
					SegmentsExperienceConstants.ID_DEFAULT, classNameId,
					_getPublishedLayoutClassPK(classPK))) {

			_deleteSegmentsExperiment(segmentsExperiment);
		}
	}

	@Override
	public SegmentsExperience fetchSegmentsExperience(
		long segmentsExperienceId) {

		return segmentsExperiencePersistence.fetchByPrimaryKey(
			segmentsExperienceId);
	}

	@Override
	public SegmentsExperience fetchSegmentsExperience(
		long groupId, String segmentsExperienceKey) {

		return segmentsExperiencePersistence.fetchByG_S(
			groupId, segmentsExperienceKey);
	}

	@Override
	public SegmentsExperience getSegmentsExperience(long segmentsExperienceId)
		throws PortalException {

		return segmentsExperiencePersistence.findByPrimaryKey(
			segmentsExperienceId);
	}

	@Override
	public SegmentsExperience getSegmentsExperience(
			long groupId, String segmentsExperienceKey)
		throws PortalException {

		return segmentsExperiencePersistence.findByG_S(
			groupId, segmentsExperienceKey);
	}

	@Override
	public List<SegmentsExperience> getSegmentsExperiences(
		long groupId, long classNameId, long classPK) {

		return segmentsExperiencePersistence.findByG_C_C(
			groupId, classNameId, _getPublishedLayoutClassPK(classPK));
	}

	@Override
	public List<SegmentsExperience> getSegmentsExperiences(
			long groupId, long classNameId, long classPK, boolean active)
		throws PortalException {

		return segmentsExperiencePersistence.findByG_C_C_A(
			groupId, classNameId, _getPublishedLayoutClassPK(classPK), active);
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
		boolean active) {

		return segmentsExperiencePersistence.findByG_S_C_C_A(
			groupId, segmentsEntryIds, classNameId,
			_getPublishedLayoutClassPK(classPK), active);
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
		long groupId, long classNameId, long classPK) {

		return segmentsExperiencePersistence.countByG_C_C(
			groupId, classNameId, _getPublishedLayoutClassPK(classPK));
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

		_validateName(nameMap);

		SegmentsExperience segmentsExperience =
			segmentsExperiencePersistence.findByPrimaryKey(
				segmentsExperienceId);

		segmentsExperience.setSegmentsEntryId(segmentsEntryId);
		segmentsExperience.setNameMap(nameMap);
		segmentsExperience.setActive(active);

		return segmentsExperiencePersistence.update(segmentsExperience);
	}

	@Override
	public SegmentsExperience updateSegmentsExperienceActive(
			long segmentsExperienceId, boolean active)
		throws PortalException {

		SegmentsExperience segmentsExperience =
			segmentsExperiencePersistence.findByPrimaryKey(
				segmentsExperienceId);

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

		int originalPriority = segmentsExperience.getPriority();

		segmentsExperience.setPriority(
			SegmentsExperienceConstants.PRIORITY_DEFAULT - 1);

		segmentsExperience = segmentsExperiencePersistence.update(
			segmentsExperience);

		swapSegmentsExperience.setPriority(
			SegmentsExperienceConstants.PRIORITY_DEFAULT - 2);

		swapSegmentsExperience = segmentsExperiencePersistence.update(
			swapSegmentsExperience);

		segmentsExperiencePersistence.flush();

		segmentsExperience.setPriority(newPriority);

		segmentsExperience =
			segmentsExperienceLocalService.updateSegmentsExperience(
				segmentsExperience);

		swapSegmentsExperience.setPriority(originalPriority);

		segmentsExperienceLocalService.updateSegmentsExperience(
			swapSegmentsExperience);

		return segmentsExperience;
	}

	private void _deleteSegmentsExperiment(
			SegmentsExperiment segmentsExperiment)
		throws PortalException {

		segmentsExperimentPersistence.remove(segmentsExperiment);
		segmentsExperimentRelPersistence.removeBySegmentsExperimentId(
			segmentsExperiment.getSegmentsExperimentId());
		resourceLocalService.deleteResource(
			segmentsExperiment, ResourceConstants.SCOPE_INDIVIDUAL);
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

	private void _validateName(Map<Locale, String> nameMap)
		throws PortalException {

		Locale locale = LocaleUtil.getSiteDefault();

		if (nameMap.isEmpty() || Validator.isNull(nameMap.get(locale))) {
			throw new SegmentsExperienceNameException();
		}
	}

	private void _validatePriority(
			long groupId, long classNameId, long classPK, int priority)
		throws PortalException {

		SegmentsExperience segmentsExperience =
			segmentsExperiencePersistence.fetchByG_C_C_P(
				groupId, classNameId, classPK, priority);

		if (segmentsExperience != null) {
			throw new SegmentsExperiencePriorityException(
				"A segments experience with the priority " + priority +
					" already exists");
		}
	}

}