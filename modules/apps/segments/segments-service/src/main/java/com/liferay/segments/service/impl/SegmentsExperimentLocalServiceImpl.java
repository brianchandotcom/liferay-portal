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
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.OrderFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
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
import com.liferay.portal.kernel.util.BigDecimalUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.segments.constants.SegmentsExperienceConstants;
import com.liferay.segments.constants.SegmentsExperimentConstants;
import com.liferay.segments.constants.SegmentsPortletKeys;
import com.liferay.segments.exception.LockedSegmentsExperimentException;
import com.liferay.segments.exception.NoSuchExperimentException;
import com.liferay.segments.exception.SegmentsExperimentConfidenceLevelException;
import com.liferay.segments.exception.SegmentsExperimentGoalException;
import com.liferay.segments.exception.SegmentsExperimentNameException;
import com.liferay.segments.exception.SegmentsExperimentRelSplitException;
import com.liferay.segments.exception.SegmentsExperimentStatusException;
import com.liferay.segments.exception.WinnerSegmentsExperienceException;
import com.liferay.segments.model.SegmentsExperiment;
import com.liferay.segments.model.SegmentsExperimentRel;
import com.liferay.segments.service.SegmentsExperienceLocalService;
import com.liferay.segments.service.SegmentsExperimentRelLocalService;
import com.liferay.segments.service.base.SegmentsExperimentLocalServiceBaseImpl;

