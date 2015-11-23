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

import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.kernel.util.StringBundler;

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

		if ((result != null) && !(result instanceof Serializable)) {
			return new ClusterNodeResponse(
				clusterNode, uuid, null,
				new ClusterException("Return value is not serializable"));
		}

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
		else {
			return false;
		}
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(9);

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
			throw new NullPointerException("ClusterNode is null");
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