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
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.messaging.MessageListenerException;
import com.liferay.portal.kernel.scheduler.SchedulerEngine;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelperUtil;
import com.liferay.portal.kernel.scheduler.SchedulerEntry;
import com.liferay.portal.kernel.scheduler.Trigger;
import com.liferay.portal.kernel.scheduler.TriggerState;
import com.liferay.portal.kernel.util.GetterUtil;

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
		catch (Exception exception) {
			if (exception instanceof MessageListenerException) {
				throw (MessageListenerException)exception;
			}

			throw new MessageListenerException(exception);
		}
		finally {
			try {
				SchedulerEngineHelperUtil.auditSchedulerJobs(
					message, TriggerState.NORMAL);
			}
			catch (Exception exception) {
				if (_log.isInfoEnabled()) {
					_log.info("Unable to send audit message", exception);
				}
			}
		}
	}

	public void setMessageListener(MessageListener messageListener) {
		_messageListener = messageListener;
	}

	public void setSchedulerEntry(SchedulerEntry schedulerEntry) {
		_schedulerEntry = schedulerEntry;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SchedulerEventMessageListenerWrapper.class);

	private MessageListener _messageListener;
	private SchedulerEntry _schedulerEntry;

}