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

package com.liferay.portal.cluster.multiple.internal;

import com.liferay.petra.executor.PortalExecutorManager;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.cluster.Address;
import com.liferay.portal.kernel.cluster.ClusterInvokeThreadLocal;
import com.liferay.portal.kernel.cluster.ClusterLink;
import com.liferay.portal.kernel.cluster.Priority;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Shuyang Zhou
 */
@Component(enabled = false, immediate = true, service = ClusterLink.class)
public class ClusterLinkImpl implements ClusterLink {

	@Override
	public boolean isEnabled() {
		return _enabled;
	}

	@Override
	public void sendMulticastMessage(Message message, Priority priority) {
		ClusterChannel clusterChannel = getChannel(priority);

		clusterChannel.sendMulticastMessage(message);
	}

	@Override
	public void sendUnicastMessage(
		Address address, Message message, Priority priority) {

		if (_localAddresses.contains(address)) {
			sendLocalMessage(message);

			return;
		}

		ClusterChannel clusterChannel = getChannel(priority);

		clusterChannel.sendUnicastMessage(message, address);
	}

	@Activate
	protected void activate() {
		_enabled = true;

		initialize(
			getChannelSettings(
				PropsKeys.CLUSTER_LINK_CHANNEL_LOGIC_NAME_TRANSPORT),
			getChannelSettings(
				PropsKeys.CLUSTER_LINK_CHANNEL_PROPERTIES_TRANSPORT),
			getChannelSettings(PropsKeys.CLUSTER_LINK_CHANNEL_NAME_TRANSPORT));
	}

	@Deactivate
	protected void deactivate() {
		if (_clusterChannels != null) {
			for (ClusterChannel clusterChannel : _clusterChannels) {
				clusterChannel.close();
			}
		}

		_localAddresses = null;
		_clusterChannels = null;
		_clusterReceivers = null;

		if (_executorService != null) {
			_executorService.shutdownNow();
		}

		_executorService = null;
	}

	protected ClusterChannel getChannel(Priority priority) {
		int channelIndex =
			priority.ordinal() * _channelCount / MAX_CHANNEL_COUNT;

		if (_log.isDebugEnabled()) {
			_log.debug(
				StringBundler.concat(
					"Select channel number ", channelIndex, " for priority ",
					priority));
		}

		return _clusterChannels.get(channelIndex);
	}

	protected Map<String, String> getChannelSettings(String propertyPrefix) {
		Map<String, String> channelSettings = new HashMap<>();

		Properties channelProperties = _props.getProperties(
			propertyPrefix, true);

		for (Map.Entry<Object, Object> entry : channelProperties.entrySet()) {
			channelSettings.put(
				(String)entry.getKey(), (String)entry.getValue());
		}

		return channelSettings;
	}

	protected ExecutorService getExecutorService() {
		return _executorService;
	}

	protected List<Address> getLocalAddresses() {
		return _localAddresses;
	}

	protected void initChannels(
			Map<String, String> channelLogicNames,
			Map<String, String> channelPropertiesLocations,
			Map<String, String> channelNames)
		throws Exception {

		_channelCount = channelPropertiesLocations.size();

		if ((_channelCount <= 0) || (_channelCount > MAX_CHANNEL_COUNT)) {
			throw new IllegalArgumentException(
				"Channel count must be between 1 and " + MAX_CHANNEL_COUNT);
		}

		_localAddresses = new ArrayList<>(_channelCount);
		_clusterChannels = new ArrayList<>(_channelCount);
		_clusterReceivers = new ArrayList<>(_channelCount);

		List<String> keys = new ArrayList<>(
			channelPropertiesLocations.keySet());

		Collections.sort(keys);

		for (String key : keys) {
			String channelPropertiesLocation = channelPropertiesLocations.get(
				key);
			String channelName = channelNames.get(key);

			if (Validator.isNull(channelPropertiesLocation) ||
				Validator.isNull(channelName)) {

				continue;
			}

			String channelLogicName = channelLogicNames.get(key);
			ClusterReceiver clusterReceiver = new ClusterForwardReceiver(this);

			ClusterChannel clusterChannel =
				_clusterChannelFactory.createClusterChannel(
					_executorService, channelLogicName,
					channelPropertiesLocation, channelName, clusterReceiver);

			_clusterChannels.add(clusterChannel);

			_clusterReceivers.add(clusterReceiver);
			_localAddresses.add(clusterChannel.getLocalAddress());
		}
	}

	protected void initialize(
		Map<String, String> channelLogicNames,
		Map<String, String> channelPropertiesLocations,
		Map<String, String> channelNames) {

		_executorService = _portalExecutorManager.getPortalExecutor(
			ClusterLinkImpl.class.getName());

		try {
			initChannels(
				channelLogicNames, channelPropertiesLocations, channelNames);
		}
		catch (Exception e) {
			_log.error("Unable to initialize channels", e);

			throw new IllegalStateException(e);
		}

		for (ClusterReceiver clusterReceiver : _clusterReceivers) {
			clusterReceiver.openLatch();
		}
	}

	protected void sendLocalMessage(Message message) {
		String destinationName = message.getDestinationName();

		if (Validator.isNotNull(destinationName)) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					StringBundler.concat(
						"Sending local cluster link message ", message, " to ",
						destinationName));
			}

			ClusterInvokeThreadLocal.setEnabled(false);

			try {
				_messageBus.sendMessage(destinationName, message);
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

	@Reference(unbind = "-")
	protected void setClusterChannelFactory(
		ClusterChannelFactory clusterChannelFactory) {

		_clusterChannelFactory = clusterChannelFactory;
	}

	@Reference(unbind = "-")
	protected void setPortalExecutorManager(
		PortalExecutorManager portalExecutorManager) {

		_portalExecutorManager = portalExecutorManager;
	}

	@Reference(unbind = "-")
	protected void setProps(Props props) {
		_props = props;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ClusterLinkImpl.class);

	private int _channelCount;
	private ClusterChannelFactory _clusterChannelFactory;
	private List<ClusterChannel> _clusterChannels;
	private List<ClusterReceiver> _clusterReceivers;
	private boolean _enabled;
	private ExecutorService _executorService;
	private List<Address> _localAddresses;

	@Reference
	private MessageBus _messageBus;

	private PortalExecutorManager _portalExecutorManager;
	private Props _props;

}