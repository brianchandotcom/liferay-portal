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

package com.liferay.portal.kernel.scheduler;

import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.scheduler.messaging.SchedulerResponse;

import java.util.Date;
import java.util.List;

/**
 * @author Michael C. Han
 */
public interface SchedulerEngineHelper {

	public void addScriptingJob(
			Trigger trigger, StorageType storageType, String description,
			String language, String script)
		throws SchedulerException;

	public void auditSchedulerJobs(Message message, TriggerState triggerState)
		throws SchedulerException;

	public void delete(String groupName, StorageType storageType)
		throws SchedulerException;

	public void delete(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException;

	public Date getEndTime(SchedulerResponse schedulerResponse);

	public TriggerState getJobState(SchedulerResponse schedulerResponse);

	public Date getNextFireTime(SchedulerResponse schedulerResponse);

	public Date getPreviousFireTime(SchedulerResponse schedulerResponse);

	public SchedulerResponse getScheduledJob(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException;

	public List<SchedulerResponse> getScheduledJobs() throws SchedulerException;

	public List<SchedulerResponse> getScheduledJobs(StorageType storageType)
		throws SchedulerException;

	public List<SchedulerResponse> getScheduledJobs(
			String groupName, StorageType storageType)
		throws SchedulerException;

	public Date getStartTime(SchedulerResponse schedulerResponse);

	public void pause(String jobName, String groupName, StorageType storageType)
		throws SchedulerException;

	public void resume(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException;

	public void run(
			long companyId, String jobName, String groupName,
			StorageType storageType)
		throws SchedulerException;

	public void schedule(
			Trigger trigger, StorageType storageType, String description,
			String destinationName, Message message)
		throws SchedulerException;

	public void schedule(
			Trigger trigger, StorageType storageType, String description,
			String destinationName, Object payload)
		throws SchedulerException;

	public void unschedule(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException;

}