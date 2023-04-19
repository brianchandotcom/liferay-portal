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

package com.liferay.adaptive.media.web.internal.background.task;

import com.liferay.adaptive.media.constants.AMOptimizeImagesBackgroundTaskConstants;
import com.liferay.osgi.util.service.Snapshot;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskStatusMessageSender;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskThreadLocal;
import com.liferay.portal.kernel.backgroundtask.constants.BackgroundTaskConstants;
import com.liferay.portal.kernel.messaging.Message;

/**
 * @author Sergio González
 */
public class OptimizeImagesStatusMessageSenderUtil {

	public static void sendStatusMessage(
		String phase, long companyId, String configurationEntryUuid) {

		Message message = new Message();

		message.put(
			BackgroundTaskConstants.BACKGROUND_TASK_ID,
			BackgroundTaskThreadLocal.getBackgroundTaskId());
		message.put(
			AMOptimizeImagesBackgroundTaskConstants.COMPANY_ID, companyId);
		message.put(
			AMOptimizeImagesBackgroundTaskConstants.CONFIGURATION_ENTRY_UUID,
			configurationEntryUuid);
		message.put(AMOptimizeImagesBackgroundTaskConstants.PHASE, phase);
		message.put("status", BackgroundTaskConstants.STATUS_IN_PROGRESS);

		BackgroundTaskStatusMessageSender backgroundTaskStatusMessageSender =
			_backgroundTaskStatusMessageSenderSnapshot.get();

		backgroundTaskStatusMessageSender.sendBackgroundTaskStatusMessage(
			message);
	}

	private static final Snapshot<BackgroundTaskStatusMessageSender>
		_backgroundTaskStatusMessageSenderSnapshot = new Snapshot<>(
			OptimizeImagesStatusMessageSenderUtil.class,
			BackgroundTaskStatusMessageSender.class);

}