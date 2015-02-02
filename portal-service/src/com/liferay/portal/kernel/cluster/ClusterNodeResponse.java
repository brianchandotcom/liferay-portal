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

import com.liferay.portal.kernel.util.StringBundler;

import java.io.Serializable;

/**
 * @author Tina Tian
 */
public class ClusterNodeResponse implements Serializable {

	public ClusterMessageType getClusterMessageType() {
		return _clusterMessageType;
	}

	public ClusterNode getClusterNode() {
		return _clusterNode;
	}

	public Exception getException() {
		return _exception;
	}

	public Object getResult() throws Exception {
		if (_exception != null) {
			throw _exception;
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

	public boolean isMulticast() {
		return _multicast;
	}

	public void setClusterMessageType(ClusterMessageType clusterMessageType) {
		_clusterMessageType = clusterMessageType;
	}

	public void setClusterNode(ClusterNode clusterNode) {
		_clusterNode = clusterNode;
	}

	public void setException(Exception exception) {
		_exception = exception;
	}

	public void setMulticast(boolean multicast) {
		_multicast = multicast;
	}

	public void setResult(Object result) {
		_result = result;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(9);

		sb.append("{clusterMessageType=");
		sb.append(_clusterMessageType);

		boolean clusterMessageTypeNotifyOrUpdate = false;

		if (_clusterMessageType.equals(ClusterMessageType.NOTIFY) ||
			_clusterMessageType.equals(ClusterMessageType.UPDATE)) {

			clusterMessageTypeNotifyOrUpdate = true;
		}

		if (clusterMessageTypeNotifyOrUpdate) {
			sb.append(", clusterNode=");
			sb.append(_clusterNode);
		}

		if (!clusterMessageTypeNotifyOrUpdate && hasException()) {
			sb.append(", exception=");
			sb.append(_exception);
		}

		sb.append(", multicast=");
		sb.append(_multicast);

		if (!clusterMessageTypeNotifyOrUpdate && !hasException()) {
			sb.append(", result=");
			sb.append(_result);
		}

		sb.append(", uuid=");
		sb.append(_uuid);
		sb.append("}");

		return sb.toString();
	}

	private ClusterMessageType _clusterMessageType;
	private ClusterNode _clusterNode;
	private Exception _exception;
	private boolean _multicast;
	private Object _result;
	private String _uuid;

}