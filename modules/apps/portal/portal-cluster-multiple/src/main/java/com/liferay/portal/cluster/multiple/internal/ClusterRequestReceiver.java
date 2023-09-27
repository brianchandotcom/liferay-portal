/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.cluster.multiple.internal;

import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.portal.kernel.cache.thread.local.Lifecycle;
import com.liferay.portal.kernel.cache.thread.local.ThreadLocalCacheManager;
import com.liferay.portal.kernel.cluster.Address;
import com.liferay.portal.kernel.cluster.ClusterEvent;
import com.liferay.portal.kernel.cluster.ClusterEventType;
import com.liferay.portal.kernel.cluster.ClusterNodeResponse;
import com.liferay.portal.kernel.cluster.ClusterRequest;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

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
	protected void doCoordinatorAddressUpdated(
		Address oldCoordinatorAddress, Address newCoordinatorAddress) {

		if (oldCoordinatorAddress.equals(newCoordinatorAddress)) {
			return;
		}

		_clusterExecutorImpl.fireClusterEvent(
			new ClusterEvent(ClusterEventType.COORDINATOR_ADDRESS_UPDATE));
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
				ClusterRequest clusterRequest = (ClusterRequest)messagePayload;

				Serializable responsePayload =
					_clusterExecutorImpl.handleReceivedClusterRequest(
						clusterRequest);

				if (clusterRequest.isFireAndForget()) {
					return;
				}

				try {
					clusterChannel.sendUnicastMessage(
						responsePayload, srcAddress);
				}
				catch (Throwable throwable) {
					_log.error(
						"Unable to send message " + responsePayload, throwable);
				}
			}
			else if (messagePayload instanceof ClusterNodeResponse) {
				_clusterExecutorImpl.handleReceivedClusterNodeResponse(
					(ClusterNodeResponse)messagePayload);
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

	private static final Log _log = LogFactoryUtil.getLog(
		ClusterRequestReceiver.class);

	private final ClusterExecutorImpl _clusterExecutorImpl;

}