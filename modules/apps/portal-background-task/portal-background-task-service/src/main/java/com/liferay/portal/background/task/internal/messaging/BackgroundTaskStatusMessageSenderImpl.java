/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.background.task.internal.messaging;

import com.liferay.portal.kernel.backgroundtask.BackgroundTaskStatusMessageSender;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskThreadLocal;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.util.Validator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrew Betts
 */
@Component(service = BackgroundTaskStatusMessageSender.class)
public class BackgroundTaskStatusMessageSenderImpl
	implements BackgroundTaskStatusMessageSender {

	@Override
	public void sendBackgroundTaskStatusMessage(Message message) {
		if (!BackgroundTaskThreadLocal.hasBackgroundTask()) {
			return;
		}

		String destinationName = message.getDestinationName();

		if (Validator.isNull(destinationName)) {
			destinationName = DestinationNames.BACKGROUND_TASK_STATUS;

			message.setDestinationName(destinationName);
		}

		if (!message.contains("companyId")) {
			message.put("companyId", CompanyThreadLocal.getCompanyId());
		}

		_messageBus.sendMessage(destinationName, message);
	}

	@Reference
	private MessageBus _messageBus;

}