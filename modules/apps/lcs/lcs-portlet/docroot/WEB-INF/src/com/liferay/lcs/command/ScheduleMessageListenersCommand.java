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

package com.liferay.lcs.command;

import com.liferay.lcs.messaging.CommandMessage;
import com.liferay.lcs.messaging.scheduler.MessageListenerSchedulerService;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author Ivica Cardic
 */
public class ScheduleMessageListenersCommand implements Command {

	@Override
	public void execute(CommandMessage commandMessage) throws PortalException {
		List<Map<String, String>> schedulerContexts =
			(List<Map<String, String>>)commandMessage.getPayload();

		Collections.shuffle(schedulerContexts);

		if (schedulerContexts != null) {
			for (Map<String, String> schedulerContext : schedulerContexts) {
				scheduleMessageListener(schedulerContext);
			}
		}
	}

	public void setMessageListenerSchedulerService(
		MessageListenerSchedulerService messageListenerSchedulerService) {

		_messageListenerSchedulerService = messageListenerSchedulerService;
	}

	protected void scheduleMessageListener(
		Map<String, String> schedulerContext) {

		_messageListenerSchedulerService.scheduleMessageListener(
			schedulerContext);
	}

	private MessageListenerSchedulerService _messageListenerSchedulerService;

}