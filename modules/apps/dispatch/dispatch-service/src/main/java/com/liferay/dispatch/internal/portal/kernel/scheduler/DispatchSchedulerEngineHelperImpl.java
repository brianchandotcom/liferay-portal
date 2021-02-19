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

package com.liferay.dispatch.internal.portal.kernel.scheduler;

import com.liferay.dispatch.portal.kernel.scheduler.ScheduledJobDispatchTrigger;
import com.liferay.dispatch.portal.kernel.scheduler.DispatchSchedulerEngineHelper;
import com.liferay.dispatch.model.DispatchTriggerModel;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelper;
import com.liferay.portal.kernel.scheduler.SchedulerException;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.scheduler.Trigger;
import com.liferay.portal.kernel.scheduler.TriggerState;
import com.liferay.portal.kernel.scheduler.messaging.SchedulerResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Matija Petanjek
 */
@Component(service = DispatchSchedulerEngineHelper.class)
public class DispatchSchedulerEngineHelperImpl
	implements DispatchSchedulerEngineHelper {

	public int getScheduledJobsCount() {
		int count = 0;

		List<SchedulerResponse> schedulerResponses = null;

		try {
			schedulerResponses = _schedulerEngineHelper.getScheduledJobs();
		}
		catch (SchedulerException schedulerException) {
			_log.error("Unable to get scheduler entries", schedulerException);

			return count;
		}

		for (SchedulerResponse schedulerResponse : schedulerResponses) {
			String jobName = schedulerResponse.getJobName();

			if (jobName.startsWith("DISPATCH_JOB_")) {
				continue;
			}

			count++;
		}

		return count;
	}

	public Date getScheduledJobNextFireDate(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException {

		return _schedulerEngineHelper.getNextFireTime(
			jobName, groupName, storageType);
	}

	public List<ScheduledJobDispatchTrigger> getScheduledJobDispatchTriggers(
		int start, int end) {

		List<ScheduledJobDispatchTrigger>
			scheduledJobDispatchTriggers = new ArrayList<>();

		List<SchedulerResponse> schedulerResponses = null;

		try {
			schedulerResponses = _schedulerEngineHelper.getScheduledJobs();
		}
		catch (SchedulerException schedulerException) {
			_log.error("Unable to get scheduler entries", schedulerException);

			return scheduledJobDispatchTriggers;
		}

		for (SchedulerResponse schedulerResponse : schedulerResponses) {
			String jobName = schedulerResponse.getJobName();

			if (jobName.startsWith("DISPATCH_JOB_")) {
				continue;
			}

			ScheduledJobDispatchTrigger scheduledJobDispatchTrigger =
				new ScheduledJobDispatchTriggerImpl(
					schedulerResponse.getDestinationName(),
					schedulerResponse.getGroupName(),
					schedulerResponse.getStorageType());

			scheduledJobDispatchTrigger.setActive(true);
			scheduledJobDispatchTrigger.setName(
				schedulerResponse.getJobName());
			scheduledJobDispatchTrigger.setSystem(true);

			Trigger trigger = schedulerResponse.getTrigger();

			scheduledJobDispatchTrigger.setStartDate(
				trigger.getStartDate());

			scheduledJobDispatchTriggers.add(
				scheduledJobDispatchTrigger);
		}

		Collections.sort(
			scheduledJobDispatchTriggers,
			Comparator.comparing(DispatchTriggerModel::getStartDate));

		return scheduledJobDispatchTriggers.subList(
			start,
			Math.min(end, scheduledJobDispatchTriggers.size()));
	}

	public TriggerState getTriggerState(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException {

		return _schedulerEngineHelper.getJobState(
			jobName, groupName, storageType);
	}

	@Override
	public void pause(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException {

		_schedulerEngineHelper.pause(jobName, groupName, storageType);
	}

	@Override
	public void resume(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException {

		_schedulerEngineHelper.resume(jobName, groupName, storageType);
	}

	@Override
	public void run(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException {

		SchedulerResponse schedulerResponse =
			_schedulerEngineHelper.getScheduledJob(
				jobName, groupName, storageType);

		_messageBus.sendMessage(
			schedulerResponse.getDestinationName(),
			schedulerResponse.getMessage());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DispatchSchedulerEngineHelperImpl.class);

	@Reference
	private MessageBus _messageBus;

	@Reference
	private SchedulerEngineHelper _schedulerEngineHelper;

}