import java.math.RoundingMode;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

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
	public SegmentsExperiment deleteSegmentsExperiment(
			long segmentsExperimentId)
		throws PortalException {

		return deleteSegmentsExperiment(
			segmentsExperimentPersistence.findByPrimaryKey(
				segmentsExperimentId),
			false);
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
				segmentsExperienceId, classNameId, classPK, statuses, 0, 1,
				null);

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
	public List<SegmentsExperiment> getSegmentsExperiments(
		long segmentsExperienceId, long classNameId, long classPK,
		int[] statuses,
		OrderByComparator<SegmentsExperiment> orderByComparator) {

		return segmentsExperimentFinder.findByS_C_C_S(
			segmentsExperienceId, classNameId, classPK, statuses,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, orderByComparator);
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
	public SegmentsExperiment runSegmentsExperiment(
			long segmentsExperimentId, double confidenceLevel,
			Map<Long, Double> segmentsExperienceIdSplitMap)
		throws PortalException {

		SegmentsExperiment segmentsExperiment =
			segmentsExperimentPersistence.findByPrimaryKey(
				segmentsExperimentId);

		_validateEditableStatus(segmentsExperiment.getStatus());

		_validateConfidenceLevel(confidenceLevel);
		_validateSplit(segmentsExperienceIdSplitMap);

		UnicodeProperties typeSettingsProperties =
			segmentsExperiment.getTypeSettingsProperties();

		typeSettingsProperties.setProperty(
			"confidenceLevel", String.valueOf(confidenceLevel));

		segmentsExperiment.setTypeSettings(typeSettingsProperties.toString());

		for (Map.Entry<Long, Double> segmentsExperienceIdSplit :
				segmentsExperienceIdSplitMap.entrySet()) {

			_segmentsExperimentRelLocalService.updateSegmentsExperimentRel(
				segmentsExperimentId, segmentsExperienceIdSplit.getKey(),
				BigDecimalUtil.scale(
					segmentsExperienceIdSplit.getValue(), 2,
					RoundingMode.HALF_DOWN));
		}

		return _updateSegmentsExperimentStatus(
			segmentsExperiment, -1,
			SegmentsExperimentConstants.Status.RUNNING.getValue());
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
			-1, status);
	}

	@Override
	public SegmentsExperiment updateSegmentsExperimentStatus(
			long segmentsExperimentId, long winnerSegmentsExperienceId,
			int status)
		throws PortalException {

		return _updateSegmentsExperimentStatus(
			segmentsExperimentPersistence.findByPrimaryKey(
				segmentsExperimentId),
			winnerSegmentsExperienceId, status);
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
			SegmentsExperiment segmentsExperiment,
			long winnerSegmentsExperienceId, int status)
		throws PortalException {

		_validateStatus(
			segmentsExperiment.getSegmentsExperimentId(),
			segmentsExperiment.getSegmentsExperienceId(),
			segmentsExperiment.getClassNameId(),
			segmentsExperiment.getClassPK(), segmentsExperiment.getStatus(),
			status, winnerSegmentsExperienceId);

		if (winnerSegmentsExperienceId != -1) {
			_updateWinnerSegmentsExperienceId(
				segmentsExperiment, winnerSegmentsExperienceId, status);
		}

		segmentsExperiment.setModifiedDate(new Date());
		segmentsExperiment.setStatus(status);

		segmentsExperimentPersistence.update(segmentsExperiment);

		sendNotificationEvent(segmentsExperiment);

		return segmentsExperiment;
	}

	private SegmentsExperiment _updateWinnerSegmentsExperienceId(
			SegmentsExperiment segmentsExperiment,
			long winnerSegmentsExperienceId, int status)
		throws PortalException {

		SegmentsExperimentRel segmentsExperimentRel =
			_segmentsExperimentRelLocalService.fetchSegmentsExperimentRel(
				segmentsExperiment.getSegmentsExperimentId(),
				winnerSegmentsExperienceId);

		if (segmentsExperimentRel == null) {
			throw new WinnerSegmentsExperienceException(
				"Winner segments experience " + winnerSegmentsExperienceId +
					" no found");
		}

		UnicodeProperties typeSettingsProperties =
			segmentsExperiment.getTypeSettingsProperties();

		typeSettingsProperties.setProperty(
			"winnerSegmentsExperienceId",
			String.valueOf(winnerSegmentsExperienceId));

		segmentsExperiment.setTypeSettings(typeSettingsProperties.toString());

		SegmentsExperimentConstants.Status statusObject =
			SegmentsExperimentConstants.Status.valueOf(status);

		if ((segmentsExperiment.getSegmentsExperienceId() !=
				SegmentsExperienceConstants.ID_DEFAULT) &&
			(statusObject == SegmentsExperimentConstants.Status.COMPLETED) &&
			(winnerSegmentsExperienceId !=
				segmentsExperiment.getSegmentsExperienceId())) {

			_segmentsExperienceLocalService.updateSegmentsExperienceActive(
				segmentsExperiment.getSegmentsExperienceId(), false);

			_segmentsExperienceLocalService.updateSegmentsExperienceActive(
				winnerSegmentsExperienceId, true);
		}

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
			currentStatus, newStatus, -1);
	}

	private void _validateConfidenceLevel(double confidenceLevel)
		throws PortalException {

		if ((confidenceLevel < 0.8) || (confidenceLevel > 0.99)) {
			throw new SegmentsExperimentConfidenceLevelException(
				"Confidence level " + confidenceLevel +
					" is not a value between 0.8 and 0.99");
		}
	}

	private void _validateEditableStatus(int status) throws PortalException {
		SegmentsExperimentConstants.Status statusObject =
			SegmentsExperimentConstants.Status.valueOf(status);

		if (!statusObject.isEditable()) {
			throw new LockedSegmentsExperimentException(
				"Segments experiment is not editable in status " +
					statusObject);
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

	private void _validateSplit(Map<Long, Double> segmentsExperienceIdSplitMap)
		throws PortalException {

		Collection<Double> segmentsExperienceIdSplitsValues =
			segmentsExperienceIdSplitMap.values();

		Stream<Double> segmentsExperienceIdSplitsStream =
			segmentsExperienceIdSplitsValues.stream();

		double segmentsExperienceIdSplitsSum =
			segmentsExperienceIdSplitsStream.mapToDouble(
				segmentsExperienceIdSplit -> BigDecimalUtil.scale(
					segmentsExperienceIdSplit, 2, RoundingMode.HALF_DOWN)
			).sum();

		if (segmentsExperienceIdSplitsSum != 1) {
			throw new SegmentsExperimentRelSplitException(
				"Segments experiment rel splits must add up to 1");
		}
	}

	private void _validateStatus(
			long segmentsExperimentId, long segmentsExperienceId,
			long classNameId, long classPK, int status, int newStatus,
			long winnerSegmentsExperienceId)
		throws SegmentsExperimentStatusException {

		SegmentsExperimentConstants.Status.validateTransition(
			status, newStatus);

		SegmentsExperimentConstants.Status newStatusObject =
			SegmentsExperimentConstants.Status.valueOf(newStatus);

		if (newStatusObject.isExclusive()) {
			List<SegmentsExperiment> segmentsExperiments =
				segmentsExperimentPersistence.findByS_C_C_S(
					new long[] {segmentsExperienceId}, classNameId, classPK,
					SegmentsExperimentConstants.Status.
						getExclusiveStatusValues());

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
					"A segments experiment with status " +
						newStatusObject.name() + " already exists");
			}
		}

		if (newStatusObject.requiresWinnerExperience() &&
			(winnerSegmentsExperienceId < 0)) {

			throw new SegmentsExperimentStatusException(
				StringBundler.concat(
					"Status ", newStatusObject.name(),
					" requires a winner segments experience"));
		}
	}

	@Reference
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

	@Reference
	private SegmentsExperimentRelLocalService
		_segmentsExperimentRelLocalService;

}