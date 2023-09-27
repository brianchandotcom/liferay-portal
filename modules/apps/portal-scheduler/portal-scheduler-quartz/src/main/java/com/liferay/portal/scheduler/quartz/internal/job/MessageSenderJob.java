/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.scheduler.quartz.internal.job;

import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.scheduler.JobState;
import com.liferay.portal.kernel.scheduler.JobStateSerializeUtil;
import com.liferay.portal.kernel.scheduler.SchedulerEngine;
import com.liferay.portal.kernel.scheduler.StorageType;

import java.util.Map;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;

/**
 * @author Michael C. Han
 * @author Bruno Farache
 */
public class MessageSenderJob implements Job {

	@Override
	public void execute(JobExecutionContext jobExecutionContext) {
		try {
			_execute(jobExecutionContext);
		}
		catch (Exception exception) {
			_log.error("Unable to execute job", exception);
		}
	}

	public void setJSONFactory(JSONFactory jsonFactory) {
		_jsonFactory = jsonFactory;
	}

	public void setMessageBus(MessageBus messageBus) {
		_messageBus = messageBus;
	}

	private void _execute(JobExecutionContext jobExecutionContext)
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

		message.put(SchedulerEngine.JOB_STATE, jobState);
		message.put(SchedulerEngine.STORAGE_TYPE, storageType);

		_messageBus.sendMessage(destinationName, message);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MessageSenderJob.class);

	private JSONFactory _jsonFactory;
	private MessageBus _messageBus;

}