/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.internal.cluster;

import com.liferay.portal.kernel.cluster.ClusterEvent;
import com.liferay.portal.kernel.cluster.ClusterEventListener;
import com.liferay.portal.kernel.cluster.ClusterEventType;
import com.liferay.portal.kernel.cluster.ClusterInvokeThreadLocal;
import com.liferay.portal.kernel.cluster.ClusterNode;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;

import java.util.List;

/**
 * @author Amos Fong
 */
public class LiveUsersClusterEventListenerImpl implements ClusterEventListener {

	@Override
	public void processClusterEvent(ClusterEvent clusterEvent) {
		List<ClusterNode> clusterNodes = clusterEvent.getClusterNodes();

		if (clusterNodes.isEmpty()) {
			return;
		}

		ClusterEventType clusterEventType = clusterEvent.getClusterEventType();

		String command = null;

		if (clusterEventType == ClusterEventType.DEPART) {
			command = "removeClusterNode";
		}
		else if (clusterEventType == ClusterEventType.JOIN) {
			command = "addClusterNode";
		}
		else {
			throw new IllegalArgumentException(
				"Unknown cluster event type " + clusterEventType);
		}

		for (ClusterNode clusterNode : clusterNodes) {
			Message message = new Message();

			message.setPayload(
				JSONUtil.put(
					"clusterNodeId", clusterNode.getClusterNodeId()
				).put(
					"command", command
				).toString());

			ClusterInvokeThreadLocal.setEnabled(false);

			try {
				MessageBusUtil.sendMessage(
					DestinationNames.LIVE_USERS, message);
			}
			finally {
				ClusterInvokeThreadLocal.setEnabled(true);
			}
		}
	}

}