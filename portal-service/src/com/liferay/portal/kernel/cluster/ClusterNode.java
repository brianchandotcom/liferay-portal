/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

	public ClusterNode(String clusterNodeId, InetAddress inetAddress) {
		if (clusterNodeId == null) {
			throw new IllegalArgumentException("Cluster node ID is null");
		}

		if (inetAddress == null) {
			throw new IllegalArgumentException("Inet address is null");
		}

		_clusterNodeId = clusterNodeId;
		_inetAddress = inetAddress;
	}

	@Override
	public int compareTo(ClusterNode clusterNode) {
		InetAddress inetAddress = clusterNode._inetAddress;

		String hostAddress = _inetAddress.getHostAddress();

		int value = hostAddress.compareTo(inetAddress.getHostAddress());

		if (value != 0) {
			return value;
		}

		if ((_portalProtocol == null) ||
			(clusterNode._portalProtocol == null)) {

			if (_portalProtocol != null) {
				return 1;
			}

			if (clusterNode._portalProtocol != null) {
				return -1;
			}

			return 0;
		}

		value = _portalProtocol.compareTo(clusterNode._portalProtocol);

		if (value != 0) {
			return value;
		}

		if (_port > clusterNode._port) {
			value = 1;
		}
		else if (_port < clusterNode._port) {
			value = -1;
		}

		return value;
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

	public InetAddress getInetAddress() {
		return _inetAddress;
	}

	public int getPort() {
		return _port;
	}

	public String getPortalProtocol() {
		return _portalProtocol;
	}

	@Override
	public int hashCode() {
		return _clusterNodeId.hashCode();
	}

	public void setPort(int port) {
		_port = port;
	}

	public void setPortalProtocol(String portalProtocol) {
		_portalProtocol = portalProtocol;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(9);

		sb.append("{clusterNodeId=");
		sb.append(_clusterNodeId);
		sb.append(", portalProtocol=");
		sb.append(_portalProtocol);
		sb.append(", inetAddress=");
		sb.append(_inetAddress);
		sb.append(", port=");
		sb.append(_port);
		sb.append("}");

		return sb.toString();
	}

	private String _clusterNodeId;
	private InetAddress _inetAddress;
	private int _port;
	private String _portalProtocol;

}