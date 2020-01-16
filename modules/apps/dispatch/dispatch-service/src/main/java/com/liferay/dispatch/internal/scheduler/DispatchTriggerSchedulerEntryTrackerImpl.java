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

	@Override
	public void addScheduledTask(
			long dispatchTriggerId, String cronExpression, Date startDate,
			Date endDate)
		throws SchedulerException {

		deleteScheduledTask(dispatchTriggerId);

		Trigger trigger = _triggerFactory.createTrigger(
			String.valueOf(dispatchTriggerId), _getGroupName(dispatchTriggerId),
			startDate, endDate, cronExpression);

		JSONObject jsonObject = JSONUtil.put(
			"dispatchTriggerId", dispatchTriggerId);

		_schedulerEngineHelper.schedule(
			trigger, StorageType.PERSISTED, null,
			DispatchConstants.EXECUTOR_DESTINATION_NAME, jsonObject.toString(),
			1000);
	}

	@Override
	public void deleteScheduledTask(long dispatchTriggerId)
		throws SchedulerException {

		SchedulerResponse schedulerResponse = getSchedulerResponse(
			dispatchTriggerId);

		if (schedulerResponse != null) {
			_schedulerEngineHelper.delete(
				String.valueOf(dispatchTriggerId),
				_getGroupName(dispatchTriggerId), StorageType.PERSISTED);
		}
	}

	@Override
	public Date getNextFireDate(long dispatchTriggerId) {
		Date nextFireDate = null;

		try {
			nextFireDate = _schedulerEngineHelper.getNextFireTime(
				String.valueOf(dispatchTriggerId),
				_getGroupName(dispatchTriggerId), StorageType.PERSISTED);
		}
		catch (SchedulerException schedulerException) {
			_log.error(schedulerException, schedulerException);
		}

		return nextFireDate;
	}

	@Override
	public Date getPreviousFireDate(long dispatchTriggerId) {
		Date nextFireDate = null;

		try {
			nextFireDate = _schedulerEngineHelper.getPreviousFireTime(
				String.valueOf(dispatchTriggerId),
				_getGroupName(dispatchTriggerId), StorageType.PERSISTED);
		}
		catch (SchedulerException schedulerException) {
			_log.error(schedulerException, schedulerException);
		}

		return nextFireDate;
	}

	@Override
	public SchedulerResponse getSchedulerResponse(long dispatchTriggerId)
		throws SchedulerException {

		return _schedulerEngineHelper.getScheduledJob(
			String.valueOf(dispatchTriggerId), _getGroupName(dispatchTriggerId),
			StorageType.PERSISTED);
	}

	private String _getGroupName(long dispatchTriggerId) {
		return String.format("DISPATCH_TRIGGER_GROUP_%s", dispatchTriggerId);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DispatchTriggerSchedulerEntryTrackerImpl.class);

	@Reference
	private SchedulerEngineHelper _schedulerEngineHelper;

	@Reference
	private TriggerFactory _triggerFactory;

}