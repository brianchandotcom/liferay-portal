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

import com.liferay.portal.cluster.internal.constants.ClusterPropsKeys;
import com.liferay.portal.kernel.cluster.Address;
import com.liferay.portal.kernel.cluster.ClusterInvokeThreadLocal;
import com.liferay.portal.kernel.cluster.ClusterLink;
import com.liferay.portal.kernel.cluster.Priority;
import com.liferay.portal.kernel.executor.PortalExecutorManager;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.concurrent.ExecutorService;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Shuyang Zhou
 */
@Component(immediate = true, service = ClusterLink.class)
public class ClusterLinkImpl implements ClusterLink {

	@Override
	public boolean isEnabled() {
		return _enabled;
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

		if (_localAddresses.contains(address)) {
			sendLocalMessage(message);

			return;
		}

		ClusterChannel clusterChannel = getChannel(priority);

		clusterChannel.sendUnicastMessage(message, address);
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_enabled = GetterUtil.getBoolean(
			_props.get(PropsKeys.CLUSTER_LINK_ENABLED));

		if (_enabled) {
			initialize(
				getChannelPropertiesStrings(properties),
				getChannelNames(properties));
		}
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
				"Select channel number " + channelIndex + " for priority " +
					priority);
		}

		return _clusterChannels.get(channelIndex);
	}

	protected Map<String, String> getChannelNames(
		Map<String, Object> properties) {

		Map<String, String> channelNames = new HashMap<>();

		int prefixLength =
			ClusterPropsKeys.CHANNEL_NAME_TRANSPORT_PREFIX.length();

		for (Entry<String, Object> entry : properties.entrySet()) {
			String key = entry.getKey();

			if (key.startsWith(
					ClusterPropsKeys.CHANNEL_NAME_TRANSPORT_PREFIX)) {

				channelNames.put(
					key.substring(prefixLength + 1), (String)entry.getValue());
			}
		}

		if (channelNames.isEmpty()) {
			Properties channelNameProperties = _props.getProperties(
				PropsKeys.CLUSTER_LINK_CHANNEL_NAME_TRANSPORT, true);

			for (Map.Entry<Object, Object> entry :
					channelNameProperties.entrySet()) {

				channelNames.put(
					(String)entry.getKey(), (String)entry.getValue());
			}
		}

		return channelNames;
	}

	protected Map<String, String> getChannelPropertiesStrings(
		Map<String, Object> properties) {

		Map<String, String> channelPropertiesStrings = new HashMap<>();

		int prefixLength =
			ClusterPropsKeys.CHANNEL_PROPERTIES_TRANSPORT_PREFIX.length();

		for (Entry<String, Object> entry : properties.entrySet()) {
			String key = entry.getKey();

			if (key.startsWith(
					ClusterPropsKeys.CHANNEL_PROPERTIES_TRANSPORT_PREFIX)) {

				channelPropertiesStrings.put(
					key.substring(prefixLength + 1), (String)entry.getValue());
			}
		}

		if (channelPropertiesStrings.isEmpty()) {
			Properties channelProperties = _props.getProperties(
				PropsKeys.CLUSTER_LINK_CHANNEL_PROPERTIES_TRANSPORT, true);

			for (Map.Entry<Object, Object> entry :
					channelProperties.entrySet()) {

				channelPropertiesStrings.put(
					(String)entry.getKey(), (String)entry.getValue());
			}
		}

		return channelPropertiesStrings;
	}

	protected ExecutorService getExecutorService() {
		return _executorService;
	}

	protected List<Address> getLocalAddresses() {
		return _localAddresses;
	}

	protected void initChannels(
			Map<String, String> channelPropertiesStrings,
			Map<String, String> channelNames)
		throws Exception {

		_channelCount = channelPropertiesStrings.size();

		if ((_channelCount <= 0) || (_channelCount > MAX_CHANNEL_COUNT)) {
			throw new IllegalArgumentException(
				"Channel count must be between 1 and " + MAX_CHANNEL_COUNT);
		}

		_localAddresses = new ArrayList<>(_channelCount);
		_clusterChannels = new ArrayList<>(_channelCount);
		_clusterReceivers = new ArrayList<>(_channelCount);

		List<String> keys = new ArrayList<>(channelPropertiesStrings.keySet());

		Collections.sort(keys);

		for (String key : keys) {
			String channelPropertiesString = channelPropertiesStrings.get(key);
			String channelName = channelNames.get(key);

			if (Validator.isNull(channelPropertiesString) ||
				Validator.isNull(channelName)) {

				continue;
			}

			ClusterReceiver clusterReceiver = new ClusterForwardReceiver(this);

			ClusterChannel clusterChannel =
				_clusterChannelFactory.createClusterChannel(
					channelPropertiesString, channelName, clusterReceiver);

			_clusterChannels.add(clusterChannel);
			_clusterReceivers.add(clusterReceiver);
			_localAddresses.add(clusterChannel.getLocalAddress());
		}
	}

	protected void initialize(
		Map<String, String> channelPropertiesStrings,
		Map<String, String> channelNames) {

		_executorService = _portalExecutorManager.getPortalExecutor(
			ClusterLinkImpl.class.getName());

		try {
			initChannels(channelPropertiesStrings, channelNames);
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

	@Reference(
		cardinality = ReferenceCardinality.OPTIONAL,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected void setMessageBus(MessageBus messageBus) {
		_messageBus = messageBus;
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

	protected void unsetMessageBus(MessageBus messageBus) {
		_messageBus = null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ClusterLinkImpl.class);

	private int _channelCount;
	private volatile ClusterChannelFactory _clusterChannelFactory;
	private List<ClusterChannel> _clusterChannels;
	private List<ClusterReceiver> _clusterReceivers;
	private boolean _enabled;
	private ExecutorService _executorService;
	private List<Address> _localAddresses;
	private volatile MessageBus _messageBus;
	private volatile PortalExecutorManager _portalExecutorManager;
	private volatile Props _props;

}