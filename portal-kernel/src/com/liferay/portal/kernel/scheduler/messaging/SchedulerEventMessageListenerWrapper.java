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

package com.liferay.portal.kernel.scheduler.messaging;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.messaging.MessageListenerException;
import com.liferay.portal.kernel.scheduler.JobState;
import com.liferay.portal.kernel.scheduler.SchedulerEngine;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelperUtil;
import com.liferay.portal.kernel.scheduler.SchedulerEntry;
import com.liferay.portal.kernel.scheduler.SchedulerException;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.scheduler.Trigger;
import com.liferay.portal.kernel.scheduler.TriggerState;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Date;

/**
 * @author Shuyang Zhou
 */
public class SchedulerEventMessageListenerWrapper
	implements SchedulerEventMessageListener {

	@Override
	public SchedulerEntry getSchedulerEntry() {
		return _schedulerEntry;
	}

	@Override
	public void receive(Message message) throws MessageListenerException {
		String destinationName = GetterUtil.getString(
			message.getString(SchedulerEngine.DESTINATION_NAME));
		String jobName = message.getString(SchedulerEngine.JOB_NAME);
		String groupName = message.getString(SchedulerEngine.GROUP_NAME);

		if (destinationName.equals(DestinationNames.SCHEDULER_DISPATCH)) {
			Trigger trigger = _schedulerEntry.getTrigger();

			if (!jobName.equals(trigger.getJobName()) ||
				!groupName.equals(trigger.getGroupName())) {

				return;
			}
		}

		try {
			_messageListener.receive(message);
		}
		catch (Exception e) {
			handleException(message, e);

			if (e instanceof MessageListenerException) {
				throw (MessageListenerException)e;
			}
			else {
				throw new MessageListenerException(e);
			}
		}
		finally {
			TriggerState triggerState = null;

			if (message.getBoolean(SchedulerEngine.DISABLE)) {
				triggerState = TriggerState.COMPLETE;

				if (destinationName.equals(
						DestinationNames.SCHEDULER_DISPATCH)) {

					MessageBusUtil.unregisterMessageListener(
						destinationName, this);
				}

				StorageType storageType = (StorageType)message.get(
					SchedulerEngine.STORAGE_TYPE);

				try {
					SchedulerEngineHelperUtil.delete(
						jobName, groupName, storageType);
				}
				catch (SchedulerException se) {
					if (_log.isInfoEnabled()) {
						_log.info(
							"Unable to delete job " + jobName + " in group " +
								groupName,
							se);
					}
				}
			}
			else {
				triggerState = TriggerState.NORMAL;
			}

			try {
				SchedulerEngineHelperUtil.auditSchedulerJobs(
					message, triggerState);
			}
			catch (Exception e) {
				if (_log.isInfoEnabled()) {
					_log.info("Unable to send audit message", e);
				}
			}
		}
	}

	/**
	 * @deprecated As of 7.0.0
	 */
	@Deprecated
	public void setGroupName(String groupName) {
		_groupName = groupName;
	}

	/**
	 * @deprecated As of 7.0.0
	 */
	@Deprecated
	public void setJobName(String jobName) {
		_jobName = jobName;
	}

	public void setMessageListener(MessageListener messageListener) {
		_messageListener = messageListener;
	}

	public void setSchedulerEntry(SchedulerEntry schedulerEntry) {
		_schedulerEntry = schedulerEntry;
	}

	protected void handleException(Message message, Exception exception) {
		JobState jobState = (JobState)message.get(SchedulerEngine.JOB_STATE);

		if (jobState != null) {
			jobState.addException(exception, new Date());
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SchedulerEventMessageListenerWrapper.class);

	/**
	 * @deprecated As of 7.0.0
	 */
	@Deprecated
	@SuppressWarnings("unused")
	private String _groupName;

	/**
	 * @deprecated As of 7.0.0
	 */
	@Deprecated
	@SuppressWarnings("unused")
	private String _jobName;

	private MessageListener _messageListener;
	private volatile SchedulerEntry _schedulerEntry;

}