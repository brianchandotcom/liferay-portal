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

package com.liferay.portal.cluster.single.internal;

import com.liferay.portal.kernel.cluster.ClusterEventListener;
import com.liferay.portal.kernel.cluster.ClusterExecutor;
import com.liferay.portal.kernel.cluster.ClusterNode;
import com.liferay.portal.kernel.cluster.ClusterRequest;
import com.liferay.portal.kernel.cluster.FutureClusterResponses;

import java.net.InetAddress;
import java.net.NetworkInterface;

import java.util.Collections;
import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Tina Tian
 */
@Component(immediate = true, service = ClusterExecutor.class)
public class SingleClusterExecutor implements ClusterExecutor {

	@Override
	public void addClusterEventListener(
		ClusterEventListener clusterEventListener) {
	}

	@Override
	public FutureClusterResponses execute(ClusterRequest clusterRequest) {
		return null;
	}

	@Override
	public InetAddress getBindInetAddress() {
		return null;
	}

	@Override
	public NetworkInterface getBindNetworkInterface() {
		return null;
	}

	@Override
	public List<ClusterEventListener> getClusterEventListeners() {
		return Collections.emptyList();
	}

	@Override
	public List<ClusterNode> getClusterNodes() {
		return Collections.emptyList();
	}

	@Override
	public ClusterNode getLocalClusterNode() {
		return null;
	}

	@Override
	public boolean isClusterNodeAlive(String clusterNodeId) {
		return false;
	}

	@Override
	public boolean isEnabled() {
		return false;
	}

	@Override
	public void removeClusterEventListener(
		ClusterEventListener clusterEventListener) {
	}

}