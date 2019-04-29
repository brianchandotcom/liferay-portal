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

package com.liferay.portal.internal.cluster;

import com.liferay.portal.kernel.cluster.ClusterEvent;
import com.liferay.portal.kernel.cluster.ClusterEventListener;
import com.liferay.portal.kernel.cluster.ClusterEventType;
import com.liferay.portal.kernel.cluster.ClusterInvokeThreadLocal;
import com.liferay.portal.kernel.cluster.ClusterNode;
import com.liferay.portal.kernel.json.JSONObject;
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

			JSONObject jsonObject = JSONUtil.put(
				"clusterNodeId", clusterNode.getClusterNodeId()
			).put(
				"command", command
			);

			message.setPayload(jsonObject.toString());

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