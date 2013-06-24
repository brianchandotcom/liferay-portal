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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;

import java.net.InetAddress;

import java.util.Collections;
import java.util.List;

/**
 * @author Shuyang Zhou
 * @author Raymond Augé
 */
public class ClusterLinkUtil {

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             ClusterLink#CLUSTER_FORWARD_MESSAGE}
	 */
	public static final String CLUSTER_FORWARD_MESSAGE =
		ClusterLink.CLUSTER_FORWARD_MESSAGE;

	public static Address getAddress(Message message) {
		return (Address)message.get(_ADDRESS);
	}

	public static InetAddress getBindInetAddress() {
		ClusterLink clusterLink = getClusterLink();

		if (clusterLink == null) {
			return null;
		}

		return clusterLink.getBindInetAddress();
	}

	public static ClusterLink getClusterLink() {
		PortalRuntimePermission.checkGetBeanProperty(ClusterLinkUtil.class);

		if ((_clusterLink == null) || !_clusterLink.isEnabled()) {
			if (_log.isWarnEnabled()) {
				_log.warn("ClusterLinkUtil has not been initialized");
			}

			return null;
		}

		return _clusterLink;
	}

	public static List<Address> getLocalTransportAddresses() {
		ClusterLink clusterLink = getClusterLink();

		if (clusterLink == null) {
			return Collections.emptyList();
		}

		return clusterLink.getLocalTransportAddresses();
	}

	public static List<Address> getTransportAddresses(Priority priority) {
		ClusterLink clusterLink = getClusterLink();

		if (clusterLink == null) {
			return Collections.emptyList();
		}

		return clusterLink.getTransportAddresses(priority);
	}

	public static boolean isForwardMessage(Message message) {
		return message.getBoolean(ClusterLink.CLUSTER_FORWARD_MESSAGE);
	}

	public static void sendMulticastMessage(
		Message message, Priority priority) {

		ClusterLink clusterLink = getClusterLink();

		if (clusterLink == null) {
			return;
		}

		clusterLink.sendMulticastMessage(message, priority);
	}

	public static void sendMulticastMessage(Object payload, Priority priority) {
		Message message = new Message();

		message.setPayload(payload);

		sendMulticastMessage(message, priority);
	}

	public static void sendUnicastMessage(
		Address address, Message message, Priority priority) {

		ClusterLink clusterLink = getClusterLink();

		if (clusterLink == null) {
			return;
		}

		clusterLink.sendUnicastMessage(address, message, priority);
	}

	public static Message setAddress(Message message, Address address) {
		message.put(_ADDRESS, address);

		return message;
	}

	public static void setForwardMessage(Message message) {
		message.put(ClusterLink.CLUSTER_FORWARD_MESSAGE, true);
	}

	public void setClusterLink(ClusterLink clusterLink) {
		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_clusterLink = clusterLink;
	}

	private static final String _ADDRESS = "CLUSTER_ADDRESS";

	private static Log _log = LogFactoryUtil.getLog(ClusterLinkUtil.class);

	private static ClusterLink _clusterLink;

}