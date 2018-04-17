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

package com.liferay.portal.kernel.messaging;

import java.util.Set;

/**
 * @author Michael C. Han
 */
public interface Destination {

	public boolean addDestinationEventListener(
		DestinationEventListener destinationEventListener);

	public void close();

	public void close(boolean force);

	public void copyDestinationEventListeners(Destination destination);

	public void copyMessageListeners(Destination destination);

	public void destroy();

	public DestinationStatistics getDestinationStatistics();

	public int getMessageListenerCount();

	public Set<MessageListener> getMessageListeners();

	public String getName();

	public boolean isRegistered();

	public void open();

	public boolean register(MessageListener messageListener);

	public boolean register(
		MessageListener messageListener, ClassLoader classLoader);

	public boolean removeDestinationEventListener(
		DestinationEventListener destinationEventListener);

	public void removeDestinationEventListeners();

	public void send(Message message);

	public boolean unregister(MessageListener messageListener);

	public void unregisterMessageListeners();

}