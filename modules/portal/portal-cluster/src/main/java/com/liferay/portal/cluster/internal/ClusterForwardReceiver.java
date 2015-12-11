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

package com.liferay.portal.cluster.internal;

import com.liferay.portal.kernel.cluster.Address;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;

import java.util.List;

/**
 * @author Shuyang Zhou
 */
public class ClusterForwardReceiver extends BaseClusterReceiver {

	public ClusterForwardReceiver(ClusterLinkImpl clusterLinkImpl) {
		super(clusterLinkImpl.getExecutorService());

		_clusterLinkImpl = clusterLinkImpl;
	}

	@Override
	protected void doReceive(Object messagePayload, Address srcAddress) {
		List<Address> localAddresses = _clusterLinkImpl.getLocalAddresses();

		if (localAddresses.contains(srcAddress)) {
			if (_log.isDebugEnabled()) {
				_log.debug("Block received message " + messagePayload);
			}
		}
		else {
			_clusterLinkImpl.sendLocalMessage((Message)messagePayload);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ClusterForwardReceiver.class);

	private final ClusterLinkImpl _clusterLinkImpl;

}