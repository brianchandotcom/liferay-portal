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

package com.liferay.dispatch.internal.helper;

import com.liferay.dispatch.constants.DispatchConstants;
import com.liferay.dispatch.exception.DispatchTriggerSchedulerException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelper;
import com.liferay.portal.kernel.scheduler.SchedulerException;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.scheduler.Trigger;
import com.liferay.portal.kernel.scheduler.TriggerFactory;

import java.util.Date;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Matija Petanjek
 */
@Component(service = DispatchTriggerHelper.class)
public class DispatchTriggerHelper {

	public void addSchedulerJob(
			long dispatchTriggerId, String cronExpression, Date startDate,
			Date endDate, StorageType storageType)
		throws DispatchTriggerSchedulerException {

		Trigger trigger = _triggerFactory.createTrigger(
			_getJobName(dispatchTriggerId), _getGroupName(dispatchTriggerId),
			startDate, endDate, cronExpression);

		try {
			_schedulerEngineHelper.schedule(
				trigger, storageType, null,
				DispatchConstants.EXECUTOR_DESTINATION_NAME,
				_getPayload(dispatchTriggerId), 1000);

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Scheduler entry created for dispatch trigger " +
						dispatchTriggerId);
			}
		}
		catch (SchedulerException schedulerException) {
			throw new DispatchTriggerSchedulerException(
				"Unable to create scheduler entry for dispatch trigger " +
					dispatchTriggerId,
				schedulerException);
		}
	}

	public void deleteSchedulerJob(
		long dispatchTriggerId, StorageType storageType) {

		try {
			_schedulerEngineHelper.delete(
				_getJobName(dispatchTriggerId),
				_getGroupName(dispatchTriggerId), storageType);
		}
		catch (SchedulerException schedulerException) {
			_log.error(
				"Unable to delete scheduler entry for dispatch trigger " +
					dispatchTriggerId,
				schedulerException);
		}
	}

	public Date getNextFireDate(long dispatchTriggerId, StorageType storageType)
		throws SchedulerException {

		return _schedulerEngineHelper.getNextFireTime(
			_getJobName(dispatchTriggerId), _getGroupName(dispatchTriggerId),
			storageType);
	}

	public Date getPreviousFireDate(
			long dispatchTriggerId, StorageType storageType)
		throws SchedulerException {

		return _schedulerEngineHelper.getPreviousFireTime(
			_getJobName(dispatchTriggerId), _getGroupName(dispatchTriggerId),
			storageType);
	}

	public void unscheduleSchedulerJob(
			long dispatchTriggerId, StorageType storageType)
		throws DispatchTriggerSchedulerException {

		try {
			_schedulerEngineHelper.unschedule(
				_getJobName(dispatchTriggerId),
				_getGroupName(dispatchTriggerId), storageType);
		}
		catch (SchedulerException schedulerException) {
			throw new DispatchTriggerSchedulerException(
				"Unable to unschedule scheduler job for dispatch Trigger " +
					dispatchTriggerId,
				schedulerException);
		}
	}

	private String _getGroupName(long dispatchTriggerId) {
		return String.format("DISPATCH_GROUP_%07d", dispatchTriggerId);
	}

	private String _getJobName(long dispatchTriggerId) {
		return String.format("DISPATCH_JOB_%07d", dispatchTriggerId);
	}

	private String _getPayload(long dispatchTriggerId) {
		return String.format("{\"dispatchTriggerId\": %d}", dispatchTriggerId);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DispatchTriggerHelper.class);

	@Reference
	private SchedulerEngineHelper _schedulerEngineHelper;

	@Reference
	private TriggerFactory _triggerFactory;

}