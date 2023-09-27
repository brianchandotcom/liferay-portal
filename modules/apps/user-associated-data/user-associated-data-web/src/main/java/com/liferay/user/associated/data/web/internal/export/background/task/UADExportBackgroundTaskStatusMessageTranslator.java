/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.user.associated.data.web.internal.export.background.task;

import com.liferay.portal.kernel.backgroundtask.BackgroundTaskStatus;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskStatusMessageTranslator;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.util.GetterUtil;

/**
 * @author Pei-Jung Lan
 */
public class UADExportBackgroundTaskStatusMessageTranslator
	implements BackgroundTaskStatusMessageTranslator {

	@Override
	public void translate(
		BackgroundTaskStatus backgroundTaskStatus, Message message) {

		String messageType = message.getString("messageType");

		if (messageType.equals("application")) {
			_translateApplicationMessage(backgroundTaskStatus, message);
		}
		else if (messageType.equals("entity")) {
			_translateEntityMessage(backgroundTaskStatus, message);
		}
	}

	private synchronized void _translateApplicationMessage(
		BackgroundTaskStatus backgroundTaskStatus, Message message) {

		long applicationDataTotal = message.getLong("applicationDataTotal");
		String applicationKey = message.getString("applicationKey");

		backgroundTaskStatus.setAttribute(
			"applicationDataTotal", applicationDataTotal);
		backgroundTaskStatus.setAttribute("applicationKey", applicationKey);
	}

	private synchronized void _translateEntityMessage(
		BackgroundTaskStatus backgroundTaskStatus, Message message) {

		long applicationDataCounter = GetterUtil.getLong(
			backgroundTaskStatus.getAttribute("applicationDataCounter"));
		long applicationDataTotal = GetterUtil.getLong(
			backgroundTaskStatus.getAttribute("applicationDataTotal"));
		String applicationKey = GetterUtil.getString(
			backgroundTaskStatus.getAttribute("applicationKey"));
		String entityName = message.getString("entityName");

		backgroundTaskStatus.setAttribute(
			"applicationDataCounter", ++applicationDataCounter);
		backgroundTaskStatus.setAttribute(
			"applicationDataTotal", applicationDataTotal);
		backgroundTaskStatus.setAttribute("applicationKey", applicationKey);
		backgroundTaskStatus.setAttribute("entityName", entityName);
	}

}