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

import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationConfiguration;
import com.liferay.portal.kernel.messaging.DestinationEventListener;
import com.liferay.portal.kernel.messaging.MessageBusEventListener;
import com.liferay.portal.kernel.messaging.MessageListener;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Michael C. Han
 */
public interface MessagingConfigurator {

	public void connect();

	public void destroy();

	public void disconnect();

	public void setDestinationConfigurations(
		Set<DestinationConfiguration> destinationConfigurations);

	public void setDestinationEventListeners(
		Map<String, List<DestinationEventListener>> destinationEventListeners);

	public void setDestinations(List<Destination> destinations);

	public void setMessageBusEventListeners(
		List<MessageBusEventListener> messageBusEventListeners);

	public void setMessageListeners(
		Map<String, List<MessageListener>> messageListeners);

}