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

package com.liferay.portal.kernel.messaging.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Shuyang Zhou
 */
public class MessagingConfiguratorRegistry {

	public static List<MessagingConfigurator> getMessagingConfigurators(
		String servletContextName) {

		return _messagingConfigurators.get(servletContextName);
	}

	public static void registerMessagingConfigurator(
		String servletContextName,
		MessagingConfigurator messagingConfigurator) {

		List<MessagingConfigurator> messagingConfigurators =
			_messagingConfigurators.get(servletContextName);

		if (messagingConfigurators == null) {
			messagingConfigurators = new ArrayList<>();

			_messagingConfigurators.put(
				servletContextName, messagingConfigurators);
		}

		messagingConfigurators.add(messagingConfigurator);
	}

	public static void unregisterMessagingConfigurator(
		String servletContextName,
		MessagingConfigurator messagingConfigurator) {

		List<MessagingConfigurator> messagingConfigurators =
			_messagingConfigurators.get(servletContextName);

		if (messagingConfigurators != null) {
			messagingConfigurators.remove(messagingConfigurator);

			if (messagingConfigurators.isEmpty()) {
				_messagingConfigurators.remove(servletContextName);
			}
		}
	}

	private static final Map<String, List<MessagingConfigurator>>
		_messagingConfigurators = new ConcurrentHashMap<>();

}