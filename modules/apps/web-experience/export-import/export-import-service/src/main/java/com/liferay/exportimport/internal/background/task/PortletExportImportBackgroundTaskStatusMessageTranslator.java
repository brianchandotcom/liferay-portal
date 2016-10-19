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

package com.liferay.exportimport.internal.background.task;

import com.liferay.portal.kernel.backgroundtask.BackgroundTaskStatus;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.util.GetterUtil;

/**
 * @author Daniel Kocsis
 */
public class PortletExportImportBackgroundTaskStatusMessageTranslator
	extends DefaultExportImportBackgroundTaskStatusMessageTranslator {

	@Override
	protected synchronized void translatePortletMessage(
		BackgroundTaskStatus backgroundTaskStatus, Message message) {

		clearBackgroundTaskStatus(backgroundTaskStatus);

		long portletModelAdditionCountersTotal = GetterUtil.getLong(
			message.get("portletModelAdditionCountersTotal"));

		backgroundTaskStatus.setAttribute(
			"allModelAdditionCountersTotal", portletModelAdditionCountersTotal);

		super.translatePortletMessage(backgroundTaskStatus, message);
	}

}