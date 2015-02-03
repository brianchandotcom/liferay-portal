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

package com.liferay.portal.kernel.cluster;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.net.InetAddress;
import java.net.InetSocketAddress;

/**
 * @author Tina Tian
 */
@ProviderType
public class ClusterNode implements Serializable {

	public ClusterNode(String clusterNodeId, InetAddress bindInetAddress) {
		if (clusterNodeId == null) {
			throw new IllegalArgumentException("Cluster node ID is null");
		}

		if (bindInetAddress == null) {
			throw new IllegalArgumentException("Bind inet address is null");
		}

		_clusterNodeId = clusterNodeId;
		_bindInetAddress = bindInetAddress;
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

		if (Validator.equals(_clusterNodeId, clusterNode._clusterNodeId) &&
			Validator.equals(_bindInetAddress, clusterNode._bindInetAddress) &&
			Validator.equals(
				_portalInetSocketAddress,
				clusterNode._portalInetSocketAddress) &&
			Validator.equals(_portalProtocol, clusterNode._portalProtocol)) {

			return true;
		}

		return false;
	}

	public InetAddress getBindInetAddress() {
		return _bindInetAddress;
	}

	public String getClusterNodeId() {
		return _clusterNodeId;
	}

	public InetAddress getPortalInetAddress() {
		if (_portalInetSocketAddress == null) {
			return null;
		}

		return _portalInetSocketAddress.getAddress();
	}

	public InetSocketAddress getPortalInetSocketAddress() {
		return _portalInetSocketAddress;
	}

	public int getPortalPort() {
		if (_portalInetSocketAddress == null) {
			return -1;
		}

		return _portalInetSocketAddress.getPort();
	}

	public String getPortalProtocol() {
		return _portalProtocol;
	}

	@Override
	public int hashCode() {
		int hash = HashUtil.hash(0, _clusterNodeId);

		hash = HashUtil.hash(hash, _bindInetAddress);
		hash = HashUtil.hash(hash, _portalInetSocketAddress);
		hash = HashUtil.hash(hash, _portalProtocol);

		return hash;
	}

	public void setPortalInetSocketAddress(
		InetSocketAddress portalInetSocketAddress) {

		_portalInetSocketAddress = portalInetSocketAddress;
	}

	public void setPortalProtocol(String portalProtocol) {
		_portalProtocol = portalProtocol;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(9);

		sb.append("{bindInetAddress=");
		sb.append(_bindInetAddress);
		sb.append(", clusterNodeId=");
		sb.append(_clusterNodeId);
		sb.append(", portalInetSocketAddress=");
		sb.append(_portalInetSocketAddress);
		sb.append(", portalProtocol=");
		sb.append(_portalProtocol);
		sb.append("}");

		return sb.toString();
	}

	private final InetAddress _bindInetAddress;
	private final String _clusterNodeId;
	private InetSocketAddress _portalInetSocketAddress;
	private String _portalProtocol;

}