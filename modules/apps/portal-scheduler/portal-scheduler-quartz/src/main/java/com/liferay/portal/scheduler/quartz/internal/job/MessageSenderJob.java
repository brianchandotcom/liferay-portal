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

package com.liferay.portal.scheduler.quartz.internal.job;

import com.liferay.portal.kernel.cluster.ClusterExecutor;
import com.liferay.portal.kernel.cluster.ClusterRequest;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.scheduler.JobState;
import com.liferay.portal.kernel.scheduler.JobStateSerializeUtil;
import com.liferay.portal.kernel.scheduler.SchedulerEngine;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelperUtil;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsKeys;

import java.util.Map;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.Scheduler;

/**
 * @author Michael C. Han
 * @author Bruno Farache
 */
public class MessageSenderJob implements Job {

	@Override
	public void execute(JobExecutionContext jobExecutionContext) {
		try {
			doExecute(jobExecutionContext);
		}
		catch (Exception e) {
			_log.error("Unable to execute job", e);
		}
	}

	public void setClusterExecutor(ClusterExecutor clusterExecutor) {
		_clusterExecutor = clusterExecutor;
	}

	public void setJSONFactory(JSONFactory jsonFactory) {
		_jsonFactory = jsonFactory;
	}

	public void setMessageBus(MessageBus messageBus) {
		_messageBus = messageBus;
	}

	public void setProps(Props props) {
		_props = props;
	}

	protected void doExecute(JobExecutionContext jobExecutionContext)
		throws Exception {

		JobDetail jobDetail = jobExecutionContext.getJobDetail();

		JobDataMap jobDataMap = jobDetail.getJobDataMap();

		String destinationName = jobDataMap.getString(
			SchedulerEngine.DESTINATION_NAME);

		String messageJSON = (String)jobDataMap.get(SchedulerEngine.MESSAGE);

		if (messageJSON == null) {
			throw new NullPointerException(
				"Message retrieved from job data map is null");
		}

		Message message = (Message)_jsonFactory.deserialize(messageJSON);

		message.put(SchedulerEngine.DESTINATION_NAME, destinationName);

		Map<String, Object> jobStateMap = (Map<String, Object>)jobDataMap.get(
			SchedulerEngine.JOB_STATE);

		JobState jobState = JobStateSerializeUtil.deserialize(jobStateMap);

		StorageType storageType = StorageType.valueOf(
			jobDataMap.getString(SchedulerEngine.STORAGE_TYPE));

		if (jobExecutionContext.getNextFireTime() == null) {
			message.put(SchedulerEngine.DISABLE, true);

			JobKey jobKey = jobDetail.getKey();

			if (GetterUtil.getBoolean(
					_props.get(PropsKeys.CLUSTER_LINK_ENABLED)) &&
				(storageType == StorageType.MEMORY_CLUSTERED)) {

				notifyClusterMember(jobKey, storageType);
			}

			if (storageType == StorageType.PERSISTED) {
				Scheduler scheduler = jobExecutionContext.getScheduler();

				scheduler.deleteJob(jobKey);
			}
		}

		message.put(SchedulerEngine.JOB_STATE, jobState);
		message.put(SchedulerEngine.STORAGE_TYPE, storageType);

		_messageBus.sendMessage(destinationName, message);
	}

	protected void notifyClusterMember(JobKey jobKey, StorageType storageType)
		throws Exception {

		MethodHandler methodHandler = new MethodHandler(
			_deleteJobMethodKey, jobKey.getName(), jobKey.getGroup(),
			storageType);

		ClusterRequest clusterRequest = ClusterRequest.createMulticastRequest(
			methodHandler, true);

		_clusterExecutor.execute(clusterRequest);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MessageSenderJob.class);

	private static final MethodKey _deleteJobMethodKey = new MethodKey(
		SchedulerEngineHelperUtil.class, "delete", String.class, String.class,
		StorageType.class);

	private ClusterExecutor _clusterExecutor;
	private JSONFactory _jsonFactory;
	private MessageBus _messageBus;
	private Props _props;

}