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

package com.liferay.portal.lar;

import com.liferay.portal.kernel.backgroundtask.BackgroundTaskThreadLocal;
import com.liferay.portal.kernel.lar.DataHandlerStatusMessageSender;
import com.liferay.portal.kernel.lar.ManifestSummary;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.sender.SingleDestinationMessageSender;
import com.liferay.portal.kernel.util.LongWrapper;
import com.liferay.portal.model.StagedModel;

import java.util.Map;

/**
 * @author Michael C. Han
 */
public class DataHandlerStatusMessageSenderImpl
	implements DataHandlerStatusMessageSender {

	public void sendStatusMessage(
		String messageType, String portletId, ManifestSummary manifestSummary) {

		if (!BackgroundTaskThreadLocal.isBackgroundExecution()) {
			return;
		}

		Message message = new Message();

		message.put(
			"backgroundTaskId",
			BackgroundTaskThreadLocal.getBackgroundTaskId());

		message.put("messageType", messageType);

		message.put("portletId", portletId);

		Map<String, LongWrapper> modelAdditionCounters =
			manifestSummary.getModelAdditionCounters();

		message.put("modelAdditionCounters", modelAdditionCounters);

		Map<String, LongWrapper> modelDeletionCounters =
			manifestSummary.getModelDeletionCounters();

		message.put("modelDeletionCounters", modelDeletionCounters);

		_singleDestinationMessageSender.send(message);
	}

	public <T extends StagedModel> void sendStatusMessage(
		String messageType, T stagedModel, ManifestSummary manifestSummary) {

		if (!BackgroundTaskThreadLocal.isBackgroundExecution()) {
			return;
		}

		Message message = new Message();

		message.put(
			"backgroundTaskId",
			BackgroundTaskThreadLocal.getBackgroundTaskId());

		message.put("messageType", messageType);

		message.put(
			"stagedModelType", stagedModel.getStagedModelType().toString());

		message.put("uuid", stagedModel.getUuid());

		Map<String, LongWrapper> modelAdditionCounters =
			manifestSummary.getModelAdditionCounters();

		message.put("modelAdditionCounters", modelAdditionCounters);

		Map<String, LongWrapper> modelDeletionCounters =
			manifestSummary.getModelDeletionCounters();

		message.put("modelDeletionCounters", modelDeletionCounters);

		_singleDestinationMessageSender.send(message);
	}

	public void setSingleDestinationMessageSender(
		SingleDestinationMessageSender singleDestinationMessageSender) {

		_singleDestinationMessageSender = singleDestinationMessageSender;
	}

	private SingleDestinationMessageSender
		_singleDestinationMessageSender;

}