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

package com.liferay.dispatch.portal.kernel.scheduler;

import com.liferay.portal.kernel.scheduler.SchedulerException;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.scheduler.TriggerState;

import java.util.Date;
import java.util.List;

/**
 * @author Matija Petanjek
 */
public interface DispatchSchedulerEngineHelper {

	public List<ScheduledJobDispatchTrigger> getScheduledJobDispatchTriggers(
		int start, int end);

	public Date getScheduledJobNextFireDate(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException;

	public int getScheduledJobsCount();

	public TriggerState getTriggerState(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException;

	public void pause(String jobName, String groupName, StorageType storageType)
		throws SchedulerException;

	public void resume(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException;

	public void run(String jobName, String groupName, StorageType storageType)
		throws SchedulerException;

}