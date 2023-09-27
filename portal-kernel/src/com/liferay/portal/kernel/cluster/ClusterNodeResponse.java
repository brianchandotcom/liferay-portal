/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.cluster;

import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;

import java.io.Serializable;

/**
 * @author Tina Tian
 */
public class ClusterNodeResponse implements Serializable {

	public static ClusterNodeResponse createExceptionClusterNodeResponse(
		ClusterNode clusterNode, String uuid, Exception exception) {

		return new ClusterNodeResponse(clusterNode, uuid, null, exception);
	}

	public static ClusterNodeResponse createResultClusterNodeResponse(
		ClusterNode clusterNode, String uuid, Serializable result) {

		return new ClusterNodeResponse(clusterNode, uuid, result, null);
	}

	public ClusterNode getClusterNode() {
		return _clusterNode;
	}

	public Exception getException() {
		return _exception;
	}

	public Serializable getResult() {
		if (_exception != null) {
			return ReflectionUtil.throwException(_exception);
		}

		return _result;
	}

	public String getUuid() {
		return _uuid;
	}

	public boolean hasException() {
		if (_exception != null) {
			return true;
		}

		return false;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(7);

		sb.append("{clusterNode=");
		sb.append(_clusterNode);

		if (hasException()) {
			sb.append(", exception=");
			sb.append(_exception);
		}
		else {
			sb.append(", result=");
			sb.append(_result);
		}

		sb.append(", uuid=");
		sb.append(_uuid);
		sb.append("}");

		return sb.toString();
	}

	private ClusterNodeResponse(
		ClusterNode clusterNode, String uuid, Serializable result,
		Exception exception) {

		if (clusterNode == null) {
			throw new NullPointerException("Cluster node is null");
		}

		_clusterNode = clusterNode;
		_uuid = uuid;
		_result = result;
		_exception = exception;
	}

	private final ClusterNode _clusterNode;
	private final Exception _exception;
	private final Serializable _result;
	private final String _uuid;

}