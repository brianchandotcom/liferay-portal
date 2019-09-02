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
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserNotificationDeliveryConstants;
import com.liferay.portal.kernel.notifications.UserNotificationManagerUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.segments.constants.SegmentsExperimentConstants;
import com.liferay.segments.constants.SegmentsPortletKeys;
import com.liferay.segments.exception.LockedSegmentsExperimentException;
import com.liferay.segments.exception.NoSuchExperimentException;
import com.liferay.segments.exception.SegmentsExperimentConfidenceLevelException;
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
			name, goal, status, status);

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

		return deleteSegmentsExperiment(segmentsExperiment, false);
	}

	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public SegmentsExperiment deleteSegmentsExperiment(
			SegmentsExperiment segmentsExperiment, boolean force)
		throws PortalException {

		// Segments experiment

		if (!force) {
			_validateEditableStatus(segmentsExperiment.getStatus());
		}

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
				segmentsExperiment);
		}
	}

	@Override
	public SegmentsExperiment fetchSegmentsExperiment(
		long segmentsExperienceId, long classNameId, long classPK,
		int[] statuses) {

		List<SegmentsExperiment> segmentsExperiments =
			segmentsExperimentFinder.findByS_C_C_S(
				segmentsExperienceId, classNameId, classPK, statuses, 0, 1);

		if (segmentsExperiments.isEmpty()) {
			return null;
		}

		return segmentsExperiments.get(0);
	}

	@Override
	public SegmentsExperiment fetchSegmentsExperiment(
		long groupId, String segmentsExperimentKey) {

		return segmentsExperimentPersistence.fetchByG_S(
			groupId, segmentsExperimentKey);
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
		long segmentsExperienceId, long classNameId, long classPK,
		int[] statuses) {

		int count = segmentsExperimentFinder.countByS_C_C_S(
			segmentsExperienceId, classNameId, classPK, statuses);

		if (count > 0) {
			return true;
		}

		return false;
	}

	@Override
	public SegmentsExperiment updateSegmentsExperiment(
			long segmentsExperimentId, double confidenceLevel, int status)
		throws PortalException {

		_validateConfidenceLevel(confidenceLevel);

		return _updateSegmentsExperimentStatus(
			segmentsExperimentPersistence.findByPrimaryKey(
				segmentsExperimentId),
			confidenceLevel, status);
	}

	@Override
	public SegmentsExperiment updateSegmentsExperiment(
			long segmentsExperimentId, String name, String description,
			String goal, String goalTarget)
		throws PortalException {

		SegmentsExperiment segmentsExperiment =
			segmentsExperimentPersistence.findByPrimaryKey(
				segmentsExperimentId);

		_validateEditableStatus(segmentsExperiment.getStatus());

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
	public SegmentsExperiment updateSegmentsExperimentStatus(
			long segmentsExperimentId, int status)
		throws PortalException {

		return _updateSegmentsExperimentStatus(
			segmentsExperimentPersistence.findByPrimaryKey(
				segmentsExperimentId),
			0, status);
	}

	@Override
	public SegmentsExperiment updateSegmentsExperimentStatus(
			String segmentsExperimentKey, int status)
		throws PortalException {

		return _updateSegmentsExperimentStatus(
			segmentsExperimentPersistence.findBySegmentsExperimentKey_First(
				segmentsExperimentKey, null),
			0, status);
	}

	protected void sendNotificationEvent(SegmentsExperiment segmentsExperiment)
		throws PortalException {

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if ((serviceContext == null) ||
			(serviceContext.getUserId() == segmentsExperiment.getUserId())) {

			return;
		}

		if (!UserNotificationManagerUtil.isDeliver(
				segmentsExperiment.getUserId(),
				SegmentsPortletKeys.SEGMENTS_EXPERIMENT, 0,
				SegmentsExperimentConstants.NOTIFICATION_TYPE_UPDATE_STATUS,
				UserNotificationDeliveryConstants.TYPE_WEBSITE)) {

			return;
		}

		JSONObject notificationEventJSONObject = JSONUtil.put(
			"classPK", segmentsExperiment.getSegmentsExperimentId()
		).put(
			"referrerClassNameId", segmentsExperiment.getClassNameId()
		).put(
			"referrerClassPK", segmentsExperiment.getClassPK()
		).put(
			"segmentsExperimentKey",
			segmentsExperiment.getSegmentsExperimentKey()
		);

		userNotificationEventLocalService.sendUserNotificationEvents(
			segmentsExperiment.getUserId(),
			SegmentsPortletKeys.SEGMENTS_EXPERIMENT,
			UserNotificationDeliveryConstants.TYPE_WEBSITE,
			notificationEventJSONObject);
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

	private SegmentsExperiment _updateSegmentsExperimentStatus(
			SegmentsExperiment segmentsExperiment, double confidenceLevel,
			int status)
		throws PortalException {

		_validateStatus(
			segmentsExperiment.getSegmentsExperimentId(),
			segmentsExperiment.getSegmentsExperienceId(),
			segmentsExperiment.getClassNameId(),
			segmentsExperiment.getClassPK(), segmentsExperiment.getStatus(),
			status);

		segmentsExperiment.setModifiedDate(new Date());

		if (confidenceLevel != 0) {
			UnicodeProperties typeSettingsProperties =
				segmentsExperiment.getTypeSettingsProperties();

			typeSettingsProperties.setProperty(
				"confidenceLevel", String.valueOf(confidenceLevel));

			segmentsExperiment.setTypeSettings(
				typeSettingsProperties.toString());
		}

		segmentsExperiment.setStatus(status);

		segmentsExperimentPersistence.update(segmentsExperiment);

		sendNotificationEvent(segmentsExperiment);

		return segmentsExperiment;
	}

	private void _validate(
			long segmentsExperimentId, long segmentsExperienceId,
			long classNameId, long classPK, String name, String goal,
			int currentStatus, int newStatus)
		throws PortalException {

		_validateGoal(goal);
		_validateName(name);
		_validateStatus(
			segmentsExperimentId, segmentsExperienceId, classNameId, classPK,
			currentStatus, newStatus);
	}

	private void _validateConfidenceLevel(double confidenceLevel)
		throws PortalException {

		if ((confidenceLevel > 1) || (confidenceLevel < 0)) {
			throw new SegmentsExperimentConfidenceLevelException(
				"Confidence level " + confidenceLevel +
					" is not a value between 0 and 1");
		}
	}

	private void _validateEditableStatus(int statusValue)
		throws PortalException {

		SegmentsExperimentConstants.Status status =
			SegmentsExperimentConstants.Status.valueOf(statusValue);

		if (!status.isEditable()) {
			throw new LockedSegmentsExperimentException(
				"Segments experiment is not editable in status " +
					SegmentsExperimentConstants.Status.parse(statusValue));
		}
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
			long classNameId, long classPK, int statusValue, int newStatusValue)
		throws SegmentsExperimentStatusException {

		SegmentsExperimentConstants.Status.validateTransition(
			statusValue, newStatusValue);

		SegmentsExperimentConstants.Status newStatus =
			SegmentsExperimentConstants.Status.valueOf(newStatusValue);

		if (newStatus.isExclusive()) {
			List<SegmentsExperiment> segmentsExperiments =
				segmentsExperimentPersistence.findByS_C_C_S(
					new long[] {segmentsExperienceId}, classNameId, classPK,
					SegmentsExperimentConstants.Status.exclusiveStates());

			if (segmentsExperiments.isEmpty()) {
				return;
			}

			if (segmentsExperiments.size() > 1) {
				throw new SegmentsExperimentStatusException(
					String.format(
						"There are %d segments experiments with exclusive " +
							"status",
						segmentsExperiments.size()));
			}

			SegmentsExperiment segmentsExperiment = segmentsExperiments.get(0);

			if (segmentsExperiment.getSegmentsExperimentId() !=
					segmentsExperimentId) {

				throw new SegmentsExperimentStatusException(
					"A segments experiment with status " + newStatus.name() +
						" already exists");
			}
		}
	}

	@Reference
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

	@Reference
	private SegmentsExperimentRelLocalService
		_segmentsExperimentRelLocalService;

}