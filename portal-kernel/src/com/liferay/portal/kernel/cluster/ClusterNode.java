/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.cluster;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;

import java.io.Serializable;

import java.net.InetAddress;
import java.net.InetSocketAddress;

import java.util.Objects;

/**
 * @author Tina Tian
 */
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
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ClusterNode)) {
			return false;
		}

		ClusterNode clusterNode = (ClusterNode)object;

		if (Objects.equals(_clusterNodeId, clusterNode._clusterNodeId) &&
			Objects.equals(_bindInetAddress, clusterNode._bindInetAddress) &&
			Objects.equals(
				_portalInetSocketAddress,
				clusterNode._portalInetSocketAddress) &&
			Objects.equals(_portalProtocol, clusterNode._portalProtocol)) {

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
		return StringBundler.concat(
			"{bindInetAddress=", _bindInetAddress, ", clusterNodeId=",
			_clusterNodeId, ", portalInetSocketAddress=",
			_portalInetSocketAddress, ", portalProtocol=", _portalProtocol,
			"}");
	}

	private final InetAddress _bindInetAddress;
	private final String _clusterNodeId;
	private InetSocketAddress _portalInetSocketAddress;
	private String _portalProtocol;

}