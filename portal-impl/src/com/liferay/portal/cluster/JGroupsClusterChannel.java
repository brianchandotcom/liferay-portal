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
import com.liferay.portal.kernel.cluster.ClusterException;
import com.liferay.portal.kernel.cluster.ClusterReceiver;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.SocketUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PropsValues;

import java.io.IOException;
import java.io.Serializable;

import java.net.InetAddress;
import java.net.NetworkInterface;

import org.jgroups.JChannel;
import org.jgroups.protocols.TP;
import org.jgroups.stack.Protocol;
import org.jgroups.stack.ProtocolStack;

/**
 * @author Tina Tian
 */
public class JGroupsClusterChannel implements ClusterChannel {

	public JGroupsClusterChannel(
		String channelProperties, String clusterName,
		ClusterReceiver clusterReceiver) {

		_initBindAddress();

		if (Validator.isNull(channelProperties)) {
			throw new NullPointerException("Channel properties is null");
		}

		if (Validator.isNull(clusterName)) {
			throw new NullPointerException("Cluster name is null");
		}

		if (clusterReceiver == null) {
			throw new NullPointerException("Cluster receiver is null");
		}

		_clusterName = clusterName;

		try {
			_jChannel = new JChannel(channelProperties);

			_jChannel.setReceiver(new JGroupsReceiver(clusterReceiver));

			try {
				_jChannel.connect(_clusterName);

				if (_log.isInfoEnabled()) {
					_log.info(
						"Create a new jgroups channel with properties " +
							_jChannel.getProperties());
				}
			}
			catch (Exception e) {
				throw new ClusterException(
					"Unable to connect to cluster " + _clusterName, e);
			}

			_localAddress = new AddressImpl(_jChannel.getAddress());
		}
		catch (Exception e) {
			throw new SystemException(
				"Unable to initial cluster channel " + clusterName, e);
		}
	}

	@Override
	public void close() {
		_jChannel.setReceiver(null);

		_jChannel.close();
	}

	@Override
	public InetAddress getBindInetAddress() {
		ProtocolStack protocolStack = _jChannel.getProtocolStack();

		Protocol protocol = protocolStack.getBottomProtocol();

		if (!(protocol instanceof TP)) {
			throw new IllegalStateException(
				"Bottom protocol of jgroups is not a transport protocol");
		}

		TP transportProtocol = (TP)protocol;

		return (InetAddress)transportProtocol.getValue("bind_addr");
	}

	@Override
	public String getClusterName() {
		return _clusterName;
	}

	@Override
	public Address getLocalAddress() {
		return _localAddress;
	}

	@Override
	public void sendMulticastMessage(Serializable message) {
		if (_jChannel.isClosed()) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Cluster channel " + _clusterName + " is alreay closed");
			}

			return;
		}

		try {
			_jChannel.send(null, message);

			if (_log.isDebugEnabled()) {
				_log.debug("Send mullticast message " + message);
			}
		}
		catch (Exception e) {
			throw new SystemException("Unable to send mullticast message", e);
		}
	}

	@Override
	public void sendUnicastMessage(Serializable message, Address address) {
		if (_jChannel.isClosed()) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Cluster channel " + _clusterName + " is alreay closed");
			}

			return;
		}

		if (address == null) {
			throw new SystemException("Target address is null");
		}

		try {
			_jChannel.send(
				(org.jgroups.Address)address.getRealAddress(), message);

			if (_log.isDebugEnabled()) {
				_log.debug("Send unicast message " + message);
			}
		}
		catch (Exception e) {
			throw new SystemException("Unable to send unicast message", e);
		}
	}

	private void _initBindAddress() {
		if (_initBindAddress) {
			return;
		}

		String autodetectAddress = PropsValues.CLUSTER_LINK_AUTODETECT_ADDRESS;

		if (Validator.isNull(autodetectAddress)) {
			return;
		}

		String host = autodetectAddress;
		int port = 80;

		int index = autodetectAddress.indexOf(CharPool.COLON);

		if (index != -1) {
			host = autodetectAddress.substring(0, index);
			port = GetterUtil.getInteger(
				autodetectAddress.substring(index + 1), port);
		}

		if (_log.isInfoEnabled()) {
			_log.info(
				"Autodetecting JGroups outgoing IP address and interface for " +
					host + ":" + port);
		}

		try {
			SocketUtil.BindInfo bindInfo = SocketUtil.getBindInfo(host, port);

			InetAddress inetAddress = bindInfo.getInetAddress();

			NetworkInterface networkInterface = bindInfo.getNetworkInterface();

			System.setProperty(
				"jgroups.bind_addr", inetAddress.getHostAddress());
			System.setProperty(
				"jgroups.bind_interface", networkInterface.getName());

			if (_log.isInfoEnabled()) {
				_log.info(
					"Setting JGroups outgoing IP address to " +
						inetAddress.getHostAddress() + " and interface to " +
							networkInterface.getName());
			}
		}
		catch (IOException e) {
			if (_log.isErrorEnabled()) {
				_log.error("Unable to detect bind address for jgroups", e);
			}
		}

		_initBindAddress = true;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JGroupsClusterChannel.class);

	private static volatile boolean _initBindAddress;

	private final String _clusterName;
	private final JChannel _jChannel;
	private final Address _localAddress;

}