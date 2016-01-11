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

package com.liferay.portal.kernel.backgroundtask;

import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.sender.SingleDestinationMessageSender;
import com.liferay.portal.kernel.messaging.sender.SingleDestinationMessageSenderFactory;
import com.liferay.portal.kernel.messaging.sender.SingleDestinationMessageSenderFactoryUtil;
import com.liferay.registry.dependency.ServiceDependencyListener;
import com.liferay.registry.dependency.ServiceDependencyManager;

/**
 * @author Andrew Betts
 */
public class BackgroundTaskStatusMessageSenderImpl
	implements BackgroundTaskStatusMessageSender {

	public void afterPropertiesSet() {
		ServiceDependencyManager serviceDependencyManager =
			new ServiceDependencyManager();

		serviceDependencyManager.addServiceDependencyListener(
			new ServiceDependencyListener() {

				@Override
				public void dependenciesFulfilled() {
					_singleDestinationMessageSender =
						SingleDestinationMessageSenderFactoryUtil.
							createSingleDestinationMessageSender(
								_destinationName);
				}

				@Override
				public void destroy() {
				}

			});

		serviceDependencyManager.registerDependencies(
			SingleDestinationMessageSenderFactory.class);
	}

	@Override
	public void sendBackgroundTaskStatusMessage(Message message) {
		if (!BackgroundTaskThreadLocal.hasBackgroundTask()) {
			return;
		}

		_singleDestinationMessageSender.send(message);
	}

	public void setDestinationName(String destinationName) {
		_destinationName = destinationName;
	}

	private String _destinationName;
	private SingleDestinationMessageSender _singleDestinationMessageSender;

}