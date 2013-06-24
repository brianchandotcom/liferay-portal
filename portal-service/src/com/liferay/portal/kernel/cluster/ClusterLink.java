/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.cluster;

import com.liferay.portal.kernel.messaging.Message;

import java.net.InetAddress;

import java.util.List;

/**
 * @author Shuyang Zhou
 */
public interface ClusterLink {

	public static final String CLUSTER_FORWARD_MESSAGE =
		"CLUSTER_FORWARD_MESSAGE";

	public static final int MAX_CHANNEL_COUNT = Priority.values().length;

	public InetAddress getBindInetAddress();

	public List<Address> getLocalTransportAddresses();

	public List<Address> getTransportAddresses(Priority priority);

	public boolean isEnabled();

	public void sendMulticastMessage(Message message, Priority priority);

	public void sendUnicastMessage(
		Address address, Message message, Priority priority);

}