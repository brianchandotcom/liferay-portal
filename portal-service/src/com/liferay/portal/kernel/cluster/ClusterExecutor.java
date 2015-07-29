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

import java.net.InetAddress;
import java.net.NetworkInterface;

import java.util.List;

/**
 * @author Shuyang Zhou
 */
public interface ClusterExecutor {

	public void addClusterEventListener(
		ClusterEventListener clusterEventListener);

	public FutureClusterResponses execute(ClusterRequest clusterRequest);

	public InetAddress getBindInetAddress();

	public NetworkInterface getBindNetworkInterface();

	public List<ClusterEventListener> getClusterEventListeners();

	public List<ClusterNode> getClusterNodes();

	public ClusterNode getLocalClusterNode();

	public boolean isClusterNodeAlive(String clusterNodeId);

	public boolean isEnabled();

	public void removeClusterEventListener(
		ClusterEventListener clusterEventListener);

}