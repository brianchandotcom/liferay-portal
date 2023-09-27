/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.scheduler;

import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.scheduler.messaging.SchedulerResponse;
import com.liferay.portal.kernel.util.ServiceProxyFactory;

import java.util.Date;
import java.util.List;

/**
 * @author Michael C. Han
 */
public class SchedulerEngineHelperUtil {

	public static void addScriptingJob(
			Trigger trigger, StorageType storageType, String description,
			String language, String script)
		throws SchedulerException {

		_schedulerEngineHelper.addScriptingJob(
			trigger, storageType, description, language, script);
	}

	public static void delete(String groupName, StorageType storageType)
		throws SchedulerException {

		_schedulerEngineHelper.delete(groupName, storageType);
	}

	public static void delete(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException {

		_schedulerEngineHelper.delete(jobName, groupName, storageType);
	}

	public static Date getEndTime(SchedulerResponse schedulerResponse) {
		return _schedulerEngineHelper.getEndTime(schedulerResponse);
	}

	public static SchedulerResponse getScheduledJob(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException {

		return _schedulerEngineHelper.getScheduledJob(
			jobName, groupName, storageType);
	}

	public static List<SchedulerResponse> getScheduledJobs()
		throws SchedulerException {

		return _schedulerEngineHelper.getScheduledJobs();
	}

	public static List<SchedulerResponse> getScheduledJobs(
			StorageType storageType)
		throws SchedulerException {

		return _schedulerEngineHelper.getScheduledJobs(storageType);
	}

	public static List<SchedulerResponse> getScheduledJobs(
			String groupName, StorageType storageType)
		throws SchedulerException {

		return _schedulerEngineHelper.getScheduledJobs(groupName, storageType);
	}

	public static Date getStartTime(SchedulerResponse schedulerResponse) {
		return _schedulerEngineHelper.getStartTime(schedulerResponse);
	}

	public static void pause(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException {

		_schedulerEngineHelper.pause(jobName, groupName, storageType);
	}

	public static void resume(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException {

		_schedulerEngineHelper.resume(jobName, groupName, storageType);
	}

	public static void schedule(
			Trigger trigger, StorageType storageType, String description,
			String destinationName, Message message)
		throws SchedulerException {

		_schedulerEngineHelper.schedule(
			trigger, storageType, description, destinationName, message);
	}

	public static void schedule(
			Trigger trigger, StorageType storageType, String description,
			String destinationName, Object payload)
		throws SchedulerException {

		_schedulerEngineHelper.schedule(
			trigger, storageType, description, destinationName, payload);
	}

	private static volatile SchedulerEngineHelper _schedulerEngineHelper =
		ServiceProxyFactory.newServiceTrackedInstance(
			SchedulerEngineHelper.class, SchedulerEngineHelperUtil.class,
			"_schedulerEngineHelper", false);

}