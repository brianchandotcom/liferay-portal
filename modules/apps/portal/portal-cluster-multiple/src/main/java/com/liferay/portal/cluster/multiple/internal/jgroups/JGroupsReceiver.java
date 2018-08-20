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

import com.liferay.portal.cluster.multiple.internal.ClusterReceiver;
import com.liferay.portal.cluster.multiple.internal.io.ClusterSerializationUtil;
import com.liferay.portal.kernel.cluster.Address;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.AggregateClassLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.jgroups.View;

/**
 * @author Tina Tian
 */
public class JGroupsReceiver extends ReceiverAdapter {

	public JGroupsReceiver(
		ClusterReceiver clusterReceiver,
		Map<ClassLoader, ClassLoader> classLoaders) {

		if (clusterReceiver == null) {
			throw new NullPointerException("Cluster receiver is null");
		}

		_clusterReceiver = clusterReceiver;
		_classLoaders = classLoaders;
	}

	@Override
	public void receive(Message message) {
		byte[] rawBuffer = message.getRawBuffer();

		if (rawBuffer == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("Message content is null");
			}

			return;
		}

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		ClassLoader aggregatedClassLoader = _classLoaders.computeIfAbsent(
			contextClassLoader,
			keyClassLoader -> AggregateClassLoader.getAggregateClassLoader(
				keyClassLoader, JGroupsReceiver.class.getClassLoader()));

		currentThread.setContextClassLoader(aggregatedClassLoader);

		try {
			_clusterReceiver.receive(
				ClusterSerializationUtil.readObject(
					rawBuffer, message.getOffset(), message.getLength()),
				new AddressImpl(message.getSrc()));
		}
		catch (ClassNotFoundException cnfe) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to deserialize message payload", cnfe);
			}
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}
	}

	@Override
	public void viewAccepted(View view) {
		if (_log.isInfoEnabled()) {
			_log.info("Accepted view " + view);
		}

		List<Address> addresses = new ArrayList<>();
		Address coordinatorAddress = null;

		List<org.jgroups.Address> jGroupsAddresses = view.getMembers();

		for (int i = 0; i < jGroupsAddresses.size(); i++) {
			Address address = new AddressImpl(jGroupsAddresses.get(i));

			if (i == 0) {
				coordinatorAddress = address;
			}

			addresses.add(address);
		}

		_clusterReceiver.addressesUpdated(addresses);
		_clusterReceiver.coordinatorAddressUpdated(coordinatorAddress);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JGroupsReceiver.class);

	private final Map<ClassLoader, ClassLoader> _classLoaders;
	private final ClusterReceiver _clusterReceiver;

}