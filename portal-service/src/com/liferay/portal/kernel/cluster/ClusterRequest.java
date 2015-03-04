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
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import java.io.Serializable;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Tina Tian
 */
public class ClusterRequest implements Serializable {

	public static ClusterRequest createMulticastRequest(Serializable payload) {
		return createMulticastRequest(payload, false);
	}

	public static ClusterRequest createMulticastRequest(
		Serializable payload, boolean skipLocal) {

		return new ClusterRequest(payload, skipLocal, true);
	}

	public static ClusterRequest createUnicastRequest(
		Serializable payload, String... targetClusterNodeIds) {

		if ((targetClusterNodeIds == null) ||
			(targetClusterNodeIds.length == 0)) {

			throw new NullPointerException("targetClusterNodeIds is null");
		}

		return new ClusterRequest(payload, false, false, targetClusterNodeIds);
	}

	public Serializable getPayload() {
		return _payload;
	}

	public Set<String> getTargetClusterNodeIds() {
		return Collections.unmodifiableSet(_targetClusterNodeIds);
	}

	public String getUuid() {
		return _uuid;
	}

	public boolean isFireAndForget() {
		return _fireAndForget;
	}

	public boolean isMulticast() {
		return _multicast;
	}

	public boolean isSkipLocal() {
		return _skipLocal;
	}

	public void setFireAndForget(boolean fireAndForget) {
		_fireAndForget = fireAndForget;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(11);

		sb.append("{multicast=");
		sb.append(_multicast);
		sb.append(", payload=");
		sb.append(_payload);
		sb.append(", skipLocal=");
		sb.append(_skipLocal);

		if (!_multicast) {
			sb.append(", _targetClusterNodeIds=");
			sb.append(_targetClusterNodeIds);
		}

		sb.append(", uuid=");
		sb.append(_uuid);
		sb.append("}");

		return sb.toString();
	}

	private ClusterRequest(
		Serializable payload, boolean skipLocal, boolean multicast,
		String... targetClusterNodeIds) {

		if (payload == null) {
			throw new NullPointerException("payload is null");
		}

		_payload = payload;
		_skipLocal = skipLocal;
		_multicast = multicast;

		_uuid = PortalUUIDUtil.generate();

		for (String targetClusterNodeId : targetClusterNodeIds) {
			_targetClusterNodeIds.add(targetClusterNodeId);
		}
	}

	private boolean _fireAndForget;
	private final boolean _multicast;
	private final Serializable _payload;
	private final boolean _skipLocal;
	private final Set<String> _targetClusterNodeIds = new HashSet<>();
	private final String _uuid;

}