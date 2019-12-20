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

package com.liferay.dispatch.internal.scheduler;

import com.liferay.dispatch.constants.DispatchConstants;
import com.liferay.dispatch.scheduler.DispatchTriggerSchedulerEntryTracker;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelper;
import com.liferay.portal.kernel.scheduler.SchedulerException;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.scheduler.Trigger;
import com.liferay.portal.kernel.scheduler.TriggerFactory;
import com.liferay.portal.kernel.scheduler.messaging.SchedulerResponse;

import java.util.Date;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true, service = DispatchTriggerSchedulerEntryTracker.class
)
public class DispatchTriggerSchedulerEntryTrackerImpl
	implements DispatchTriggerSchedulerEntryTracker {

	public static final String JOB_NAME_PREFIX_X =
		"COMMERCE_DATA_INTEGRATION_PROCESS_%s";

	@Override
	public void addScheduledTask(
			long commerceDataIntegrationProcessId, String cronExpression,
			Date startDate, Date endDate)
		throws SchedulerException {

		deleteScheduledTask(commerceDataIntegrationProcessId);

		Trigger trigger = _triggerFactory.createTrigger(
			String.valueOf(commerceDataIntegrationProcessId),
			_getGroupName(commerceDataIntegrationProcessId), startDate, endDate,
			cronExpression);

		JSONObject payLoad = JSONUtil.put(
			"commerceDataIntegrationProcessId",
			commerceDataIntegrationProcessId);

		_schedulerEngineHelper.schedule(
			trigger, StorageType.PERSISTED, null,
			DispatchConstants.EXECUTOR_DESTINATION_NAME, payLoad.toString(),
			1000);
	}

	@Override
	public void deleteScheduledTask(long commerceDataIntegrationProcessId)
		throws SchedulerException {

		SchedulerResponse scheduledJob = getScheduledJob(
			commerceDataIntegrationProcessId);

		if (scheduledJob != null) {
			_schedulerEngineHelper.delete(
				String.valueOf(commerceDataIntegrationProcessId),
				_getGroupName(commerceDataIntegrationProcessId),
				StorageType.PERSISTED);
		}
	}

	@Override
	public Date getNextFireTime(long commerceDataIntegrationProcessId) {
		Date nextFireTime = null;

		try {
			nextFireTime = _schedulerEngineHelper.getNextFireTime(
				String.valueOf(commerceDataIntegrationProcessId),
				_getGroupName(commerceDataIntegrationProcessId),
				StorageType.PERSISTED);
		}
		catch (SchedulerException se) {
			_log.error(se, se);
		}

		return nextFireTime;
	}

	@Override
	public Date getPreviousFireTime(long commerceDataIntegrationProcessId) {
		Date nextFireTime = null;

		try {
			nextFireTime = _schedulerEngineHelper.getPreviousFireTime(
				String.valueOf(commerceDataIntegrationProcessId),
				_getGroupName(commerceDataIntegrationProcessId),
				StorageType.PERSISTED);
		}
		catch (SchedulerException se) {
			_log.error(se, se);
		}

		return nextFireTime;
	}

	@Override
	public SchedulerResponse getScheduledJob(
			long commerceDataIntegrationProcessId)
		throws SchedulerException {

		return _schedulerEngineHelper.getScheduledJob(
			String.valueOf(commerceDataIntegrationProcessId),
			_getGroupName(commerceDataIntegrationProcessId),
			StorageType.PERSISTED);
	}

	private String _getGroupName(long commerceDataIntegrationProcessId) {
		return String.format(
			JOB_NAME_PREFIX_X, commerceDataIntegrationProcessId);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DispatchTriggerSchedulerEntryTrackerImpl.class);

	@Reference
	private SchedulerEngineHelper _schedulerEngineHelper;

	@Reference
	private TriggerFactory _triggerFactory;

}