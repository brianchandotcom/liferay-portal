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

package com.liferay.portal.kernel.cluster.messaging;

import com.liferay.portal.kernel.cluster.Address;
import com.liferay.portal.kernel.cluster.ClusterLinkUtil;
import com.liferay.portal.kernel.cluster.Priority;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Message;

/**
 * @author Shuyang Zhou
 */
public class ClusterBridgeMessageListener extends BaseMessageListener {

	public void setActive(boolean active) {
		_active = active;
	}

	public void setPriority(Priority priority) {
		_priority = priority;
	}

	@Override
	protected void doReceive(Message message) throws Exception {
		if (!_active) {
			return;
		}

		if (ClusterLinkUtil.isForwardMessage(message)) {
			return;
		}

		Address address = ClusterLinkUtil.getAddress(message);

		if (address == null) {
			if (_log.isInfoEnabled()) {
				_log.info("Bridging cluster link multicast message " + message);
			}

			ClusterLinkUtil.sendMulticastMessage(message, _priority);
		}
		else {
			if (_log.isInfoEnabled()) {
				_log.info(
					"Bridging cluster link unicast message " + message +
						" to " + address);
			}

			ClusterLinkUtil.sendUnicastMessage(address, message, _priority);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ClusterBridgeMessageListener.class);

	private boolean _active = true;
	private Priority _priority;

}