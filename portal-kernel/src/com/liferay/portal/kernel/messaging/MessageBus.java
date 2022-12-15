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

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 */
@ProviderType
public interface MessageBus {

	public Destination getDestination(String destinationName);

	public int getDestinationCount();

	public boolean hasDestination(String destinationName);

	public boolean registerMessageListener(
		String destinationName, MessageListener messageListener);

	public void sendMessage(String destinationName, Message message);

	public void shutdown();

	public void shutdown(boolean force);

	public boolean unregisterMessageListener(
		String destinationName, MessageListener messageListener);

}