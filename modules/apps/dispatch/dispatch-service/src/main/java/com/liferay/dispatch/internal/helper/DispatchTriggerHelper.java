/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
import com.liferay.portal.kernel.scheduler.messaging.SchedulerResponse;

import java.util.Date;
import java.util.TimeZone;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Matija Petanjek
 */
@Component(service = DispatchTriggerHelper.class)
public class DispatchTriggerHelper {

	public void addSchedulerJob(
			long dispatchTriggerId, String cronExpression, Date startDate,
			Date endDate, StorageType storageType, String timeZoneId)
		throws DispatchTriggerSchedulerException {

		Trigger trigger = _triggerFactory.createTrigger(
			_getJobName(dispatchTriggerId), _getGroupName(dispatchTriggerId),
			startDate, endDate, cronExpression,
			TimeZone.getTimeZone(timeZoneId));

		try {
			_schedulerEngineHelper.schedule(
				trigger, storageType, null,
				DispatchConstants.EXECUTOR_DESTINATION_NAME,
				_getPayload(dispatchTriggerId));

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
			String jobName = _getJobName(dispatchTriggerId);
			String groupName = _getGroupName(dispatchTriggerId);

			_schedulerEngineHelper.delete(jobName, groupName, storageType);

			SchedulerResponse scheduledJob =
				_schedulerEngineHelper.getScheduledJob(
					jobName, groupName, storageType);

			while (scheduledJob != null) {
				scheduledJob = _schedulerEngineHelper.getScheduledJob(
					jobName, groupName, storageType);
			}
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

		SchedulerResponse schedulerResponse =
			_schedulerEngineHelper.getScheduledJob(
				_getJobName(dispatchTriggerId),
				_getGroupName(dispatchTriggerId), storageType);

		if (schedulerResponse == null) {
			return null;
		}

		return _schedulerEngineHelper.getNextFireTime(schedulerResponse);
	}

	public Date getPreviousFireDate(
			long dispatchTriggerId, StorageType storageType)
		throws SchedulerException {

		SchedulerResponse schedulerResponse =
			_schedulerEngineHelper.getScheduledJob(
				_getJobName(dispatchTriggerId),
				_getGroupName(dispatchTriggerId), storageType);

		if (schedulerResponse == null) {
			return null;
		}

		return _schedulerEngineHelper.getPreviousFireTime(schedulerResponse);
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