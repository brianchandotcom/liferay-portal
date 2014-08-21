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
import java.util.concurrent.CountDownLatch;

import org.jgroups.Channel;
import org.jgroups.Message;
import org.jgroups.View;

/**
 * @author Michael C. Han
 * @author Tina Tian
 */
public class ClusterRequestReceiver extends BaseReceiver {

	public ClusterRequestReceiver(ClusterExecutorImpl clusterExecutorImpl) {
		_countDownLatch = new CountDownLatch(1);
		_clusterExecutorImpl = clusterExecutorImpl;
	}

	public void openLatch() {
		_countDownLatch.countDown();
	}

	@Override
	public void receive(Message message) {
		try {
			_countDownLatch.await();
		}
		catch (InterruptedException ie) {
			_log.error(
				"Latch opened prematurely by interruption. Dependence may " +
					"not be ready.");
		}

		Object obj = message.getObject();

		if (obj == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("Message content is null");
			}

			return;
		}

		Address sourceAddress = new AddressImpl(message.getSrc());

		if (sourceAddress.equals(
				_clusterExecutorImpl.getLocalClusterNodeAddress())) {

			boolean isProcessed = processLocalMessage(obj);

			if (isProcessed) {
				return;
			}
		}

		try {
			if (obj instanceof ClusterRequest) {
				ClusterRequest clusterRequest = (ClusterRequest)obj;

				processClusterRequest(clusterRequest, sourceAddress);
			}
			else if (obj instanceof ClusterNodeResponse) {
				ClusterNodeResponse clusterNodeResponse =
					(ClusterNodeResponse)obj;

				processClusterResponse(clusterNodeResponse, sourceAddress);
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
	public void viewAccepted(View view) {
		super.viewAccepted(view);

		if (_lastView == null) {
			_lastView = view;

			return;
		}

		List<Address> departAddresses = getDepartAddresses(view);
		List<Address> newAddresses = getNewAddresses(view);

		_lastView = view;

		try {
			_countDownLatch.await();
		}
		catch (InterruptedException ie) {
			_log.error(
				"Latch opened prematurely by interruption. Dependence may " +
					"not be ready.");
		}

		if (!newAddresses.isEmpty()) {
			_clusterExecutorImpl.sendNotifyRequest();
		}

		if (!departAddresses.isEmpty()) {
			_clusterExecutorImpl.memberRemoved(departAddresses);
		}
	}

	protected List<Address> getDepartAddresses(View view) {
		List<org.jgroups.Address> currentJGroupsAddresses = view.getMembers();
		List<org.jgroups.Address> lastJGroupsAddresses = _lastView.getMembers();

		List<org.jgroups.Address> departJGroupsAddresses =
			new ArrayList<org.jgroups.Address>(lastJGroupsAddresses);

		departJGroupsAddresses.removeAll(currentJGroupsAddresses);

		if (departJGroupsAddresses.isEmpty()) {
			return Collections.emptyList();
		}

		List<Address> departAddresses = new ArrayList<Address>(
			departJGroupsAddresses.size());

		for (org.jgroups.Address departJGroupsAddress :
				departJGroupsAddresses) {

			Address departAddress = new AddressImpl(departJGroupsAddress);

			departAddresses.add(departAddress);
		}

		return departAddresses;
	}

	protected List<Address> getNewAddresses(View view) {
		List<org.jgroups.Address> currentJGroupsAddresses = view.getMembers();
		List<org.jgroups.Address> lastJGroupsAddresses = _lastView.getMembers();

		List<org.jgroups.Address> newJGroupsAddresses =
			new ArrayList<org.jgroups.Address>(currentJGroupsAddresses);

		newJGroupsAddresses.removeAll(lastJGroupsAddresses);

		if (newJGroupsAddresses.isEmpty()) {
			return Collections.emptyList();
		}

		List<Address> newAddresses = new ArrayList<Address>(
			newJGroupsAddresses.size());

		for (org.jgroups.Address newJGroupsAddress : newJGroupsAddresses) {
			Address newAddress = new AddressImpl(newJGroupsAddress);

			newAddresses.add(newAddress);
		}

		return newAddresses;
	}

	protected void handleResponse(
		Address address, ClusterRequest clusterRequest, Object returnValue,
		Exception exception) {

		ClusterNodeResponse clusterNodeResponse =
			_clusterExecutorImpl.generateClusterNodeResponse(
				clusterRequest, returnValue, exception);

		Channel channel = _clusterExecutorImpl.getControlChannel();

		try {
			channel.send(
				(org.jgroups.Address)address.getRealAddress(),
				clusterNodeResponse);
		}
		catch (Exception e) {
			_log.error(
				"Unable to send response message " + clusterNodeResponse, e);
		}
		catch (Throwable t) {
			_log.error(t, t);
		}
	}

	protected void processClusterRequest(
		ClusterRequest clusterRequest, Address sourceAddress) {

		ClusterMessageType clusterMessageType =
			clusterRequest.getClusterMessageType();

		if (clusterMessageType.equals(ClusterMessageType.NOTIFY) ||
			clusterMessageType.equals(ClusterMessageType.UPDATE)) {

			_clusterExecutorImpl.memberJoined(
				sourceAddress, clusterRequest.getOriginatingClusterNode());

			if (clusterMessageType.equals(ClusterMessageType.NOTIFY)) {
				handleResponse(sourceAddress, clusterRequest, null, null);
			}

			return;
		}

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
			handleResponse(
				sourceAddress, clusterRequest, returnValue, exception);
		}
	}

	protected void processClusterResponse(
		ClusterNodeResponse clusterNodeResponse, Address sourceAddress) {

		ClusterMessageType clusterMessageType =
			clusterNodeResponse.getClusterMessageType();

		if (clusterMessageType.equals(ClusterMessageType.NOTIFY)) {
			_clusterExecutorImpl.memberJoined(
				sourceAddress, clusterNodeResponse.getClusterNode());

			return;
		}

		String uuid = clusterNodeResponse.getUuid();

		FutureClusterResponses futureClusterResponses =
			_clusterExecutorImpl.getExecutionResults(uuid);

		if (futureClusterResponses == null) {
			if (_log.isInfoEnabled()) {
				_log.info("Unable to find response container for " + uuid);
			}

			return;
		}

		if (futureClusterResponses.expectsReply(sourceAddress)) {
			futureClusterResponses.addClusterNodeResponse(clusterNodeResponse);
		}
		else {
			if (_log.isWarnEnabled()) {
				_log.warn("Unknown uuid " + uuid + " from " + sourceAddress);
			}
		}
	}

	protected boolean processLocalMessage(Object message) {
		if (message instanceof ClusterRequest) {
			ClusterRequest clusterRequest = (ClusterRequest)message;

			if (clusterRequest.isSkipLocal()) {
				return true;
			}
		}

		if (_clusterExecutorImpl.isShortcutLocalMethod()) {
			return true;
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ClusterRequestReceiver.class);

	private final ClusterExecutorImpl _clusterExecutorImpl;
	private final CountDownLatch _countDownLatch;
	private volatile View _lastView;

}