/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.cluster;

import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.module.service.Snapshot;

/**
 * @author Shuyang Zhou
 * @author Raymond Augé
 */
public class ClusterLinkUtil {

	public static Address getAddress(Message message) {
		return (Address)message.get(_ADDRESS);
	}

	public static void sendMulticastMessage(
		Message message, Priority priority) {

		ClusterLink clusterLink = _clusterLinkSnapshot.get();

		clusterLink.sendMulticastMessage(message, priority);
	}

	public static void sendMulticastMessage(Object payload, Priority priority) {
		Message message = new Message();

		message.setPayload(payload);

		sendMulticastMessage(message, priority);
	}

	public static void sendUnicastMessage(
		Address address, Message message, Priority priority) {

		ClusterLink clusterLink = _clusterLinkSnapshot.get();

		clusterLink.sendUnicastMessage(address, message, priority);
	}

	public static Message setAddress(Message message, Address address) {
		message.put(_ADDRESS, address);

		return message;
	}

	private static final String _ADDRESS = "CLUSTER_ADDRESS";

	private static final Snapshot<ClusterLink> _clusterLinkSnapshot =
		new Snapshot<>(ClusterLinkUtil.class, ClusterLink.class);

}