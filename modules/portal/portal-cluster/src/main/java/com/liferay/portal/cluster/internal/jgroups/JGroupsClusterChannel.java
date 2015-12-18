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

package com.liferay.portal.cluster.internal.jgroups;

import com.liferay.portal.cluster.internal.ClusterChannel;
import com.liferay.portal.cluster.internal.ClusterReceiver;
import com.liferay.portal.kernel.cluster.Address;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.io.Serializer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.net.InetAddress;

import java.nio.ByteBuffer;

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
		ClusterReceiver clusterReceiver, InetAddress bindInetAddress) {

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
		_clusterReceiver = clusterReceiver;

		try {
			_jChannel = new JChannel(channelProperties);

			if (bindInetAddress != null) {
				ProtocolStack protocolStack = _jChannel.getProtocolStack();

				TP tp = protocolStack.getTransport();

				tp.setBindAddress(bindInetAddress);
			}

			_jChannel.setReceiver(new JGroupsReceiver(clusterReceiver));

			_jChannel.connect(_clusterName);

			_localAddress = new AddressImpl(_jChannel.getAddress());

			if (_log.isInfoEnabled()) {
				_log.info(
					"Create a new JGroups channel with properties " +
						_jChannel.getProperties());
			}
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
	public ClusterReceiver getClusterReceiver() {
		return _clusterReceiver;
	}

	@Override
	public Address getLocalAddress() {
		return _localAddress;
	}

	@Override
	public void sendMulticastMessage(Serializable message) {
		sendMessage(message, null);
	}

	@Override
	public void sendUnicastMessage(Serializable message, Address address) {
		if (address == null) {
			throw new SystemException("Target address is null");
		}

		sendMessage(message, (org.jgroups.Address)address.getRealAddress());
	}

	protected void sendMessage(
		Serializable message, org.jgroups.Address address) {

		if (_jChannel.isClosed()) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Cluster channel " + _clusterName + " is alreay closed");
			}

			return;
		}

		Serializer serializer = new Serializer();

		serializer.writeObject(message);

		ByteBuffer byteBuffer = serializer.toByteBuffer();

		try {
			_jChannel.send(
				address, byteBuffer.array(), byteBuffer.position(),
				byteBuffer.remaining());

			if (_log.isDebugEnabled()) {
				if (address == null) {
					_log.debug("Send mullticast message " + message);
				}
				else {
					_log.debug("Send unicast message " + message);
				}
			}
		}
		catch (Exception e) {
			if (address == null) {
				throw new SystemException(
					"Unable to send mullticast message", e);
			}
			else {
				throw new SystemException("Unable to send unicast message", e);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JGroupsClusterChannel.class);

	private final String _clusterName;
	private final ClusterReceiver _clusterReceiver;
	private final JChannel _jChannel;
	private final Address _localAddress;

}