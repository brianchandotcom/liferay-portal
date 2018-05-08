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

package com.liferay.portal.search.internal.background.task;

import com.liferay.portal.kernel.backgroundtask.BackgroundTaskConstants;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskStatusMessageSender;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskThreadLocal;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.search.background.task.ReindexBackgroundTaskConstants;
import com.liferay.portal.kernel.search.background.task.ReindexStatusMessageSender;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrew Betts
 */
@Component(immediate = true, service = ReindexStatusMessageSender.class)
public class ReindexStatusMessageSenderImpl
	implements ReindexStatusMessageSender {

	@Override
	public void sendStatusMessage(String className, long count, long total) {
		Message message = new Message();

		message.put(
			BackgroundTaskConstants.BACKGROUND_TASK_ID,
			BackgroundTaskThreadLocal.getBackgroundTaskId());
		message.put(ReindexBackgroundTaskConstants.CLASS_NAME, className);
		message.put(ReindexBackgroundTaskConstants.COUNT, count);
		message.put(ReindexBackgroundTaskConstants.TOTAL, total);
		message.put("status", BackgroundTaskConstants.STATUS_IN_PROGRESS);

		sendBackgroundTaskStatusMessage(message);
	}

	@Override
	public void sendStatusMessage(
		String phase, long companyId, long[] companyIds) {

		Message message = new Message();

		message.put(
			BackgroundTaskConstants.BACKGROUND_TASK_ID,
			BackgroundTaskThreadLocal.getBackgroundTaskId());
		message.put(ReindexBackgroundTaskConstants.COMPANY_ID, companyId);
		message.put(ReindexBackgroundTaskConstants.COMPANY_IDS, companyIds);
		message.put(ReindexBackgroundTaskConstants.PHASE, phase);
		message.put("status", BackgroundTaskConstants.STATUS_IN_PROGRESS);

		sendBackgroundTaskStatusMessage(message);
	}

	protected void sendBackgroundTaskStatusMessage(Message message) {
		_backgroundTaskStatusMessageSender.sendBackgroundTaskStatusMessage(
			message);

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Sent reindex background task status message: " + message);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ReindexStatusMessageSenderImpl.class);

	@Reference
	private BackgroundTaskStatusMessageSender
		_backgroundTaskStatusMessageSender;

}