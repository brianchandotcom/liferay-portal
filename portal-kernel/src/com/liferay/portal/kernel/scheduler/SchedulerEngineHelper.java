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
import com.liferay.portal.kernel.messaging.MessageListener;
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

	public void delete(SchedulerEntry schedulerEntry, StorageType storageType)
		throws SchedulerException;

	public void delete(String groupName, StorageType storageType)
		throws SchedulerException;

	public void delete(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException;

	public Date getEndTime(SchedulerResponse schedulerResponse);

	public Date getEndTime(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException;

	public Date getFinalFireTime(SchedulerResponse schedulerResponse);

	public Date getFinalFireTime(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException;

	public TriggerState getJobState(SchedulerResponse schedulerResponse);

	public TriggerState getJobState(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException;

	public Date getNextFireTime(SchedulerResponse schedulerResponse);

	public Date getNextFireTime(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException;

	public Date getPreviousFireTime(SchedulerResponse schedulerResponse);

	public Date getPreviousFireTime(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException;

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

	public Date getStartTime(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException;

	public void pause(String groupName, StorageType storageType)
		throws SchedulerException;

	public void pause(String jobName, String groupName, StorageType storageType)
		throws SchedulerException;

	public void register(
		MessageListener messageListener, SchedulerEntry schedulerEntry,
		String destinationName);

	public void resume(String groupName, StorageType storageType)
		throws SchedulerException;

	public void resume(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException;

	public void schedule(
			Trigger trigger, StorageType storageType, String description,
			String destinationName, Message message)
		throws SchedulerException;

	public void schedule(
			Trigger trigger, StorageType storageType, String description,
			String destinationName, Object payload)
		throws SchedulerException;

	public void shutdown() throws SchedulerException;

	public void start() throws SchedulerException;

	public void unregister(MessageListener messageListener);

	public void unschedule(
			SchedulerEntry schedulerEntry, StorageType storageType)
		throws SchedulerException;

	public void unschedule(String groupName, StorageType storageType)
		throws SchedulerException;

	public void unschedule(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException;

	public void update(
			String jobName, String groupName, StorageType storageType,
			String description, String language, String script)
		throws SchedulerException;

	public void update(Trigger trigger, StorageType storageType)
		throws SchedulerException;

}