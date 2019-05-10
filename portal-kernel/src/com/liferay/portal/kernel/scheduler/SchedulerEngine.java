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
import com.liferay.portal.kernel.messaging.proxy.MessagingProxy;
import com.liferay.portal.kernel.messaging.proxy.ProxyMode;
import com.liferay.portal.kernel.scheduler.messaging.SchedulerResponse;

import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Michael C. Han
 * @author Bruno Farache
 * @author Shuyang Zhou
 * @author Tina Tian
 */
@ProviderType
public interface SchedulerEngine {

	public static final String DESCRIPTION = "DESCRIPTION";

	public static final String DESTINATION_NAME = "DESTINATION_NAME";

	public static final String DISABLE = "DISABLE";

	public static final String END_TIME = "END_TIME";

	public static final String EXCEPTIONS_MAX_SIZE = "EXCEPTIONS_MAX_SIZE";

	public static final String FINAL_FIRE_TIME = "FINAL_FIRE_TIME";

	public static final String GROUP_NAME = "GROUP_NAME";

	public static final String JOB_NAME = "JOB_NAME";

	public static final String JOB_STATE = "JOB_STATE";

	public static final String LANGUAGE = "LANGUAGE";

	public static final String MESSAGE = "MESSAGE";

	public static final String NEXT_FIRE_TIME = "NEXT_FIRE_TIME";

	public static final String PREVIOUS_FIRE_TIME = "PREVIOUS_FIRE_TIME";

	public static final String SCHEDULER = "SCHEDULER";

	public static final String SCHEDULER_CLUSTER_INVOKING =
		"scheduler.cluster.invoking";

	public static final String SCRIPT = "SCRIPT";

	public static final String START_TIME = "START_TIME";

	public static final String STORAGE_TYPE = "STORAGE_TYPE";

	public void delete(String groupName, StorageType storageType)
		throws SchedulerException;

	public void delete(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException;

	@MessagingProxy(mode = ProxyMode.SYNC)
	public SchedulerResponse getScheduledJob(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException;

	@MessagingProxy(mode = ProxyMode.SYNC)
	public List<SchedulerResponse> getScheduledJobs() throws SchedulerException;

	@MessagingProxy(mode = ProxyMode.SYNC)
	public List<SchedulerResponse> getScheduledJobs(StorageType storageType)
		throws SchedulerException;

	@MessagingProxy(mode = ProxyMode.SYNC)
	public List<SchedulerResponse> getScheduledJobs(
			String groupName, StorageType storageType)
		throws SchedulerException;

	public void pause(String groupName, StorageType storageType)
		throws SchedulerException;

	public void pause(String jobName, String groupName, StorageType storageType)
		throws SchedulerException;

	public void resume(String groupName, StorageType storageType)
		throws SchedulerException;

	public void resume(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException;

	public void schedule(
			Trigger trigger, String description, String destinationName,
			Message message, StorageType storageType)
		throws SchedulerException;

	@MessagingProxy(local = true, mode = ProxyMode.SYNC)
	public void shutdown() throws SchedulerException;

	@MessagingProxy(local = true, mode = ProxyMode.SYNC)
	public void start() throws SchedulerException;

	public void suppressError(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException;

	@MessagingProxy(mode = ProxyMode.SYNC)
	public void unschedule(String groupName, StorageType storageType)
		throws SchedulerException;

	@MessagingProxy(mode = ProxyMode.SYNC)
	public void unschedule(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException;

	public void update(Trigger trigger, StorageType storageType)
		throws SchedulerException;

	@MessagingProxy(mode = ProxyMode.SYNC)
	public void validateTrigger(Trigger trigger, StorageType storageType)
		throws SchedulerException;

}