/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.cluster.multiple.internal;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;

import java.util.concurrent.ExecutorService;

/**
 * @author Tina Tian
 */
public class TestClusterChannelFactory implements ClusterChannelFactory {

	@Override
	public ClusterChannel createClusterChannel(
		ExecutorService executorService, String channelLogicName,
		String channelPropertiesLocation, String clusterName,
		ClusterReceiver clusterReceiver) {

		return new TestClusterChannel(
			channelLogicName, channelPropertiesLocation, clusterName,
			clusterReceiver);
	}

	@Override
	public InetAddress getBindInetAddress() {
		return InetAddress.getLoopbackAddress();
	}

	@Override
	public NetworkInterface getBindNetworkInterface() {
		try {
			return NetworkInterface.getByInetAddress(
				InetAddress.getLoopbackAddress());
		}
		catch (SocketException socketException) {
			throw new IllegalStateException(socketException);
		}
	}

}