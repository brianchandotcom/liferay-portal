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

import com.liferay.portal.kernel.cluster.ClusterInvokeThreadLocal;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;
import java.util.concurrent.ExecutorService;

import org.jgroups.Address;

/**
 * @author Shuyang Zhou
 */
public class ClusterForwardReceiver extends BaseReceiver {

	public ClusterForwardReceiver(
		ExecutorService executorService,
		List<Address> localTransportAddresses) {

		super(executorService);
		_localTransportAddresses = localTransportAddresses;
	}

	@Override
	protected void doReceive(org.jgroups.Message jGroupsMessage) {
		if (!_localTransportAddresses.contains(jGroupsMessage.getSrc()) ||
			(jGroupsMessage.getDest() != null)) {

			Message message = (Message)jGroupsMessage.getObject();

			String destinationName = message.getDestinationName();

			if (Validator.isNotNull(destinationName)) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Forwarding cluster link message " + message + " to " +
							destinationName);
				}

				ClusterInvokeThreadLocal.setEnabled(false);

				try {
					MessageBusUtil.sendMessage(destinationName, message);
				}
				finally {
					ClusterInvokeThreadLocal.setEnabled(true);
				}
			}
			else {
				if (_log.isErrorEnabled()) {
					_log.error(
						"Forwarded cluster link message has no destination " +
							message);
				}
			}
		}
		else {
			if (_log.isDebugEnabled()) {
				_log.debug("Block received message " + jGroupsMessage);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ClusterForwardReceiver.class);

	private final List<Address> _localTransportAddresses;

}