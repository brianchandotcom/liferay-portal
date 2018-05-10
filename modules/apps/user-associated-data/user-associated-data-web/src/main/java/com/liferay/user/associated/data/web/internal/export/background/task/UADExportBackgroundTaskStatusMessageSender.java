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

package com.liferay.user.associated.data.web.internal.export.background.task;

import com.liferay.portal.kernel.backgroundtask.BackgroundTaskConstants;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskStatusMessageSender;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskThreadLocal;
import com.liferay.portal.kernel.messaging.Message;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(
	immediate = true, service = UADExportBackgroundTaskStatusMessageSender.class
)
public class UADExportBackgroundTaskStatusMessageSender {

	public void sendStatusMessage(String messageType, String entityName) {
		Message message = new Message();

		message.put(
			BackgroundTaskConstants.BACKGROUND_TASK_ID,
			BackgroundTaskThreadLocal.getBackgroundTaskId());
		message.put("entityName", entityName);
		message.put("messageType", messageType);

		_backgroundTaskStatusMessageSender.sendBackgroundTaskStatusMessage(
			message);
	}

	public void sendStatusMessage(
		String messageType, String applicationKey, long total) {

		Message message = new Message();

		message.put(
			BackgroundTaskConstants.BACKGROUND_TASK_ID,
			BackgroundTaskThreadLocal.getBackgroundTaskId());
		message.put("applicationDataTotal", total);
		message.put("applicationKey", applicationKey);
		message.put("messageType", messageType);

		_backgroundTaskStatusMessageSender.sendBackgroundTaskStatusMessage(
			message);
	}

	@Reference
	private BackgroundTaskStatusMessageSender
		_backgroundTaskStatusMessageSender;

}