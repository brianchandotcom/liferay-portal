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

import java.util.Collection;

/**
 * @author Michael C. Han
 */
public interface MessageBus {

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public void addDestination(Destination destination);

	public boolean addMessageBusEventListener(
		MessageBusEventListener messageBusEventListener);

	public Destination getDestination(String destinationName);

	public int getDestinationCount();

	public Collection<String> getDestinationNames();

	public Collection<Destination> getDestinations();

	public boolean hasDestination(String destinationName);

	public boolean hasMessageListener(String destinationName);

	public boolean registerMessageListener(
		String destinationName, MessageListener messageListener);

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public Destination removeDestination(String destinationName);

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public Destination removeDestination(
		String destinationName, boolean closeOnRemove);

	public boolean removeMessageBusEventListener(
		MessageBusEventListener messageBusEventListener);

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public void replace(Destination destination);

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public void replace(Destination destination, boolean closeOnReplace);

	public void sendMessage(String destinationName, Message message);

	public void shutdown();

	public void shutdown(boolean force);

	public boolean unregisterMessageListener(
		String destinationName, MessageListener messageListener);

}