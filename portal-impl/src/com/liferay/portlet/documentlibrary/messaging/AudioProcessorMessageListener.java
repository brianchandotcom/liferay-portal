/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.documentlibrary.messaging;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.documentlibrary.util.AudioProcessorUtil;

/**
 * @author Juan González
 * @author Sergio González
 */
public class AudioProcessorMessageListener extends BaseMessageListener {

	@Override
	protected void doReceive(Message message) throws Exception {
		FileVersion fileVersion = (FileVersion)message.getPayload();

		try {
			AudioProcessorUtil.generateAudio(fileVersion);
		}
		catch (Exception e) {
			_log.warn(
				"Unable to generate audio for file version: " +
					fileVersion.getFileVersionId(), e);
		}

		if (PropsValues.DL_FILE_ENTRY_PROCESSORS_TRIGGER_SYNCHRONOUSLY) {
			MessageBusUtil.sendMessage(
				message.getResponseDestinationName(), message);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		AudioProcessorMessageListener.class);

}