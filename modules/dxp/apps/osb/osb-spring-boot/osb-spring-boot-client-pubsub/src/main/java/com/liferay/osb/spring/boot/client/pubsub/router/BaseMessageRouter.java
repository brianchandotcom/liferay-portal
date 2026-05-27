/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.spring.boot.client.pubsub.router;

import com.liferay.osb.spring.boot.client.pubsub.Message;
import com.liferay.osb.spring.boot.client.pubsub.subscriber.MessageSubscriber;
import com.liferay.petra.string.StringBundler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Amos Fong
 * @author Kyle Bischof
 */
public class BaseMessageRouter implements MessageRouter {

	public void addRoute(
		MessageSubscriber messageSubscriber, List<String> topicPatterns) {

		if ((topicPatterns == null) || topicPatterns.isEmpty()) {
			_log.error("Topic patterns are empty");

			return;
		}

		_messageSubscriberMap.put(messageSubscriber, topicPatterns);

		_cachedMessageSubscriberMap.clear();
	}

	@Override
	public void route(String topic, Message message) {
		List<MessageSubscriber> messageSubscribers = getMessageSubscribers(
			topic);

		for (MessageSubscriber messageSubscriber : messageSubscribers) {
			if (_log.isDebugEnabled()) {
				Class<?> messageSubscriberClass = messageSubscriber.getClass();

				_log.debug(
					StringBundler.concat(
						"Routing ", topic, " to ",
						messageSubscriberClass.getName()));

				_log.debug("Message: " + message.toString());
			}

			messageSubscriber.receive(message);
		}

		if (_log.isDebugEnabled() && messageSubscribers.isEmpty()) {
			_log.debug("No subscribers were found for topic " + topic);
		}
	}

	protected List<MessageSubscriber> getMessageSubscribers(String topic) {
		List<MessageSubscriber> messageSubscribers =
			_cachedMessageSubscriberMap.get(topic);

		if (messageSubscribers != null) {
			return messageSubscribers;
		}

		messageSubscribers = new ArrayList<>();

		for (Map.Entry<MessageSubscriber, List<String>> entry :
				_messageSubscriberMap.entrySet()) {

			for (String topicPattern : entry.getValue()) {
				if (topic.matches(topicPattern)) {
					messageSubscribers.add(entry.getKey());

					break;
				}
			}
		}

		_cachedMessageSubscriberMap.put(topic, messageSubscribers);

		return messageSubscribers;
	}

	private static final Logger _log = LoggerFactory.getLogger(
		BaseMessageRouter.class);

	private final Map<String, List<MessageSubscriber>>
		_cachedMessageSubscriberMap = new ConcurrentHashMap<>();
	private final Map<MessageSubscriber, List<String>> _messageSubscriberMap =
		new ConcurrentHashMap<>();

}