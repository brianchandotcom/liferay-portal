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

package com.liferay.portal.cluster;

import com.liferay.portal.kernel.cluster.messaging.ClusterForwardMessageListener;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.List;

import org.jgroups.Address;
import org.jgroups.Message;

/**
 * @author Shuyang Zhou
 */
public class ClusterForwardReceiver extends BaseReceiver {

	public ClusterForwardReceiver(
		List<Address> localTransportAddresses,
		ClusterForwardMessageListener clusterForwardMessageListener) {

		_localTransportAddresses = localTransportAddresses;
		_clusterForwardMessageListener = clusterForwardMessageListener;
	}

	@Override
	public void receive(Message message) {
		if (!_localTransportAddresses.contains(message.getSrc()) ||
			(message.getDest() != null)) {

			_clusterForwardMessageListener.receive(
				(com.liferay.portal.kernel.messaging.Message)
					message.getObject());
		}
		else {
			if (_log.isDebugEnabled()) {
				_log.debug("Block received message " + message);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ClusterForwardReceiver.class);

	private final ClusterForwardMessageListener _clusterForwardMessageListener;
	private final List<org.jgroups.Address> _localTransportAddresses;

}