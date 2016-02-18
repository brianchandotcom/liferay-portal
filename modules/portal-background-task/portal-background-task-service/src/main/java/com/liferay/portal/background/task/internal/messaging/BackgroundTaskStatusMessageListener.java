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

package com.liferay.portal.background.task.internal.messaging;

import com.liferay.portal.kernel.backgroundtask.BackgroundTaskConstants;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskStatus;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskStatusMessageTranslator;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskStatusRegistry;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Message;

/**
 * @author Michael C. Han
 */
public class BackgroundTaskStatusMessageListener extends BaseMessageListener {

	public BackgroundTaskStatusMessageListener(
		long backgroundTaskId,
		BackgroundTaskStatusMessageTranslator
			backgroundTaskStatusMessageTranslator,
		BackgroundTaskStatusRegistry backgroundTaskStatusRegistry) {

		_backgroundTaskId = backgroundTaskId;
		_backgroundTaskStatusMessageTranslator =
			backgroundTaskStatusMessageTranslator;
		_backgroundTaskStatusRegistry = backgroundTaskStatusRegistry;
	}

	@Override
	protected void doReceive(Message message) throws Exception {
		long backgroundTaskId = message.getLong(
			BackgroundTaskConstants.BACKGROUND_TASK_ID);

		if (backgroundTaskId != _backgroundTaskId) {
			return;
		}

		BackgroundTaskStatus backgroundTaskStatus =
			_backgroundTaskStatusRegistry.getBackgroundTaskStatus(
				backgroundTaskId);

		if (backgroundTaskStatus == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Unable to locate status for background task " +
						backgroundTaskId + " to process " + message);
			}

			return;
		}

		_backgroundTaskStatusMessageTranslator.translate(
			backgroundTaskStatus, message);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BackgroundTaskStatusMessageListener.class);

	private final long _backgroundTaskId;
	private final BackgroundTaskStatusMessageTranslator
		_backgroundTaskStatusMessageTranslator;
	private final BackgroundTaskStatusRegistry _backgroundTaskStatusRegistry;

}