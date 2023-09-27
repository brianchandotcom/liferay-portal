/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.cluster.multiple.internal;

import java.net.InetAddress;
import java.net.NetworkInterface;

import java.util.concurrent.ExecutorService;

/**
 * @author Tina Tian
 */
public interface ClusterChannelFactory {

	public ClusterChannel createClusterChannel(
		ExecutorService executorService, String channleLogicName,
		String channelPropertiesLocation, String clusterName,
		ClusterReceiver clusterReceiver);

	public InetAddress getBindInetAddress();

	public NetworkInterface getBindNetworkInterface();

}