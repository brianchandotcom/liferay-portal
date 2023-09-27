/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.cluster.multiple.internal;

import com.liferay.portal.kernel.cluster.Address;

import java.io.Serializable;

import java.net.InetAddress;

/**
 * @author Tina Tian
 */
public interface ClusterChannel {

	public void close();

	public InetAddress getBindInetAddress();

	public String getClusterName();

	public ClusterReceiver getClusterReceiver();

	public Address getLocalAddress();

	public void sendMulticastMessage(Serializable message);

	public void sendUnicastMessage(Serializable message, Address address);

}