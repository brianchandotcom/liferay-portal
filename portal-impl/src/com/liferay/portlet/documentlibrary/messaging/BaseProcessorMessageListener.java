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

/**
 * @author Alexander Chow
 */
public abstract class BaseProcessorMessageListener extends BaseMessageListener {

	@Override
	protected void doReceive(Message message) throws Exception {
		Object[] array = (Object[])message.getPayload();

		FileVersion copyFromVersion = (FileVersion)array[0];
		FileVersion fileVersion = (FileVersion)array[1];

		try {
			generate(copyFromVersion, fileVersion);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to process file version " +
						fileVersion.getFileVersionId(),
					e);
			}
		}

		if (PropsValues.DL_FILE_ENTRY_PROCESSORS_TRIGGER_SYNCHRONOUSLY) {
			MessageBusUtil.sendMessage(
				message.getResponseDestinationName(), message);
		}
	}

	protected abstract void generate(
			FileVersion copyFromVersion, FileVersion fileVersion)
		throws Exception;

	private static Log _log = LogFactoryUtil.getLog(
		BaseProcessorMessageListener.class);

}