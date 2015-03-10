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
import com.liferay.portal.kernel.cluster.ClusterChannel;
import com.liferay.portal.kernel.cluster.ClusterChannelFactory;
import com.liferay.portal.kernel.cluster.ClusterInvokeThreadLocal;
import com.liferay.portal.kernel.cluster.ClusterLink;
import com.liferay.portal.kernel.cluster.ClusterReceiver;
import com.liferay.portal.kernel.cluster.Priority;
import com.liferay.portal.kernel.executor.PortalExecutorManagerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;

/**
 * @author Shuyang Zhou
 */
@DoPrivileged
public class ClusterLinkImpl implements ClusterLink {

	public void destroy() {
		if (!isEnabled()) {
			return;
		}

		for (ClusterChannel clusterChannel : _transportChannels) {
			clusterChannel.close();
		}

		_executorService.shutdownNow();
	}

	@Override
	public void initialize() {
		if (!isEnabled()) {
			return;
		}

		_executorService = PortalExecutorManagerUtil.getPortalExecutor(
			ClusterLinkImpl.class.getName());

		try {
			initChannels();
		}
		catch (Exception e) {
			if (_log.isErrorEnabled()) {
				_log.error("Unable to initialize channels", e);
			}

			throw new IllegalStateException(e);
		}

		for (ClusterReceiver clusterReceiver : _clusterReceivers) {
			clusterReceiver.openLatch();
		}
	}

	@Override
	public boolean isEnabled() {
		return PropsValues.CLUSTER_LINK_ENABLED;
	}

	@Override
	public void sendMulticastMessage(Message message, Priority priority) {
		if (!isEnabled()) {
			return;
		}

		ClusterChannel clusterChannel = getChannel(priority);

		clusterChannel.sendMulticastMessage(message);
	}

	@Override
	public void sendUnicastMessage(
		Address address, Message message, Priority priority) {

		if (!isEnabled()) {
			return;
		}

		if (_localTransportAddresses.contains(address)) {
			sendLocalMessage(message);

			return;
		}

		ClusterChannel clusterChannel = getChannel(priority);

		clusterChannel.sendUnicastMessage(message, address);
	}

	public void setClusterChannelFactory(
		ClusterChannelFactory clusterChannelFactory) {

		_clusterChannelFactory = clusterChannelFactory;
	}

	protected ClusterChannel getChannel(Priority priority) {
		int channelIndex =
			priority.ordinal() * _channelCount / MAX_CHANNEL_COUNT;

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Select channel number " + channelIndex + " for priority " +
					priority);
		}

		return _transportChannels.get(channelIndex);
	}

	protected ExecutorService getExecutorService() {
		return _executorService;
	}

	protected List<Address> getLocalTransportAddresses() {
		return _localTransportAddresses;
	}

	protected void initChannels() throws Exception {
		Properties transportProperties = PropsUtil.getProperties(
			PropsKeys.CLUSTER_LINK_CHANNEL_PROPERTIES_TRANSPORT, true);

		_channelCount = transportProperties.size();

		if ((_channelCount <= 0) || (_channelCount > MAX_CHANNEL_COUNT)) {
			throw new IllegalArgumentException(
				"Channel count must be between 1 and " + MAX_CHANNEL_COUNT);
		}

		_localTransportAddresses = new ArrayList<>(_channelCount);
		_transportChannels = new ArrayList<>(_channelCount);
		_clusterReceivers = new ArrayList<>(_channelCount);

		List<String> keys = new ArrayList<>(_channelCount);

		for (Object key : transportProperties.keySet()) {
			keys.add((String)key);
		}

		Collections.sort(keys);

		for (int i = 0; i < keys.size(); i++) {
			String customName = keys.get(i);

			String value = transportProperties.getProperty(customName);

			ClusterReceiver clusterReceiver = new ClusterForwardReceiver(this);

			ClusterChannel clusterChannel =
				_clusterChannelFactory.createClusterChannel(
					value, _LIFERAY_TRANSPORT_CHANNEL + i, clusterReceiver);

			_clusterReceivers.add(clusterReceiver);
			_localTransportAddresses.add(clusterChannel.getLocalAddress());
			_transportChannels.add(clusterChannel);
		}
	}

	protected void sendLocalMessage(Message message) {
		String destinationName = message.getDestinationName();

		if (Validator.isNotNull(destinationName)) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Sending local cluster link message " + message + " to " +
						destinationName);
			}

			ClusterInvokeThreadLocal.setEnabled(false);

			try {
				MessageBusUtil.sendMessage(destinationName, message);
			}
			finally {
				ClusterInvokeThreadLocal.setEnabled(true);
			}
		}
		else {
			_log.error(
				"Local cluster link message has no destination " + message);
		}
	}

	private static final String _LIFERAY_TRANSPORT_CHANNEL =
		"LIFERAY-TRANSPORT-CHANNEL-";

	private static final Log _log = LogFactoryUtil.getLog(
		ClusterLinkImpl.class);

	private int _channelCount;
	private ClusterChannelFactory _clusterChannelFactory;
	private List<ClusterReceiver> _clusterReceivers;
	private ExecutorService _executorService;
	private List<Address> _localTransportAddresses;
	private List<ClusterChannel> _transportChannels;

}