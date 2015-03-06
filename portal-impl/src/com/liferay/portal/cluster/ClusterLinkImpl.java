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
import com.liferay.portal.kernel.cluster.ClusterInvokeThreadLocal;
import com.liferay.portal.kernel.cluster.ClusterLink;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;

import org.jgroups.JChannel;

/**
 * @author Shuyang Zhou
 */
@DoPrivileged
public class ClusterLinkImpl extends ClusterBase implements ClusterLink {

	@Override
	public void destroy() {
		if (!isEnabled()) {
			return;
		}

		for (JChannel jChannel : _transportJChannels) {
			jChannel.setReceiver(null);

			jChannel.close();
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

		for (JChannel jChannel : _transportJChannels) {
			JGroupsReceiver jGroupsReceiver =
				(JGroupsReceiver)jChannel.getReceiver();

			jGroupsReceiver.openLatch();
		}
	}

	@Override
	public void sendMulticastMessage(Message message, Priority priority) {
		if (!isEnabled()) {
			return;
		}

		JChannel jChannel = getChannel(priority);

		try {
			jChannel.send(null, message);
		}
		catch (Exception e) {
			_log.error("Unable to send multicast message " + message, e);
		}
	}

	@Override
	public void sendUnicastMessage(
		Address address, Message message, Priority priority) {

		if (!isEnabled()) {
			return;
		}

		org.jgroups.Address jGroupsAddress =
			(org.jgroups.Address)address.getRealAddress();

		if (_localTransportAddresses.contains(jGroupsAddress)) {
			forwardMessage(message);

			return;
		}

		JChannel jChannel = getChannel(priority);

		try {
			jChannel.send(jGroupsAddress, message);
		}
		catch (Exception e) {
			_log.error("Unable to send unicast message " + message, e);
		}
	}

	protected void forwardMessage(Message message) {
		String destinationName = message.getDestinationName();

		if (Validator.isNotNull(destinationName)) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Forwarding cluster link message " + message + " to " +
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
			if (_log.isErrorEnabled()) {
				_log.error(
					"Forwarded cluster link message has no destination " +
						message);
			}
		}
	}

	protected JChannel getChannel(Priority priority) {
		int channelIndex =
			priority.ordinal() * _channelCount / MAX_CHANNEL_COUNT;

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Select channel number " + channelIndex + " for priority " +
					priority);
		}

		return _transportJChannels.get(channelIndex);
	}

	protected ExecutorService getExecutorService() {
		return _executorService;
	}

	protected List<org.jgroups.Address> getLocalTransportAddresses() {
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
		_transportJChannels = new ArrayList<>(_channelCount);

		List<String> keys = new ArrayList<>(_channelCount);

		for (Object key : transportProperties.keySet()) {
			keys.add((String)key);
		}

		Collections.sort(keys);

		for (int i = 0; i < keys.size(); i++) {
			String customName = keys.get(i);

			String value = transportProperties.getProperty(customName);

			JChannel jChannel = createJChannel(
				value, new ClusterForwardReceiver(this),
				_LIFERAY_TRANSPORT_CHANNEL + i);

			_localTransportAddresses.add(jChannel.getAddress());
			_transportJChannels.add(jChannel);
		}
	}

	private static final String _LIFERAY_TRANSPORT_CHANNEL =
		"LIFERAY-TRANSPORT-CHANNEL-";

	private static final Log _log = LogFactoryUtil.getLog(
		ClusterLinkImpl.class);

	private int _channelCount;
	private ExecutorService _executorService;
	private List<org.jgroups.Address> _localTransportAddresses;
	private List<JChannel> _transportJChannels;

}