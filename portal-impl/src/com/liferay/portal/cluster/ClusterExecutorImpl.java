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

import com.liferay.portal.kernel.cluster.Address;
import com.liferay.portal.kernel.cluster.ClusterEvent;
import com.liferay.portal.kernel.cluster.ClusterEventListener;
import com.liferay.portal.kernel.cluster.ClusterException;
import com.liferay.portal.kernel.cluster.ClusterExecutor;
import com.liferay.portal.kernel.cluster.ClusterMessageType;
import com.liferay.portal.kernel.cluster.ClusterNode;
import com.liferay.portal.kernel.cluster.ClusterNodeResponse;
import com.liferay.portal.kernel.cluster.ClusterRequest;
import com.liferay.portal.kernel.cluster.ClusterResponseCallback;
import com.liferay.portal.kernel.cluster.FutureClusterResponses;
import com.liferay.portal.kernel.concurrent.ConcurrentReferenceValueHashMap;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.executor.PortalExecutorManagerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.memory.FinalizeManager;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.util.PortalInetSocketAddressEventListener;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;

import java.io.Serializable;

import java.net.InetAddress;
import java.net.InetSocketAddress;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;

import org.jgroups.JChannel;

/**
 * @author Tina Tian
 * @author Shuyang Zhou
 */
@DoPrivileged
public class ClusterExecutorImpl
	extends ClusterBase
	implements ClusterExecutor, PortalInetSocketAddressEventListener {

	public static final String CLUSTER_EXECUTOR_CALLBACK_THREAD_POOL =
		"CLUSTER_EXECUTOR_CALLBACK_THREAD_POOL";

	@Override
	public void addClusterEventListener(
		ClusterEventListener clusterEventListener) {

		if (!isEnabled()) {
			return;
		}

		_clusterEventListeners.addIfAbsent(clusterEventListener);
	}

	@Override
	public void afterPropertiesSet() {
		if (PropsValues.CLUSTER_EXECUTOR_DEBUG_ENABLED) {
			addClusterEventListener(new DebuggingClusterEventListenerImpl());
		}

		if (PropsValues.LIVE_USERS_ENABLED) {
			addClusterEventListener(new LiveUsersClusterEventListenerImpl());
		}

		_secure = StringUtil.equalsIgnoreCase(
			Http.HTTPS, PropsValues.WEB_SERVER_PROTOCOL);

		super.afterPropertiesSet();
	}

	@Override
	public void destroy() {
		if (!isEnabled()) {
			return;
		}

		PortalExecutorManagerUtil.shutdown(
			CLUSTER_EXECUTOR_CALLBACK_THREAD_POOL, true);

		_controlJChannel.setReceiver(null);

		_controlJChannel.close();

		_clusterEventListeners.clear();
		_clusterNodeAddresses.clear();
		_futureClusterResponses.clear();
		_liveInstances.clear();
		_localAddress = null;
		_localClusterNode = null;
	}

	@Override
	public FutureClusterResponses execute(ClusterRequest clusterRequest) {
		if (!isEnabled()) {
			return null;
		}

		List<Address> addresses = prepareAddresses(clusterRequest);

		FutureClusterResponses futureClusterResponses =
			new FutureClusterResponses(addresses);

		if (!clusterRequest.isFireAndForget()) {
			String uuid = clusterRequest.getUuid();

			_futureClusterResponses.put(uuid, futureClusterResponses);
		}

		if (_shortcutLocalMethod &&
			addresses.remove(getLocalClusterNodeAddress())) {

			runLocalMethod(clusterRequest, futureClusterResponses);
		}

		if (clusterRequest.isMulticast()) {
			try {
				_controlJChannel.send(null, clusterRequest);
			}
			catch (Exception e) {
				throw new SystemException(
					"Unable to send multicast request", e);
			}
		}
		else {
			for (Address address : addresses) {
				org.jgroups.Address jGroupsAddress =
					(org.jgroups.Address)address.getRealAddress();

				try {
					_controlJChannel.send(jGroupsAddress, clusterRequest);
				}
				catch (Exception e) {
					throw new SystemException(
						"Unable to send unicast request", e);
				}
			}
		}

		return futureClusterResponses;
	}

	@Override
	public FutureClusterResponses execute(
		ClusterRequest clusterRequest,
		ClusterResponseCallback clusterResponseCallback) {

		FutureClusterResponses futureClusterResponses = execute(clusterRequest);

		ClusterResponseCallbackJob clusterResponseCallbackJob =
			new ClusterResponseCallbackJob(
				clusterResponseCallback, futureClusterResponses);

		_executorService.execute(clusterResponseCallbackJob);

		return futureClusterResponses;
	}

	@Override
	public List<ClusterEventListener> getClusterEventListeners() {
		if (!isEnabled()) {
			return Collections.emptyList();
		}

		return Collections.unmodifiableList(_clusterEventListeners);
	}

	@Override
	public List<Address> getClusterNodeAddresses() {
		if (!isEnabled()) {
			return Collections.emptyList();
		}

		return getAddresses(_controlJChannel);
	}

	@Override
	public List<ClusterNode> getClusterNodes() {
		if (!isEnabled()) {
			return Collections.emptyList();
		}

		return new ArrayList<ClusterNode>(_liveInstances.values());
	}

	@Override
	public ClusterNode getLocalClusterNode() {
		if (!isEnabled()) {
			return null;
		}

		return _localClusterNode;
	}

	@Override
	public Address getLocalClusterNodeAddress() {
		if (!isEnabled()) {
			return null;
		}

		return _localAddress;
	}

	@Override
	public void initialize() {
		if (!isEnabled()) {
			return;
		}

		_executorService = PortalExecutorManagerUtil.getPortalExecutor(
			CLUSTER_EXECUTOR_CALLBACK_THREAD_POOL);

		PortalUtil.addPortalInetSocketAddressEventListener(this);

		_localAddress = new AddressImpl(_controlJChannel.getAddress());

		initLocalClusterNode();

		memberJoined(_localAddress, _localClusterNode);

		sendNotifyRequest();

		ClusterRequestReceiver clusterRequestReceiver =
			(ClusterRequestReceiver)_controlJChannel.getReceiver();

		clusterRequestReceiver.openLatch();
	}

	@Override
	public boolean isClusterNodeAlive(Address address) {
		if (!isEnabled()) {
			return false;
		}

		List<Address> addresses = getAddresses(_controlJChannel);

		return addresses.contains(address);
	}

	@Override
	public boolean isClusterNodeAlive(String clusterNodeId) {
		if (!isEnabled()) {
			return false;
		}

		return _clusterNodeAddresses.containsKey(clusterNodeId);
	}

	@Override
	public void portalLocalInetSockAddressConfigured(
		InetSocketAddress inetSocketAddress) {

		if (!isEnabled() ||
			(_localClusterNode.getPortalInetSocketAddress() != null)) {

			return;
		}

		try {
			_localClusterNode.setPortalInetSocketAddress(inetSocketAddress);

			memberJoined(_localAddress, _localClusterNode);

			ClusterRequest clusterRequest = ClusterRequest.createClusterRequest(
				ClusterMessageType.UPDATE, _localClusterNode);

			_controlJChannel.send(null, clusterRequest);
		}
		catch (Exception e) {
			_log.error("Unable to determine configure node port", e);
		}
	}

	@Override
	public void portalServerInetSocketAddressConfigured(
		InetSocketAddress inetSocketAddress) {
	}

	@Override
	public void removeClusterEventListener(
		ClusterEventListener clusterEventListener) {

		if (!isEnabled()) {
			return;
		}

		_clusterEventListeners.remove(clusterEventListener);
	}

	public void setClusterEventListeners(
		List<ClusterEventListener> clusterEventListeners) {

		if (!isEnabled()) {
			return;
		}

		_clusterEventListeners.addAllAbsent(clusterEventListeners);
	}

	public void setShortcutLocalMethod(boolean shortcutLocalMethod) {
		if (!isEnabled()) {
			return;
		}

		_shortcutLocalMethod = shortcutLocalMethod;
	}

	protected void fireClusterEvent(ClusterEvent clusterEvent) {
		for (ClusterEventListener listener : _clusterEventListeners) {
			listener.processClusterEvent(clusterEvent);
		}
	}

	protected ClusterNodeResponse generateClusterNodeResponse(
		ClusterRequest clusterRequest, Object returnValue,
		Exception exception) {

		ClusterNodeResponse clusterNodeResponse = new ClusterNodeResponse();

		clusterNodeResponse.setAddress(getLocalClusterNodeAddress());
		clusterNodeResponse.setClusterNode(getLocalClusterNode());
		clusterNodeResponse.setClusterMessageType(
			clusterRequest.getClusterMessageType());
		clusterNodeResponse.setMulticast(clusterRequest.isMulticast());
		clusterNodeResponse.setUuid(clusterRequest.getUuid());

		if (exception != null) {
			clusterNodeResponse.setException(exception);
		}
		else {
			if (returnValue instanceof Serializable) {
				clusterNodeResponse.setResult(returnValue);
			}
			else if (returnValue != null) {
				clusterNodeResponse.setException(
					new ClusterException("Return value is not serializable"));
			}
		}

		return clusterNodeResponse;
	}

	protected InetSocketAddress getConfiguredPortalInetSockAddress(
		boolean secure) {

		InetSocketAddress inetSocketAddress = null;

		String portalInetSocketAddressValue = null;

		if (secure) {
			portalInetSocketAddressValue =
				PropsValues.PORTAL_INSTANCE_HTTPS_INET_SOCKET_ADDRESS;
		}
		else {
			portalInetSocketAddressValue =
				PropsValues.PORTAL_INSTANCE_HTTP_INET_SOCKET_ADDRESS;
		}

		if (Validator.isNotNull(portalInetSocketAddressValue)) {
			String[] parts = StringUtil.split(
				portalInetSocketAddressValue, CharPool.COLON);

			if (parts.length == 2) {
				try {
					inetSocketAddress = new InetSocketAddress(
						InetAddress.getByName(parts[0]),
						GetterUtil.getIntegerStrict(parts[1]));
				}
				catch (Exception e) {
					_log.error(
						"Unable to parse portal InetSocketAddress from " +
							portalInetSocketAddressValue,
						e);
				}
			}
		}

		return inetSocketAddress;
	}

	protected JChannel getControlChannel() {
		return _controlJChannel;
	}

	protected FutureClusterResponses getExecutionResults(String uuid) {
		return _futureClusterResponses.get(uuid);
	}

	@Override
	protected void initChannels() throws Exception {
		Properties controlProperties = PropsUtil.getProperties(
			PropsKeys.CLUSTER_LINK_CHANNEL_PROPERTIES_CONTROL, false);

		String controlProperty = controlProperties.getProperty(
			PropsKeys.CLUSTER_LINK_CHANNEL_PROPERTIES_CONTROL);

		ClusterRequestReceiver clusterRequestReceiver =
			new ClusterRequestReceiver(this);

		_controlJChannel = createJChannel(
			controlProperty, clusterRequestReceiver, _DEFAULT_CLUSTER_NAME);
	}

	protected void initLocalClusterNode() {
		InetAddress inetAddress = getBindInetAddress(_controlJChannel);

		ClusterNode clusterNode = new ClusterNode(
			PortalUUIDUtil.generate(), inetAddress);

		InetSocketAddress inetSocketAddress =
			getConfiguredPortalInetSockAddress(_secure);

		if (inetSocketAddress != null) {
			clusterNode.setPortalInetSocketAddress(inetSocketAddress);
		}

		_localClusterNode = clusterNode;
	}

	protected boolean isShortcutLocalMethod() {
		return _shortcutLocalMethod;
	}

	protected void memberJoined(Address joinAddress, ClusterNode clusterNode) {
		_liveInstances.put(joinAddress, clusterNode);

		Address previousAddress = _clusterNodeAddresses.put(
			clusterNode.getClusterNodeId(), joinAddress);

		if ((previousAddress == null) && !_localAddress.equals(joinAddress)) {
			ClusterEvent clusterEvent = ClusterEvent.join(clusterNode);

			// PLACEHOLDER

			fireClusterEvent(clusterEvent);
		}
	}

	protected void memberRemoved(List<Address> departAddresses) {
		List<ClusterNode> departClusterNodes = new ArrayList<ClusterNode>();

		for (Address departAddress : departAddresses) {
			ClusterNode departClusterNode = _liveInstances.remove(
				departAddress);

			if (departClusterNode == null) {
				continue;
			}

			departClusterNodes.add(departClusterNode);

			_clusterNodeAddresses.remove(departClusterNode.getClusterNodeId());
		}

		if (departClusterNodes.isEmpty()) {
			return;
		}

		ClusterEvent clusterEvent = ClusterEvent.depart(departClusterNodes);

		fireClusterEvent(clusterEvent);
	}

	protected List<Address> prepareAddresses(ClusterRequest clusterRequest) {
		boolean isMulticast = clusterRequest.isMulticast();

		List<Address> addresses = null;

		if (isMulticast) {
			addresses = getAddresses(_controlJChannel);
		}
		else {
			addresses = new ArrayList<Address>();

			Collection<Address> clusterNodeAddresses =
				clusterRequest.getTargetClusterNodeAddresses();

			if (clusterNodeAddresses != null) {
				addresses.addAll(clusterNodeAddresses);
			}

			Collection<String> clusterNodeIds =
				clusterRequest.getTargetClusterNodeIds();

			if (clusterNodeIds != null) {
				for (String clusterNodeId : clusterNodeIds) {
					Address address = _clusterNodeAddresses.get(clusterNodeId);

					addresses.add(address);
				}
			}
		}

		if (clusterRequest.isSkipLocal()) {
			addresses.remove(getLocalClusterNodeAddress());
		}

		return addresses;
	}

	protected void runLocalMethod(
		ClusterRequest clusterRequest,
		FutureClusterResponses futureClusterResponses) {

		MethodHandler methodHandler = clusterRequest.getMethodHandler();

		Object returnValue = null;
		Exception exception = null;

		if (methodHandler == null) {
			exception = new ClusterException(
				"Payload is not of type " + MethodHandler.class.getName());
		}
		else {
			try {
				returnValue = methodHandler.invoke();
			}
			catch (Exception e) {
				exception = e;
			}
		}

		if (!clusterRequest.isFireAndForget()) {
			ClusterNodeResponse clusterNodeResponse =
				generateClusterNodeResponse(
					clusterRequest, returnValue, exception);

			futureClusterResponses.addClusterNodeResponse(clusterNodeResponse);
		}
	}

	protected void sendNotifyRequest() {
		ClusterRequest clusterRequest = ClusterRequest.createClusterRequest(
			ClusterMessageType.NOTIFY, _localClusterNode);

		try {
			_controlJChannel.send(null, clusterRequest);
		}
		catch (Exception e) {
			_log.error("Unable to send notify message", e);
		}
	}

	private static final String _DEFAULT_CLUSTER_NAME =
		"LIFERAY-CONTROL-CHANNEL";

	private static final Log _log = LogFactoryUtil.getLog(
		ClusterExecutorImpl.class);

	private final CopyOnWriteArrayList<ClusterEventListener>
		_clusterEventListeners =
			new CopyOnWriteArrayList<ClusterEventListener>();
	private final Map<String, Address> _clusterNodeAddresses =
		new ConcurrentHashMap<String, Address>();
	private JChannel _controlJChannel;
	private ExecutorService _executorService;
	private Map<String, FutureClusterResponses> _futureClusterResponses =
		new ConcurrentReferenceValueHashMap<String, FutureClusterResponses>(
			FinalizeManager.WEAK_REFERENCE_FACTORY);
	private final Map<Address, ClusterNode> _liveInstances =
		new ConcurrentHashMap<Address, ClusterNode>();
	private Address _localAddress;
	private ClusterNode _localClusterNode;
	private boolean _secure;
	private boolean _shortcutLocalMethod;

	private class ClusterResponseCallbackJob implements Runnable {

		public ClusterResponseCallbackJob(
			ClusterResponseCallback clusterResponseCallback,
			FutureClusterResponses futureClusterResponses) {

			_clusterResponseCallback = clusterResponseCallback;
			_futureClusterResponses = futureClusterResponses;
		}

		@Override
		public void run() {
			BlockingQueue<ClusterNodeResponse> blockingQueue =
				_futureClusterResponses.getPartialResults();

			_clusterResponseCallback.callback(blockingQueue);
		}

		private final ClusterResponseCallback _clusterResponseCallback;
		private final FutureClusterResponses _futureClusterResponses;

	}

}