/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.distributed.messaging.google.pubsub.connector;

import com.google.cloud.pubsub.v1.AckReplyConsumer;
import com.google.cloud.pubsub.v1.MessageReceiver;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.PubsubMessage;

import com.liferay.osb.distributed.messaging.Message;
import com.liferay.osb.distributed.messaging.subscribing.router.MessageRouter;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

/**
 * @author Amos Fong
 */
public class DefaultMessageReceiver implements MessageReceiver {

	public DefaultMessageReceiver(String topic, MessageRouter messageRouter) {
		_topic = topic;
		_messageRouter = messageRouter;
	}

	@Override
	public void receiveMessage(
		PubsubMessage pubsubMessage, AckReplyConsumer ackReplyConsumer) {

		ByteString byteString = pubsubMessage.getData();

		String messageBody = byteString.toStringUtf8();

		if (_log.isDebugEnabled()) {
			_log.debug("Received message " + messageBody);
		}

		try {
			Message message = new Message(messageBody);

			message.setStringAttributes(pubsubMessage.getAttributes());
			message.setTopic(_topic);

			_messageRouter.route(_topic, message);

			ackReplyConsumer.ack();
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			ackReplyConsumer.nack();
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DefaultMessageReceiver.class);

	private final MessageRouter _messageRouter;
	private final String _topic;

}