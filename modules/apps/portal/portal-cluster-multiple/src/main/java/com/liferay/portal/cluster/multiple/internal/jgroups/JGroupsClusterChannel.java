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

package com.liferay.portal.cluster.multiple.internal.jgroups;

import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.cluster.multiple.configuration.ClusterExecutorConfiguration;
import com.liferay.portal.cluster.multiple.internal.BaseClusterChannel;
import com.liferay.portal.cluster.multiple.internal.ClusterReceiver;
import com.liferay.portal.cluster.multiple.internal.io.ClusterSerializationUtil;
import com.liferay.portal.kernel.cluster.Address;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.lang.reflect.Method;

import java.net.InetAddress;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

import org.jgroups.JChannel;
import org.jgroups.conf.ProtocolStackConfigurator;
import org.jgroups.protocols.TP;
import org.jgroups.stack.Protocol;
import org.jgroups.stack.ProtocolStack;

/**
 * @author Tina Tian
 */
public class JGroupsClusterChannel extends BaseClusterChannel {

	public JGroupsClusterChannel(
		ExecutorService executorService, String channelLogicName,
		ProtocolStackConfigurator protocolStackConfigurator, String clusterName,
		ClusterReceiver clusterReceiver, InetAddress bindInetAddress,
		ClusterExecutorConfiguration clusterExecutorConfiguration,
		Map<ClassLoader, ClassLoader> classLoaders) {

		super(executorService);

		if (protocolStackConfigurator == null) {
			throw new NullPointerException("ProtocolStackConfigurator is null");
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
			_jChannel = new JChannel(protocolStackConfigurator);

			if (Validator.isNotNull(channelLogicName)) {
				_jChannel.setName(channelLogicName);
			}

			if (bindInetAddress != null) {
				ProtocolStack protocolStack = _jChannel.getProtocolStack();

				TP tp = protocolStack.getTransport();

				tp.setBindAddress(bindInetAddress);
			}

			_jChannel.setReceiver(
				new JGroupsReceiver(clusterReceiver, classLoaders));

			_jChannel.connect(_clusterName);

			_localAddress = new AddressImpl(_jChannel.getAddress());

			if (_log.isInfoEnabled()) {
				StringBundler sb = new StringBundler(7);

				sb.append("Create a new JGroups channel {channelName: ");
				sb.append(_clusterName);
				sb.append(", localAddress: ");
				sb.append(_localAddress.getDescription());
				sb.append(", properties: ");
				sb.append(
					_getJChannelProperties(
						clusterExecutorConfiguration.excludedPropertyKeys()));
				sb.append("}");

				_log.info(sb.toString());
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

	protected void doSendMessage(Serializable message, Address address) {
		if (_jChannel.isClosed()) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Cluster channel " + _clusterName + " is alreay closed");
			}

			return;
		}

		org.jgroups.Address jgroupsAddress = null;

		if (address != null) {
			jgroupsAddress = (org.jgroups.Address)address.getRealAddress();
		}

		try {
			_jChannel.send(
				jgroupsAddress, ClusterSerializationUtil.writeObject(message));

			if (_log.isDebugEnabled()) {
				if (address == null) {
					_log.debug("Send multicast message " + message);
				}
				else {
					_log.debug("Send unicast message " + message);
				}
			}
		}
		catch (Exception e) {
			if (address == null) {
				throw new SystemException(
					"Unable to send multicast message", e);
			}

			throw new SystemException("Unable to send unicast message", e);
		}
	}

	private String _getJChannelProperties(String[] excludedPropertyKeys)
		throws ReflectiveOperationException {

		StringBundler sb = new StringBundler();

		ProtocolStack protocolStack = _jChannel.getProtocolStack();

		List<Protocol> protocols = protocolStack.getProtocols();

		for (int i = protocols.size() - 1; i >= 0; i--) {
			Protocol protocol = protocols.get(i);

			sb.append(protocol.getName());

			Map<String, String> properties =
				(Map<String, String>)_getPropsMethod.invoke(null, protocol);

			for (String excludedPropertyKey : excludedPropertyKeys) {
				properties.remove(excludedPropertyKey);
			}

			if (!properties.isEmpty()) {
				sb.append(StringPool.OPEN_PARENTHESIS);

				for (Map.Entry<String, String> entry : properties.entrySet()) {
					sb.append(entry.getKey());
					sb.append(StringPool.EQUAL);
					sb.append(entry.getValue());
					sb.append(StringPool.SEMICOLON);
				}

				sb.setStringAt(StringPool.CLOSE_PARENTHESIS, sb.index() - 1);
			}

			sb.append(StringPool.COLON);
		}

		if (sb.index() > 0) {
			sb.setIndex(sb.index() - 1);
		}

		return sb.toString();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JGroupsClusterChannel.class);

	private static final Method _getPropsMethod;

	static {
		try {
			_getPropsMethod = ReflectionUtil.getDeclaredMethod(
				ProtocolStack.class, "getProps", Protocol.class);
		}
		catch (Exception e) {
			throw new ExceptionInInitializerError(e);
		}
	}

	private final String _clusterName;
	private final ClusterReceiver _clusterReceiver;
	private final JChannel _jChannel;
	private final Address _localAddress;

}