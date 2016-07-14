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

package com.liferay.portal.kernel.cluster;

import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portal.kernel.util.ProxyFactory;

/**
 * @author Shuyang Zhou
 * @author Raymond Augé
 */
public class ClusterLinkUtil {

	public static Address getAddress(Message message) {
		return (Address)message.get(_ADDRESS);
	}

	/**
	 * @deprecated As of 7.1.0, replaced by {@link #_getClusterLink()}
	 */
	@Deprecated
	public static ClusterLink getClusterLink() {
		return _getClusterLink();
	}

	public static void sendMulticastMessage(
		Message message, Priority priority) {

		_getClusterLink().sendMulticastMessage(message, priority);
	}

	public static void sendMulticastMessage(Object payload, Priority priority) {
		Message message = new Message();

		message.setPayload(payload);

		sendMulticastMessage(message, priority);
	}

	public static void sendUnicastMessage(
		Address address, Message message, Priority priority) {

		_getClusterLink().sendUnicastMessage(address, message, priority);
	}

	public static Message setAddress(Message message, Address address) {
		message.put(_ADDRESS, address);

		return message;
	}

	private static ClusterLink _getClusterLink() {
		PortalRuntimePermission.checkGetBeanProperty(ClusterLinkUtil.class);

		return _clusterLink;
	}

	private static final String _ADDRESS = "CLUSTER_ADDRESS";

	private static volatile ClusterLink _clusterLink =
		ProxyFactory.newServiceTrackedInstance(
			ClusterLink.class, ClusterLinkUtil.class, "_clusterLink");

}