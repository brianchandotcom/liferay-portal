/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.scheduler;

import com.liferay.portal.kernel.messaging.Message;
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

	public static final String END_TIME = "END_TIME";

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

	public SchedulerResponse getScheduledJob(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException;

	public List<SchedulerResponse> getScheduledJobs() throws SchedulerException;

	public List<SchedulerResponse> getScheduledJobs(StorageType storageType)
		throws SchedulerException;

	public List<SchedulerResponse> getScheduledJobs(
			String groupName, StorageType storageType)
		throws SchedulerException;

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
			Trigger trigger, String description, String destinationName,
			Message message, StorageType storageType)
		throws SchedulerException;

	public void shutdown() throws SchedulerException;

	public void start() throws SchedulerException;

	public void validateTrigger(Trigger trigger, StorageType storageType)
		throws SchedulerException;

}