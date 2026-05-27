/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.spring.boot.client.pubsub.subscriber.internal;

import com.google.cloud.pubsub.v1.AckReplyConsumer;
import com.google.cloud.pubsub.v1.MessageReceiver;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.PubsubMessage;

import com.liferay.osb.spring.boot.client.pubsub.Message;
import com.liferay.osb.spring.boot.client.pubsub.router.MessageRouter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Amos Fong
 * @author Kyle Bischof
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

			message.setStringAttributes(pubsubMessage.getAttributesMap());
			message.setTopic(_topic);

			_messageRouter.route(_topic, message);

			ackReplyConsumer.ack();
		}
		catch (Exception exception) {
			_log.error(String.valueOf(exception), exception);

			ackReplyConsumer.nack();
		}
	}

	private static final Logger _log = LoggerFactory.getLogger(
		DefaultMessageReceiver.class);

	private final MessageRouter _messageRouter;
	private final String _topic;

}