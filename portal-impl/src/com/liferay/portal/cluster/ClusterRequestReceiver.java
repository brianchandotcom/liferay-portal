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

package com.liferay.portal.cluster;

import com.liferay.portal.kernel.cache.Lifecycle;
import com.liferay.portal.kernel.cache.ThreadLocalCacheManager;
import com.liferay.portal.kernel.cluster.Address;
import com.liferay.portal.kernel.cluster.ClusterException;
import com.liferay.portal.kernel.cluster.ClusterInvokeThreadLocal;
import com.liferay.portal.kernel.cluster.ClusterMessageType;
import com.liferay.portal.kernel.cluster.ClusterNode;
import com.liferay.portal.kernel.cluster.ClusterNodeResponse;
import com.liferay.portal.kernel.cluster.ClusterRequest;
import com.liferay.portal.kernel.cluster.FutureClusterResponses;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.CentralizedThreadLocal;
import com.liferay.portal.kernel.util.MethodHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jgroups.Channel;
import org.jgroups.Message;
import org.jgroups.View;

/**
 * @author Michael C. Han
 * @author Tina Tian
 */
public class ClusterRequestReceiver extends BaseReceiver {

	public ClusterRequestReceiver(ClusterExecutorImpl clusterExecutorImpl) {
		_clusterExecutorImpl = clusterExecutorImpl;
	}

	@Override
	protected void doReceive(Message message) {
		Object obj = message.getObject();

		if (obj == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("Message content is null");
			}

			return;
		}

		org.jgroups.Address sourceJGroupsAddress = message.getSrc();

		Channel channel = _clusterExecutorImpl.getControlChannel();

		if (sourceJGroupsAddress.equals(channel.getAddress())) {
			return;
		}

		try {
			if (obj instanceof ClusterRequest) {
				ClusterRequest clusterRequest = (ClusterRequest)obj;

				processClusterRequest(
					clusterRequest, new AddressImpl(sourceJGroupsAddress));
			}
			else if (obj instanceof ClusterNodeResponse) {
				ClusterNodeResponse clusterNodeResponse =
					(ClusterNodeResponse)obj;

				processClusterResponse(
					clusterNodeResponse, new AddressImpl(sourceJGroupsAddress));
			}
			else if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to process message content of type " +
						obj.getClass());
			}
		}
		finally {
			ThreadLocalCacheManager.clearAll(Lifecycle.REQUEST);

			CentralizedThreadLocal.clearShortLivedThreadLocals();
		}
	}

	@Override
	protected void doViewAccepted(View oldView, View newView) {
		List<Address> departAddresses = getDepartAddresses(oldView, newView);
		List<Address> newAddresses = getNewAddresses(oldView, newView);

		if (!newAddresses.isEmpty()) {
			_clusterExecutorImpl.sendNotifyRequest();
		}

		if (!departAddresses.isEmpty()) {
			_clusterExecutorImpl.memberRemoved(departAddresses);
		}
	}

	protected List<Address> getDepartAddresses(View oldView, View newView) {
		List<org.jgroups.Address> currentJGroupsAddresses =
			newView.getMembers();
		List<org.jgroups.Address> lastJGroupsAddresses = oldView.getMembers();

		List<org.jgroups.Address> departJGroupsAddresses = new ArrayList<>(
			lastJGroupsAddresses);

		departJGroupsAddresses.removeAll(currentJGroupsAddresses);

		if (departJGroupsAddresses.isEmpty()) {
			return Collections.emptyList();
		}

		List<Address> departAddresses = new ArrayList<>(
			departJGroupsAddresses.size());

		for (org.jgroups.Address departJGroupsAddress :
				departJGroupsAddresses) {

			Address departAddress = new AddressImpl(departJGroupsAddress);

			departAddresses.add(departAddress);
		}

		return departAddresses;
	}

	protected List<Address> getNewAddresses(View oldView, View newView) {
		List<org.jgroups.Address> currentJGroupsAddresses =
			newView.getMembers();
		List<org.jgroups.Address> lastJGroupsAddresses = oldView.getMembers();

		List<org.jgroups.Address> newJGroupsAddresses = new ArrayList<>(
			currentJGroupsAddresses);

		newJGroupsAddresses.removeAll(lastJGroupsAddresses);

		if (newJGroupsAddresses.isEmpty()) {
			return Collections.emptyList();
		}

		List<Address> newAddresses = new ArrayList<>(
			newJGroupsAddresses.size());

		for (org.jgroups.Address newJGroupsAddress : newJGroupsAddresses) {
			Address newAddress = new AddressImpl(newJGroupsAddress);

			newAddresses.add(newAddress);
		}

		return newAddresses;
	}

	protected void processClusterRequest(
		ClusterRequest clusterRequest, Address sourceAddress) {

		Object responsePayload = null;

		ClusterMessageType clusterMessageType =
			clusterRequest.getClusterMessageType();

		if (clusterMessageType.equals(ClusterMessageType.NOTIFY) ||
			clusterMessageType.equals(ClusterMessageType.UPDATE)) {

			_clusterExecutorImpl.memberJoined(
				sourceAddress, clusterRequest.getOriginatingClusterNode());

			if (clusterMessageType.equals(ClusterMessageType.NOTIFY)) {
				responsePayload = ClusterRequest.createClusterRequest(
					ClusterMessageType.UPDATE,
					_clusterExecutorImpl.getLocalClusterNode());
			}
		}
		else {
			MethodHandler methodHandler = clusterRequest.getMethodHandler();

			Object returnValue = null;
			Exception exception = null;

			if (methodHandler != null) {
				try {
					ClusterInvokeThreadLocal.setEnabled(false);

					returnValue = methodHandler.invoke();
				}
				catch (Exception e) {
					exception = e;

					_log.error("Unable to invoke method " + methodHandler, e);
				}
				finally {
					ClusterInvokeThreadLocal.setEnabled(true);
				}
			}
			else {
				exception = new ClusterException(
					"Payload is not of type " + MethodHandler.class.getName());
			}

			if (!clusterRequest.isFireAndForget()) {
				responsePayload =
					_clusterExecutorImpl.generateClusterNodeResponse(
						clusterRequest, returnValue, exception);
			}
		}

		if (responsePayload == null) {
			return;
		}

		Channel channel = _clusterExecutorImpl.getControlChannel();

		try {
			channel.send(
				(org.jgroups.Address)sourceAddress.getRealAddress(),
				responsePayload);
		}
		catch (Throwable t) {
			_log.error("Unable to send message " + responsePayload, t);
		}
	}

	protected void processClusterResponse(
		ClusterNodeResponse clusterNodeResponse, Address sourceAddress) {

		String uuid = clusterNodeResponse.getUuid();

		FutureClusterResponses futureClusterResponses =
			_clusterExecutorImpl.getExecutionResults(uuid);

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