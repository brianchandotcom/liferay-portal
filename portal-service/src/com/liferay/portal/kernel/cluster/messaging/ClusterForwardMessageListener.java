/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.cluster.ClusterLinkUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Shuyang Zhou
 */
public class ClusterForwardMessageListener implements ClusterMessageListener {

	@Override
	public void receive(Message message) {
		String destinationName = message.getDestinationName();

		if (Validator.isNotNull(destinationName)) {
			if (_log.isInfoEnabled()) {
				_log.info(
					"Forwarding cluster link message " + message + " to " +
						destinationName);
			}

			ClusterLinkUtil.setForwardMessage(message);

			MessageBusUtil.sendMessage(destinationName, message);
		}
		else {
			if (_log.isErrorEnabled()) {
				_log.error(
					"Forwarded cluster link message has no destination " +
						message);
			}
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		ClusterForwardMessageListener.class);

}