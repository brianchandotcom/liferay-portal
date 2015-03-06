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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;

import java.util.List;
import java.util.concurrent.ExecutorService;

import org.jgroups.Address;

/**
 * @author Shuyang Zhou
 */
public class ClusterForwardReceiver extends BaseReceiver {

	public ClusterForwardReceiver(
		ClusterLinkImpl clusterLinkImpl, ExecutorService executorService,
		List<Address> localTransportAddresses) {

		super(executorService);

		_clusterLinkImpl = clusterLinkImpl;
		_localTransportAddresses = localTransportAddresses;
	}

	@Override
	protected void doReceive(org.jgroups.Message jGroupsMessage) {
		if (!_localTransportAddresses.contains(jGroupsMessage.getSrc())) {
			Message message = (Message)jGroupsMessage.getObject();

			_clusterLinkImpl.forwardMessage(message);
		}
		else {
			if (_log.isDebugEnabled()) {
				_log.debug("Block received message " + jGroupsMessage);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ClusterForwardReceiver.class);

	private final ClusterLinkImpl _clusterLinkImpl;
	private final List<Address> _localTransportAddresses;

}