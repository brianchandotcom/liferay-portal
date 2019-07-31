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

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.OrderFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.segments.constants.SegmentsConstants;
import com.liferay.segments.exception.NoSuchExperimentException;
import com.liferay.segments.exception.SegmentsExperimentNameException;
import com.liferay.segments.exception.SegmentsExperimentStatusException;
import com.liferay.segments.model.SegmentsExperiment;
import com.liferay.segments.service.base.SegmentsExperimentLocalServiceBaseImpl;

import java.util.Date;
import java.util.List;

/**
 * @author Eduardo García
 */
public class SegmentsExperimentLocalServiceImpl
	extends SegmentsExperimentLocalServiceBaseImpl {

	@Override
	public SegmentsExperiment addSegmentsExperiment(
			long segmentsExperienceId, long classNameId, long classPK,
			String name, String description, ServiceContext serviceContext)
		throws PortalException {

		// Segments experiment

		long segmentsExperimentId = counterLocalService.increment();

		long publishedLayoutClassPK = _getPublishedLayoutClassPK(classPK);
		int status = SegmentsConstants.SEGMENTS_EXPERIMENT_STATUS_DRAFT;

		_validate(
			segmentsExperienceId, classNameId, publishedLayoutClassPK, name,
			status);

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
		segmentsExperiment.setClassPK(publishedLayoutClassPK);
		segmentsExperiment.setName(name);
		segmentsExperiment.setDescription(description);
		segmentsExperiment.setStatus(status);

		segmentsExperimentPersistence.update(segmentsExperiment);

		// Resources

		resourceLocalService.addModelResources(
			segmentsExperiment, serviceContext);

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

		return segmentsExperiment;
	}

	@Override
	public void deleteSegmentsExperiments(
			long segmentsExperienceId, long classNameId, long classPK)
		throws PortalException {

		List<SegmentsExperiment> segmentsExperiments =
			segmentsExperimentPersistence.findByS_C_C(
				segmentsExperienceId, classNameId,
				_getPublishedLayoutClassPK(classPK));

		for (SegmentsExperiment segmentsExperiment : segmentsExperiments) {
			segmentsExperimentLocalService.deleteSegmentsExperiment(
				segmentsExperiment.getSegmentsExperimentId());
		}
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
			segmentsExperienceId, classNameId,
			_getPublishedLayoutClassPK(classPK));
	}

	@Override
	public List<SegmentsExperiment> getSegmentsExperienceSegmentsExperiments(
		long segmentsExperienceId, long classNameId, long classPK, int status) {

		return segmentsExperimentPersistence.findByS_C_C_S(
			segmentsExperienceId, classNameId, classPK, status);
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
			groupId, classNameId, _getPublishedLayoutClassPK(classPK));
	}

	@Override
	public SegmentsExperiment updateSegmentsExperiment(
			long segmentsExperimentId, String name, String description)
		throws PortalException {

		SegmentsExperiment segmentsExperiment =
			segmentsExperimentPersistence.findByPrimaryKey(
				segmentsExperimentId);

		segmentsExperiment.setName(name);
		segmentsExperiment.setDescription(description);

		return segmentsExperimentPersistence.update(segmentsExperiment);
	}

	@Override
	public SegmentsExperiment updateSegmentsExperiment(
			String segmentsExperimentKey, int status)
		throws NoSuchExperimentException {

		SegmentsExperiment segmentsExperiment =
			segmentsExperimentPersistence.findBySegmentsExperimentKey_First(
				segmentsExperimentKey, null);

		segmentsExperiment.setStatus(status);

		return segmentsExperimentPersistence.update(segmentsExperiment);
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

	private DynamicQuery _getSegmentsExperienceIdsDynamicQuery(
		long segmentsEntryId) {

		DynamicQuery dynamicQuery =
			segmentsExperienceLocalService.dynamicQuery();

		Property segmentsEntryIdProperty = PropertyFactoryUtil.forName(
			"segmentsEntryId");

		dynamicQuery.add(segmentsEntryIdProperty.eq(segmentsEntryId));

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("segmentsExperienceId"));

		return dynamicQuery;
	}

	private void _validate(
			long segmentsExperienceId, long classNameId, long classPK,
			String name, int status)
		throws PortalException {

		_validateName(name);
		_validateStatus(segmentsExperienceId, classNameId, classPK, status);
	}

	private void _validateName(String name) throws PortalException {
		if (Validator.isNull(name)) {
			throw new SegmentsExperimentNameException();
		}
	}

	private void _validateStatus(
			long segmentsExperienceId, long classNameId, long classPK,
			int status)
		throws SegmentsExperimentStatusException {

		if (SegmentsConstants.SEGMENTS_EXPERIMENT_STATUS_DRAFT != status) {
			return;
		}

		if (ListUtil.isNotEmpty(
				segmentsExperimentPersistence.findByS_C_C_S(
					segmentsExperienceId, classNameId, classPK,
					SegmentsConstants.SEGMENTS_EXPERIMENT_STATUS_DRAFT))) {

			throw new SegmentsExperimentStatusException();
		}
	}

}