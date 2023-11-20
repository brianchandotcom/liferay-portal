/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.messaging;

import com.liferay.portal.kernel.module.service.Snapshot;

/**
 * @author Michael C. Han
 * @author Raymond Augé
 */
public class MessageBusUtil {

	public static Destination getDestination(String destinationName) {
		MessageBus messageBus = _messageBusSnapshot.get();

		return messageBus.getDestination(destinationName);
	}

	public static MessageBus getMessageBus() {
		return _messageBusSnapshot.get();
	}

	public static void sendMessage(String destinationName, Message message) {
		MessageBus messageBus = _messageBusSnapshot.get();

		messageBus.sendMessage(destinationName, message);
	}

	public static void sendMessage(String destinationName, Object payload) {
		MessageBus messageBus = _messageBusSnapshot.get();

		Message message = new Message();

		message.setPayload(payload);

		messageBus.sendMessage(destinationName, message);
	}

	private static final Snapshot<MessageBus> _messageBusSnapshot =
		new Snapshot<>(MessageBusUtil.class, MessageBus.class);

}