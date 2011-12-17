/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.cluster.FutureClusterResponses;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.InetAddressUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.NamedThreadFactory;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.WeakValueConcurrentHashMap;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.util.PortalPortEventListener;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;

import java.io.Serializable;

import java.net.InetAddress;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.jgroups.ChannelException;
import org.jgroups.JChannel;

/**
 * @author Tina Tian
 * @author Shuyang Zhou
 */
public class ClusterExecutorImpl
	extends ClusterBase implements ClusterExecutor, PortalPortEventListener {

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

		super.afterPropertiesSet();
	}

	@Override
	public void destroy() {
		if (!isEnabled()) {
			return;
		}

		_scheduledExecutorService.shutdownNow();

		_clusterRequestReceiver.destroy();

		_controlChannel.close();
	}

	public FutureClusterResponses execute(ClusterRequest clusterRequest)
		throws SystemException {

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

		if (!clusterRequest.isSkipLocal() && _shortcutLocalMethod &&
			addresses.remove(getLocalClusterNodeAddress())) {

			ClusterNodeResponse clusterNodeResponse = runLocalMethod(
				clusterRequest.getMethodHandler());

			clusterNodeResponse.setMulticast(clusterRequest.isMulticast());
			clusterNodeResponse.setUuid(clusterRequest.getUuid());

			futureClusterResponses.addClusterNodeResponse(clusterNodeResponse);
		}

		if (clusterRequest.isMulticast()) {
			sendMulticastRequest(clusterRequest);
		}
		else {
			sendUnicastRequest(clusterRequest, addresses);
		}

		return futureClusterResponses;
	}

	public List<ClusterEventListener> getClusterEventListeners() {
		if (!isEnabled()) {
			return Collections.emptyList();
		}

		return Collections.unmodifiableList(_clusterEventListeners);
	}

	public List<Address> getClusterNodeAddresses() {
		if (!isEnabled()) {
			return Collections.emptyList();
		}

		removeExpiredInstances();

		return new ArrayList<Address>(_clusterNodeAddresses.values());
	}

	public List<ClusterNode> getClusterNodes() {
		if (!isEnabled()) {
			return Collections.emptyList();
		}

		removeExpiredInstances();

		Set<ObjectValuePair<Address, ClusterNode>> liveInstances =
			_liveInstances.keySet();

		List<ClusterNode> liveClusterNodes = new ArrayList<ClusterNode>(
			liveInstances.size());

		for (ObjectValuePair<Address, ClusterNode> liveInstance :
				liveInstances) {

			liveClusterNodes.add(liveInstance.getValue());
		}

		return liveClusterNodes;
	}

	public ClusterNode getLocalClusterNode() {
		if (!isEnabled()) {
			return null;
		}

		return _localClusterNode;
	}

	public Address getLocalClusterNodeAddress() {
		if (!isEnabled()) {
			return null;
		}

		return _localAddress;
	}

	public void initialize() {
		if (!isEnabled()) {
			return;
		}

		PortalUtil.addPortalPortEventListener(this);

		_localAddress = new AddressImpl(_controlChannel.getLocalAddress());

		try {
			initLocalClusterNode();
		}
		catch (SystemException se) {
			_log.error("Unable to determine local network address", se);
		}

		ObjectValuePair<Address, ClusterNode> localInstance =
			new ObjectValuePair<Address, ClusterNode>(
				_localAddress, _localClusterNode);

		_liveInstances.put(localInstance, Long.MAX_VALUE);

		_clusterNodeAddresses.put(
			_localClusterNode.getClusterNodeId(), _localAddress);

		_clusterRequestReceiver.initialize();

		_scheduledExecutorService = Executors.newScheduledThreadPool(
			1,
			new NamedThreadFactory(
				ClusterExecutorImpl.class.getName(), Thread.NORM_PRIORITY,
				Thread.currentThread().getContextClassLoader()));

		_scheduledExecutorService.scheduleWithFixedDelay(
			new HeartbeatTask(), 0,
			PropsValues.CLUSTER_EXECUTOR_HEARTBEAT_INTERVAL,
			TimeUnit.MILLISECONDS);
	}

	public boolean isClusterNodeAlive(Address address) {
		if (!isEnabled()) {
			return false;
		}

		removeExpiredInstances();

		return _clusterNodeAddresses.containsValue(address);
	}

	public boolean isClusterNodeAlive(String clusterNodeId) {
		if (!isEnabled()) {
			return false;
		}

		removeExpiredInstances();

		return _clusterNodeAddresses.containsKey(clusterNodeId);
	}

	@Override
	public boolean isEnabled() {
		return PropsValues.CLUSTER_LINK_ENABLED;
	}

	public void portalPortConfigured(int port) {
		if (!isEnabled() ||
			_localClusterNode.getPort() ==
				PropsValues.PORTAL_INSTANCE_HTTP_PORT) {

			return;
		}

		_localClusterNode.setPort(port);
	}

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

	protected JChannel getControlChannel() {
		return _controlChannel;
	}

	protected FutureClusterResponses getExecutionResults(String uuid) {
		return _futureClusterResponses.get(uuid);
	}

	@Override
	protected void initChannels() {
		Properties controlProperties = PropsUtil.getProperties(
			PropsKeys.CLUSTER_LINK_CHANNEL_PROPERTIES_CONTROL, false);

		String controlProperty = controlProperties.getProperty(
			PropsKeys.CLUSTER_LINK_CHANNEL_PROPERTIES_CONTROL);

		_clusterRequestReceiver = new ClusterRequestReceiver(this);

		try {
			_controlChannel = createJChannel(
				controlProperty, _clusterRequestReceiver,
				_DEFAULT_CLUSTER_NAME);
		}
		catch (ChannelException ce) {
			_log.error(ce, ce);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	protected void initLocalClusterNode() throws SystemException {
		_localClusterNode = new ClusterNode(PortalUUIDUtil.generate());

		if (PropsValues.PORTAL_INSTANCE_HTTP_PORT > 0) {
			_localClusterNode.setPort(PropsValues.PORTAL_INSTANCE_HTTP_PORT);
		}
		else {
			_localClusterNode.setPort(PortalUtil.getPortalPort(false));
		}

		try {
			InetAddress inetAddress = bindInetAddress;

			if (inetAddress == null) {
				inetAddress = InetAddressUtil.getLocalInetAddress();
			}

			_localClusterNode.setInetAddress(inetAddress);

			_localClusterNode.setHostName(inetAddress.getHostName());
		}
		catch (Exception e) {
			throw new SystemException(
				"Unable to determine local network address", e);
		}
	}

	protected boolean isShortcutLocalMethod() {
		return _shortcutLocalMethod;
	}

	protected void notify(
		Address address, ClusterNode clusterNode, long expirationTime) {

		removeExpiredInstances();

		if (System.currentTimeMillis() > expirationTime) {
			return;
		}

		ObjectValuePair<Address, ClusterNode> liveInstance =
			new ObjectValuePair<Address, ClusterNode>(address, clusterNode);

		// Go through the extra step of removing, and then putting the new
		// expiration time. See LPS-23463.

		Long oldExpirationTime = _liveInstances.remove(liveInstance);

		_liveInstances.put(liveInstance, expirationTime);

		if ((oldExpirationTime != null) ||
			((_localAddress != null) && _localAddress.equals(address))) {

			return;
		}

		_clusterNodeAddresses.put(clusterNode.getClusterNodeId(), address);

		ClusterEvent clusterEvent = ClusterEvent.join(clusterNode);

		fireClusterEvent(clusterEvent);
	}

	protected List<Address> prepareAddresses(ClusterRequest clusterRequest) {
		boolean isMulticast = clusterRequest.isMulticast();

		List<Address> addresses = null;

		if (isMulticast) {
			addresses = getAddresses(_controlChannel);
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

		return addresses;
	}

	protected void removeExpiredInstances() {
		if (_liveInstances.isEmpty()) {
			return;
		}

		Set<Map.Entry<ObjectValuePair<Address, ClusterNode>, Long>>
			liveInstances = _liveInstances.entrySet();

		Iterator<Map.Entry<ObjectValuePair<Address, ClusterNode>, Long>> itr =
			liveInstances.iterator();

		long now = System.currentTimeMillis();

		while (itr.hasNext()) {
			Map.Entry<ObjectValuePair<Address, ClusterNode>, Long> entry =
				itr.next();

			long expirationTime = entry.getValue();

			if (now < expirationTime) {
				continue;
			}

			ObjectValuePair<Address, ClusterNode> liveInstance = entry.getKey();

			ClusterNode clusterNode = liveInstance.getValue();

			if (_localClusterNode.equals(clusterNode)) {
				continue;
			}

			_clusterNodeAddresses.remove(clusterNode.getClusterNodeId());

			itr.remove();

			ClusterEvent clusterEvent = ClusterEvent.depart(clusterNode);

			fireClusterEvent(clusterEvent);
		}
	}

	protected ClusterNodeResponse runLocalMethod(MethodHandler methodHandler) {
		ClusterNodeResponse clusterNodeResponse = new ClusterNodeResponse();

		ClusterNode localClusterNode = getLocalClusterNode();

		clusterNodeResponse.setAddress(getLocalClusterNodeAddress());
		clusterNodeResponse.setClusterNode(localClusterNode);
		clusterNodeResponse.setClusterMessageType(ClusterMessageType.EXECUTE);

		if (methodHandler == null) {
			clusterNodeResponse.setException(
				new ClusterException(
					"Payload is not of type " + MethodHandler.class.getName()));

			return clusterNodeResponse;
		}

		try {
			Object returnValue = methodHandler.invoke(true);

			if (returnValue instanceof Serializable) {
				clusterNodeResponse.setResult(returnValue);
			}
			else if (returnValue != null) {
				clusterNodeResponse.setException(
					new ClusterException("Return value is not serializable"));
			}
		}
		catch (Exception e) {
			clusterNodeResponse.setException(e);
		}

		return clusterNodeResponse;
	}

	protected void sendMulticastRequest(ClusterRequest clusterRequest)
		throws SystemException {

		try {
			_controlChannel.send(null, null, clusterRequest);
		}
		catch (ChannelException ce) {
			_log.error(
				"Unable to send multicast message " + clusterRequest, ce);

			throw new SystemException("Unable to send multicast request", ce);
		}
	}

	protected void sendUnicastRequest(
			ClusterRequest clusterRequest, List<Address> addresses)
		throws SystemException {

		for (Address address : addresses) {
			org.jgroups.Address jGroupsAddress =
				(org.jgroups.Address)address.getRealAddress();

			try {
				_controlChannel.send(jGroupsAddress, null, clusterRequest);
			}
			catch (ChannelException ce) {
				_log.error(
					"Unable to send unicast message " + clusterRequest, ce);

				throw new SystemException("Unable to send unicast request", ce);
			}
		}
	}

	private static final String _DEFAULT_CLUSTER_NAME =
		"LIFERAY-CONTROL-CHANNEL";

	private static Log _log = LogFactoryUtil.getLog(ClusterExecutorImpl.class);

	public ScheduledExecutorService _scheduledExecutorService;
	private CopyOnWriteArrayList<ClusterEventListener> _clusterEventListeners =
		new CopyOnWriteArrayList<ClusterEventListener>();
	private Map<String, Address> _clusterNodeAddresses =
		new ConcurrentHashMap<String, Address>();
	private ClusterRequestReceiver _clusterRequestReceiver;
	private JChannel _controlChannel;
	private Map<String, FutureClusterResponses> _futureClusterResponses =
		new WeakValueConcurrentHashMap<String, FutureClusterResponses>();
	private Map<ObjectValuePair<Address, ClusterNode>, Long> _liveInstances =
		new ConcurrentHashMap<ObjectValuePair<Address, ClusterNode>, Long>();
	private Address _localAddress;
	private ClusterNode _localClusterNode;
	private boolean _shortcutLocalMethod;

	private class HeartbeatTask implements Runnable {

		public void run() {
			try {
				ClusterRequest clusterNotifyRequest =
					ClusterRequest.createClusterNotifyRequest(
						_localClusterNode);

				sendMulticastRequest(clusterNotifyRequest);
			}
			catch (Exception e) {
				if (_log.isDebugEnabled()) {
					_log.debug("Unable to send check in request", e);
				}

				_scheduledExecutorService.scheduleWithFixedDelay(
					new HeartbeatTask(), 0,
					PropsValues.CLUSTER_EXECUTOR_HEARTBEAT_INTERVAL,
					TimeUnit.MILLISECONDS);
			}
		}

	}

}