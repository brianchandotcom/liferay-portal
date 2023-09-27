/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.cluster;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.security.SecureRandomUtil;

import java.io.Serializable;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

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

			throw new NullPointerException("Target cluster node IDs is null");
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
			throw new NullPointerException("Payload is null");
		}

		_payload = payload;
		_skipLocal = skipLocal;
		_multicast = multicast;

		_uuid = _generateUUID();

		for (String targetClusterNodeId : targetClusterNodeIds) {
			_targetClusterNodeIds.add(targetClusterNodeId);
		}
	}

	private String _generateUUID() {
		UUID uuid = new UUID(
			SecureRandomUtil.nextLong(), SecureRandomUtil.nextLong());

		return uuid.toString();
	}

	private boolean _fireAndForget;
	private final boolean _multicast;
	private final Serializable _payload;
	private final boolean _skipLocal;
	private final Set<String> _targetClusterNodeIds = new HashSet<>();
	private final String _uuid;

}