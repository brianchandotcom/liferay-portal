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

package com.liferay.change.tracking.internal.background.task;

import com.liferay.change.tracking.internal.process.model.CTProcessLogEntryModel;
import com.liferay.change.tracking.internal.process.model.CTProcessLogModel;
import com.liferay.change.tracking.internal.process.util.CTProcessMessageSenderUtil;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskStatus;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskStatusMessageTranslator;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;

import java.io.Serializable;

import java.util.Date;
import java.util.Map;

/**
 * @author Zoltan Csaszi
 * @author Daniel Kocsis
 */
public class CTPublishBackgroundTaskStatusMessageTranslator
	implements BackgroundTaskStatusMessageTranslator {

	@Override
	public void translate(
		BackgroundTaskStatus backgroundTaskStatus, Message message) {

		CTProcessLogModel ctProcessLogModel =
			(CTProcessLogModel)backgroundTaskStatus.getAttribute(
				"ctProcessLogModel");

		if (ctProcessLogModel == null) {
			ctProcessLogModel = new CTProcessLogModel();

			backgroundTaskStatus.setAttribute(
				"ctProcessLogModel", ctProcessLogModel);
		}

		CTProcessLogEntryModel.Builder builder =
			new CTProcessLogEntryModel.Builder();

		final CTProcessLogEntryModel ctProcessLogEntryModel = builder.date(
			(Date)message.get("date")
		).level(
			((CTProcessMessageSenderUtil.Level)message.get("level")).getLabel()
		).messageKey(
			message.getString("message")
		).messageParameters(
			(Map<String, Serializable>)message.get("messageParameters")
		).build();

		ctProcessLogModel.insertLogEntry(ctProcessLogEntryModel);

		if (_log.isInfoEnabled()) {
			_log.info(ctProcessLogModel.toString());
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CTPublishBackgroundTaskStatusMessageTranslator.class);

}