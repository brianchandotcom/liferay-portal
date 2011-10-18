/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.cluster;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.net.InetAddress;

/**
 * @author Tina Tian
 */
public class ClusterNode implements Comparable<ClusterNode>, Serializable {

	public ClusterNode(String clusterNodeId) {
		_clusterNodeId = clusterNodeId;
	}

	public int compareTo(ClusterNode clusterNode) {
		InetAddress inetAddress = clusterNode.getInetAddress();

		String ipAddress = inetAddress.getHostAddress();

		String curIpAddress = _inetAddress.getHostAddress();

		return curIpAddress.compareTo(ipAddress);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof ClusterNode)) {
			return false;
		}

		ClusterNode clusterNode = (ClusterNode)obj;

		if (Validator.equals(_clusterNodeId, clusterNode._clusterNodeId)) {
			return true;
		}

		return false;
	}

	public String getClusterNodeId() {
		return _clusterNodeId;
	}

	public String getHostName() {
		return _hostName;
	}

	public InetAddress getInetAddress() {
		return _inetAddress;
	}

	public int getPort() {
		return _port;
	}

	@Override
	public int hashCode() {
		return _clusterNodeId.hashCode();
	}

	public void setHostName(String hostName) {
		_hostName = hostName;
	}

	public void setInetAddress(InetAddress inetAddress) {
		_inetAddress = inetAddress;
	}

	public void setPort(int port) {
		_port = port;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(9);

		sb.append("{clusterNodeId=");
		sb.append(_clusterNodeId);
		sb.append(", hostName=");
		sb.append(_hostName);
		sb.append(", inetAddress=");
		sb.append(_inetAddress);
		sb.append(", port=");
		sb.append(_port);
		sb.append("}");

		return sb.toString();
	}

	private String _clusterNodeId;
	private String _hostName;
	private InetAddress _inetAddress;
	private int _port;

}