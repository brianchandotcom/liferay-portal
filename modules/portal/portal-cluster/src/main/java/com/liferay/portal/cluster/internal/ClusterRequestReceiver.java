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

package com.liferay.portal.cluster.internal;

import com.liferay.portal.cluster.BaseClusterReceiver;
import com.liferay.portal.cluster.ClusterChannel;
import com.liferay.portal.kernel.cache.thread.local.Lifecycle;
import com.liferay.portal.kernel.cache.thread.local.ThreadLocalCacheManager;
import com.liferay.portal.kernel.cluster.Address;
import com.liferay.portal.kernel.cluster.ClusterEvent;
import com.liferay.portal.kernel.cluster.ClusterEventType;
import com.liferay.portal.kernel.cluster.ClusterNode;
import com.liferay.portal.kernel.cluster.ClusterNodeResponse;
import com.liferay.portal.kernel.cluster.ClusterRequest;
import com.liferay.portal.kernel.cluster.FutureClusterResponses;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.CentralizedThreadLocal;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Michael C. Han
 * @author Tina Tian
 */
public class ClusterRequestReceiver extends BaseClusterReceiver {

	public ClusterRequestReceiver(ClusterExecutorImpl clusterExecutorImpl) {
		super(clusterExecutorImpl.getExecutorService());

		_clusterExecutorImpl = clusterExecutorImpl;
	}

	@Override
	protected void doAddressesUpdated(
		List<Address> oldAddresses, List<Address> newAddresses) {

		List<Address> addedAddresses = new ArrayList<>(newAddresses);

		addedAddresses.removeAll(oldAddresses);

		if (!addedAddresses.isEmpty()) {
			_clusterExecutorImpl.sendNotifyRequest();
		}

		List<Address> removedAddresses = new ArrayList<>(oldAddresses);

		removedAddresses.removeAll(newAddresses);

		if (!removedAddresses.isEmpty()) {
			_clusterExecutorImpl.memberRemoved(removedAddresses);
		}
	}

	@Override
	protected void doCoordinatorUpdated(
		Address oldCoordinator, Address newCoordinator) {

		if (oldCoordinator.equals(newCoordinator)) {
			return;
		}

		_clusterExecutorImpl.fireClusterEvent(
			new ClusterEvent(ClusterEventType.COORDINATOR_UPDATE));
	}

	@Override
	protected void doReceive(Object messagePayload, Address srcAddress) {
		ClusterChannel clusterChannel =
			_clusterExecutorImpl.getClusterChannel();

		if (srcAddress.equals(clusterChannel.getLocalAddress())) {
			return;
		}

		try {
			if (messagePayload instanceof ClusterRequest) {
				processClusterRequest(
					(ClusterRequest)messagePayload, srcAddress);
			}
			else if (messagePayload instanceof ClusterNodeResponse) {
				processClusterResponse((ClusterNodeResponse)messagePayload);
			}
			else if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to process message content of type " +
						messagePayload.getClass());
			}
		}
		finally {
			ThreadLocalCacheManager.clearAll(Lifecycle.REQUEST);

			CentralizedThreadLocal.clearShortLivedThreadLocals();
		}
	}

	protected void processClusterRequest(
		ClusterRequest clusterRequest, Address sourceAddress) {

		Serializable responsePayload =
			_clusterExecutorImpl.handleReceivedClusterRequest(clusterRequest);

		if (responsePayload == null) {
			return;
		}

		ClusterChannel clusterChannel =
			_clusterExecutorImpl.getClusterChannel();

		try {
			clusterChannel.sendUnicastMessage(responsePayload, sourceAddress);
		}
		catch (Throwable t) {
			_log.error("Unable to send message " + responsePayload, t);
		}
	}

	protected void processClusterResponse(
		ClusterNodeResponse clusterNodeResponse) {

		String uuid = clusterNodeResponse.getUuid();

		FutureClusterResponses futureClusterResponses =
			_clusterExecutorImpl.getFutureClusterResponses(uuid);

		if (futureClusterResponses == null) {
			if (_log.isInfoEnabled()) {
				_log.info("Unable to find response container for " + uuid);
			}

			return;
		}

		if (!futureClusterResponses.addClusterNodeResponse(
				clusterNodeResponse) &&
			_log.isWarnEnabled()) {

			ClusterNode clusterNode = clusterNodeResponse.getClusterNode();

			_log.warn(
				"Unexpected cluster node ID " + clusterNode.getClusterNodeId() +
					" for response container with UUID " + uuid);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ClusterRequestReceiver.class);

	private final ClusterExecutorImpl _clusterExecutorImpl;

}