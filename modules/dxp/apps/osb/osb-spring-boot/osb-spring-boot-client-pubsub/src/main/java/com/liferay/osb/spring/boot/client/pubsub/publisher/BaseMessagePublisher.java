/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.distributed.messaging.publishing;

import com.liferay.osb.distributed.messaging.Message;
import com.liferay.osb.distributed.messaging.model.QueuedMessage;
import com.liferay.osb.distributed.messaging.publishing.broker.MessageBroker;
import com.liferay.osb.distributed.messaging.service.QueuedMessageLocalService;
import com.liferay.osgi.util.StringPlus;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Amos Fong
 */
public abstract class BaseMessagePublisher implements MessagePublisher {

	public synchronized void flushQueuedMessages() throws Exception {
		Set<MessageBroker> messageBrokers = _messageBrokersMap.keySet();

		for (MessageBroker messageBroker : messageBrokers) {
			flushQueueMessages(messageBroker);
		}
	}

	public synchronized void publish(String topic, Message message)
		throws Exception {

		List<MessageBroker> messageBrokers = getMessageBrokers(topic);

		for (MessageBroker messageBroker : messageBrokers) {
			try {
				flushQueueMessages(messageBroker);

				if (_log.isDebugEnabled()) {
					Class<?> messageBrokerClass = messageBroker.getClass();

					_log.debug(
						StringBundler.concat(
							"Publishing message for topic ", topic, " to ",
							messageBrokerClass.getName()));

					_log.debug("Message: " + message.toString());
				}

				messageBroker.publish(topic, message);
			}
			catch (Exception exception) {
				handleError(messageBroker, topic, message, exception);
			}
		}

		if (_log.isDebugEnabled() && messageBrokers.isEmpty()) {
			_log.debug("No brokers were found for topic " + topic);
		}
	}

	protected void addMessageBroker(
		MessageBroker messageBroker, Map<String, Object> properties) {

		List<String> publishingTopicPatterns = StringPlus.asList(
			properties.get("publishing.topic.pattern"));

		if (publishingTopicPatterns.isEmpty()) {
			_log.error("Publishing topic patterns are empty");
		}

		_messageBrokersMap.put(messageBroker, publishingTopicPatterns);

		_cachedMessageBrokersMap.clear();
	}

	protected void flushQueueMessages(MessageBroker messageBroker)
		throws Exception {

		Class<?> messageBrokerClass = messageBroker.getClass();

		List<QueuedMessage> queuedMessages =
			queuedMessageLocalService.getQueuedMessages(
				messageBrokerClass.getName());

		for (QueuedMessage queuedMessage : queuedMessages) {
			Message message = queuedMessage.getMessage();

			if (_log.isDebugEnabled()) {
				_log.debug(
					StringBundler.concat(
						"Flushing queued message for topic ",
						queuedMessage.getTopic(), " to ",
						messageBrokerClass.getName()));

				_log.debug("Message: " + message.toString());
			}

			messageBroker.publish(queuedMessage.getTopic(), message);

			queuedMessageLocalService.deleteQueuedMessage(
				queuedMessage.getQueuedMessageId());
		}
	}

	protected List<MessageBroker> getMessageBrokers(String topic) {
		List<MessageBroker> messageBrokers = _cachedMessageBrokersMap.get(
			topic);

		if (messageBrokers != null) {
			return messageBrokers;
		}

		messageBrokers = new ArrayList<>();

		for (Map.Entry<MessageBroker, List<String>> entry :
				_messageBrokersMap.entrySet()) {

			for (String topicPattern : entry.getValue()) {
				if (topic.matches(topicPattern)) {
					messageBrokers.add(entry.getKey());

					break;
				}
			}
		}

		_cachedMessageBrokersMap.put(topic, messageBrokers);

		return messageBrokers;
	}

	protected void handleError(
			MessageBroker messageBroker, String topic, Message message,
			Exception exception)
		throws PortalException {

		_log.error(
			"Error publishing message. Queueing message to retry later.",
			exception);

		Class<?> messageBrokerClass = messageBroker.getClass();

		queuedMessageLocalService.addQueuedMessage(
			messageBrokerClass.getName(), topic, message);
	}

	@Reference
	protected QueuedMessageLocalService queuedMessageLocalService;

	private static final Log _log = LogFactoryUtil.getLog(
		BaseMessagePublisher.class);

	private final Map<String, List<MessageBroker>> _cachedMessageBrokersMap =
		new ConcurrentHashMap<>();
	private final Map<MessageBroker, List<String>> _messageBrokersMap =
		new ConcurrentHashMap<>();

}