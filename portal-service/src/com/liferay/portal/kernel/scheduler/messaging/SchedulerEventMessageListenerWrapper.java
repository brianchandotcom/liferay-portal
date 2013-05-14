/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.scheduler.TriggerState;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import java.util.Date;

/**
 * @author Shuyang Zhou
 */
public class SchedulerEventMessageListenerWrapper implements MessageListener {

	public void afterPropertiesSet() {
		if (_jobName.length() > SchedulerEngine.GROUP_NAME_MAX_LENGTH) {
			_jobName = _jobName.substring(
				0, SchedulerEngine.GROUP_NAME_MAX_LENGTH);
		}

		if (_groupName.length() > SchedulerEngine.JOB_NAME_MAX_LENGTH) {
			_groupName = _groupName.substring(
				0, SchedulerEngine.JOB_NAME_MAX_LENGTH);
		}

		_key = _jobName.concat(StringPool.PERIOD).concat(_groupName);

		if (_messageListenerUUID == null) {
			_messageListenerUUID = PortalUUIDUtil.generate();
		}
	}

	public String getMessageListenerUUID() {
		return _messageListenerUUID;
	}

	@Override
	public void receive(Message message) throws MessageListenerException {
		String destinationName = GetterUtil.getString(
			message.getString(SchedulerEngine.DESTINATION_NAME));

		if (destinationName.equals(DestinationNames.SCHEDULER_DISPATCH)) {
			String receiverKey = GetterUtil.getString(
				message.getString(SchedulerEngine.RECEIVER_KEY));

			if (!receiverKey.equals(_key)) {
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
	 * @deprecated As of 6.2.0, replaced by {@link #setGroupName(String)}
	 */
	public void setClassName(String className) {
		_groupName = className;
		_jobName = className;
	}

	public void setGroupName(String groupName) {
		_groupName = groupName;
	}

	public void setJobName(String jobName) {
		_jobName = jobName;
	}

	public void setMessageListener(MessageListener messageListener) {
		_messageListener = messageListener;
	}

	public void setMessageListenerUUID(String messageListenerUUID) {
		_messageListenerUUID = messageListenerUUID;
	}

	protected void handleException(Message message, Exception exception) {
		JobState jobState = (JobState)message.get(SchedulerEngine.JOB_STATE);

		if (jobState != null) {
			jobState.addException(exception, new Date());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		SchedulerEventMessageListenerWrapper.class);

	private String _groupName;
	private String _jobName;
	private String _key;
	private MessageListener _messageListener;
	private String _messageListenerUUID;

}