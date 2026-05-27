/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.spring.boot.client.pubsub.publisher;

import com.liferay.osb.spring.boot.client.pubsub.Message;
import com.liferay.osb.spring.boot.client.pubsub.broker.MessageBroker;

import org.apache.commons.lang3.RandomStringUtils;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Kyle Bischof
 */
public class BaseMessagePublisherTest {

	@Test
	public void testCustomHandleErrorOverride() throws Exception {
		String errorMessage = RandomStringUtils.randomAlphanumeric(12);

		HandleErrorPublisher publisher = new HandleErrorPublisher();

		publisher.messageBroker = new ThrowingBroker(errorMessage);

		Message message = new Message(RandomStringUtils.randomAlphanumeric(12));

		publisher.publish(
			"topic-" + RandomStringUtils.randomAlphabetic(6), message);

		Assert.assertEquals(1, publisher.getHandledCount());
	}

	@Test
	public void testHandleErrorRethrowsByDefault() {
		String errorMessage = RandomStringUtils.randomAlphanumeric(16);

		TestPublisher publisher = new TestPublisher();

		publisher.messageBroker = new ThrowingBroker(errorMessage);

		Message message = new Message(RandomStringUtils.randomAlphanumeric(12));

		Exception exception = Assert.assertThrows(
			Exception.class,
			() -> publisher.publish(
				"topic-" + RandomStringUtils.randomAlphabetic(6), message));

		Assert.assertEquals(errorMessage, exception.getMessage());
	}

	@Test
	public void testPublishDelegatesToBroker() throws Exception {
		String topic = "topic-" + RandomStringUtils.randomAlphabetic(8);

		RecordingBroker broker = new RecordingBroker();

		TestPublisher publisher = new TestPublisher();

		publisher.messageBroker = broker;

		Message message = new Message(RandomStringUtils.randomAlphanumeric(12));

		publisher.publish(topic, message);

		Assert.assertEquals(1, broker.getPublishCount());
		Assert.assertEquals(topic, broker.getLastTopic());
		Assert.assertSame(message, broker.getLastMessage());
	}

	private static class HandleErrorPublisher extends BaseMessagePublisher {

		public int getHandledCount() {
			return _handledCount;
		}

		@Override
		protected void handleError(
			String topic, Message message, Exception exception) {

			_handledCount++;
		}

		private int _handledCount;

	}

	private static class RecordingBroker implements MessageBroker {

		public Message getLastMessage() {
			return _lastMessage;
		}

		public String getLastTopic() {
			return _lastTopic;
		}

		public int getPublishCount() {
			return _publishCount;
		}

		@Override
		public void publish(String topic, Message message) {
			_publishCount++;
			_lastTopic = topic;
			_lastMessage = message;
		}

		private Message _lastMessage;
		private String _lastTopic;
		private int _publishCount;

	}

	private static class TestPublisher extends BaseMessagePublisher {
	}

	private static class ThrowingBroker implements MessageBroker {

		public ThrowingBroker(String exceptionMessage) {
			_exceptionMessage = exceptionMessage;
		}

		@Override
		public void publish(String topic, Message message) throws Exception {
			throw new Exception(_exceptionMessage);
		}

		private final String _exceptionMessage;

	}

}