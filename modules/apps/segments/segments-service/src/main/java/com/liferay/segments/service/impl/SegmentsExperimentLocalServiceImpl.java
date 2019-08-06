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
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.OrderFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.segments.constants.SegmentsExperimentConstants;
import com.liferay.segments.exception.NoSuchExperimentException;
import com.liferay.segments.exception.SegmentsExperimentGoalException;
import com.liferay.segments.exception.SegmentsExperimentNameException;
import com.liferay.segments.exception.SegmentsExperimentStatusException;
import com.liferay.segments.model.SegmentsExperiment;
import com.liferay.segments.service.SegmentsExperienceLocalService;
import com.liferay.segments.service.SegmentsExperimentRelLocalService;
import com.liferay.segments.service.base.SegmentsExperimentLocalServiceBaseImpl;

import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo García
 */
@Component(
	property = "model.class.name=com.liferay.segments.model.SegmentsExperiment",
	service = AopService.class
)
public class SegmentsExperimentLocalServiceImpl
	extends SegmentsExperimentLocalServiceBaseImpl {

	@Override
	public SegmentsExperiment addSegmentsExperiment(
			long segmentsExperienceId, long classNameId, long classPK,
			String name, String description, String goal, String goalTarget,
			ServiceContext serviceContext)
		throws PortalException {

		// Segments experiment

		long segmentsExperimentId = counterLocalService.increment();

		int status = SegmentsExperimentConstants.STATUS_DRAFT;

		_validate(
			segmentsExperimentId, segmentsExperienceId, classNameId, classPK,
			name, goal, status);

		SegmentsExperiment segmentsExperiment =
			segmentsExperimentPersistence.create(segmentsExperimentId);

		segmentsExperiment.setUuid(serviceContext.getUuid());
		segmentsExperiment.setGroupId(serviceContext.getScopeGroupId());

		User user = userLocalService.getUser(serviceContext.getUserId());

		segmentsExperiment.setCompanyId(user.getCompanyId());
		segmentsExperiment.setUserId(user.getUserId());
		segmentsExperiment.setUserName(user.getFullName());

		segmentsExperiment.setCreateDate(
			serviceContext.getCreateDate(new Date()));
		segmentsExperiment.setModifiedDate(
			serviceContext.getModifiedDate(new Date()));
		segmentsExperiment.setSegmentsExperienceId(segmentsExperienceId);
		segmentsExperiment.setSegmentsExperimentKey(
			String.valueOf(counterLocalService.increment()));
		segmentsExperiment.setClassNameId(classNameId);
		segmentsExperiment.setClassPK(classPK);
		segmentsExperiment.setName(name);
		segmentsExperiment.setDescription(description);

		UnicodeProperties typeSettings = new UnicodeProperties(true);

		typeSettings.setProperty("goal", goal);
		typeSettings.setProperty("goalTarget", goalTarget);

		segmentsExperiment.setTypeSettings(typeSettings.toString());

		segmentsExperiment.setStatus(status);

		segmentsExperimentPersistence.update(segmentsExperiment);

		// Resources

		resourceLocalService.addModelResources(
			segmentsExperiment, serviceContext);

		// Segments experiment rel

		_segmentsExperimentRelLocalService.addSegmentsExperimentRel(
			segmentsExperiment.getSegmentsExperimentId(),
			segmentsExperiment.getSegmentsExperienceId(), serviceContext);

		return segmentsExperiment;
	}

	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public SegmentsExperiment deleteSegmentsExperiment(
			SegmentsExperiment segmentsExperiment)
		throws PortalException {

		// Segments experiment

		segmentsExperimentPersistence.remove(segmentsExperiment);

		// Resources

		resourceLocalService.deleteResource(
			segmentsExperiment, ResourceConstants.SCOPE_INDIVIDUAL);

		// Segments experiment rels

		_segmentsExperimentRelLocalService.deleteSegmentsExperimentRels(
			segmentsExperiment.getSegmentsExperimentId());

		return segmentsExperiment;
	}

	@Override
	public void deleteSegmentsExperiments(
			long segmentsExperienceId, long classNameId, long classPK)
		throws PortalException {

		List<SegmentsExperiment> segmentsExperiments =
			segmentsExperimentPersistence.findByS_C_C(
				segmentsExperienceId, classNameId, classPK);

		for (SegmentsExperiment segmentsExperiment : segmentsExperiments) {
			segmentsExperimentLocalService.deleteSegmentsExperiment(
				segmentsExperiment.getSegmentsExperimentId());
		}
	}

	@Override
	public SegmentsExperiment fetchSegmentsExperiment(
		long segmentsExperienceId, long classNameId, long classPK, int status) {

		List<SegmentsExperiment> segmentsExperiments =
			segmentsExperimentFinder.findByS_C_C_S(
				segmentsExperienceId, classNameId, classPK, status, 0, 1);

		if (segmentsExperiments.isEmpty()) {
			return null;
		}

		return segmentsExperiments.get(0);
	}

	@Override
	public List<SegmentsExperiment> getSegmentsEntrySegmentsExperiments(
		long segmentsEntryId) {

		DynamicQuery dynamicQuery =
			segmentsExperimentLocalService.dynamicQuery();

		Property segmentsExperienceIdProperty = PropertyFactoryUtil.forName(
			"segmentsExperienceId");

		dynamicQuery.add(
			segmentsExperienceIdProperty.in(
				_getSegmentsExperienceIdsDynamicQuery(segmentsEntryId)));

		dynamicQuery.addOrder(OrderFactoryUtil.desc("createDate"));

		return segmentsExperimentLocalService.dynamicQuery(dynamicQuery);
	}

	@Override
	public List<SegmentsExperiment> getSegmentsExperienceSegmentsExperiments(
		long segmentsExperienceId, long classNameId, long classPK) {

		return segmentsExperimentPersistence.findByS_C_C(
			segmentsExperienceId, classNameId, classPK);
	}

	@Override
	public List<SegmentsExperiment> getSegmentsExperienceSegmentsExperiments(
		long[] segmentsExperienceIds, long classNameId, long classPK,
		int[] statuses, int start, int end) {

		return segmentsExperimentPersistence.findByS_C_C_S(
			segmentsExperienceIds, classNameId, classPK, statuses, start, end);
	}

	@Override
	public SegmentsExperiment getSegmentsExperiment(
			String segmentsExperimentKey)
		throws NoSuchExperimentException {

		return segmentsExperimentPersistence.findBySegmentsExperimentKey_First(
			segmentsExperimentKey, null);
	}

	@Override
	public List<SegmentsExperiment> getSegmentsExperiments(
		long groupId, long classNameId, long classPK) {

		return segmentsExperimentPersistence.findByG_C_C(
			groupId, classNameId, classPK);
	}

	@Override
	public boolean hasSegmentsExperiment(
		long segmentsExperienceId, long classNameId, long classPK, int status) {

		int count = segmentsExperimentFinder.countByS_C_C_S(
			segmentsExperienceId, classNameId, classPK, status);

		if (count > 0) {
			return true;
		}

		return false;
	}

	@Override
	public SegmentsExperiment updateSegmentsExperiment(
			long segmentsExperimentId, String name, String description,
			String goal, String goalTarget)
		throws PortalException {

		SegmentsExperiment segmentsExperiment =
			segmentsExperimentPersistence.findByPrimaryKey(
				segmentsExperimentId);

		_validateGoal(goal);
		_validateName(name);

		segmentsExperiment.setModifiedDate(new Date());
		segmentsExperiment.setName(name);
		segmentsExperiment.setDescription(description);

		UnicodeProperties typeSettingsProperties =
			segmentsExperiment.getTypeSettingsProperties();

		typeSettingsProperties.setProperty("goal", goal);
		typeSettingsProperties.setProperty("goalTarget", goalTarget);

		segmentsExperiment.setTypeSettings(typeSettingsProperties.toString());

		return segmentsExperimentPersistence.update(segmentsExperiment);
	}

	@Override
	public SegmentsExperiment updateSegmentsExperiment(
			String segmentsExperimentKey, int status)
		throws PortalException {

		SegmentsExperiment segmentsExperiment =
			segmentsExperimentPersistence.findBySegmentsExperimentKey_First(
				segmentsExperimentKey, null);

		_validateStatus(
			segmentsExperiment.getSegmentsExperimentId(),
			segmentsExperiment.getSegmentsExperienceId(),
			segmentsExperiment.getClassNameId(),
			segmentsExperiment.getClassPK(), status);

		segmentsExperiment.setModifiedDate(new Date());
		segmentsExperiment.setStatus(status);

		return segmentsExperimentPersistence.update(segmentsExperiment);
	}

	private DynamicQuery _getSegmentsExperienceIdsDynamicQuery(
		long segmentsEntryId) {

		DynamicQuery dynamicQuery =
			_segmentsExperienceLocalService.dynamicQuery();

		Property segmentsEntryIdProperty = PropertyFactoryUtil.forName(
			"segmentsEntryId");

		dynamicQuery.add(segmentsEntryIdProperty.eq(segmentsEntryId));

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("segmentsExperienceId"));

		return dynamicQuery;
	}

	private void _validate(
			long segmentsExperimentId, long segmentsExperienceId,
			long classNameId, long classPK, String name, String goal,
			int status)
		throws PortalException {

		_validateGoal(goal);
		_validateName(name);
		_validateStatus(
			segmentsExperimentId, segmentsExperienceId, classNameId, classPK,
			status);
	}

	private void _validateGoal(String goal) throws PortalException {
		if (SegmentsExperimentConstants.Goal.parse(goal) == null) {
			throw new SegmentsExperimentGoalException();
		}
	}

	private void _validateName(String name) throws PortalException {
		if (Validator.isNull(name)) {
			throw new SegmentsExperimentNameException();
		}
	}

	private void _validateStatus(
			long segmentsExperimentId, long segmentsExperienceId,
			long classNameId, long classPK, int status)
		throws SegmentsExperimentStatusException {

		if (SegmentsExperimentConstants.Status.parse(status) == null) {
			throw new SegmentsExperimentStatusException();
		}

		if (SegmentsExperimentConstants.STATUS_DRAFT != status) {
			return;
		}

		SegmentsExperiment segmentsExperiment =
			segmentsExperimentPersistence.fetchByS_C_C_S_First(
				segmentsExperienceId, classNameId, classPK,
				SegmentsExperimentConstants.STATUS_DRAFT, null);

		if ((segmentsExperiment != null) &&
			(segmentsExperiment.getSegmentsExperimentId() !=
				segmentsExperimentId)) {

			throw new SegmentsExperimentStatusException();
		}
	}

	@Reference
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

	@Reference
	private SegmentsExperimentRelLocalService
		_segmentsExperimentRelLocalService;

}