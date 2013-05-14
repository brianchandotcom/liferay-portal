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

package com.liferay.portal.cluster;

import com.liferay.portal.kernel.cluster.ClusterEvent;
import com.liferay.portal.kernel.cluster.ClusterEventListener;
import com.liferay.portal.kernel.cluster.ClusterEventType;
import com.liferay.portal.kernel.cluster.ClusterLink;
import com.liferay.portal.kernel.cluster.ClusterNode;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
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

		ClusterEventType clusterEventType = clusterEvent.getClusterEventType();

		if (clusterEventType.equals(ClusterEventType.DEPART)) {
			for (ClusterNode clusterNode : clusterNodes) {
				_processDepartEvent(clusterNode);
			}
		}
		else if (clusterEventType.equals(ClusterEventType.JOIN)) {
			for (ClusterNode clusterNode : clusterNodes) {
				_processJoinEvent(clusterNode);
			}
		}
	}

	private void _processDepartEvent(ClusterNode clusterNode) {
		Message message = new Message();

		message.put(ClusterLink.CLUSTER_FORWARD_MESSAGE, true);

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("clusterNodeId", clusterNode.getClusterNodeId());
		jsonObject.put("command", "removeClusterNode");

		message.setPayload(jsonObject.toString());

		MessageBusUtil.sendMessage(DestinationNames.LIVE_USERS, message);
	}

	private void _processJoinEvent(ClusterNode clusterNode) {
		Message message = new Message();

		message.put(ClusterLink.CLUSTER_FORWARD_MESSAGE, true);

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("clusterNodeId", clusterNode.getClusterNodeId());
		jsonObject.put("command", "addClusterNode");

		message.setPayload(jsonObject.toString());

		MessageBusUtil.sendMessage(DestinationNames.LIVE_USERS, message);
	}

}