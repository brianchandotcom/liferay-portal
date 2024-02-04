/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.messaging;

import com.liferay.change.tracking.internal.background.task.CTPublishBackgroundTaskExecutor;
import com.liferay.portal.kernel.backgroundtask.constants.BackgroundTaskConstants;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.messaging.MessageBusInterceptor;

import java.util.Objects;

import org.osgi.service.component.annotations.Component;

/**
 * @author Pei-Jung Lan
 */
@Component(service = MessageBusInterceptor.class)
public class CTPublishMessageBusInterceptor implements MessageBusInterceptor {

	@Override
	public boolean intercept(
		MessageBus messageBus, String destinationName, Message message) {

		if (!Objects.equals(
				DestinationNames.BACKGROUND_TASK_STATUS, destinationName)) {

			return false;
		}

		String taskExecutorClassName = message.getString(
			"taskExecutorClassName");

		int status = message.getInteger("status");

		if (Objects.equals(
				CTPublishBackgroundTaskExecutor.class.getName(),
				taskExecutorClassName) &&
			(status == BackgroundTaskConstants.STATUS_FAILED)) {

			return true;
		}

		return false;
	}

}